package cl.altair.accesos.principal.formularios;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;

import cl.altair.acceso.dao.ProgramaDAO;
import cl.altair.acceso.dao.UsuarioDAO;
import cl.altair.acceso.modelo.Programa;
import cl.altair.acceso.modelo.Usuario;
import cl.altair.utiles.generales.InfoRegistro;
import cl.altair.utiles.generales.PasswordHash;
import cl.miempresa.accesos.principal.Main;

public class Ingreso extends Dialog {
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private Text usernameField;
	private Text passwordField;
	private final static Logger LOGGER = Logger.getLogger(Ingreso.class.getName());
	private ToolTip tip;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Ingreso(Shell parent) {
		super(parent);
	}
	
	  protected Control createDialogArea(Composite parent) {
		    Composite comp = (Composite) super.createDialogArea(parent);

		    tip = new ToolTip(this.getParentShell(), SWT.BALLOON | SWT.ICON_ERROR);
		    
		    GridLayout layout = (GridLayout) comp.getLayout();
		    layout.numColumns = 2;

		    Label usernameLabel = new Label(comp, SWT.RIGHT);
		    usernameLabel.setText("Correo Electrónico: ");

		    usernameField = new Text(comp, SWT.SINGLE);
		    usernameField.addListener(SWT.FocusOut, new Listener() {
		    	public void handleEvent(Event e) {
		    		if(!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(usernameField.getText())){
		    			usernameField.setFocus();
		                Point loc = usernameField.toDisplay(usernameField.getLocation());
		                tip.setMessage("Formato de correo incorrecto");
		                tip.setLocation(loc);
						tip.setVisible(true);
		    		}		
		    		return;
		        }
		    });

		    GridData data = new GridData(GridData.FILL_HORIZONTAL);
		    usernameField.setLayoutData(data);

		    Label passwordLabel = new Label(comp, SWT.RIGHT);
		    passwordLabel.setText("Contraseña: ");

		    passwordField = new Text(comp, SWT.SINGLE | SWT.PASSWORD);
		    data = new GridData(GridData.FILL_HORIZONTAL);
		    passwordField.setLayoutData(data);

		    return comp;
	  }
	  
	  protected void createButtonsForButtonBar(Composite parent) {
		    super.createButtonsForButtonBar(parent);
		    createButton(parent, RESET_ID, "Limpiar Campos", false);
	  }

	  protected void buttonPressed(int buttonId) {
	    if (buttonId == RESET_ID) {
	      usernameField.setText("");
	      passwordField.setText("");
	    } else if(buttonId == IDialogConstants.OK_ID){
	    	if(passwordField.getText().length() > 0){
		    	try {
		    		ProgramaDAO pdao = new ProgramaDAO();
		    		List<Programa> listaProgramas = pdao.findAll();
		    		//Si existe el registro del programa se valida al usuario
		    		if(!listaProgramas.isEmpty()){
		    			//Llama la informacion del usuario en la base de datos
			    		UsuarioDAO udao = new UsuarioDAO();
			    		List<Usuario> listaUsuarios = udao.findByLogin(usernameField.getText());
			    		// Si el usuario no existe se muestra un mensaje de error y luego se limpian los campos para un nuevo intento
			    		if(listaUsuarios.isEmpty()){
							LOGGER.info("No existe el usaurio " + usernameField.getText());
							IStatus status=new Status(IStatus.ERROR,"Plugin ID","Usuario no existe",null);
							ErrorDialog.openError(this.getParentShell(), "USUARIO INCORRECTO", "El usuario " + usernameField.getText() + " no existe, intentelo nuevamente", status);
						    usernameField.setText("");
						    passwordField.setText("");
			    		} else {
			    			String clave = listaUsuarios.get(0).getClave();
							//Valida la clave local versus la del portal, si esta correcta el programa prosigue
					    	if(PasswordHash.validatePassword(passwordField.getText(), clave)){
					    		LOGGER.info("CLAVES COINCIDEN");
					    		//Asigna el usuario conectado
					    		Main.usuarioActivo = listaUsuarios.get(0);
					    		super.buttonPressed(buttonId);
					    		return;
					    	} else {
								LOGGER.info("Clave ingresada no corresponde");
								IStatus status=new Status(IStatus.ERROR,"Plugin ID","La clave no es correcta",null);
								ErrorDialog.openError(this.getParentShell(), "CLAVE INCORRECTA", "La clave ingresada no corresponde a la registrada para este usuario", status);
							    passwordField.setText("");
					    	}	
			    		}
		    		} else {
			    		//Llamando al portal para el primer ingreso
						int rc = InfoRegistro.getInfoRegistro(usernameField.getText(), passwordField.getText());
						switch(rc){
							case 1:{
								IStatus status=new Status(IStatus.ERROR,"Plugin ID","La clave no es correcta",null);
								ErrorDialog.openError(this.getParentShell(), "CLAVE INCORRECTA", "La clave ingresada no corresponde a la registrada para este usuario", status);
								break;
							}
							case 2:{
								IStatus status=new Status(IStatus.ERROR,"Plugin ID","Usuario no existe",null);
								ErrorDialog.openError(this.getParentShell(), "USUARIO INCORRECTO", "El usuario " + usernameField.getText() + " no existe, intentelo nuevamente", status);
								break;
							}
							default:{
								super.buttonPressed(buttonId);
								break;
							}
						}
		    		}
				} catch (IOException e) {
					LOGGER.info("NO TIENE CONECCION A INTERNET!!");
					IStatus status=new Status(IStatus.ERROR,"Plugin ID","Ud. debe conectarse a internet",e);
					ErrorDialog.openError(this.getParentShell(), "SIN INTERNET", "Este programa requiere conexión a internet", status);
					this.getParentShell().dispose();
					System.exit(0);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	    	} else {
                Point loc = passwordField.toDisplay(passwordField.getLocation());
                tip.setMessage("Debe ingresar una clave");
                tip.setLocation(loc);
				tip.setVisible(true);
	    	}
	    } else if(buttonId == IDialogConstants.CANCEL_ID){
	    	// Si se cancela el dialogo de login el programa se cierra
	    	LOGGER.info("Se cancelo el dialogo de login");
			this.getParentShell().dispose();
			System.exit(0);
	    }
	  }

}
