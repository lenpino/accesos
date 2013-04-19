package cl.altair.accesos.wizard.edificio;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import cl.altair.acceso.dao.ComunaDAO;
import cl.altair.acceso.dao.RegionDAO;
import cl.altair.acceso.modelo.Comuna;
import cl.altair.acceso.modelo.Direccion;
import cl.altair.acceso.modelo.Region;
import cl.altair.utiles.generales.ComboUtil;
import cl.altair.utiles.generales.Validador;

public class WUIPaginaBasica extends WizardPage {
  private Composite container;
  private StyledText nombre;
  private Label lblTipo;
  private Combo comboTipo;
  private ComboViewer tipoViewer;
  private Label lblPisos;
  private Text pisos;
  private Label lblDireccin;
  private Text direccion;
  private Label lblRegion;
  private Combo regiones;
  private ComboViewer regionesViewer;
  private Label lblComuna;
  private Combo comunas;
  private ComboViewer comunasViewer;
  private Label lblCiudad;
  private Text ciudad;
  private RegionDAO rdao = new RegionDAO();
//  private ComunaDAO cdao = new ComunaDAO();

  public WUIPaginaBasica() {
    super("Primer paso");
    setTitle("Primer paso");
    setDescription("Datos b\u00E1sicos del edificio");
  }

  @Override
  public void createControl(Composite parent) {
	//Extrae los datos geograficos de la base de datos
	List<Region> listaRegiones = rdao.findAll();
//	final List<Comuna> listaComunas = cdao.findAll();
	
    container = new Composite(parent, SWT.NULL);
    GridLayout gl_container = new GridLayout(4, false);
    gl_container.marginLeft = 25;
    gl_container.marginRight = 80;
    gl_container.marginTop = 40;
    container.setLayout(gl_container);
    // Required to avoid an error in the system
    setControl(container);
    
    Label lblNombre = new Label(container, SWT.NONE);
    lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblNombre.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
    lblNombre.setText("Nombre:");
    
    nombre = new StyledText(container, SWT.BORDER | SWT.SINGLE);
    GridData gd_nombre = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
    gd_nombre.widthHint = 386;
    nombre.setLayoutData(gd_nombre);
    
    lblTipo = new Label(container, SWT.NONE);
    lblTipo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblTipo.setText("Tipo:");
    
    tipoViewer = new ComboViewer(container, SWT.NONE);
    comboTipo = tipoViewer.getCombo();
    String[] tiposEdificio = new String[] {"Oficina", "Residencial"};
    comboTipo.setItems(tiposEdificio);
    GridData gd_comboTipo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_comboTipo.widthHint = 106;
    comboTipo.setLayoutData(gd_comboTipo);    
    //Pone la oficina como valor default
    comboTipo.select(0);

    
    lblPisos = new Label(container, SWT.NONE);
    lblPisos.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblPisos.setText("Pisos:");
    
    pisos = new Text(container, SWT.BORDER);
    GridData gd_pisos = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
    gd_pisos.widthHint = 35;
    pisos.setLayoutData(gd_pisos);
    
    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    
    lblDireccin = new Label(container, SWT.NONE);
    lblDireccin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblDireccin.setText("Direcci\u00F3n:");
    
    direccion = new Text(container, SWT.BORDER);
    direccion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    
    lblCiudad = new Label(container, SWT.NONE);
    lblCiudad.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblCiudad.setText("Ciudad:");
    
    ciudad = new Text(container, SWT.BORDER);
    ciudad.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    new Label(container, SWT.NONE);
    new Label(container, SWT.NONE);
    
    lblRegion = new Label(container, SWT.NONE);
    lblRegion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblRegion.setText("Region:");
    
    //Combo de las regiones
    regionesViewer = new ComboViewer(container, SWT.NONE);
    regiones = regionesViewer.getCombo();
    GridData gd_regiones = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_regiones.widthHint = 105;  
    regionesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent arg0) {
		    IStructuredSelection selection = (IStructuredSelection) arg0.getSelection();
		    if(comunasViewer != null){
		    	//Obtiene el ID de la region seleccionada
		    	Integer id = ((Region) selection.getFirstElement()).getRegionId();
		    	//Descubre las comunes asociadas a la region seleccionada
		    	List<Comuna> lasComunasSeleccionadas = ComunaDAO.getComunasRegion(id);
		    	//Entrega los datos al viewer
		    	comunasViewer.setInput(lasComunasSeleccionadas.toArray());
		    	comunasViewer.setSelection(new StructuredSelection((lasComunasSeleccionadas.toArray()[0])));
		    }
		}
	});  
	regionesViewer.setContentProvider(ArrayContentProvider.getInstance());
	regionesViewer.setLabelProvider(new LabelProvider() {
		  @Override
		  public String getText(Object element) {
		    if (element instanceof Region) {
			      return ((Region)element).getRegionNombre();
			}
		    return super.getText(element);
		  }
		});		
	regionesViewer.setInput(listaRegiones.toArray());	
	regionesViewer.setSelection(new StructuredSelection(listaRegiones.toArray()[0]));
    regiones.setLayoutData(gd_regiones);
    
    lblComuna = new Label(container, SWT.NONE);
    lblComuna.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblComuna.setText("Comuna:");
    
    //Combo de las comunas
    comunasViewer = new ComboViewer(container, SWT.NONE);
    comunasViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent arg0) {
		    IStructuredSelection selection = (IStructuredSelection) arg0.getSelection();
		    if(selection.getFirstElement() != null){
		  		  if(nombre.getText().length() > 0 && pisos.getText().length() > 0 && direccion.getText().length() > 0 && ciudad.getText().length() > 0){
		  			  setDatosBasicos();
		  			  setPageComplete(true);
		  		  }		    	
		    }
		}
	});  

	comunasViewer.setContentProvider(ArrayContentProvider.getInstance());
	comunasViewer.setLabelProvider(new LabelProvider() {
		  @Override
		  public String getText(Object element) {
		    if (element instanceof Comuna) {
			      return ((Comuna)element).getComunaNombre();
			}
		    return super.getText(element);
		  }
		});		
    GridData gd_comunas = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_comunas.widthHint = 104;
    comunas = comunasViewer.getCombo();
    ComboUtil.addAutoCompleteFeature(comunas);
