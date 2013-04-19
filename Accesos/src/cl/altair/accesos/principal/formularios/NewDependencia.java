package cl.altair.accesos.principal.formularios;

import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;

import cl.altair.acceso.dao.EdificioDAO;
import cl.altair.acceso.dao.EntityManagerHelper;
import cl.altair.acceso.dao.TipoDependenciaDAO;
import cl.altair.acceso.modelo.Dependencia;
import cl.altair.acceso.modelo.Edificio;
import cl.altair.acceso.modelo.TipoDependencia;
import cl.miempresa.accesos.modelo.providers.TreeEdificio;
import cl.miempresa.accesos.principal.ArbolEdificio;

public class NewDependencia extends Dialog {

	protected Object result;
	protected Shell shlDependencia;
	private Text elnombre;
	private List<TipoDependencia> listaTipoDependencia;
	private Integer[] listaPisos;
	private Edificio edificio;
	private ArbolEdificio elArbolEdificio;
	private EdificioDAO edao = new EdificioDAO();



	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NewDependencia(Shell parent, int style) {
		super(parent, style);
		setText("Nueva Dependencia");
		//Lee el edificio desde la BD
		edificio = edao.findAll().get(0);
		//Saca los pisos del edificio para desplegarlos en el combo
		if(edificio.getPisos() == null){
			listaPisos = new Integer[0];
		} else {
			listaPisos = new Integer[edificio.getPisos().intValue()];
			for(int i=0;i<listaPisos.length;i++){
				listaPisos[i] = new Integer(i+1);
			}
		}
		listaTipoDependencia = getTipoDependencias();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlDependencia.open();
		shlDependencia.layout();
		Display display = getParent().getDisplay();
		while (!shlDependencia.isDisposed()) {
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
		shlDependencia = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX);
		shlDependencia.setSize(329, 242);
		shlDependencia.setText("Dependencia");
		shlDependencia.setLayout(new FormLayout());
		
		Composite cuerpo = new Composite(shlDependencia, SWT.NONE);
		FormData fd_cuerpo = new FormData();
		fd_cuerpo.top = new FormAttachment(0);
		fd_cuerpo.left = new FormAttachment(0);
		fd_cuerpo.right = new FormAttachment(0, 248);
		fd_cuerpo.bottom = new FormAttachment(0, 150);
		cuerpo.setLayoutData(fd_cuerpo);
		cuerpo.setLayout(new GridLayout(3, false));
		new Label(cuerpo, SWT.NONE);
		new Label(cuerpo, SWT.NONE);
		new Label(cuerpo, SWT.NONE);
		new Label(cuerpo, SWT.NONE);
		
		Label lblNombre = new Label(cuerpo, SWT.NONE);
		lblNombre.setText("Nombre:");
		
		elnombre = new Text(cuerpo, SWT.BORDER);
		GridData gd_elnombre = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_elnombre.widthHint = 167;
		elnombre.setLayoutData(gd_elnombre);
		new Label(cuerpo, SWT.NONE);
		
		Label lblTipo = new Label(cuerpo, SWT.NONE);
		lblTipo.setText("Tipo:");
		
		final ComboViewer comboViewer = new ComboViewer(cuerpo, SWT.NONE);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new LabelProvider() {
			  @Override
			  public String getText(Object element) {
			    if (element instanceof TipoDependencia) {
			      TipoDependencia unTipoDependencia = (TipoDependencia) element;
			      return unTipoDependencia.getTipoDependencia();
			    }
			    return super.getText(element);
			  }
			});		
		comboViewer.setInput(listaTipoDependencia.toArray());
		comboViewer.setSelection(new StructuredSelection(listaTipoDependencia.toArray()[0]));
		
		final Combo eltipo = comboViewer.getCombo();
		
		GridData gd_eltipo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_eltipo.widthHint = 232;
		eltipo.setLayoutData(gd_eltipo);
		new Label(cuerpo, SWT.NONE);
		
