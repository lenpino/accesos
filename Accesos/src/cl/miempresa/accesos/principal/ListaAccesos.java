package cl.miempresa.accesos.principal;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import cl.altair.acceso.dao.AccesoDAO;
import cl.altair.acceso.modelo.Acceso;
import cl.altair.utiles.generales.Utils;

public class ListaAccesos extends ViewPart {

	private TableViewer tableViewer;
	java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("HH:mm:ss"); 
	java.util.Locale pais = java.util.Locale.GERMANY;
	java.text.DecimalFormat dec = (java.text.DecimalFormat)java.text.NumberFormat.getInstance(pais);
	private final static Logger traza = Logger.getLogger(ListaAccesos.class.getName());

	@Override
	public void createPartControl(Composite arg0) {
		createViewer(arg0);
	}

	private void createViewer(Composite parent) {
		traza.info("Creando listado de visitas");
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, tableViewer);
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(getAccesos());
		// Layout the viewer
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		tableViewer.getControl().setLayoutData(gridData);
	}

	public TableViewer getViewer() {
		return tableViewer;
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Nombre", "Apellido", "RUT", "Hora Ingreso", "Empresa", "Dependencia", "Hora Salida" };
		int[] bounds = { 110, 110, 110, 110, 110, 110, 110 };
		
		// Primera columna Nombre Visita
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;		
				return p.getVisita().getNombre();
			}
		});
		//Segunda columna Apellido Visita
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;		
				return p.getVisita().getApellido();
			}
		});

		// Tercera columna RUT Visita
		col = createTableViewerColumn(titles[2], bounds[2], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;		
				return Utils.formatRut(p.getVisita().getRut().toString() + p.getVisita().getDv());
			}
		});
		
		// Cuarta columna Hora Ingreso
		col = createTableViewerColumn(titles[3], bounds[3], 1);		
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;
				return fmt.format(p.getFechaingreso()==null?0:p.getFechaingreso());
			}
		});

		// Quinta columna Empresa
		col = createTableViewerColumn(titles[4], bounds[4], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;
				return p.getDestino()==null?"":p.getDestino().getNombre();
			}
		});
		
		// Sexta Columna Dependencia
		col = createTableViewerColumn(titles[5], bounds[5], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;
				return p.getDependencia().getIdentificador();
			}
		});
/*
		// Cuarta columna
		col = createTableViewerColumn(titles[4], bounds[4], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;
				return p.getElMotivo() == null?"":p.getElMotivo().getMotivo();
			}
		});
*/
		
		// Septima columna Hora Salida
		col = createTableViewerColumn(titles[6], bounds[6], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Acceso p = (Acceso) element;
				return fmt.format(p.getFechasalida()==null?0:p.getFechasalida());
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private List<Acceso> getAccesos() {
		List<Acceso> listaAccesos;
		// Busco los accesos en la BD
		AccesoDAO adao = new AccesoDAO();
		listaAccesos = adao.getTodayList();
		return listaAccesos;
	}
	@Override
	public void setFocus() {
		tableViewer.getControl().setFocus();

	}
	//Se actualiza la tabla de accesos
	public void refresh() {
		tableViewer.setInput(getAccesos());
		tableViewer.refresh();
	} 
}
