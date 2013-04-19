package cl.altair.accesos.wizard.edificio;

import java.util.logging.Logger;

import org.eclipse.jface.wizard.Wizard;

import cl.altair.acceso.dao.EdificioDAO;
import cl.altair.acceso.dao.EntityManagerHelper;
import cl.altair.acceso.modelo.Directorio;
import cl.altair.acceso.modelo.Edificio;
import cl.altair.acceso.modelo.Empresa;
import cl.altair.acceso.modelo.Persona;
import cl.altair.acceso.modelo.Usuario;
import cl.altair.acceso.modelo.UsuarioInmueble;
import cl.altair.accesos.wizard.UsuarioInmueble.WizardUsuarioInmueble;


public class WizardEdificio extends Wizard {

  protected WUIPaginaBasica uno;
  protected WUIPaginaPropietario dos;
  private Edificio eledificio = new Edificio();
  private Empresa laEmpresa = new Empresa();
  private Persona laPersona = new Persona();
  private Usuario usrAdm = new Usuario();
  private EdificioDAO edao = new EdificioDAO();
  private final static Logger traza = Logger.getLogger(WizardEdificio.class.getName());

  public WizardEdificio() {
    super();
//    eledificio = edao.findAll().get(0);
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    uno = new WUIPaginaBasica();
    dos = new WUIPaginaPropietario();
    addPage(uno);
    addPage(dos);
  }

  @Override
  public boolean performFinish() {
	  try {
		  traza.info("Grabando informacion inicial del edificio");
		  Directorio unDirectorio = new Directorio("Directorio");
		  eledificio.setPropietario(laEmpresa);
		  eledificio.setDirectorio(unDirectorio);
		  EntityManagerHelper.beginTransaction();
		  edao.save(eledificio);
		  EntityManagerHelper.commit();
		  EntityManagerHelper.closeEntityManager();
	} catch (Exception e) {
		traza.fine("Error al grabar datos del edificio " + e.getMessage());
		e.printStackTrace();
		return false;
	}
	  return true;
  }

public Edificio getEledificio() {
	return eledificio;
}

public void setEledificio(Edificio eledificio) {
	this.eledificio = eledificio;
}

public Empresa getLaEmpresa() {
	return laEmpresa;
}

public void setLaEmpresa(Empresa laEmpresa) {
	this.laEmpresa = laEmpresa;
}

public Persona getLaPersona() {
	return laPersona;
}

public void setLaPersona(Persona laPersona) {
	this.laPersona = laPersona;
}

public Usuario getUsrAdm() {
	return usrAdm;
}

public void setUsrAdm(Usuario usrAdm) {
	this.usrAdm = usrAdm;
}
} 