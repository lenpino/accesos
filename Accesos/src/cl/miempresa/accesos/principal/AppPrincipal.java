package cl.miempresa.accesos.principal;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Group;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.FillLayout;

public class AppPrincipal {

	protected Shell shell;
	TableViewer tableViewer;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppPrincipal window = new AppPrincipal();
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
		shell.setSize(754, 615);
		shell.setText("Software de Gestion de Acceso");
		shell.setLayout(new FormLayout());
		
		Composite composite = new Composite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		composite.setLayout(new BorderLayout(0, 0));
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 593);
		fd_composite.right = new FormAttachment(0, 752);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.NORTH);
		composite_1.setLayout(new GridLayout(1, false));
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.SOUTH);
		
		SashForm sashForm_1 = new SashForm(composite, SWT.NONE);
		sashForm_1.setLayoutData(BorderLayout.CENTER);
		
		Group grpDirectorio = new Group(sashForm_1, SWT.NONE);
		grpDirectorio.setText("Directorio");
		grpDirectorio.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_3 = new Composite(grpDirectorio, SWT.NONE);
		composite_3.setLayout(new GridLayout(1, false));
		
		ToolBar toolBar = new ToolBar(composite_3, SWT.FLAT | SWT.RIGHT);
/*		
		TreeViewer treeViewer = new TreeViewer(composite_3, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setSize(364, 458);
*/		
		ArbolDirectorio elArbol = new ArbolDirectorio();
		elArbol.createPartControl(composite_3);
		
		Group grpListadoAccesos = new Group(sashForm_1, SWT.NONE);
		grpListadoAccesos.setText("Listado Accesos");
		grpListadoAccesos.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_4 = new Composite(grpListadoAccesos, SWT.NONE);
		composite_4.setLayout(new GridLayout(1, false));
		
		ListaAccesos listado = new ListaAccesos();
		listado.createPartControl(composite_4);
		
		ToolBar toolBar_1 = new ToolBar(composite_4, SWT.FLAT | SWT.RIGHT);
		sashForm_1.setWeights(new int[] {1, 1});
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("Archivo");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmAbrir = new MenuItem(menu_1, SWT.NONE);
		mntmAbrir.setText("Abrir...");
		
		MenuItem mntmCerrar = new MenuItem(menu_1, SWT.NONE);
		mntmCerrar.setText("Cerrar");
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setText("Configuraci\u00F3n");

	}
  
}
