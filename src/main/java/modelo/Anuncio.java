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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="anuncio")
public class Anuncio implements Serializable {
	
	private static final long serialVersionUID = 8111941024423804489L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String cuerpo;
	
	@Column(nullable = false)
	private boolean comentarioHabilitado;
	
	@ManyToOne
	private Usuario creador;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@ManyToOne
	private Cartelera cartelera;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="anuncio", fetch=FetchType.EAGER)
	private List<Comentario> comentarios;
	
	@Column(nullable = false)
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
	
	
}
