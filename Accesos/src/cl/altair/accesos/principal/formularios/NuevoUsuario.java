package cl.altair.accesos.principal.formularios;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Point;

import cl.altair.acceso.dao.EntityManagerHelper;
import cl.altair.acceso.dao.RolDAO;
import cl.altair.acceso.dao.UsuarioDAO;
import cl.altair.acceso.modelo.Rol;
import cl.altair.acceso.modelo.Usuario;
import cl.altair.utiles.generales.PasswordHash;
import cl.miempresa.accesos.principal.Main;

public class NuevoUsuario extends FormDialog {
	private Text correo;
	private Text clave;
	private Text txtNewText;
	private Text txtNewText_1;
	private Text txtNewText_2;
	private Text text_2;
	private GridData gd_btnDatosPersonales;
	private boolean valida = false;

	public NuevoUsuario(Shell shell) {
		super(shell);
	}
	  
	protected void okPressed(){
		try {
			//fecha actual
			java.util.Date utilDate = new java.util.Date();
			long lnMilisegundos = utilDate.getTime();
			
			RolDAO rdao = new RolDAO();
			UsuarioDAO udao = new UsuarioDAO();
			Usuario unUsuario = new Usuario();
			Rol conserje;
			List<Rol> roles = rdao.findByNombre("Conserje");
			if(roles.isEmpty()){
				conserje = new Rol();
				conserje.setNombre("Conserje");
				//Inserta el rol de conserje si no existe
				EntityManagerHelper.beginTransaction();
				rdao.save(conserje);
				EntityManagerHelper.commit();
				EntityManagerHelper.closeEntityManager();
			} else {
				conserje = roles.get(0);
			}
			unUsuario.setClave(PasswordHash.createHash(clave.getText()));
			unUsuario.setLogin(correo.getText());
			unUsuario.setEstado("activo");
			unUsuario.setFechaingreso(new java.sql.Date(lnMilisegundos));
			unUsuario.getRoles().add(conserje);
			//Inserta el rol de conserje si no existe
			EntityManagerHelper.beginTransaction();
			udao.save(unUsuario);
			EntityManagerHelper.commit();
			EntityManagerHelper.closeEntityManager();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		if(valida)
			super.okPressed();
	}
	
	protected void createFormContent(IManagedForm managedForm) {
		managedForm.getForm().setImage(null);
		managedForm.getForm().setText("Nuevo Usuario");
        try {
            final ScrolledForm form = managedForm.getForm();
            final FormToolkit toolkit = managedForm.getToolkit();
            GridLayout layout = new GridLayout();
            layout.marginTop = 5;
            form.getBody().setLayout(layout);
            
            layout.numColumns = 2;
            GridData gd = new GridData();
            gd.horizontalSpan = 2;
            Label lblEmailUsuario = new Label(form.getBody(), SWT.NULL);
            lblEmailUsuario.setText("Email Usuario:");
            correo = new Text(form.getBody(), SWT.BORDER);
            GridData gd_text = new GridData(GridData.FILL_HORIZONTAL);
            gd_text.widthHint = 265;
            gd_text.grabExcessHorizontalSpace = false;
            correo.setLayoutData(gd_text);
            
            //Crea icono de decoracion de error y mensaje para el error
    		final ControlDecoration controlDecoration = new ControlDecoration(correo, SWT.LEFT | SWT.TOP);
    		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
    		controlDecoration.setImage(fieldDecoration.getImage());
    		controlDecoration.hide();
    		
    		//Valida el campo de correo al terminar su edicion
		    correo.addListener(SWT.FocusOut, new Listener() {
		    	public void handleEvent(Event e) {
		    		if(!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(correo.getText())){
		    			correo.setFocus();
		    			controlDecoration.show();
		    			controlDecoration.showHoverText("Correo incorrecto");
		    		} else {
		    			valida = true;
		    		}
		    		return;
		        }
		    });
		    //Se esconde el error al volver a editar el campo
			correo.addModifyListener(new ModifyListener() {
		    	public void modifyText(ModifyEvent event) {
		    		controlDecoration.hide();
		    	}
		    });

            
            Label lblClaveUsuario = new Label(managedForm.getForm().getBody(), SWT.NONE);
            lblClaveUsuario.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
            managedForm.getToolkit().adapt(lblClaveUsuario, true, true);
            lblClaveUsuario.setText("Clave Usuario:");
            
            clave = new Text(form.getBody(), SWT.BORDER | SWT.PASSWORD);
            GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
            gd_text_1.widthHint = 265;
            clave.setLayoutData(gd_text_1);
            managedForm.getToolkit().adapt(clave, true, true);

            //Crea icono de decoracion de error y mensaje para el error
    		final ControlDecoration decopwd = new ControlDecoration(clave, SWT.LEFT | SWT.TOP);
    		FieldDecoration campopwd = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
    		controlDecoration.setImage(campopwd.getImage());
    		controlDecoration.hide();

    		//Valida el campo de email al terminar su edicion
		    clave.addListener(SWT.FocusOut, new Listener() {
		    	public void handleEvent(Event e) {
		    		if(clave.getText().length() < 8){
		    			clave.setFocus();
		    			decopwd.show();
		    			decopwd.showHoverText("La clave debe poseer al menos 8 caracteres");
		    		} else {
		    			valida = true;
		    		}
		    		return;
		        }
		    });
		    //Se esconde el error al volver a editar el campo
			clave.addModifyListener(new ModifyListener() {
		    	public void modifyText(ModifyEvent event) {
		    		decopwd.hide();
		    	}
		    });

            Label lblRol = new Label(form.getBody(), SWT.NONE);
            managedForm.getToolkit().adapt(lblRol, true, true);
            lblRol.setText("Rol:");
            
            Label lblConserje = new Label(form.getBody(), SWT.NONE);
            managedForm.getToolkit().adapt(lblConserje, true, true);
            lblConserje.setText("CONSERJE");
            
            Button btnDatosPersonales = new Button(form.getBody(), SWT.CHECK);
            btnDatosPersonales.setText("Datos personales");
            gd_btnDatosPersonales = new GridData();
            gd_btnDatosPersonales.horizontalSpan = 2;
            btnDatosPersonales.setLayoutData(gd_btnDatosPersonales);
            
        	// create a tooltip
        	ToolTip tooltip = new MyTooltip(lblEmailUsuario);
        	
        	Label label = managedForm.getToolkit().createSeparator(managedForm.getForm().getBody(), SWT.HORIZONTAL);
        	label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        	
        	Label label_1 = managedForm.getToolkit().createSeparator(managedForm.getForm().getBody(), SWT.HORIZONTAL);
        	label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        	
        	Label lblRut = managedForm.getToolkit().createLabel(form.getBody(), "RUT:", SWT.NONE);
        	
        	text_2 = managedForm.getToolkit().createText(form.getBody(), "New Text", SWT.BORDER);
        	GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        	gd_text_2.widthHint = 265;
        	text_2.setLayoutData(gd_text_2);
        	text_2.setText("");
        	
        	Label lblNombre = managedForm.getToolkit().createLabel(form.getBody(), "Nombre:", SWT.NONE);
        	
        	txtNewText = managedForm.getToolkit().createText(form.getBody(), "New Text", SWT.NONE);
        	GridData gd_txtNewText = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        	gd_txtNewText.widthHint = 265;
        	txtNewText.setLayoutData(gd_txtNewText);
        	txtNewText.setText("");
        	
        	Label lblApellido = managedForm.getToolkit().createLabel(form.getBody(), "Apellido:", SWT.NONE);
        	
        	txtNewText_1 = managedForm.getToolkit().createText(form.getBody(), "New Text", SWT.NONE);
        	GridData gd_txtNewText_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        	gd_txtNewText_1.widthHint = 266;
        	txtNewText_1.setLayoutData(gd_txtNewText_1);
        	txtNewText_1.setText("");
        	
        	Label lblApellidoMat = managedForm.getToolkit().createLabel(form.getBody(), "Apellido Mat:", SWT.NONE);
        	
        	txtNewText_2 = managedForm.getToolkit().createText(form.getBody(), "New Text", SWT.NONE);
        	GridData gd_txtNewText_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        	gd_txtNewText_2.widthHint = 264;
        	txtNewText_2.setLayoutData(gd_txtNewText_2);
        	txtNewText_2.setText("");
        	managedForm.getForm().setMinSize(new Point(400, 300));
        	tooltip.setPopupDelay(2);

            
	    } catch (Exception e) {
	            System.out.println("Error");
	    }
	}
	
	private class MyTooltip extends ToolTip {

		public MyTooltip(Control control) {
			super(control);
		}

		protected Composite createToolTipContentArea(Event event,
				Composite parent) {
			FormToolkit toolkit = new FormToolkit(parent.getDisplay());
			FormColors colors = toolkit.getColors();
			Color top = colors.getColor(IFormColors.H_GRADIENT_END);
			Color bot = colors.getColor(IFormColors.H_GRADIENT_START);

			// create the base form
			Form form = toolkit.createForm(parent);
			form.setText("Snippet8");
			form.setTextBackground(new Color[] { top, bot }, new int[] { 100 }, true);
			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			form.getBody().setLayout(layout);

			// create the text for user information
			FormText text = toolkit.createFormText(form.getBody(), true);
			GridData td = new GridData();
			td.horizontalSpan = 2;
			td.heightHint = 100;
			td.widthHint = 200;
			text.setLayoutData(td);

			text.setText(
				"<form><p>snippet8</p><p>snippet8</p></form>", 
				true, 
				false);

			// create the picture representing the user
			td = new GridData();
			td.horizontalSpan = 1;
			td.heightHint = 100;
			td.widthHint = 64;
			FormText formImage = 
				toolkit.createFormText(form.getBody(), false);
			formImage.setText(
				"<form><p><img href=\"image\"/></p></form>", 
				true, false);
			formImage.setLayoutData(td);

			Image image = SWTResourceManager.getImage(NuevoUsuario.class, "/com/altair/accesos/recursos/organization32.jpg");
			formImage.setImage("image", image);

			return parent;
		}
	}
}
