package modelo;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rest.JView;

@Entity
@Table(name="cartelera")
public class Cartelera implements Serializable {

	private static final long serialVersionUID = 9137874459530674411L;

	@Id@GeneratedValue
	@Column(name="cartelera_id")
	//@JsonView(JView.Publico.class)
	@JsonView(JView.SoloID.class)
	private Long id;
	
	@Column(nullable = false, unique=true)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Cartelera.class, JView.Simple.class, JView.CarteleraCompleta.class})
	private String titulo;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="intereses_usuario_cartelera",
	    joinColumns=@JoinColumn(name="cartelera_id", nullable=false),
	    inverseJoinColumns=@JoinColumn(name="usuario_id",nullable=false))
	@JsonView({JView.Cartelera.class, JView.CarteleraCompleta.class})
	private Set<Usuario> interesados;
	
	@OneToMany(cascade={CascadeType.REMOVE}, mappedBy="cartelera", fetch=FetchType.EAGER)
	@JsonView({JView.Cartelera.class, JView.CarteleraCompleta.class})
	private List<Anuncio> anuncios;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="cartelera_usuarioEliminar",	
		joinColumns=@JoinColumn(name="cartelera_id", nullable=false),
	    inverseJoinColumns=@JoinColumn(name="usuario_id", nullable=false))
	@JsonView(JView.CarteleraCompleta.class)
	private Set<Usuario> usuarioEliminar = new HashSet<Usuario>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="cartelera_usuarioModificar",	
		joinColumns=@JoinColumn(name="cartelera_id", nullable=false),
	    inverseJoinColumns=@JoinColumn(name="usuario_id", nullable=false))
	@JsonView(JView.CarteleraCompleta.class)
	private Set<Usuario> usuarioPublicar = new HashSet<Usuario>();
	
	@Column(nullable = false)
	@JsonView(JView.Privado.class)
	private boolean habilitado;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Cartelera(){
		this.anuncios = new ArrayList<Anuncio>();
		this.interesados = new HashSet<Usuario>();
	}
	
	public Cartelera(String titulo) {
		super();
		this.titulo = titulo;
		this.anuncios = new ArrayList<Anuncio>();
		this.interesados = new HashSet<Usuario>();
		this.habilitado = true;
	}

	public Cartelera(String titulo, List<Anuncio> notas) {
		super();
		this.titulo = titulo;
		this.anuncios = notas;
		this.interesados = new HashSet<Usuario>();
		this.habilitado = true;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(List<Anuncio> notas) {
		this.anuncios = notas;
	}
	
	public void addAnuncio(Anuncio anuncio){
		this.anuncios.add(anuncio);
	}
	
	public void removeAnuncio(Anuncio anuncio){
		this.anuncios.remove(anuncio);
	}
	
	public Set<Usuario> getInteresados() {
		return interesados;
	}

	public void setInteresados(Set<Usuario> interesados) {
		this.interesados = interesados;
	}
	
	public void addInteresado(Usuario usuario){
		this.interesados.add(usuario);
	}
	
	public Set<Usuario> getUsuarioPublicar() {
		return usuarioPublicar;
	}

	public void setUsuarioPublicar(Set<Usuario> usuarioPublicar) {
		this.usuarioPublicar = usuarioPublicar;
	}
	
	public void addUsuarioPublicar(Usuario usuarioPublicar){
		this.usuarioPublicar.add(usuarioPublicar);
	}
	public void removeUsuarioPublicar(Usuario usuarioPublicar){
		this.usuarioPublicar.remove(usuarioPublicar);
	}

	public Set<Usuario> getUsuarioEliminar() {
		return usuarioEliminar;
	}

	public void setUsuarioEliminar(Set<Usuario> usuarioEliminar) {
		this.usuarioEliminar = usuarioEliminar;
	}
	
	public void addUsuarioEliminar(Usuario usuarioEliminar){
		this.usuarioEliminar.add(usuarioEliminar);
	}
	public void removeUsuarioEliminar(Usuario usuarioEliminar){
		this.usuarioEliminar.remove(usuarioEliminar);
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

	@Override
	public String toString() {
		return "Cartelera [id=" + id + ", titulo=" + titulo + ", habilitado=" + habilitado + "]";
	}
	
}
