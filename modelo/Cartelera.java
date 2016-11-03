package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cartelera {
	private String titulo;
	private List<Anuncio> anuncios;
	private Set<Usuario> interesados;
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
	
	public void removeInteresado(Usuario usuario){
		this.interesados.remove(usuario);
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
