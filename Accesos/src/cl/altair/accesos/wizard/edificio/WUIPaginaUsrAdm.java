package cl.altair.accesos.wizard.edificio;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import cl.altair.accesos.wizard.UsuarioInmueble.WUIPaginaEmpresa;
import cl.altair.accesos.wizard.UsuarioInmueble.WUIPaginaPersona;
import cl.altair.accesos.wizard.UsuarioInmueble.WizardUsuarioInmueble;

public class WUIPaginaUsrAdm extends WizardPage {
  private Composite container;
  private Group grpUsuario;
  private Label nombre;
  private Label apellido;
  private Label email;
  private Label celular;
  private Label fechanac;
  private Text elnombre;
  private Text elapellido;
  private Text elemail;
  private Text elcelular;
  private DateTime lafechanac;
  private Label lblNewLabel_1;
  
  
  public WUIPaginaUsrAdm() {
    super("Empresa");
    setTitle("Primer Paso");
    setDescription("Complete los datos del usuario administrador");
    setPageComplete(false);
  }

  @Override
  public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    // Required to avoid an error in the system
	    setControl(container);
	    container.setLayout(new FillLayout(SWT.HORIZONTAL));
	    
	    grpUsuario = new Group(container, SWT.NONE);
	    grpUsuario.setText("Usuario");
	    grpUsuario.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
	    grpUsuario.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
	    GridLayout gl_grpUsuario = new GridLayout(2, false);
	    gl_grpUsuario.verticalSpacing = 3;
	    grpUsuario.setLayout(gl_grpUsuario);
	    
	    lblNewLabel_1 = new Label(grpUsuario, SWT.NONE);
	    lblNewLabel_1.setText("RUT:");
	    new Label(grpUsuario, SWT.NONE);
	    new Label(grpUsuario, SWT.NONE);
	    new Label(grpUsuario, SWT.NONE);
	    
	    nombre = new Label(grpUsuario, SWT.NONE);
	    nombre.setText("Nombre:");
	    nombre.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
	    
	    elnombre = new Text(grpUsuario, SWT.BORDER);
	    GridData gd_elnombre = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	    gd_elnombre.widthHint = 175;
	    elnombre.setLayoutData(gd_elnombre);
	    new Label(grpUsuario, SWT.NONE);
	    new Label(grpUsuario, SWT.NONE);
	    
	    apellido = new Label(grpUsuario, SWT.NONE);
	    apellido.setText("Apellido:");
	    apellido.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
	    
	    elapellido = new Text(grpUsuario, SWT.BORDER);
	    GridData gd_elapellido = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	    gd_elapellido.widthHint = 186;
	    elapellido.setLayoutData(gd_elapellido);
	    new Label(grpUsuario, SWT.NONE);
	    new Label(grpUsuario, SWT.NONE);
	    
	    email = new Label(grpUsuario, SWT.NONE);
	    email.setText("Email:");
	    email.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
	    
	    elemail = new Text(grpUsuario, SWT.BORDER);
	    GridData gd_elemail = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	    gd_elemail.widthHint = 190;
	    elemail.setLayoutData(gd_elemail);
	    new Label(grpUsuario, SWT.NONE);
	    new Label(grpUsuario, SWT.NONE);
	    
	    celular = new Label(grpUsuario, SWT.NONE);
	    celular.setText("Celular:");
	    celular.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
	    
	    elcelular = new Text(grpUsuario, SWT.BORDER);
	    GridData gd_elcelular = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	    gd_elcelular.widthHint = 215;
	    elcelular.setLayoutData(gd_elcelular);
	    new Label(grpUsuario, SWT.NONE);
	    new Label(grpUsuario, SWT.NONE);
	    
	    fechanac = new Label(grpUsuario, SWT.NONE);
	    fechanac.setText("Fecha Nac.:");
	    fechanac.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
	    
	    lafechanac = new DateTime(grpUsuario, SWT.BORDER);
	    lafechanac.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	    
	    elnombre.addModifyListener(new ModifyListener() {
		  public void modifyText(ModifyEvent event) {
			  if(elnombre.getText().length() > 0 
					  && elapellido.getText().length() > 0 
					  && elemail.getText().length() > 0
					  && elcelular.getText().length() > 0){
				  setDatosPersona();
				  ((WUIPaginaPersona)(getWizard().getPage("Empresa"))).setPageComplete(true);
				  setPageComplete(true);
			  }
		 }
	    });    
	    elapellido.addModifyListener(new ModifyListener() {
		  public void modifyText(ModifyEvent event) {
			  if(elnombre.getText().length() > 0 
					  && elapellido.getText().length() > 0 
					  && elemail.getText().length() > 0
					  && elcelular.getText().length() > 0){
				  setDatosPersona();
				  ((WUIPaginaEmpresa)(getWizard().getPage("Empresa"))).setPageComplete(true);
				  setPageComplete(true);
			  }
		  }
	    });    
	    elemail.addModifyListener(new ModifyListener() {
		  public void modifyText(ModifyEvent event) {
			  if(elnombre.getText().length() > 0 
					  && elapellido.getText().length() > 0 
					  && elemail.getText().length() > 0
					  && elcelular.getText().length() > 0){
				  setDatosPersona();
				  ((WUIPaginaEmpresa)(getWizard().getPage("Empresa"))).setPageComplete(true);
				  setPageComplete(true);
			  }
		 }
	    });    
	    elcelular.addModifyListener(new ModifyListener() {
		  public void modifyText(ModifyEvent event) {
			  if(elnombre.getText().length() > 0 
					  && elapellido.getText().length() > 0 
					  && elemail.getText().length() > 0
					  && elcelular.getText().length() > 0){
				  setDatosPersona();
				  ((WUIPaginaEmpresa)(getWizard().getPage("Empresa"))).setPageComplete(true);
				  setPageComplete(true);
			  }
		 }
	    });
	    setPageComplete(false);

	  }
  
  private void setDatosPersona(){
//	  ((WizardEdificio)getWizard()).getLaPersona().setRut(((WizardEdificio)getWizard()).getElusuario().getRut());
//	  ((WizardEdificio)getWizard()).getLaPersona().setDv(((WizardEdificio)getWizard()).getElusuario().getDv());
	  ((WizardEdificio)getWizard()).getLaPersona().setNombre(elnombre.getText());
	  ((WizardEdificio)getWizard()).getLaPersona().setApellido(elapellido.getText());
	  ((WizardEdificio)getWizard()).getLaPersona().setEmail(elemail.getText());
	  ((WizardEdificio)getWizard()).getLaPersona().setCelular(elcelular.getText());
  }
} 
