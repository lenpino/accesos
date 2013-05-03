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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cl.altair.acceso.dao.ProgramaDAO;
import cl.altair.acceso.dao.UsuarioDAO;
import cl.altair.acceso.modelo.Programa;
import cl.altair.acceso.modelo.Usuario;
import cl.altair.utiles.generales.InfoRegistro;
import cl.altair.utiles.generales.PasswordHash;

public class Ingreso extends Dialog {
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private Text usernameField;
	private Text passwordField;
	private final static Logger LOGGER = Logger.getLogger(Ingreso.class.getName());
	
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

		    GridLayout layout = (GridLayout) comp.getLayout();
		    layout.numColumns = 2;

		    Label usernameLabel = new Label(comp, SWT.RIGHT);
		    usernameLabel.setText("Correo Electr—nico: ");

		    usernameField = new Text(comp, SWT.SINGLE);
		    GridData data = new GridData(GridData.FILL_HORIZONTAL);
		    usernameField.setLayoutData(data);

		    Label passwordLabel = new Label(comp, SWT.RIGHT);
		    passwordLabel.setText("Contrase–a: ");

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
						//Valida la clave local versus la del portal
				    	if(PasswordHash.validatePassword(passwordField.getText(), clave)){
				    		System.out.println("CLAVES COINCIDEN");
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
		    		//Llamando al portal
					InfoRegistro.getInfoRegistro(usernameField.getText(), passwordField.getText());
	    		}
			} catch (IOException e) {
				LOGGER.info("NO TIENE CONECCION A INTERNET!!");
				IStatus status=new Status(IStatus.ERROR,"Plugin ID","Ud. debe conectarse a internet",e);
				ErrorDialog.openError(this.getParentShell(), "SIN INTERNET", "Este programa requiere conexi—n a internet", status);
				this.getParentShell().dispose();
				System.exit(0);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
	    } else if(buttonId == IDialogConstants.CANCEL_ID){
	    	// Si se cancela el dialogo de login el programa se cierra
	    	LOGGER.info("Se cancelo el dialogo de login");
			this.getParentShell().dispose();
			System.exit(0);
	    }
	  }

}
