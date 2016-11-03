package modelo;

import java.util.ArrayList;
import java.util.List;

public class Rol {
	private String nombre;
	private List<Permiso> permisos;
	
	public Rol(){
		this.permisos = new ArrayList<Permiso>();
	}
	
	public Rol(String nombre, List<Permiso> permisos) {
		super();
		this.nombre = nombre;
		this.permisos = permisos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}
	
	public void addPermiso(Permiso permiso){
		this.permisos.add(permiso);
	}
	
	public void removePermiso(Permiso permiso){
		this.permisos.remove(permiso);
	}
	
}
