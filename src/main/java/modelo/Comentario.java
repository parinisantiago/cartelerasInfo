package modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import rest.JView;

@Entity
@Table(name="comentario")
public class Comentario implements Serializable{
	private static final long serialVersionUID = -4661212242115460013L;
	
	@Id@GeneratedValue
	//@JsonView(JView.Publico.class)
	@JsonView(JView.SoloID.class)
	private Long id;
	
	@Column(nullable = false)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Comentario.class, JView.Simple.class})
	private String texto;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Comentario.class, JView.Simple.class})
	private Date fecha;
	
	@ManyToOne
	@JsonView(JView.Comentario.class)
	private Anuncio anuncio; 
	
	@ManyToOne
	//@JsonView(JView.Publico.class)
	@JsonView(JView.Comentario.class)
	private Usuario creador;
	
	@Column(nullable = false)
	@JsonView(JView.Privado.class)
	private boolean habilitado;
	
	public Comentario() {
		super();
	}

	public Comentario(String texto, Date fecha, Usuario usuario) {
		super();
		this.texto = texto;
		this.fecha = fecha;
		this.creador = usuario;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public void setAnuncio(Anuncio anuncio){
		this.anuncio = anuncio;
	}
	
	public Anuncio getAnuncio(){
		return this.anuncio;
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
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
		return "Comentario [id=" + id + ", anuncio=" + anuncio + ", texto=" + texto + ", fecha=" + fecha + ", creador="
				+ creador + ", habilitado=" + habilitado + "]";
	}
	
}
