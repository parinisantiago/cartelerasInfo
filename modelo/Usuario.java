package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String user;
	private String password;
	private boolean habilitado;
	private List<Rol> roles;
	
	public Usuario(){
		this.roles = new ArrayList<Rol>();
	}
	
	public Usuario(String user, List<Rol> roles) {
		super();
		this.user = user;
		this.roles = roles;
	}

	public Usuario(String user, String password, boolean habilitado, List<Rol> roles) {
		super();
		this.user = user;
		this.password = password;
		this.habilitado = habilitado;
		this.roles = roles;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
	public void deshabilitar(){
		this.setHabilitado(false);
	}
	
	public void habilitar(){
		this.setHabilitado(true);
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public void addRol(Rol rol){
		this.roles.add(rol);
	}
	
}
