package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rest.JView;

@Entity
@Table(name="notificacion")
public class Notificacion {
	
	@Id
	@GeneratedValue
	//@JsonView(JView.Publico.class)
	@JsonView(JView.SoloID.class)
	private Long id;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JsonView(JView.Notificacion.class)
	private Usuario usuario;
	
	@Column(nullable = false)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Notificacion.class, JView.Simple.class})
	private String descripcion;

	
	public Notificacion(){
		super();
	}
	
	public Notificacion(String descripcion) {
		super();
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getUsuario(){
		return this.usuario;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", usuario=" + usuario + ", descripcion=" + descripcion + "]";
	}

}
