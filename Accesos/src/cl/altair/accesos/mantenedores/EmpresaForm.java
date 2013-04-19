package cl.altair.accesos.mantenedores;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.layout.GridData;

public class EmpresaForm extends FormPage {
	private Text txtNewText;
	private Text txtNewText_1;
	private Text txtNewText_2;
	private Text txtNewText_3;
	private Table table;

	/**
	 * Create the form page.
	 * @param id
	 * @param title
	 */
	public EmpresaForm(String id, String title) {
		super(id, title);
	}

	/**
	 * Create the form page.
	 * @param editor
	 * @param id
	 * @param title
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter id "Some id"
	 * @wbp.eval.method.parameter title "Some title"
	 */
	public EmpresaForm(FormEditor editor, String id, String title) {
		super(editor, id, title);
		setPartName("Empresa");
	}

	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Creacion de Empresa");
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		managedForm.getForm().getBody().setLayout(new FormLayout());
		
		Composite composite = managedForm.getToolkit().createCompositeSeparator(managedForm.getForm().getBody());
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 28);
		fd_composite.right = new FormAttachment(0, 584);
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);
		managedForm.getToolkit().paintBordersFor(composite);
		
		Composite composite_1 = managedForm.getToolkit().createCompositeSeparator(managedForm.getForm().getBody());
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(0, 390);
		fd_composite_1.right = new FormAttachment(0, 584);
		fd_composite_1.top = new FormAttachment(0, 372);
		fd_composite_1.left = new FormAttachment(0, 10);
		composite_1.setLayoutData(fd_composite_1);
		managedForm.getToolkit().paintBordersFor(composite_1);
		
		Composite composite_2 = managedForm.getToolkit().createComposite(managedForm.getForm().getBody(), SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(0, 366);
		fd_composite_2.right = new FormAttachment(0, 584);
		fd_composite_2.top = new FormAttachment(0, 34);
		fd_composite_2.left = new FormAttachment(0, 10);
		composite_2.setLayoutData(fd_composite_2);
		managedForm.getToolkit().paintBordersFor(composite_2);
		composite_2.setLayout(new FormLayout());
		
		Label lblRut = managedForm.getToolkit().createLabel(composite_2, "RUT", SWT.NONE);
		FormData fd_lblRut = new FormData();
		fd_lblRut.left = new FormAttachment(0, 10);
		fd_lblRut.top = new FormAttachment(0, 30);
		lblRut.setLayoutData(fd_lblRut);
		
		txtNewText = managedForm.getToolkit().createText(composite_2, "New Text", SWT.NONE);
		txtNewText.setText("");
		fd_lblRut.right = new FormAttachment(100, -482);
		FormData fd_txtNewText = new FormData();
		fd_txtNewText.left = new FormAttachment(lblRut, 6);
		fd_txtNewText.top = new FormAttachment(0, 25);
		txtNewText.setLayoutData(fd_txtNewText);
		
		Label label = managedForm.getToolkit().createLabel(composite_2, "-", SWT.NONE);
		fd_txtNewText.right = new FormAttachment(label, -6);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 28);
		label.setLayoutData(fd_label);
		
		txtNewText_1 = managedForm.getToolkit().createText(composite_2, "New Text", SWT.NONE);
		fd_label.right = new FormAttachment(txtNewText_1, -6);
		txtNewText_1.setText("");
		FormData fd_txtNewText_1 = new FormData();
		fd_txtNewText_1.top = new FormAttachment(0, 25);
		fd_txtNewText_1.left = new FormAttachment(0, 259);
		fd_txtNewText_1.right = new FormAttachment(100, -294);
		txtNewText_1.setLayoutData(fd_txtNewText_1);
		
		Label lblRazonSocial = managedForm.getToolkit().createLabel(composite_2, "Razon Social", SWT.NONE);
		FormData fd_lblRazonSocial = new FormData();
		fd_lblRazonSocial.top = new FormAttachment(lblRut, 22);
		fd_lblRazonSocial.left = new FormAttachment(0, 10);
		lblRazonSocial.setLayoutData(fd_lblRazonSocial);
		
		txtNewText_2 = managedForm.getToolkit().createText(composite_2, "New Text", SWT.NONE);
		txtNewText_2.setText("");
		fd_txtNewText.bottom = new FormAttachment(txtNewText_2, -17);
		FormData fd_txtNewText_2 = new FormData();
		fd_txtNewText_2.right = new FormAttachment(100, -176);
		fd_txtNewText_2.left = new FormAttachment(lblRazonSocial, 16);
		fd_txtNewText_2.bottom = new FormAttachment(lblRazonSocial, 0, SWT.BOTTOM);
		txtNewText_2.setLayoutData(fd_txtNewText_2);
		
		Label lblNombreAlt = managedForm.getToolkit().createLabel(composite_2, "Nombre Alt.", SWT.NONE);
		FormData fd_lblNombreAlt = new FormData();
		fd_lblNombreAlt.top = new FormAttachment(lblRazonSocial, 22);
		fd_lblNombreAlt.left = new FormAttachment(lblRut, 0, SWT.LEFT);
		lblNombreAlt.setLayoutData(fd_lblNombreAlt);
		
		txtNewText_3 = managedForm.getToolkit().createText(composite_2, "New Text", SWT.NONE);
		txtNewText_3.setText("");
		FormData fd_txtNewText_3 = new FormData();
		fd_txtNewText_3.right = new FormAttachment(100, -176);
		fd_txtNewText_3.left = new FormAttachment(lblNombreAlt, 19);
		fd_txtNewText_3.bottom = new FormAttachment(lblNombreAlt, 0, SWT.BOTTOM);
		txtNewText_3.setLayoutData(fd_txtNewText_3);
		
		Label lblHolding = new Label(composite_2, SWT.NONE);
		FormData fd_lblHolding = new FormData();
		fd_lblHolding.top = new FormAttachment(lblNombreAlt, 24);
		fd_lblHolding.left = new FormAttachment(0, 10);
		lblHolding.setLayoutData(fd_lblHolding);
		managedForm.getToolkit().adapt(lblHolding, true, true);
		lblHolding.setText("Holding");
		
		ComboViewer comboViewer = new ComboViewer(composite_2, SWT.NONE);
		Combo combo = comboViewer.getCombo();
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(100, -176);
		fd_combo.left = new FormAttachment(lblHolding, 42);
		fd_combo.bottom = new FormAttachment(lblHolding, 0, SWT.BOTTOM);
		combo.setLayoutData(fd_combo);
		managedForm.getToolkit().paintBordersFor(combo);
		
		Section sctnEmpresasRelacionadas = managedForm.getToolkit().createSection(composite_2, Section.TWISTIE | Section.TITLE_BAR);
		FormData fd_sctnEmpresasRelacionadas = new FormData();
		fd_sctnEmpresasRelacionadas.bottom = new FormAttachment(lblHolding, 154, SWT.BOTTOM);
		fd_sctnEmpresasRelacionadas.top = new FormAttachment(lblHolding, 22);
		fd_sctnEmpresasRelacionadas.right = new FormAttachment(txtNewText_2, 0, SWT.RIGHT);
		fd_sctnEmpresasRelacionadas.left = new FormAttachment(0, 10);
		sctnEmpresasRelacionadas.setLayoutData(fd_sctnEmpresasRelacionadas);
		managedForm.getToolkit().paintBordersFor(sctnEmpresasRelacionadas);
		sctnEmpresasRelacionadas.setText("Empresas Relacionadas");
		sctnEmpresasRelacionadas.setExpanded(true);
		
		Composite composite_4 = managedForm.getToolkit().createComposite(sctnEmpresasRelacionadas, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(composite_4);
		sctnEmpresasRelacionadas.setClient(composite_4);
		composite_4.setLayout(new GridLayout(5, false));
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		
		ListViewer listViewer = new ListViewer(composite_4, SWT.BORDER | SWT.V_SCROLL);
		List list = listViewer.getList();
		new Label(composite_4, SWT.NONE);
		
		CheckboxTableViewer checkboxTableViewer = CheckboxTableViewer.newCheckList(composite_4, SWT.BORDER | SWT.FULL_SELECTION);
		table = checkboxTableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().paintBordersFor(table);
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		
		Button btnNewButton = managedForm.getToolkit().createButton(composite_4, "Agregar", SWT.NONE);
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		
		Section sctnUnidades = managedForm.getToolkit().createSection(composite_2, Section.TWISTIE | Section.TITLE_BAR);
		FormData fd_sctnUnidades = new FormData();
		fd_sctnUnidades.right = new FormAttachment(txtNewText_2, 0, SWT.RIGHT);
		fd_sctnUnidades.bottom = new FormAttachment(100);
		fd_sctnUnidades.left = new FormAttachment(lblRut, 0, SWT.LEFT);
		sctnUnidades.setLayoutData(fd_sctnUnidades);
		managedForm.getToolkit().paintBordersFor(sctnUnidades);
		sctnUnidades.setText("Unidades");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		
		Composite composite_3 = managedForm.getToolkit().createComposite(managedForm.getForm().getBody(), SWT.NONE);
		FormData fd_composite_3 = new FormData();
		fd_composite_3.bottom = new FormAttachment(0, 429);
		fd_composite_3.right = new FormAttachment(0, 584);
		fd_composite_3.top = new FormAttachment(0, 396);
		fd_composite_3.left = new FormAttachment(0, 10);
		composite_3.setLayoutData(fd_composite_3);
		managedForm.getToolkit().paintBordersFor(composite_3);
	}
}
