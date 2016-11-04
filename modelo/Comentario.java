package modelo;

import java.util.Date;

public class Comentario {
	private String texto;
	private Date fecha;
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
