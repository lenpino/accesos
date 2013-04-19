package cl.altair.accesos.wizard.UsuarioInmueble;

import java.util.logging.Logger;

import org.eclipse.jface.wizard.Wizard;

import cl.altair.acceso.dao.DirectorioDAO;
import cl.altair.acceso.dao.EdificioDAO;
import cl.altair.acceso.dao.EmpresaDAO;
import cl.altair.acceso.dao.EntityManagerHelper;
import cl.altair.acceso.dao.PersonaDAO;
import cl.altair.acceso.modelo.Directorio;
import cl.altair.acceso.modelo.Edificio;
import cl.altair.acceso.modelo.Empresa;
import cl.altair.acceso.modelo.Persona;
import cl.altair.acceso.modelo.UsuarioInmueble;
import cl.miempresa.accesos.modelo.providers.TreeDirectorio;
import cl.miempresa.accesos.principal.ArbolDirectorio;


public class WizardUsuarioInmueble extends Wizard {

  protected WUIPaginaBasica one;
  protected WUIPaginaEmpresa two;
  protected WUIPaginaPersona tres;
  protected WUIPaginaDependencias cuatro;
  private final static Logger LOGGER = Logger.getLogger(WizardUsuarioInmueble.class.getName());
  private Edificio eledificio;
  private Directorio unDirectorio;
  private UsuarioInmueble elusuario = new UsuarioInmueble();
  private Persona laPersona = new Persona();
  private Empresa laEmpresa = new Empresa();
  private ArbolDirectorio elArbolDirectorio;
  private EdificioDAO edao = new EdificioDAO();
  private DirectorioDAO ddao = new DirectorioDAO();

  public WizardUsuarioInmueble() {
    super();
    setNeedsProgressMonitor(true);
  }

  public WizardUsuarioInmueble(Edificio eledificio, ArbolDirectorio elArbolDirectorio) {
	    super();
		this.eledificio = eledificio;
		this.elArbolDirectorio = elArbolDirectorio;
	    setNeedsProgressMonitor(true);
  }

  public WizardUsuarioInmueble(ArbolDirectorio elArbolDirectorio) {
	    super();
		//Lee el edificio desde la BD
		eledificio = edao.findAll().get(0);
		unDirectorio = ddao.findAll().get(0);
		this.elArbolDirectorio = elArbolDirectorio;
	    setNeedsProgressMonitor(true);
}

  @Override
  public void addPages() {
    one = new WUIPaginaBasica();
    two = new WUIPaginaEmpresa();
    tres = new WUIPaginaPersona();
    cuatro = new WUIPaginaDependencias();
    addPage(one);
    addPage(two);
    addPage(tres);
    addPage(cuatro);
  }

  @Override
  public boolean performFinish() {
	  try {
		LOGGER.info("Grabando informacion del usuario inmueble " + elusuario.getRut());
		  PersonaDAO pdao = new PersonaDAO();
		  EmpresaDAO emdao = new EmpresaDAO();
		  this.getEledificio().getDirectorio().getMiembros().add(elusuario);
		  //El usuario inmueble no tiene relacion ni depenencia con PERSONA o EMPRESA
		  EntityManagerHelper.beginTransaction();
		  if(elusuario.getTipoUsuario().equalsIgnoreCase("persona")){
			  LOGGER.finest("Grabando informacion del usuario persona " + elusuario.getRut());
			  pdao.save(laPersona);
		  } else if(elusuario.getTipoUsuario().equalsIgnoreCase("empresa")){
			  LOGGER.finest("Grabando informacion del usuario empresa " + elusuario.getRut());
			  emdao.save(laEmpresa);
		  }
		  edao.update(this.eledificio);
		  EntityManagerHelper.commit();
		  EntityManagerHelper.closeEntityManager();		 
		  
		  //Refresca el arbol con la nueva dependencia
		  TreeDirectorio todoArbol = new TreeDirectorio();
		  Directorio[] eldirectorio = new Directorio[1];
		  eldirectorio[0] = unDirectorio;
		  todoArbol.setDirectorio(eldirectorio);
		  todoArbol.setNombre(eledificio.getNombre());
		  elArbolDirectorio.getViewer().setInput(todoArbol);
		  elArbolDirectorio.getViewer().refresh();

		  return true;
	} catch (Exception e) {
		LOGGER.info("ERROR: " + e.getMessage());
		e.printStackTrace();
		return false;
	}
  }

public Edificio getEledificio() {
	return eledificio;
}

public void setEledificio(Edificio eledificio) {
	this.eledificio = eledificio;
}

public UsuarioInmueble getElusuario() {
	return elusuario;
}

public void setElusuario(UsuarioInmueble elusaurio) {
	this.elusuario = elusaurio;
}

public Persona getLaPersona() {
	return laPersona;
}

public void setLaPersona(Persona laPersona) {
	this.laPersona = laPersona;
}

public Empresa getLaEmpresa() {
	return laEmpresa;
}

public void setLaEmpresa(Empresa laEmpresa) {
	this.laEmpresa = laEmpresa;
}

public ArbolDirectorio getElArbolDirectorio() {
	return elArbolDirectorio;
}

public void setElArbolDirectorio(ArbolDirectorio elArbolDirectorio) {
	this.elArbolDirectorio = elArbolDirectorio;
}
} 