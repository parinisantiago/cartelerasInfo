package rest;

public class JView {
	//actualmente sin uso
	public interface Publico{};
	
	public interface Privado extends Publico{};
	
	public interface SoloID{}
	public interface Simple extends SoloID{};
	public interface Anidado extends Simple{};
	
	public interface Anuncio extends SoloID{};
	public interface Cartelera extends SoloID{};
	public interface Comentario extends SoloID{};
	public interface Notificacion extends SoloID{};
	public interface Rol extends SoloID{};
	public interface Usuario extends SoloID{};
	
	public interface CarteleraCompleta extends Cartelera{};
	
}
