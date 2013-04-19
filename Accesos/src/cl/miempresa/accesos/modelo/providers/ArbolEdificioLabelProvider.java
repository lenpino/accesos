package cl.miempresa.accesos.modelo.providers;

import java.io.InputStream;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import cl.altair.acceso.modelo.Dependencia;
import cl.altair.acceso.modelo.Edificio;
import cl.altair.utiles.generales.Piso;

public class ArbolEdificioLabelProvider extends StyledCellLabelProvider {
	
	
	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		StyledString text = new StyledString();
		Image empresa = null;
		Image oficina = null;
		Image edificio = null;
	    String userdir = System.getProperty("user.dir");  

	    // Create the images
	    try {
	    	InputStream imageStreamEdificio = getClass().getResourceAsStream("/com/altair/accesos/recursos/building.ico");
	    	InputStream imageStreamOrg = getClass().getResourceAsStream("/com/altair/accesos/recursos/organization32.jpg"); 
	    	InputStream imageStreamOrg2 = getClass().getResourceAsStream("/com/altair/accesos/recursos/organization32.jpg"); 

	    	edificio = new Image(Display.getCurrent(),imageStreamEdificio); 
	    	oficina = new Image(Display.getCurrent(), imageStreamOrg);
	    	empresa = new Image(Display.getCurrent(), imageStreamOrg2);
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    if(element instanceof Edificio){
	    	Edificio eledificio = (Edificio) element;
	    	text.append(eledificio.getNombre()==null?"":eledificio.getNombre());
	    	cell.setImage(edificio);
	    } else if (element instanceof Piso) {
			Piso unpiso = (Piso) element;
			text.append("PISO " + unpiso.getPiso(), StyledString.COUNTER_STYLER);
			cell.setImage(empresa);
//			text.append(" ( " +usuario.getDependencias().size() + " ) ", StyledString.COUNTER_STYLER);
		} else {
			Dependencia laDependencia = (Dependencia) element;
			text.append(laDependencia.getIdentificador());
			cell.setImage(oficina);
		}
		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);
	}
}
