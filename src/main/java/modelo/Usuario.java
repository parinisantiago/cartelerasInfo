package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id@GeneratedValue
	@Column(name="usuario_id")
	private Long id;
	
	@Column(nullable = false, unique=true)
	private String user;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private boolean habilitado;
	
	@OneToMany(mappedBy="creador")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@OneToMany(mappedBy="creador")
	private List<Anuncio> misAnuncios  = new ArrayList<Anuncio>();
	

	@OneToOne
	private Rol rol;
	
	@ManyToMany(mappedBy="interesados")
	private Set<Cartelera> intereses = new HashSet<Cartelera>();
	
	@ManyToMany(mappedBy="usuarioEliminar")
	private Set<Cartelera> cartelerasEliminar = new HashSet<Cartelera>();
	
	@ManyToMany(mappedBy="usuarioPublicar")
	private Set<Cartelera> cartelerasModificar;
	
	@OneToMany(mappedBy="usuario", cascade={CascadeType.ALL})
	private List<Notificacion> notificaciones = new ArrayList<Notificacion>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Set<Cartelera> getIntereses() {
		return intereses;
	}

	public void setIntereses(Set<Cartelera> intereses) {
		this.intereses = intereses;
	}

	public Set<Cartelera> getCartelerasEliminar() {
		return cartelerasEliminar;
	}

	public void setCartelerasEliminar(Set<Cartelera> cartelerasEliminar) {
		this.cartelerasEliminar = cartelerasEliminar;
	}

	public Set<Cartelera> getCartelerasModificar() {
		return cartelerasModificar;
	}

	public void setCartelerasModificar(Set<Cartelera> cartelerasModificar) {
		this.cartelerasModificar = cartelerasModificar;
	}


	public Usuario(){
	}
	
	public Usuario(String user,Rol rol) {
		super();
		this.user = user;
		this.rol = rol;
	}

	public Usuario(String user, String password, boolean habilitado, Rol rol) {
		super();
		this.user = user;
		this.password = password;
		this.habilitado = habilitado;
		this.rol = rol;
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

	public Rol getRoles() {
		return this.rol;
	}

	public void setRoles(Rol rol) {
		this.rol = rol;
	}

	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public void addNotificacion(Notificacion notificacion){
		this.notificaciones.add(notificacion);
		//notificacion.setUsuario(this);
	}
	
	public List<Anuncio> getMisAnuncios(){
		return this.misAnuncios;
	}
	
	public void setMisAnuncios(List<Anuncio> misAnuncios){
		this.misAnuncios = misAnuncios;
	}
	
	public void addAnuncio(Anuncio anuncio){
		this.misAnuncios.add(anuncio);
		anuncio.setCreador(this);
	}
}
