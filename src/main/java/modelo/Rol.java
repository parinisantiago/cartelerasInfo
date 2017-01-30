package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rest.JView;


@Entity
@Table(name="rol")
public class Rol implements Serializable{

	private static final long serialVersionUID = 8131275885237673765L;

	@Id
	@GeneratedValue
	//@JsonView(JView.Publico.class)
	@JsonView(JView.SoloID.class)
	private Long id;
	
	@Column(nullable = false, unique=true)
	//@JsonView(JView.Publico.class)
	@JsonView({JView.Rol.class, JView.Simple.class, JView.CarteleraCompleta.class})
	private String nombre;
	
	public Rol(){
		super();
	}
	
	public Rol(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Rol)) {
			return false;
		}
		Rol other = (Rol) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}


}
