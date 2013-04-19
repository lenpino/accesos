package cl.miempresa.accesos.modelo.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cl.altair.acceso.modelo.Edificio;
import cl.altair.utiles.generales.Piso;

public class ArbolEdificioContentProvider implements ITreeContentProvider {

	private TreeEdificio model;

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.model = (TreeEdificio) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model.getEdificio();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof Edificio){
			Edificio eledificio = (Edificio) parentElement;
			return TreeEdificio.getTreeEdificio(eledificio);
		}
		if (parentElement instanceof Piso) {
			Piso unpiso = (Piso) parentElement;
			return unpiso.getDependencias().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof Edificio){
			return true;
		} 
		if (element instanceof Piso) {
			return true;
		}
		return false;
	}

}
