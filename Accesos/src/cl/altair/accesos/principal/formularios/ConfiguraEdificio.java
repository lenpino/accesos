package cl.altair.accesos.principal.formularios;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;

public class ConfiguraEdificio extends Dialog {

	protected Object result;
	protected Shell shlEdificio;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ConfiguraEdificio(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlEdificio.open();
		shlEdificio.layout();
		Display display = getParent().getDisplay();
		while (!shlEdificio.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlEdificio = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX);
		shlEdificio.setSize(326, 358);
		shlEdificio.setText("Edificio");
		shlEdificio.setLayout(new FormLayout());
		
		Composite composite = new Composite(shlEdificio, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 273);
		fd_composite.right = new FormAttachment(0, 316);
		fd_composite.top = new FormAttachment(0, 61);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new GridLayout(2, false));
		new Label(composite, SWT.NONE);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new FormLayout());
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
		gd_composite_1.widthHint = 300;
		gd_composite_1.heightHint = 196;
		composite_1.setLayoutData(gd_composite_1);
		
		Label lblNombre = new Label(composite_1, SWT.NONE);
		FormData fd_lblNombre = new FormData();
		fd_lblNombre.top = new FormAttachment(0, 10);
		fd_lblNombre.left = new FormAttachment(0, 10);
		lblNombre.setLayoutData(fd_lblNombre);
		lblNombre.setText("Nombre:");
		
		text = new Text(composite_1, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(lblNombre, 220, SWT.RIGHT);
		fd_text.top = new FormAttachment(0, 5);
		fd_text.left = new FormAttachment(lblNombre, 6);
		text.setLayoutData(fd_text);
		
		Label lblDireccion = new Label(composite_1, SWT.NONE);
		FormData fd_lblDireccion = new FormData();
		fd_lblDireccion.left = new FormAttachment(lblNombre, 0, SWT.LEFT);
		lblDireccion.setLayoutData(fd_lblDireccion);
		lblDireccion.setText("Tipo:");
		
		ComboViewer comboViewer = new ComboViewer(composite_1, SWT.NONE);
		Combo combo = comboViewer.getCombo();
		fd_lblDireccion.bottom = new FormAttachment(combo, 0, SWT.BOTTOM);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(text, 2);
		fd_combo.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_combo.left = new FormAttachment(text, 0, SWT.LEFT);
		combo.setLayoutData(fd_combo);
		
		Label lblPisos = new Label(composite_1, SWT.NONE);
		FormData fd_lblPisos = new FormData();
		fd_lblPisos.top = new FormAttachment(lblDireccion, 6);
		fd_lblPisos.left = new FormAttachment(lblNombre, 0, SWT.LEFT);
		lblPisos.setLayoutData(fd_lblPisos);
		lblPisos.setText("Pisos:");
		
		text_1 = new Text(composite_1, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_text_1.top = new FormAttachment(combo, 1);
		fd_text_1.left = new FormAttachment(text, 0, SWT.LEFT);
		text_1.setLayoutData(fd_text_1);
		
		Group grpDireccion = new Group(composite_1, SWT.NONE);
		grpDireccion.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpDireccion.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		grpDireccion.setText("Direccion");
		FormData fd_grpDireccion = new FormData();
		fd_grpDireccion.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_grpDireccion.left = new FormAttachment(0, 10);
		fd_grpDireccion.top = new FormAttachment(lblPisos, 6);
		fd_grpDireccion.bottom = new FormAttachment(100, 3);
		grpDireccion.setLayoutData(fd_grpDireccion);
		
		Label lblDireccion_1 = new Label(grpDireccion, SWT.NONE);
		lblDireccion_1.setBounds(10, 10, 74, 14);
		lblDireccion_1.setText("Direccion 1:");
		
		text_2 = new Text(grpDireccion, SWT.BORDER);
		text_2.setBounds(90, 5, 157, 19);
		
		Label lblDireccion_2 = new Label(grpDireccion, SWT.NONE);
		lblDireccion_2.setBounds(10, 30, 74, 14);
		lblDireccion_2.setText("Direccion 2:");
		
		text_3 = new Text(grpDireccion, SWT.BORDER);
		text_3.setBounds(90, 25, 157, 19);
		
		Label lblComuna = new Label(grpDireccion, SWT.NONE);
		lblComuna.setBounds(10, 50, 59, 14);
		lblComuna.setText("Comuna:");
		
		ComboViewer comboViewer_1 = new ComboViewer(grpDireccion, SWT.NONE);
		Combo combo_1 = comboViewer_1.getCombo();
		combo_1.setBounds(90, 42, 157, 22);
		
		Label lblCiudad = new Label(grpDireccion, SWT.NONE);
		lblCiudad.setBounds(10, 70, 59, 14);
		lblCiudad.setText("Ciudad:");
		
		text_4 = new Text(grpDireccion, SWT.BORDER);
		text_4.setBounds(90, 65, 157, 19);
		new Label(composite, SWT.NONE);
		
		Composite composite_2 = new Composite(shlEdificio, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.top = new FormAttachment(0, 10);
		fd_composite_2.left = new FormAttachment(0, 10);
		composite_2.setLayoutData(fd_composite_2);
		
		CLabel label = new CLabel(composite_2, SWT.NONE);
		label.setText("");
		label.setBackground(SWTResourceManager.getImage(ConfiguraEdificio.class, "/com/altair/accesos/recursos/ambiru.png"));
		label.setBounds(0, 0, 307, 45);
		
		Composite composite_3 = new Composite(shlEdificio, SWT.NONE);
		FormData fd_composite_3 = new FormData();
		fd_composite_3.right = new FormAttachment(100, -67);
		fd_composite_3.left = new FormAttachment(0, 81);
		fd_composite_3.bottom = new FormAttachment(100, -10);
		composite_3.setLayoutData(fd_composite_3);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button btnNewButton = new Button(composite_3, SWT.NONE);
		btnNewButton.setText("Cancelar");
		
		Button btnNewButton_1 = new Button(composite_3, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton_1.setText("Aceptar");
		
		Label label_1 = new Label(shlEdificio, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_composite_3.top = new FormAttachment(label_1, 1);
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(composite);
		fd_label_1.bottom = new FormAttachment(100, -37);
		fd_label_1.right = new FormAttachment(composite_2, 0, SWT.RIGHT);
		fd_label_1.left = new FormAttachment(0, 10);
		fd_label_1.height = 8;
		label_1.setLayoutData(fd_label_1);

	}
}
