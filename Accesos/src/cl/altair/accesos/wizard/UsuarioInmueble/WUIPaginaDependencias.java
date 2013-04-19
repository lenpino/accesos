package cl.altair.accesos.wizard.UsuarioInmueble;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FormData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import cl.altair.acceso.modelo.Dependencia;

public class WUIPaginaDependencias extends WizardPage {
  private Composite container;
  private Group group;
  private List listaDependencias;
  private ListViewer listViewer;
  private Button button;

  public WUIPaginaDependencias() {
    super("Dependencia");
    setTitle("Tercer Paso");
    setDescription("Asocia el nuevo usuario a sus dependencias");
    setPageComplete(false);
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    // Required to avoid an error in the system
    setControl(container);
    container.setLayout(new FormLayout());
    //Datos del grupo
    group = new Group(container, SWT.NONE);
    group.setText("Dependencias");
    group.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    group.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
    group.setLayout(new GridLayout(3, false));
    FormData fd_group = new FormData();
    fd_group.left = new FormAttachment(0, 10);
    fd_group.right = new FormAttachment(0, 580);
    fd_group.top = new FormAttachment(0, 10);
    fd_group.bottom = new FormAttachment(100, -10);
    group.setLayoutData(fd_group);
    //Leyendas
    Label lblTodasLasDependencias = new Label(group, SWT.NONE);
    GridData gd_lblTodasLasDependencias = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
    gd_lblTodasLasDependencias.heightHint = 20;
    lblTodasLasDependencias.setLayoutData(gd_lblTodasLasDependencias);
    lblTodasLasDependencias.setText("Todas las dependencias");
    new Label(group, SWT.NONE);
    
    Label lblDependenciasDelUsurio = new Label(group, SWT.NONE);
    lblDependenciasDelUsurio.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
    lblDependenciasDelUsurio.setText("Dependencias del usurio");
    
    //Lista de dependencias totales del edificio
    listViewer = new ListViewer(group, SWT.BORDER | SWT.V_SCROLL);
    listViewer.setContentProvider(ArrayContentProvider.getInstance());
    listViewer.setLabelProvider(new LabelProvider() {
	  @Override
	  public String getText(Object element) {
	    if (element instanceof Dependencia) {
	      Dependencia unaDependencia = (Dependencia) element;
	      return unaDependencia.getIdentificador();
	    }
	    return super.getText(element);
	  }
	});		
    listViewer.setInput(((WizardUsuarioInmueble)getWizard()).getEledificio().getDependencias());
    listaDependencias = listViewer.getList();
    GridData gd_listaDependencias = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2);
    gd_listaDependencias.heightHint = 210;
    listaDependencias.setLayoutData(gd_listaDependencias);
        
    button = new Button(group, SWT.NONE);
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    button.setText("Agregar");

    //Listado de dependencias del usuario
    final ListViewer listViewer_1 = new ListViewer(group, SWT.BORDER | SWT.V_SCROLL);
    listViewer_1.setContentProvider(ArrayContentProvider.getInstance());
    listViewer_1.setLabelProvider(new LabelProvider() {
	  @Override
	  public String getText(Object element) {
	    if (element instanceof Dependencia) {
	      Dependencia unaDependencia = (Dependencia) element;
	      return unaDependencia.getIdentificador();
	    }
	    return super.getText(element);
	  }
	});		
    listViewer_1.setInput(((WizardUsuarioInmueble)getWizard()).getElusuario().getDependencias());
    final List listaDependenciasUsuario = listViewer_1.getList();
    listaDependenciasUsuario.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));

    Button btnQuitar = new Button(group, SWT.NONE);
    btnQuitar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
    btnQuitar.setText("Quitar");
    
    button.addSelectionListener(new SelectionAdapter(){
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			//Agrega al usuario la dependencia seleccionada
			((WizardUsuarioInmueble)getWizard()).getElusuario().getDependencias().add(((Dependencia)listViewer.getElementAt(listaDependencias.getSelectionIndex())));
			//Agrega a la dependencia el usuario que se esta creando (No funciona en cascada)
			((Dependencia)listViewer.getElementAt(listaDependencias.getSelectionIndex())).setUsuarioInmueble(((WizardUsuarioInmueble)getWizard()).getElusuario());
			//Refresca el listado de dependencias del usuario
			listViewer_1.refresh();
			setPageComplete(true);
		}
    }); 

    btnQuitar.addSelectionListener(new SelectionAdapter(){
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			//Elimina la dependencia de la lista del usaurio
			if(!((WizardUsuarioInmueble)getWizard()).getElusuario().getDependencias().isEmpty())
				((WizardUsuarioInmueble)getWizard()).getElusuario().getDependencias().remove(((Dependencia)listViewer_1.getElementAt(listaDependenciasUsuario.getSelectionIndex())));
			//Refresca el listado de dependencias del usuario
			listViewer_1.refresh();
			setPageComplete(true);
		}
    });
    
    setControl(container);
  }
} 