package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import rest.JView;

@Entity
@Table(name="anuncio")
public class Anuncio implements Serializable {
	
	private static final long serialVersionUID = 8111941024423804489L;
	
	@Id
	@GeneratedValue
	//@JsonView(JView.Publico.class)
	@JsonView(JView.SoloID.class)
	private Long id;
	
	@Column(nullable = false)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Anuncio.class, JView.Simple.class, JView.CarteleraCompleta.class})
	private String titulo;
	
	@Column(nullable = false)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Anuncio.class, JView.Simple.class, JView.CarteleraCompleta.class})
	private String cuerpo;
	
	@Column(nullable = false)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Anuncio.class, JView.Simple.class, JView.CarteleraCompleta.class})
	private boolean comentarioHabilitado;
	
	@ManyToOne
	@JsonView({JView.Anuncio.class, JView.CarteleraCompleta.class})
	private Usuario creador;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Anuncio.class, JView.Simple.class, JView.CarteleraCompleta.class})
	private Date fecha;
	
	@ManyToOne
	@JsonView(JView.Anuncio.class)
	private Cartelera cartelera;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="anuncio", fetch=FetchType.EAGER)
	@JsonView({JView.Anuncio.class,JView.CarteleraCompleta.class})
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@Column(nullable = false)
	@JsonView(JView.Privado.class)
	private boolean habilitado;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cartelera getCartelera() {
		return cartelera;
	}

	public void setCartelera(Cartelera cartelera) {
		this.cartelera = cartelera;
	}

	public Anuncio() {
		this.comentarios = new ArrayList<Comentario>();
	}

	public Anuncio(String titulo, String cuerpo, boolean comentarioHabilitado, Usuario creador, Date fecha) {
		super();
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.comentarioHabilitado = comentarioHabilitado;
		this.creador = creador;
		this.fecha = fecha;
		this.habilitado = true;
	}

	public Anuncio(String titulo, String cuerpo, boolean comentarioHabilitado, Usuario creador, Date fecha,
			List<Comentario> comentarios) {
		super();
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.comentarioHabilitado = comentarioHabilitado;
		this.creador = creador;
		this.fecha = fecha;
		this.comentarios = comentarios;
		this.habilitado = true;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public boolean isComentarioHabilitado() {
		return comentarioHabilitado;
	}

	public void setComentarioHabilitado(boolean comentarioHabilitado) {
		this.comentarioHabilitado = comentarioHabilitado;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public void addComentario(Comentario comentario){
		this.comentarios.add(comentario);
		//comentario.setAnuncio(this);
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
		return "Anuncio [id=" + id + ", titulo=" + titulo + ", cuerpo=" + cuerpo + ", comentarioHabilitado="
				+ comentarioHabilitado + ", fecha=" + fecha + ", habilitado=" + habilitado + "]";
	}
	
}
