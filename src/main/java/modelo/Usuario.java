package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String user;
	private String password;
	private boolean habilitado;
	
	@OneToMany(mappedBy="creador", cascade={CascadeType.ALL})
	private List<Comentario> comentarios;
	
	@OneToMany(mappedBy="creador", cascade={CascadeType.ALL})
	private List<Anuncio> misAnuncios;
	
	//TODO mapear
	private List<Rol> roles;
	
	@OneToMany(mappedBy="usuario", cascade={CascadeType.ALL})
	private List<Notificacion> notificaciones;
	
	@ManyToMany(mappedBy="interesados", cascade={CascadeType.ALL})
	private Set<Cartelera> intereses;
	
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
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public void addNotificacion(Notificacion notificacion){
		this.notificaciones.add(notificacion);
	}
	
}
