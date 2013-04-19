package cl.altair.accesos.principal.formularios;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import cl.altair.acceso.modelo.Dependencia;
import cl.altair.acceso.modelo.Edificio;
import org.eclipse.swt.layout.FillLayout;

public class NuevoUsuarioInmueble extends Dialog {

	protected Object result;
	protected Shell shlNuevaEmpresa;
	private Text rut;
	private Text nombre;
	private Text nombrePersona;
	private Text apellidoPersona;
	private Text email;
	private Text celular;
	private Text text_6;
	private Text razonSocial;
	private Text nombreEmpresa;
	private Text telefono;
	private Text dv;
	private Edificio edificio;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NuevoUsuarioInmueble(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlNuevaEmpresa.open();
		shlNuevaEmpresa.layout();
		Display display = getParent().getDisplay();
		while (!shlNuevaEmpresa.isDisposed()) {
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
		shlNuevaEmpresa = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX);
		shlNuevaEmpresa.setSize(450, 525);
		shlNuevaEmpresa.setText("Nuevo Usuario Inmueble");
		shlNuevaEmpresa.setLayout(new FormLayout());
		
		Composite composite = new Composite(shlNuevaEmpresa, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		fd_composite.right = new FormAttachment(0, 450);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FormLayout());
		
		Label lblNombre = new Label(composite, SWT.NONE);
		FormData fd_lblNombre = new FormData();
		lblNombre.setLayoutData(fd_lblNombre);
		lblNombre.setText("Nombre:");
		
		Label lblRut = new Label(composite, SWT.NONE);
		fd_lblNombre.top = new FormAttachment(lblRut, 0, SWT.TOP);
		FormData fd_lblRut = new FormData();
		fd_lblRut.left = new FormAttachment(0, 37);
		lblRut.setLayoutData(fd_lblRut);
		lblRut.setText("RUT:");
		
		Group grpTipoUsuario = new Group(composite, SWT.NONE);
		fd_lblRut.bottom = new FormAttachment(grpTipoUsuario, -26);
		grpTipoUsuario.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpTipoUsuario.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		grpTipoUsuario.setText("Tipo Usuario");
		FormData fd_grpTipoUsuario = new FormData();
		fd_grpTipoUsuario.left = new FormAttachment(0, 37);
		fd_grpTipoUsuario.top = new FormAttachment(0, 111);
		grpTipoUsuario.setLayoutData(fd_grpTipoUsuario);
		
		Button btnPersona = new Button(grpTipoUsuario, SWT.RADIO);
		btnPersona.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		btnPersona.setBounds(10, 10, 91, 18);
		btnPersona.setText("Persona");
		
		Button btnEmpresa = new Button(grpTipoUsuario, SWT.RADIO);
		btnEmpresa.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		btnEmpresa.setBounds(10, 26, 91, 18);
		btnEmpresa.setText("Empresa");
		
		Group grpPersona = new Group(composite, SWT.NONE);
		grpPersona.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpPersona.setFont(SWTResourceManager.getFont("Verdana", 10, SWT.NORMAL));
		fd_grpTipoUsuario.bottom = new FormAttachment(grpPersona, -6);
		grpPersona.setText("Persona");
		FormData fd_grpPersona = new FormData();
		fd_grpPersona.top = new FormAttachment(0, 188);
		fd_grpPersona.right = new FormAttachment(100, -40);
		fd_grpPersona.left = new FormAttachment(0, 37);
		grpPersona.setLayoutData(fd_grpPersona);
		
		Label lblNombre_1 = new Label(grpPersona, SWT.NONE);
		lblNombre_1.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblNombre_1.setBounds(10, 10, 59, 14);
		lblNombre_1.setText("Nombre:");
		
		Label lblApellido = new Label(grpPersona, SWT.NONE);
		lblApellido.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblApellido.setBounds(10, 30, 59, 14);
		lblApellido.setText("Apellido:");
		
		Label lblCorreoElec = new Label(grpPersona, SWT.NONE);
		lblCorreoElec.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblCorreoElec.setBounds(10, 50, 59, 14);
		lblCorreoElec.setText("Email:");
		
		Label lblCelular = new Label(grpPersona, SWT.NONE);
		lblCelular.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblCelular.setBounds(10, 70, 59, 14);
		lblCelular.setText("Celular:");
		
		Label lblFechaNac = new Label(grpPersona, SWT.NONE);
		lblFechaNac.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblFechaNac.setBounds(10, 90, 59, 14);
		lblFechaNac.setText("Fecha Nac.:");
		
		nombrePersona = new Text(grpPersona, SWT.BORDER);
		nombrePersona.setBounds(75, 5, 274, 19);
		
		apellidoPersona = new Text(grpPersona, SWT.BORDER);
		apellidoPersona.setBounds(75, 25, 274, 19);
		
		email = new Text(grpPersona, SWT.BORDER);
		email.setBounds(75, 45, 274, 19);
		
		celular = new Text(grpPersona, SWT.BORDER);
		celular.setBounds(75, 65, 274, 19);
		
		text_6 = new Text(grpPersona, SWT.BORDER);
		text_6.setBounds(75, 85, 274, 19);
		
		rut = new Text(composite, SWT.BORDER);
		FormData fd_rut = new FormData();
		fd_rut.bottom = new FormAttachment(grpTipoUsuario, -26);
		fd_rut.left = new FormAttachment(lblRut, 6);
		fd_rut.right = new FormAttachment(100, -285);
		rut.setLayoutData(fd_rut);
		
		nombre = new Text(composite, SWT.BORDER);
		FormData fd_nombre = new FormData();
		fd_nombre.right = new FormAttachment(grpPersona, 0, SWT.RIGHT);
		fd_nombre.top = new FormAttachment(rut, 0, SWT.TOP);
		fd_nombre.left = new FormAttachment(lblNombre, 6);
		nombre.setLayoutData(fd_nombre);
		
		Group grpDependencias = new Group(composite, SWT.NONE);
		grpDependencias.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpDependencias.setFont(SWTResourceManager.getFont("Verdana", 10, SWT.NORMAL));
		fd_grpTipoUsuario.right = new FormAttachment(100, -303);
		grpDependencias.setText("Dependencias");
		FormData fd_grpDependencias = new FormData();
		fd_grpDependencias.top = new FormAttachment(grpTipoUsuario, 0, SWT.TOP);
		fd_grpDependencias.bottom = new FormAttachment(grpTipoUsuario, 71);
		fd_grpDependencias.left = new FormAttachment(grpTipoUsuario, 6);
		fd_grpDependencias.right = new FormAttachment(100, -35);
		grpDependencias.setLayoutData(fd_grpDependencias);
		//Lista de dependencias del edificio
		ListViewer listViewer = new ListViewer(grpDependencias, SWT.BORDER | SWT.V_SCROLL);
		
		listViewer.setContentProvider(new IStructuredContentProvider() {			
			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {			
			}	
			@Override
			public void dispose() {
			}			
			@Override
			public Object[] getElements(Object arg0) {
				Set<Dependencia> lasDependencias = (Set<Dependencia>)arg0;
				return lasDependencias.toArray();
			}
		});
		
		listViewer.setInput(getDependencias());
		listViewer.setLabelProvider(new LabelProvider() {
		      public Image getImage(Object element) {
		          return null;
		        }
		        public String getText(Object element) {
				    if (element instanceof Dependencia) {
					      Dependencia unaDependencia = (Dependencia) element;
					      return unaDependencia.getIdentificador();
					    }
					return super.getText(element);
		        }
		});
		
	    listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
	        public void selectionChanged(SelectionChangedEvent event) {
	          IStructuredSelection selection = (IStructuredSelection)event.getSelection();
	          StringBuffer sb = new StringBuffer("Selection - ");
	          sb.append("tatal " + selection.size() + " items selected: ");
	          for(Iterator iterator = selection.iterator(); iterator.hasNext(); ) {
	            sb.append(iterator.next() + ", ");
	          }
	          System.out.println(sb);
	        }
	      });

		List list = listViewer.getList();
