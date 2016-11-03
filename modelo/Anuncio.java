package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Anuncio {
	private String titulo;
	private String cuerpo;
	private boolean comentarioHabilitado;
	private Usuario posteador;
	private Date fecha;
	private List<Comentario> comentarios;
	private boolean habilitado;
	
	public Anuncio() {
		this.comentarios = new ArrayList<Comentario>();
	}

	public Anuncio(String titulo, String cuerpo, boolean comentarioHabilitado, Usuario posteador, Date fecha) {
		super();
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.comentarioHabilitado = comentarioHabilitado;
		this.posteador = posteador;
		this.fecha = fecha;
	}

	public Anuncio(String titulo, String cuerpo, boolean comentarioHabilitado, Usuario posteador, Date fecha,
			List<Comentario> comentarios) {
		super();
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.comentarioHabilitado = comentarioHabilitado;
		this.posteador = posteador;
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

	public Usuario getPosteador() {
		return posteador;
	}

	public void setPosteador(Usuario posteador) {
		this.posteador = posteador;
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
