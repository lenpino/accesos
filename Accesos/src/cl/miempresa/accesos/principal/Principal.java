package cl.miempresa.accesos.principal;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;

public class Principal {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Principal window = new Principal();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(764, 551);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);
		mntmFile.setText("File...");
		
		MenuItem mntmEdit = new MenuItem(menu, SWT.NONE);
		mntmEdit.setText("Edit");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(null);
		
		Group grpDirectorio = new Group(composite, SWT.NONE);
		grpDirectorio.setText("Directorio");
		grpDirectorio.setBounds(10, 86, 261, 387);
		
		Group grpListadoDeAccesos = new Group(composite, SWT.NONE);
		grpListadoDeAccesos.setText("Listado de Accesos");
		grpListadoDeAccesos.setBounds(277, 86, 477, 387);
		
		Label label = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 479, 744, 2);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(638, 491, 94, 28);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setBounds(538, 491, 94, 28);
		btnNewButton_1.setText("New Button");
		
		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(10, 10, 744, 20);
		
		ToolItem tltmAccion = new ToolItem(toolBar, SWT.NONE);
		tltmAccion.setText("Accion 1");
		
		ToolItem tltmAccion_1 = new ToolItem(toolBar, SWT.NONE);
		tltmAccion_1.setText("Accion 2");
		
		ToolItem tltmRadio = new ToolItem(toolBar, SWT.RADIO);
		tltmRadio.setText("Radio");
		
		ToolItem tltmItemDrop = new ToolItem(toolBar, SWT.DROP_DOWN);
		tltmItemDrop.setText("Item drop");
		
		ToolItem tltmCheckItem = new ToolItem(toolBar, SWT.CHECK);
		tltmCheckItem.setText("Check item");
		
		CoolBar coolBar = new CoolBar(composite, SWT.FLAT);
		coolBar.setBounds(10, 39, 744, 30);
		
		CoolItem coolItem_1 = new CoolItem(coolBar, SWT.NONE);
		coolItem_1.setText("Accion 1");
		
		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);

	}
}
