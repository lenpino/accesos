package cl.altair.accesos.principal.formularios;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cl.altair.utiles.generales.InfoRegistro;
import cl.altair.utiles.generales.PasswordHash;
import cl.altair.utiles.generales.RegistroUsr;

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
	    		//Llamando al portal
				InfoRegistro.getInfoRegistro(usernameField.getText());
				//Valida la clave local versus la del portal
		    	if(PasswordHash.validatePassword(passwordField.getText(), InfoRegistro.getClave())){
		    		System.out.println("CLAVES COINCIDEN");
		    		RegistroUsr.registraUsr(usernameField.getText());
		    	} else {
		    		System.out.println("CLAVES NO COINCIDEN");
		    	}
		    	
			} catch (IOException e) {
				System.out.println("NO TIENE CONECCION A INTERNET!!");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
	    	System.out.println("email = " + usernameField.getText());
	    	
	    } else if(buttonId == IDialogConstants.CANCEL_ID){
	    	System.out.println("clave = " + passwordField.getText());
	    }
	    super.buttonPressed(buttonId);
	  }

}
