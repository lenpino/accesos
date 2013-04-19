package cl.altair.accesos.wizard.UsuarioInmueble;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.StyledText;
import cl.altair.utiles.generales.Validador;

public class WUIPaginaBasica extends WizardPage {
  private StyledText rut;
  private Composite container;
  private StyledText dv;
  private StyledText nombre;
  private Group group;
  private Button btnPersona;
  private Button btnEmpresa;
  private String seleccion;
  private boolean flag = false;

  public WUIPaginaBasica() {
    super("Primer paso");
    setTitle("Primer paso");
    setDescription("Datos b\u00E1sicos del usuario");
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    container.setLayout(new FormLayout());
    Label label1 = new Label(container, SWT.NULL);
    label1.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    FormData fd_label1 = new FormData();
    fd_label1.left = new FormAttachment(0, 14);
    label1.setLayoutData(fd_label1);
    label1.setText("RUT:");

    rut = new StyledText(container, SWT.BORDER | SWT.SINGLE);
    FormData fd_rut = new FormData();
    fd_rut.top = new FormAttachment(label1, 1, SWT.TOP);
    rut.setLayoutData(fd_rut);
    rut.setText("");
    rut.addListener(SWT.Verify, new Listener() {
    	public void handleEvent(Event e) {
    		e.doit = Validador.isNumber(e.text);
            return;
        }
    });
    // Required to avoid an error in the system
    setControl(container);
    
    dv = new StyledText(container, SWT.BORDER | SWT.SINGLE);
    fd_rut.right = new FormAttachment(100, -396);
    FormData fd_dv = new FormData();
    fd_dv.top = new FormAttachment(label1, 1, SWT.TOP);
    fd_dv.left = new FormAttachment(rut, 6);
    fd_dv.right = new FormAttachment(100, -367);
    dv.setLayoutData(fd_dv);
    dv.setTextLimit(1);
    dv.addListener(SWT.Verify, new Listener() {
    	public void handleEvent(Event e) {
    		e.doit = Validador.isDV(e.text);
            return;
        }
    });

    Label lblNombre = new Label(container, SWT.NONE);
    fd_label1.bottom = new FormAttachment(lblNombre, -20);
    lblNombre.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    FormData fd_lblNombre = new FormData();
    fd_lblNombre.top = new FormAttachment(0, 63);
    fd_lblNombre.left = new FormAttachment(label1, 0, SWT.LEFT);
    lblNombre.setLayoutData(fd_lblNombre);
    lblNombre.setText("Nombre:");
    
    nombre = new StyledText(container, SWT.BORDER | SWT.SINGLE);
    fd_rut.left = new FormAttachment(nombre, 0, SWT.LEFT);
    FormData fd_nombre = new FormData();
    fd_nombre.right = new FormAttachment(100, -367);
    fd_nombre.left = new FormAttachment(lblNombre, 5);
    fd_nombre.top = new FormAttachment(lblNombre, 1, SWT.TOP);
    nombre.setLayoutData(fd_nombre);
    
    group = new Group(container, SWT.NONE);
    group.setText("Tipo Usuario");
    group.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    group.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
    FormData fd_group = new FormData();
    fd_group.top = new FormAttachment(lblNombre, 24);
    fd_group.bottom = new FormAttachment(100, -92);
    fd_group.left = new FormAttachment(0, 10);
    fd_group.right = new FormAttachment(100, -14);
    group.setLayoutData(fd_group);
    
    btnPersona = new Button(group, SWT.RADIO);
    btnPersona.setText("Persona");
    btnPersona.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    btnPersona.setBounds(10, 10, 91, 18);
    btnPersona.addSelectionListener(new SelectionListener(){
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
		}
		@Override
		public void widgetSelected(SelectionEvent arg0) {
            boolean isSelected = ((Button)arg0.getSource()).getSelection();
            if(isSelected){     
            	System.out.println("Persona");
                if (!rut.getText().isEmpty() && !dv.getText().isEmpty() && !nombre.getText().isEmpty() && flag == true) {
                    seleccion = "persona";         
                    setDatosUsuario(seleccion);
                    setPageComplete(true);
                }
            }  	
		}
    });
    
    btnEmpresa = new Button(group, SWT.RADIO);
    btnEmpresa.setText("Empresa");
    btnEmpresa.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    btnEmpresa.setBounds(10, 44, 91, 18);
    
    final Label lblErrorRut = new Label(container, SWT.NONE);
    lblErrorRut.setFont(SWTResourceManager.getFont("Lucida Grande", 14, SWT.NORMAL));
    lblErrorRut.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    FormData fd_lblErrorRut = new FormData();
    fd_lblErrorRut.bottom = new FormAttachment(rut, 0, SWT.BOTTOM);
    fd_lblErrorRut.left = new FormAttachment(dv, 72);
    lblErrorRut.setLayoutData(fd_lblErrorRut);
    lblErrorRut.setText("RUT INCORRECTO");
    lblErrorRut.setVisible(false);
    
    btnEmpresa.addSelectionListener(new SelectionListener(){
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
		}
		@Override
		public void widgetSelected(SelectionEvent arg0) {
            boolean isSelected = ((Button)arg0.getSource()).getSelection();
            if(isSelected){     
                if (!rut.getText().isEmpty() && !dv.getText().isEmpty() && !nombre.getText().isEmpty() && flag == true) {
                    seleccion = "empresa";
                    setDatosUsuario(seleccion);
                    setPageComplete(true);     
                }
            }  	
		}
    }); 
    
    nombre.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		if(rut.getText().isEmpty() || dv.getText().isEmpty()){
    			lblErrorRut.setVisible(true);
    			flag = false;
    		} else {
    			if(!Validador.isRUT(Integer.parseInt(rut.getText()), dv.getText().charAt(0)))
    				lblErrorRut.setVisible(true);
    			else
    				flag = true;
    		}
            return;
        }
    });

    rut.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		lblErrorRut.setVisible(false);
            return;
        }
    });

    dv.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		lblErrorRut.setVisible(false);
            return;
        }
    });

    setPageComplete(false);
  }

  public IWizardPage getNextPage() {
	    // Si se selecciona la opcion de persona se va a la pagina de personas
	    if (seleccion.equalsIgnoreCase("persona")) {
	    	return getWizard().getPage("Persona"); 
	    }
	    return getWizard().getPage("Empresa");
  }

  public String getText1() {
    return rut.getText();
  }
  private void setDatosUsuario(String tipo){
      ((WizardUsuarioInmueble)getWizard()).getElusuario().setRut(new Integer(rut.getText()));
      ((WizardUsuarioInmueble)getWizard()).getElusuario().setDv(dv.getText());
      ((WizardUsuarioInmueble)getWizard()).getElusuario().setNombre(nombre.getText());
      ((WizardUsuarioInmueble)getWizard()).getElusuario().setTipoUsuario(tipo);
  }
} 