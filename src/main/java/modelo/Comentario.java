package modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="comentario")
public class Comentario implements Serializable{
	private static final long serialVersionUID = -4661212242115460013L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="comentarios")
	private Anuncio anuncio;
	
	private String texto;
	private Date fecha;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="comentarios")
	private Usuario creador;
	
	private boolean habilitado;
	
	public Comentario() {
		super();
	}

	public Comentario(String texto, Date fecha, Usuario creador) {
		super();
		this.texto = texto;
		this.fecha = fecha;
		this.creador = creador;
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
	
	
}
