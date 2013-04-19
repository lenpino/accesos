package cl.miempresa.accesos.principal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;

public class Login extends Shell {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Login shell = new Login(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Login(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new FormLayout());
		
		Composite composite = new Composite(this, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 483);
		fd_composite.right = new FormAttachment(0, 678);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		composite.setBackgroundImage(SWTResourceManager.getImage("/Users/Len/Pictures/EEnaughtyman1280x854.jpg"));
		composite.setLayout(new BorderLayout(0, 0));
		
		Label lblUsuario = new Label(composite, SWT.NONE);
		lblUsuario.setLayoutData(BorderLayout.CENTER);
		lblUsuario.setText("Usuario");
//		setImage(SWTResourceManager.getImage("/Users/Len/Pictures/137702-1440-900.jpg"));
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(678, 505);
		setBackgroundImage(SWTResourceManager.getImage("/Users/Len/Pictures/EEnaughtyman1280x854.jpg"));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