//	comunasViewer.setInput(listaComunas.toArray());	
//	comunasViewer.setSelection(new StructuredSelection(listaComunas.toArray()[0]));
    comunas.setLayoutData(gd_comunas);
    
    //Eventos registrados para el control de los widgets de esta pagina del wizard
    nombre.addModifyListener(new ModifyListener() {
  	  public void modifyText(ModifyEvent event) {
		  IStructuredSelection comunaSeleccionada = (IStructuredSelection)comunasViewer.getSelection();
		  IStructuredSelection selection = (IStructuredSelection)tipoViewer.getSelection();
		  if(nombre.getText().length() > 0 && 
				  pisos.getText().length() > 0 && 
				  direccion.getText().length() > 0 && 
				  ciudad.getText().length() > 0 &&
				  !comunaSeleccionada.isEmpty() &&
				  !selection.isEmpty()){
			  setDatosBasicos();
			  setPageComplete(true);
		  }else {
			  setPageComplete(false);
		  }
  	 }
    });

    pisos.addModifyListener(new ModifyListener() {
    	  public void modifyText(ModifyEvent event) {
    		  IStructuredSelection comunaSeleccionada = (IStructuredSelection)comunasViewer.getSelection();
    		  IStructuredSelection selection = (IStructuredSelection)tipoViewer.getSelection();
    		  if(nombre.getText().length() > 0 && 
    				  pisos.getText().length() > 0 && 
    				  direccion.getText().length() > 0 && 
    				  ciudad.getText().length() > 0 &&
    				  !comunaSeleccionada.isEmpty() &&
    				  !selection.isEmpty()){
    			  setDatosBasicos();
    			  setPageComplete(true);
    		  }else {
    			  setPageComplete(false);
    		  }
    	 }
      });

    direccion.addModifyListener(new ModifyListener() {
    	  public void modifyText(ModifyEvent event) {
    		  IStructuredSelection comunaSeleccionada = (IStructuredSelection)comunasViewer.getSelection();
    		  IStructuredSelection selection = (IStructuredSelection)tipoViewer.getSelection();
    		  if(nombre.getText().length() > 0 && 
    				  pisos.getText().length() > 0 && 
    				  direccion.getText().length() > 0 && 
    				  ciudad.getText().length() > 0 &&
    				  !comunaSeleccionada.isEmpty() &&
    				  !selection.isEmpty()){
    			  setDatosBasicos();
    			  setPageComplete(true);
    		  }else {
    			  setPageComplete(false);
    		  }
    	 }
      });

    ciudad.addModifyListener(new ModifyListener() {
    	  public void modifyText(ModifyEvent event) {
    		  IStructuredSelection comunaSeleccionada = (IStructuredSelection)comunasViewer.getSelection();
    		  IStructuredSelection selection = (IStructuredSelection)tipoViewer.getSelection();
    		  if(nombre.getText().length() > 0 && 
    				  pisos.getText().length() > 0 && 
    				  direccion.getText().length() > 0 && 
    				  ciudad.getText().length() > 0 &&
    				  !comunaSeleccionada.isEmpty() &&
    				  !selection.isEmpty()){
    			  setDatosBasicos();
    			  setPageComplete(true);
    		  }else {
    			  setPageComplete(false);
    		  }
    	 }
      });

    //Verifica que solo se ingresen numeros en el campo de pisos
    pisos.addListener(SWT.Verify, new Listener() {
    	public void handleEvent(Event e) {
    		e.doit = Validador.isNumber(e.text);
            return;
        }
    });

    setPageComplete(false);
  }

  

  private void setDatosBasicos(){
	  //Captura los valores seleccionados de cada uno de los combos
	  IStructuredSelection selection = (IStructuredSelection)tipoViewer.getSelection();  
	  IStructuredSelection regionSeleccionada = (IStructuredSelection)regionesViewer.getSelection();
	  IStructuredSelection comunaSeleccionada = (IStructuredSelection)comunasViewer.getSelection();
	  
      ((WizardEdificio)getWizard()).getEledificio().setNombre(nombre.getText());
      ((WizardEdificio)getWizard()).getEledificio().setTipo((String)selection.getFirstElement());
      ((WizardEdificio)getWizard()).getEledificio().setPisos(new Integer(pisos.getText()));
      
      //Crea el objeto con la direccion
      Direccion laDireccion =  new Direccion();
      laDireccion.setCiudad(ciudad.getText());
      laDireccion.setDireccion1(direccion.getText());
      laDireccion.setComuna((Comuna)comunaSeleccionada.getFirstElement());
      laDireccion.setRegion((Region)regionSeleccionada.getFirstElement());
      //Asigna la direccion creada al edificio
      ((WizardEdificio)getWizard()).getEledificio().setDireccion(laDireccion);
      
  }
} 