		Label lblPiso = new Label(cuerpo, SWT.NONE);
		lblPiso.setText("Piso:");
		
		final ComboViewer comboViewer_1 = new ComboViewer(cuerpo, SWT.NONE);
		comboViewer_1.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer_1.setLabelProvider(new LabelProvider() {
			  @Override
			  public String getText(Object element) {
			    if (element instanceof Integer) {
				      Integer unPiso = (Integer) element;
				      return unPiso.toString();
				}
			    return super.getText(element);
			  }
			});		
		comboViewer_1.setInput(listaPisos);
		comboViewer_1.setSelection(new StructuredSelection(listaPisos[0]));

		final Combo elpiso = comboViewer_1.getCombo();
		elpiso.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite botones = new Composite(shlDependencia, SWT.NONE);
		FormData fd_botones = new FormData();
		fd_botones.bottom = new FormAttachment(100, -10);
		fd_botones.top = new FormAttachment(100, -64);
		fd_botones.left = new FormAttachment(0);
		fd_botones.right = new FormAttachment(0, 329);
		botones.setLayoutData(fd_botones);
		
		//Boton CANCELAR ///////////////////////////////////////////////////////////////////////////////////////////////////////
		Button btnCancelar = new Button(botones, SWT.NONE);
		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlDependencia.dispose();
			}
		});
		btnCancelar.setBounds(68, 26, 94, 28);
		btnCancelar.setText("Cancelar");
		
		//Boton ACEPTAR ////////////////////////////////////////////////////////////////////////////////////////////////////////
		final Button btnAceptar = new Button(botones, SWT.NONE);
		btnAceptar.setEnabled(false);
		//Accion de grabar la nueva dependencia con el boton ACEPTAR
		btnAceptar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Lee los datos seleccionados
				TipoDependencia untipo = (TipoDependencia)((IStructuredSelection) comboViewer.getSelection()).getFirstElement();
				Integer unpiso = (Integer)((IStructuredSelection) comboViewer_1.getSelection()).getFirstElement();	
				
				//Setea los campos de la nueva dependencia
				Dependencia laDependencia = new Dependencia();
				laDependencia.setIdentificador(elnombre.getText());
				laDependencia.setTipo(untipo);
				laDependencia.setPiso(unpiso);
				edificio.agregaDependencia(laDependencia);
				
				//Actualiza el edificio
				EntityManagerHelper.beginTransaction();
		    	edao.update(edificio);
		    	EntityManagerHelper.commit();
		 		EntityManagerHelper.closeEntityManager();
		 		
		 		//Refresca el arbol con la nueva dependencia
		 		TreeEdificio todoArbol = new TreeEdificio();
				Edificio[] arbol = new Edificio[1];
				arbol[0] = edificio;			
				todoArbol.setEdificio(arbol);
				todoArbol.setNombre(edificio.getNombre());
		 		elArbolEdificio.getViewer().setInput(todoArbol);
				elArbolEdificio.getViewer().refresh();
				
				//Cierra la ventana
		 		shlDependencia.dispose();
			}
		});
		btnAceptar.setBounds(168, 26, 94, 28);
		btnAceptar.setText("Aceptar");
		//Linea
		Label label = new Label(botones, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 10, 309, 2);
		
		elnombre.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(elnombre.getText().length() > 0){
					btnAceptar.setEnabled(true);
					Display.getCurrent().update();
				} else {
					btnAceptar.setEnabled(false);
					Display.getCurrent().update();
				}
			}
		});

	}

	private List<TipoDependencia> getTipoDependencias(){
		TipoDependenciaDAO tddao = new TipoDependenciaDAO();
		return tddao.findAll();
	}

	public ArbolEdificio getElArbolEdificio() {
		return elArbolEdificio;
	}

	public void setElArbolEdificio(ArbolEdificio elArbolEdificio) {
		this.elArbolEdificio = elArbolEdificio;
	}
}
