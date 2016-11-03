package modelo;

import java.util.Date;

public class Comentario {
	private String texto;
	private Date fecha;
	private Usuario usuario;
	private boolean habilitado;
	
	public Comentario() {
		super();
	}

	public Comentario(String texto, Date fecha, Usuario usuario) {
		super();
		this.texto = texto;
		this.fecha = fecha;
		this.usuario = usuario;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
