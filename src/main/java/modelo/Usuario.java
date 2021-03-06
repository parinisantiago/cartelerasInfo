package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rest.JView;

@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id@GeneratedValue
	@Column(name="usuario_id")
	//@JsonView(JView.Publico.class)
	@JsonView(JView.SoloID.class)
	private Long id;
	
	@Column(nullable = false, unique=true)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Usuario.class, JView.Simple.class, JView.CarteleraCompleta.class, JView.Cartelera.class, JView.Anuncio.class})
	private String user;
	
	@Column(nullable = false)
	@JsonView({JView.Usuario.class, JView.Simple.class, JView.CarteleraCompleta.class, JView.Cartelera.class, JView.Anuncio.class})
	private String profilePic;
	
	@Column(nullable = false)
	@JsonView(JView.Privado.class)
	private String password;
	
	@Column(nullable = false)
	@JsonView(JView.Privado.class)
	private boolean habilitado;
	
	@OneToMany(mappedBy="creador", fetch=FetchType.EAGER)
	//@JsonView(JView.Usuario.class)
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@OneToMany(mappedBy="creador")
	private List<Anuncio> misAnuncios  = new ArrayList<Anuncio>();

	@OneToOne
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Usuario.class, JView.Simple.class, JView.CarteleraCompleta.class})
	private Rol rol;
	
	@ManyToMany(mappedBy="interesados")
	private Set<Cartelera> intereses = new HashSet<Cartelera>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="cartelera_usuarioEliminar",	
			joinColumns=@JoinColumn(name="usuario_id", nullable=false),
		    inverseJoinColumns=@JoinColumn(name="cartelera_id", nullable=false))
	@JsonView(JView.Usuario.class)
	private Set<Cartelera> cartelerasEliminar = new HashSet<Cartelera>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="cartelera_usuarioModificar",	
			joinColumns=@JoinColumn(name="usuario_id", nullable=false),
		    inverseJoinColumns=@JoinColumn(name="cartelera_id", nullable=false))
	@JsonView(JView.Usuario.class)
	private Set<Cartelera> cartelerasModificar = new HashSet<Cartelera>();
	
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

	public void addComentario(Comentario comentario) {
		this.comentarios.add(comentario);
		
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

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", user=" + user + ", habilitado=" + habilitado + ", rol=" + rol + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Usuario){
			Usuario otro = (Usuario) obj;
			boolean ids = otro.getId() == this.getId() ;
			boolean nombre = otro.getUser().equals(this.getUser());
			return (ids && nombre);
		}
		else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (this.getId().intValue() + this.getId().hashCode()+ this.getUser().hashCode() );
	}

}
