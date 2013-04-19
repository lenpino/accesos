package cl.miempresa.accesos.principal;

import java.util.Set;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.custom.CLabel;

import cl.altair.acceso.dao.AccesoDAO;
import cl.altair.acceso.dao.EntityManagerHelper;
import cl.altair.acceso.dao.MotivoDAO;
import cl.altair.acceso.dao.PersonaDAO;
import cl.altair.acceso.modelo.Acceso;
import cl.altair.acceso.modelo.Dependencia;
import cl.altair.acceso.modelo.Edificio;
import cl.altair.acceso.modelo.Persona;
import cl.altair.acceso.modelo.UsuarioInmueble;
import cl.altair.utiles.generales.Validador;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;


public class RegistroVisita extends Dialog {

	protected Object result;
	protected Shell shlRegistroDeAcceso;
	private Text textRut;
	private  ToolTip tip;
	private Text textNombre;
	private Text textApellidoPaterno;
	private Text textApellidoMaterno;
	private Text textDV;
	private Text textEmail;
	private Text textFonoContacto;
	//private List<Empresa> empresas;
	private ComboViewer comboDependencia;
	private ComboViewer comboViewerUsuario;
	private Edificio edificio;
	private ListaAccesos listado;
	private Button btnGrabar;
	private DefaultToolTip toolTip;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public RegistroVisita(Shell parent, int style) {
		super(parent, style);
		setText("Registro de Acceso");
//		getEmpresas();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlRegistroDeAcceso.open();
		shlRegistroDeAcceso.layout();
		Display display = getParent().getDisplay();
		while (!shlRegistroDeAcceso.isDisposed()) {
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
		shlRegistroDeAcceso = new Shell(getParent(), SWT.BORDER | SWT.TITLE | SWT.APPLICATION_MODAL);
		shlRegistroDeAcceso.setSize(490, 451);
		shlRegistroDeAcceso.setText("Registro de Acceso");
		shlRegistroDeAcceso.setLayout(new FormLayout());
		
		Composite composite = new Composite(shlRegistroDeAcceso, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(102, 153, 153));
		FormData fd_composite = new FormData();
		fd_composite.left = new FormAttachment(0, 10);
		fd_composite.bottom = new FormAttachment(0, 57);
		fd_composite.top = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);
		
		Group grpContacto = new Group(shlRegistroDeAcceso, SWT.NONE);
		fd_composite.right = new FormAttachment(grpContacto, 0, SWT.RIGHT);
		grpContacto.setLayout(new GridLayout(2, false));
		FormData fd_grpContacto = new FormData();
		fd_grpContacto.left = new FormAttachment(0, 10);
		fd_grpContacto.right = new FormAttachment(100, -10);
		grpContacto.setLayoutData(fd_grpContacto);
		grpContacto.setText("Destino Visita");
		
		Group grpVisita = new Group(shlRegistroDeAcceso, SWT.NONE);
		fd_grpContacto.top = new FormAttachment(grpVisita, 19);
		
		Label lblEmpresa_1 = new Label(grpContacto, SWT.NONE);
		lblEmpresa_1.setText("Empresa");
		
		//Combo de los usuarios del edificio
		comboViewerUsuario = new ComboViewer(grpContacto, SWT.NONE);				
		comboViewerUsuario.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent arg0) {
			    IStructuredSelection selection = (IStructuredSelection) arg0.getSelection();
			    if(comboDependencia != null){
			    	comboDependencia.setInput(((UsuarioInmueble) selection.getFirstElement()).getDependencias().toArray());
			    	comboDependencia.setSelection(new StructuredSelection(((UsuarioInmueble) selection.getFirstElement()).getDependencias().toArray()[0]));
			    }
			}
		});
		Combo combo = comboViewerUsuario.getCombo();
		GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_combo.widthHint = 180;
		combo.setLayoutData(gd_combo);
		
		comboViewerUsuario.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerUsuario.setLabelProvider(new LabelProvider() {
			  @Override
			  public String getText(Object element) {
			    if (element instanceof UsuarioInmueble) {
			      UsuarioInmueble unUsuario = (UsuarioInmueble) element;
			      return unUsuario.getNombre();
			    }
			    return super.getText(element);
			  }
			});
		comboViewerUsuario.setInput(getUsuariosInmueble().toArray());
		
		Label lblOficina = new Label(grpContacto, SWT.NONE);
		lblOficina.setText("Dependencia");
		
		//Combo de las dependencias del usuario
		comboDependencia = new ComboViewer(grpContacto, SWT.NONE);
		comboDependencia.setContentProvider(ArrayContentProvider.getInstance());
		comboDependencia.setLabelProvider(new LabelProvider() {
			  @Override
			  public String getText(Object element) {
			    if (element instanceof Dependencia) {
			      Dependencia unaDependencia = (Dependencia) element;
			      return unaDependencia.getIdentificador();
			    }
			    return super.getText(element);
			  }
			});		
		Combo comboDep = comboDependencia.getCombo();
		GridData gd_combo_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_combo_1.widthHint = 180;
		comboDep.setLayoutData(gd_combo_1);
		
		//posterior a la creacion del combo de las dependencias se selecciona el primer elemento de los usuarios
		if(!getUsuariosInmueble().isEmpty())
			comboViewerUsuario.setSelection(new StructuredSelection(getUsuariosInmueble().toArray()[0]));
		
		grpVisita.setLayout(new GridLayout(5, false));
		FormData fd_grpVisita = new FormData();
		fd_grpVisita.bottom = new FormAttachment(100, -171);
		fd_grpVisita.top = new FormAttachment(composite, 6);
		fd_grpVisita.left = new FormAttachment(0, 10);
		fd_grpVisita.right = new FormAttachment(100, -10);
		grpVisita.setLayoutData(fd_grpVisita);
		grpVisita.setText("Visita");
		
		Label lblRut = new Label(grpVisita, SWT.NONE);
		lblRut.setText("RUT");
		
        tip = new ToolTip(shlRegistroDeAcceso, SWT.BALLOON | SWT.ICON_ERROR);
        
		textRut = new Text(grpVisita, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 135;
		textRut.setLayoutData(gd_text);
		//Valida que solo se ingresen numeros
	    textRut.addListener(SWT.Verify, new Listener() {
	    	public void handleEvent(Event e) {
	    		//Apaga el tootip si esta visible
	    		if(tip.isVisible())
	    			tip.setVisible(false);
	    		e.doit = Validador.isNumber(e.text);
	            return;
	        }
	    });
   
		Label label = new Label(grpVisita, SWT.NONE);
		label.setText("-");
		
		textDV = new Text(grpVisita, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_4.widthHint = 20;
		textDV.setLayoutData(gd_text_4);
		textDV.setTextLimit(1);
		textDV.addListener(SWT.Verify, new Listener() {
	    	public void handleEvent(Event e) {
	    		e.doit = Validador.isDV(e.text);
	            return;
	        }
	    });	
		
		CLabel lblNewLabel_1 = new CLabel(grpVisita, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 7);
		gd_lblNewLabel_1.heightHint = 149;
		gd_lblNewLabel_1.widthHint = 133;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setBackground(SWTResourceManager.getImage(RegistroVisita.class, "/com/altair/accesos/recursos/Scan-110414-0002.jpg"));
		lblNewLabel_1.setText("");
		
		Label lblNombre = new Label(grpVisita, SWT.NONE);
		lblNombre.setText("Nombre");
		
		textNombre = new Text(grpVisita, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_1.widthHint = 139;
		textNombre.setLayoutData(gd_text_1);
		textNombre.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent event) {
	    		tip.setVisible(false);
	    	}
	    });
		
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		
		Label lblApellido = new Label(grpVisita, SWT.NONE);
		lblApellido.setText("Apellido Paterno");
		
		textApellidoPaterno = new Text(grpVisita, SWT.BORDER);
		textApellidoPaterno.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		toolTip = new DefaultToolTip(textApellidoPaterno);
		toolTip.setText("Hello World");
		
		textApellidoPaterno.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent event) {
	    		tip.setVisible(false);
	    		toolTip.hide();
	    	}
	    });
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		
		Label lblEmpresa = new Label(grpVisita, SWT.NONE);
		lblEmpresa.setText("Apellido Materno");
		
		textApellidoMaterno = new Text(grpVisita, SWT.BORDER);
		textApellidoMaterno.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textApellidoMaterno.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent event) {
	    		tip.setVisible(false);
	    	}
	    });
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		
		Label lblEmail = new Label(grpVisita, SWT.NONE);
		lblEmail.setText("E-mail");
		
		textEmail = new Text(grpVisita, SWT.BORDER);
		textEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textEmail.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent event) {
	    		tip.setVisible(false);
	    	}
	    });
		textEmail.addListener(SWT.FocusOut, new Listener() {
	    	public void handleEvent(Event e) {
	    		if(!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(textEmail.getText())){
	    			textEmail.setFocus();
	                Point loc = textEmail.toDisplay(textEmail.getLocation());
	                tip.setMessage("Formato de correo incorrecto");
	                tip.setLocation(loc);
					tip.setVisible(true);
	    		}		
	    		return;
	        }
	    });
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		
		Label lblTelefono = new Label(grpVisita, SWT.NONE);
		lblTelefono.setText("Telefono Contacto");
		
		textFonoContacto = new Text(grpVisita, SWT.BORDER);
		textFonoContacto.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textFonoContacto.addListener(SWT.Verify, new Listener() {
	    	public void handleEvent(Event e) {
	    		//Apaga el tootip si esta visible
	    		if(tip.isVisible())
	    			tip.setVisible(false);
	    		e.doit = Validador.isNumber(e.text);
	            return;
	        }
	    });
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		new Label(grpVisita, SWT.NONE);
		
		CLabel lblNewLabel = new CLabel(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getImage(RegistroVisita.class, "/com/altair/accesos/recursos/tarski.jpg"));
		lblNewLabel.setBounds(0, 0, 470, 47);
		lblNewLabel.setText("");
		
		Button btnCancelar = new Button(shlRegistroDeAcceso, SWT.NONE);
		fd_grpContacto.bottom = new FormAttachment(btnCancelar, -17);
		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlRegistroDeAcceso.dispose();
			}
		});
		FormData fd_btnCancelar = new FormData();
		btnCancelar.setLayoutData(fd_btnCancelar);
		btnCancelar.setText("Cancelar");
		
		btnGrabar = new Button(shlRegistroDeAcceso, SWT.NONE);
		btnGrabar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(validaFormulario()){
					//Creacion del visitante y de su informacion de contacto
	//				Empresa laEmpresa = (Empresa)((IStructuredSelection) comboViewer.getSelection()).getFirstElement();
					Dependencia laDependencia = (Dependencia)((IStructuredSelection) comboDependencia.getSelection()).getFirstElement();
					UsuarioInmueble destino = (UsuarioInmueble)((IStructuredSelection) comboViewerUsuario.getSelection()).getFirstElement();
	
	
					PersonaDAO pdao = new PersonaDAO();
					Persona laPersona = new Persona();
					laPersona.setRut(Integer.parseInt(textRut.getText()));
					laPersona.setDv(textDV.getText());
					laPersona.setNombre(textNombre.getText());
					laPersona.setApellido(textApellidoPaterno.getText());
					laPersona.setApellidomat(textApellidoMaterno.getText());
					laPersona.setEmail(textEmail.getText());
					laPersona.setCelular(textFonoContacto.getText());
					
					EntityManagerHelper.beginTransaction();
			    	pdao.save(laPersona);
			    	EntityManagerHelper.commit();
			 		EntityManagerHelper.closeEntityManager();
	
			 		/*
					Contacto elContacto = new Contacto();
					elContacto.setEmail(text_8.getText());
					elContacto.setTelefono(text_9.getText());
					elContacto.setUnidad((Unidad)laEmpresa.getUnidades().toArray()[0]);
					
					laPersona.getInfoContacto().add(elContacto);
					
					EntityManagerHelper.beginTransaction();
			    	pdao.update(laPersona);
			    	EntityManagerHelper.commit();
			 		EntityManagerHelper.closeEntityManager();
			 		*/
					// Fin de la creacion del visitante y su informacion de contacto
			 		
					AccesoDAO adao = new AccesoDAO();
					Acceso unAcceso = new Acceso();
					MotivoDAO mdao = new MotivoDAO();
					
				    java.util.Date utilDate = new java.util.Date();
				    //fecha actual
					long lnMilisegundos = utilDate.getTime();
					java.sql.Timestamp fechaActual = new java.sql.Timestamp(lnMilisegundos);
									
	//				unAcceso.setAnfitrion(pdao.findByRut(Integer.parseInt("10273426")).get(0));
					unAcceso.setDependencia(laDependencia);
					unAcceso.setDestino(destino);
					unAcceso.setFechaingreso(fechaActual);
	//				unAcceso.setFechasalida(fechaActual);
					unAcceso.setVisita(laPersona);
					unAcceso.setElMotivo(mdao.findByMotivo("Reunión").get(0));
					
					EntityManagerHelper.beginTransaction();
			    	adao.save(unAcceso);
			    	EntityManagerHelper.commit();
			 		EntityManagerHelper.closeEntityManager();
	
					System.out.println("Dependencia = " + laDependencia.getIdentificador());
	//				System.out.println("Empresa = " + laEmpresa.getRazonSocial());
					System.out.println("Destino = " + destino.getNombre());
					
					
	
			        listado.refresh();
			        //Cierra el dialogo
			        shlRegistroDeAcceso.dispose();
				}
			}
		});
		fd_btnCancelar.top = new FormAttachment(btnGrabar, 0, SWT.TOP);
		fd_btnCancelar.left = new FormAttachment(btnGrabar, 6);
		FormData fd_btnGrabar = new FormData();
		fd_btnGrabar.bottom = new FormAttachment(100, -10);
		fd_btnGrabar.right = new FormAttachment(100, -253);
		btnGrabar.setLayoutData(fd_btnGrabar);
		btnGrabar.setText("Grabar");

	}
	
	private Set<UsuarioInmueble> getUsuariosInmueble(){
		return this.edificio.getDirectorio().getMiembros();
	}

	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
		
	public void creaAcceso(Persona laPersona, Dependencia dependencia){
	    java.util.Date utilDate = new java.util.Date();
	    //fecha actual
		long lnMilisegundos = utilDate.getTime();
		java.sql.Timestamp fechaActual = new java.sql.Timestamp(lnMilisegundos);

		Acceso elAcceso = new Acceso();
		elAcceso.setVisita(laPersona);
		elAcceso.setFechaingreso(fechaActual);
		elAcceso.setFechasalida(fechaActual);
		elAcceso.setDependencia(dependencia);
	}

	public ListaAccesos getListado() {
		return listado;
	}

	public void setListado(ListaAccesos listado) {
		this.listado = listado;
	}
	
	private boolean validaFormulario(){
		if(textRut.getText().isEmpty() || textDV.getText().isEmpty()){
			if(textRut.getText().isEmpty()){
				textRut.setFocus();
                Point loc = textRut.toDisplay(textRut.getLocation());
                tip.setMessage("Ingrese RUT");
                tip.setLocation(loc);
				tip.setVisible(true);
			} else if(textDV.getText().isEmpty()){
				textDV.setFocus();
                Point loc = textRut.toDisplay(textRut.getLocation());
                tip.setMessage("Ingrese DV");
                tip.setLocation(loc);
				tip.setVisible(true);
			}
			return false;
		} else if(!Validador.isRUT(Integer.parseInt(textRut.getText()), textDV.getText().charAt(0))){
				textRut.setFocus();
                Point loc = textRut.toDisplay(textRut.getLocation());
                tip.setMessage("RUT INCORRECTO");
                tip.setLocation(loc);
				tip.setVisible(true);
				return false;
		} else if(textNombre.getText().isEmpty()){
			textNombre.setFocus();
			Point loc = textNombre.toDisplay(textNombre.getLocation());
			System.out.print("x= " + textNombre.getLocation().x + " y= " + textNombre.getLocation().y);
			System.out.println("x= " + loc.x + " y= " + loc.y);
			tip.setMessage("Ingrese el nombre de la persona");
			tip.setLocation(loc);
			tip.setVisible(true);
			return false;
		} else if(textApellidoPaterno.getText().isEmpty()){
			textApellidoPaterno.setFocus();
			Point loc = textApellidoPaterno.toDisplay(textApellidoPaterno.getLocation());
			System.out.println(" x= " + loc.x + " y= " + loc.y);
			tip.setMessage("Ingrese el apellido de la persona");
			tip.setLocation(loc);
			tip.setVisible(true);
			
//			toolTip.activate();
			toolTip.show(loc);
			return false;			
		} else if(textApellidoMaterno.getText().isEmpty()){
			textApellidoMaterno.setFocus();
			Point loc = textApellidoMaterno.toDisplay(textApellidoMaterno.getLocation());
			System.out.println(" x= " + loc.x + " y= " + loc.y);
			tip.setMessage("Ingrese el apellido materno de la persona");
			tip.setLocation(loc);
			tip.setVisible(true);
			return false;			
		} else if(textEmail.getText().isEmpty()){
			textEmail.setFocus();
			Point loc = textEmail.toDisplay(textEmail.getLocation());
			System.out.println(" x= " + loc.x + " y= " + loc.y);
			tip.setMessage("Ingrese el correo electrónico la persona");
			tip.setLocation(loc);
			tip.setVisible(true);
			return false;	
		} else if(!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(textEmail.getText())){  		
    			textEmail.setFocus();
                Point loc = textEmail.toDisplay(textEmail.getLocation());
                tip.setMessage("Formato de correo incorrecto");
                tip.setLocation(loc);
				tip.setVisible(true);
		} else if(textFonoContacto.getText().isEmpty()){
			textFonoContacto.setFocus();
			Point loc = textFonoContacto.toDisplay(textFonoContacto.getLocation());
			System.out.println(" x= " + loc.x + " y= " + loc.y);
			tip.setMessage("Ingrese el teléfono de contacto de la persona");
			tip.setLocation(loc);
			tip.setVisible(true);
			return false;									
		}
		return true;
	}
}
