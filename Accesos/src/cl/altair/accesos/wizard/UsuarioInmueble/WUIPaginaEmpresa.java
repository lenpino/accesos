package cl.altair.accesos.wizard.UsuarioInmueble;

import org.eclipse.jface.wizard.IWizardPage;
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

public class WUIPaginaEmpresa extends WizardPage {
  private Composite container;
  private Group group;
  private Label razon;
  private Label nombre;
  private Text larazon;
  private Label telefono;
  private Text elnombre;
  private Text eltelefono;

  public WUIPaginaEmpresa() {
    super("Empresa");
    setTitle("Segundo Paso");
    setDescription("Complete los datos de la empresa");
    setPageComplete(false);
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    container.setLayout(new FormLayout());
    // Required to avoid an error in the system
    setControl(container);
    
    group = new Group(container, SWT.NONE);
    group.setText("Empresa");
    group.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    group.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
    FormData fd_group = new FormData();
    fd_group.right = new FormAttachment(100, -10);
    fd_group.bottom = new FormAttachment(0, 233);
    fd_group.top = new FormAttachment(0, 10);
    fd_group.left = new FormAttachment(0, 10);
    group.setLayoutData(fd_group);
    
    razon = new Label(group, SWT.NONE);
    razon.setText("Razon Social:");
    razon.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    razon.setBounds(10, 24, 82, 14);
    
    nombre = new Label(group, SWT.NONE);
    nombre.setText("Nombre:");
    nombre.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    nombre.setBounds(10, 67, 59, 14);
    
    larazon = new Text(group, SWT.BORDER);
    larazon.setBounds(91, 22, 258, 19);
    
    telefono = new Label(group, SWT.NONE);
    telefono.setText("Tel\u00E9fono:");
    telefono.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    telefono.setBounds(10, 106, 59, 14);
    
    elnombre = new Text(group, SWT.BORDER);
    elnombre.setBounds(91, 63, 258, 19);
    
    eltelefono = new Text(group, SWT.BORDER);
    eltelefono.setBounds(91, 102, 258, 19);
    
    larazon.addModifyListener(new ModifyListener() {
	  public void modifyText(ModifyEvent event) {
		  if(larazon.getText().length() > 0 && elnombre.getText().length() > 0 && eltelefono.getText().length() > 0){
			  setDatosEmpresa();
			  ((WUIPaginaPersona)(getWizard().getPage("Persona"))).setPageComplete(true);
			  setPageComplete(true);
		  }
	 }
    });
    	  
    elnombre.addModifyListener(new ModifyListener() {
    	public void modifyText(ModifyEvent event) {
  		  if(larazon.getText().length() > 0 && elnombre.getText().length() > 0 && eltelefono.getText().length() > 0){
			  setDatosEmpresa();
			  ((WUIPaginaPersona)(getWizard().getPage("Persona"))).setPageComplete(true);
			  setPageComplete(true);
		  }
    	}
    });

    eltelefono.addModifyListener(new ModifyListener() {
  	  public void modifyText(ModifyEvent event) {
		  if(larazon.getText().length() > 0 && elnombre.getText().length() > 0 && eltelefono.getText().length() > 0){
			  setDatosEmpresa();
			  ((WUIPaginaPersona)(getWizard().getPage("Persona"))).setPageComplete(true);
			  setPageComplete(true);
		  }
  	  }
    });
    
    eltelefono.addListener(SWT.Verify, new Listener() {
    	public void handleEvent(Event e) {
    		e.doit = Validador.isNumber(e.text);
            return;
        }
    });    
  }
  
  public IWizardPage getNextPage() {
	    return getWizard().getPage("Dependencia");
  }
  private void setDatosEmpresa(){
	  ((WizardUsuarioInmueble)getWizard()).getLaEmpresa().setRut(((WizardUsuarioInmueble)getWizard()).getElusuario().getRut());
	  ((WizardUsuarioInmueble)getWizard()).getLaEmpresa().setDv(((WizardUsuarioInmueble)getWizard()).getElusuario().getDv());
	  ((WizardUsuarioInmueble)getWizard()).getLaEmpresa().setRazonSocial(larazon.getText());
	  ((WizardUsuarioInmueble)getWizard()).getLaEmpresa().setNombreFantasia(elnombre.getText());
	  ((WizardUsuarioInmueble)getWizard()).getLaEmpresa().setTelefono(eltelefono.getText());
	  ((WizardUsuarioInmueble)getWizard()).getLaEmpresa().setEstado("activa");
  }
} 
