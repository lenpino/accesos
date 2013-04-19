package cl.altair.accesos.wizard.UsuarioInmueble;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.DateTime;

public class WUIPaginaPersona extends WizardPage {
  private Composite container;
  private Group group;
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

  public WUIPaginaPersona() {
    super("Persona");
    setMessage("Complete los datos de la persona");
    setTitle("Segundo Paso");
    setDescription("Complete los datos de la persona");
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    // Required to avoid an error in the system
    setControl(container);
    container.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    group = new Group(container, SWT.NONE);
    group.setText("Persona");
    group.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    group.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
    GridLayout gl_group = new GridLayout(2, false);
    gl_group.verticalSpacing = 8;
    group.setLayout(gl_group);
    
    nombre = new Label(group, SWT.NONE);
    nombre.setText("Nombre:");
    nombre.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    elnombre = new Text(group, SWT.BORDER);
    GridData gd_elnombre = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
    gd_elnombre.widthHint = 175;
    elnombre.setLayoutData(gd_elnombre);
    new Label(group, SWT.NONE);
    new Label(group, SWT.NONE);
    
    apellido = new Label(group, SWT.NONE);
    apellido.setText("Apellido:");
    apellido.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    elapellido = new Text(group, SWT.BORDER);
    GridData gd_elapellido = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
    gd_elapellido.widthHint = 186;
    elapellido.setLayoutData(gd_elapellido);
    new Label(group, SWT.NONE);
    new Label(group, SWT.NONE);
    
    email = new Label(group, SWT.NONE);
    email.setText("Email:");
    email.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    elemail = new Text(group, SWT.BORDER);
    GridData gd_elemail = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
    gd_elemail.widthHint = 190;
    elemail.setLayoutData(gd_elemail);
    new Label(group, SWT.NONE);
    new Label(group, SWT.NONE);
    
    celular = new Label(group, SWT.NONE);
    celular.setText("Celular:");
    celular.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    elcelular = new Text(group, SWT.BORDER);
    GridData gd_elcelular = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
    gd_elcelular.widthHint = 215;
    elcelular.setLayoutData(gd_elcelular);
    new Label(group, SWT.NONE);
    new Label(group, SWT.NONE);
    
    fechanac = new Label(group, SWT.NONE);
    fechanac.setText("Fecha Nac.:");
    fechanac.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    
    lafechanac = new DateTime(group, SWT.BORDER);
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
	  ((WizardUsuarioInmueble)getWizard()).getLaPersona().setRut(((WizardUsuarioInmueble)getWizard()).getElusuario().getRut());
	  ((WizardUsuarioInmueble)getWizard()).getLaPersona().setDv(((WizardUsuarioInmueble)getWizard()).getElusuario().getDv());
	  ((WizardUsuarioInmueble)getWizard()).getLaPersona().setNombre(elnombre.getText());
	  ((WizardUsuarioInmueble)getWizard()).getLaPersona().setApellido(elapellido.getText());
	  ((WizardUsuarioInmueble)getWizard()).getLaPersona().setEmail(elemail.getText());
	  ((WizardUsuarioInmueble)getWizard()).getLaPersona().setCelular(elcelular.getText());
  }
} 