//		list.setItems(new String[] {"Hola", "Chao"});
		list.setBounds(10, 10, 152, 34);
		
		Button btnAgregar = new Button(grpDependencias, SWT.NONE);
		btnAgregar.setBounds(167, 16, 81, 28);
		btnAgregar.setText("Agregar");
		if(getDependencias().isEmpty())
			btnAgregar.setEnabled(false);
		
		Group grpEmpresa = new Group(composite, SWT.NONE);
		fd_grpPersona.bottom = new FormAttachment(grpEmpresa, -6);
		grpEmpresa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpEmpresa.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		grpEmpresa.setText("Empresa");
		FormData fd_grpEmpresa = new FormData();
		fd_grpEmpresa.top = new FormAttachment(0, 327);
		fd_grpEmpresa.bottom = new FormAttachment(100, -26);
		fd_grpEmpresa.right = new FormAttachment(100, -40);
		fd_grpEmpresa.left = new FormAttachment(0, 37);
		grpEmpresa.setLayoutData(fd_grpEmpresa);
		
		Label lblRazonSocial = new Label(grpEmpresa, SWT.NONE);
		lblRazonSocial.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblRazonSocial.setBounds(10, 10, 75, 14);
		lblRazonSocial.setText("Razon Social:");
		
		Label lblNombre_2 = new Label(grpEmpresa, SWT.NONE);
		lblNombre_2.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblNombre_2.setBounds(10, 30, 59, 14);
		lblNombre_2.setText("Nombre:");
		
		razonSocial = new Text(grpEmpresa, SWT.BORDER);
		razonSocial.setBounds(91, 5, 258, 19);
		
		Label lblTelfono = new Label(grpEmpresa, SWT.NONE);
		lblTelfono.setFont(SWTResourceManager.getFont("Lucida Grande", 10, SWT.NORMAL));
		lblTelfono.setBounds(10, 50, 59, 14);
		lblTelfono.setText("Tel\u00E9fono:");
		
		nombreEmpresa = new Text(grpEmpresa, SWT.BORDER);
		nombreEmpresa.setBounds(91, 25, 258, 19);
		
		telefono = new Text(grpEmpresa, SWT.BORDER);
		telefono.setBounds(91, 45, 258, 19);
		Composite composite_1 = new Composite(composite, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(rut, -11);
		fd_composite_1.top = new FormAttachment(0, 10);
		fd_composite_1.left = new FormAttachment(0, 10);
		fd_composite_1.right = new FormAttachment(100, -10);
		composite_1.setLayoutData(fd_composite_1);
		
		CLabel label = new CLabel(composite_1, SWT.NONE);
		label.setBackground(SWTResourceManager.getImage(NuevoUsuarioInmueble.class, "/com/altair/accesos/recursos/ambiru.png"));
		label.setBounds(0, 0, 430, 45);
		label.setText("");
		
		dv = new Text(composite, SWT.BORDER);
		fd_lblNombre.left = new FormAttachment(dv, 6);
		FormData fd_dv = new FormData();
		fd_dv.top = new FormAttachment(rut, 0, SWT.TOP);
		fd_dv.left = new FormAttachment(rut, 6);
		fd_dv.right = new FormAttachment(100, -259);
		dv.setLayoutData(fd_dv);
		
		Composite composite_2 = new Composite(shlNuevaEmpresa, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(100, -10);
		fd_composite_2.left = new FormAttachment(0, 120);
		fd_composite_2.right = new FormAttachment(0, 324);
		composite_2.setLayoutData(fd_composite_2);
		
		Button btnCancelar = new Button(composite_2, SWT.NONE);
		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlNuevaEmpresa.dispose();
			}
		});
		btnCancelar.setText("Cancelar");
		
		Button btnNewButton = new Button(composite_2, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setText("Grabar");
		
		Label label_1 = new Label(shlNuevaEmpresa, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_composite_2.top = new FormAttachment(0, 465);
		fd_composite.bottom = new FormAttachment(100, -60);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(composite_2, -6);
		fd_label_1.left = new FormAttachment(0, 10);
		fd_label_1.right = new FormAttachment(0, 440);
		fd_label_1.top = new FormAttachment(0, 457);
		label_1.setLayoutData(fd_label_1);

	}
	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}

	private Set<Dependencia> getDependencias(){
		return this.edificio.getDependencias();
	}
}
