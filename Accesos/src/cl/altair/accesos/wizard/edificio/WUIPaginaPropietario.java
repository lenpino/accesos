package cl.altair.accesos.wizard.edificio;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;

import cl.altair.utiles.generales.Validador;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.StyledText;

public class WUIPaginaPropietario extends WizardPage {
  private Composite container;
  private Group grpComunidad;
  private Label razon;
  private Label nombre;
  private Text larazon;
  private Label telefono;
  private Text elnombre;
  private Text eltelefono;
  private Label label;
  private StyledText rut;
  private StyledText dv;
  private boolean flag = false;
  private Label lblErrorRut;

  public WUIPaginaPropietario() {
    super("Empresa");
    setTitle("Segundo Paso");
    setDescription("Complete los datos de la empresa propietaria (Comunidad)");
    setPageComplete(false);
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    container.setLayout(new FormLayout());
    // Required to avoid an error in the system
    setControl(container);
    
    grpComunidad = new Group(container, SWT.NONE);
    grpComunidad.setText("Comunidad");
    grpComunidad.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    grpComunidad.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
    GridLayout gl_grpComunidad = new GridLayout(5, false);
    gl_grpComunidad.verticalSpacing = 10;
    gl_grpComunidad.marginTop = 20;
    grpComunidad.setLayout(gl_grpComunidad);
    FormData fd_grpComunidad = new FormData();
    fd_grpComunidad.right = new FormAttachment(100, -10);
    fd_grpComunidad.bottom = new FormAttachment(0, 233);
    fd_grpComunidad.top = new FormAttachment(0, 10);
    fd_grpComunidad.left = new FormAttachment(0, 10);
    grpComunidad.setLayoutData(fd_grpComunidad);
    
    label = new Label(grpComunidad, SWT.NONE);
    label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
    label.setText("RUT:");
    label.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    rut = new StyledText(grpComunidad, SWT.BORDER | SWT.SINGLE);
    rut.setText("");
    GridData gd_rut = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
    gd_rut.widthHint = 184;
    rut.setLayoutData(gd_rut);
    rut.addListener(SWT.Verify, new Listener() {
    	public void handleEvent(Event e) {
    		e.doit = Validador.isNumber(e.text);
            return;
        }
    });

    
    dv = new StyledText(grpComunidad, SWT.BORDER | SWT.SINGLE);
    dv.setTextLimit(1);
    GridData gd_dv = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
    gd_dv.widthHint = 18;
    dv.setLayoutData(gd_dv);
    dv.setTextLimit(1);
    dv.addListener(SWT.Verify, new Listener() {
    	public void handleEvent(Event e) {
    		e.doit = Validador.isDV(e.text);
            return;
        }
    });
    new Label(grpComunidad, SWT.NONE);
    
    lblErrorRut = new Label(grpComunidad, SWT.NONE);
    lblErrorRut.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    lblErrorRut.setText("RUT INCORRECTO");
    lblErrorRut.setVisible(false);
    
    razon = new Label(grpComunidad, SWT.NONE);
    razon.setText("Razon Social:");
    razon.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    larazon = new Text(grpComunidad, SWT.BORDER);
    larazon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    
    larazon.addModifyListener(new ModifyListener() {
	  public void modifyText(ModifyEvent event) {
  		  if(larazon.getText().length() > 0 && 
  				  elnombre.getText().length() > 0 && 
  				  eltelefono.getText().length() > 0 && 
  				  !rut.getText().isEmpty() && 
  				  !dv.getText().isEmpty() && flag == true){
  			  setDatosEmpresa();
			  setPageComplete(true);
		  } else {
			  setPageComplete(false);
		  }
	 }
    });
    larazon.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		if(rut.getText().isEmpty() || dv.getText().isEmpty()){
    			lblErrorRut.setVisible(true);
    		} else {
    			if(!Validador.isRUT(Integer.parseInt(rut.getText()), dv.getText().charAt(0)))
    				lblErrorRut.setVisible(true);
    			else
    				flag = true;
    		}
        return;
    }
    });

    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);
    
    nombre = new Label(grpComunidad, SWT.NONE);
    nombre.setText("Nombre:");
    nombre.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    elnombre = new Text(grpComunidad, SWT.BORDER);
    elnombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    
    elnombre.addModifyListener(new ModifyListener() {
    	public void modifyText(ModifyEvent event) {
  		  if(larazon.getText().length() > 0 && 
  				  elnombre.getText().length() > 0 && 
  				  eltelefono.getText().length() > 0 && 
  				  !rut.getText().isEmpty() && 
  				  !dv.getText().isEmpty() && flag == true){
			  setDatosEmpresa();
			  setPageComplete(true);
		  }else {
			  setPageComplete(false);
		  }
    	}
    });
    
    elnombre.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		if(rut.getText().isEmpty() || dv.getText().isEmpty()){
    			lblErrorRut.setVisible(true);
    		} else {
    			if(!Validador.isRUT(Integer.parseInt(rut.getText()), dv.getText().charAt(0)))
    				lblErrorRut.setVisible(true);
    			else
    				flag = true;
    		}
        return;
    }
    });
    
    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);
    
    telefono = new Label(grpComunidad, SWT.NONE);
    telefono.setText("Tel\u00E9fono:");
    telefono.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    eltelefono = new Text(grpComunidad, SWT.BORDER);
    eltelefono.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    
    eltelefono.addModifyListener(new ModifyListener() {
  	  public void modifyText(ModifyEvent event) {
  		  if(larazon.getText().length() > 0 && 
  				  elnombre.getText().length() > 0 && 
  				  eltelefono.getText().length() > 0 && 
  				  !rut.getText().isEmpty() && 
  				  !dv.getText().isEmpty() && flag == true){
  			  setDatosEmpresa();
			  setPageComplete(true);
		  }else {
			  setPageComplete(false);
		  }
  	  }
    });
    eltelefono.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		if(rut.getText().isEmpty() || dv.getText().isEmpty()){
    			lblErrorRut.setVisible(true);
    		} else {
    			if(!Validador.isRUT(Integer.parseInt(rut.getText()), dv.getText().charAt(0))){
    				lblErrorRut.setVisible(true);		
    				flag = false;
    			}else{
    				flag = true;
    			}
    		}
        return;
    }
    });

    eltelefono.addListener(SWT.Verify, new Listener() {
    	public void handleEvent(Event e) {
    		e.doit = Validador.isNumber(e.text);
        return;
    }
    });
    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);
    new Label(grpComunidad, SWT.NONE);

    rut.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		lblErrorRut.setVisible(false);
            return;
        }
    });

    rut.addListener(SWT.FocusOut, new Listener() {
    	public void handleEvent(Event e) {
    		if(!rut.getText().isEmpty() && !dv.getText().isEmpty()){
				if(!Validador.isRUT(Integer.parseInt(rut.getText()), dv.getText().charAt(0))){
					lblErrorRut.setVisible(true);		
					flag = false;
				} else {
					flag = true;
				}
    		}
        return;
        }
    });
    
    dv.addListener(SWT.FocusIn, new Listener() {
    	public void handleEvent(Event e) {
    		lblErrorRut.setVisible(false);
            return;
        }
    });

    dv.addListener(SWT.FocusOut, new Listener() {
    	public void handleEvent(Event e) {
    		if(!rut.getText().isEmpty() && !dv.getText().isEmpty()){
				if(!Validador.isRUT(Integer.parseInt(rut.getText()), dv.getText().charAt(0))){
					lblErrorRut.setVisible(true);		
					flag = false;
				} else {
					flag = true;
				}
    		}
        return;
        }
    });

  }
  
  private void setDatosEmpresa(){
	  ((WizardEdificio)getWizard()).getLaEmpresa().setRut(new Integer(rut.getText()));
	  ((WizardEdificio)getWizard()).getLaEmpresa().setDv(dv.getText());
	  ((WizardEdificio)getWizard()).getLaEmpresa().setRazonSocial(larazon.getText());
	  ((WizardEdificio)getWizard()).getLaEmpresa().setNombreFantasia(elnombre.getText());
	  ((WizardEdificio)getWizard()).getLaEmpresa().setTelefono(eltelefono.getText());
	  ((WizardEdificio)getWizard()).getLaEmpresa().setEstado("activa");
  }
} 
