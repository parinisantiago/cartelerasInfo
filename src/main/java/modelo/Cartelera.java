package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="cartelera")
public class Cartelera implements Serializable {

	private static final long serialVersionUID = 9137874459530674411L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(
		name="intereses_usuario_cartelera",
	    joinColumns=@JoinColumn(name="cartelera_id", referencedColumnName="id"),
	    inverseJoinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id"))
	private Set<Usuario> interesados;
	
	private List<Anuncio> anuncios;
	private Set<Usuario> usuarioEliminar;
	private Set<Usuario> usuarioPublicar;
	private boolean habilitado;
	
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
	
	
}
