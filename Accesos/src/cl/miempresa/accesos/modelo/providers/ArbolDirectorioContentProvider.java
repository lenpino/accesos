package cl.miempresa.accesos.modelo.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cl.altair.acceso.modelo.Directorio;
import cl.altair.acceso.modelo.UsuarioInmueble;

public class ArbolDirectorioContentProvider implements ITreeContentProvider {

	private TreeDirectorio model;

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.model = (TreeDirectorio) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model.getDirectorio();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof Directorio){
			Directorio eldirectorio = (Directorio) parentElement;
			return eldirectorio.getMiembros().toArray();
		}
		if (parentElement instanceof UsuarioInmueble) {
			UsuarioInmueble usuarios = (UsuarioInmueble) parentElement;
			return usuarios.getDependencias().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof Directorio){
			return true;
		} 
		if (element instanceof UsuarioInmueble) {
			return true;
		}
		return false;
	}

}
