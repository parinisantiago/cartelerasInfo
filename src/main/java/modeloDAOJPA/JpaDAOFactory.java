package modeloDAOJPA;

import modelo.Anuncio;
import modelo.Cartelera;
import modelo.Comentario;
import modelo.Notificacion;
import modelo.Rol;
import modelo.Usuario;
import modeloDAO.AnuncioDAO;
import modeloDAO.CarteleraDAO;
import modeloDAO.ComentarioDAO;
import modeloDAO.NotificacionDAO;
import modeloDAO.RolDAO;

public class JpaDAOFactory {
	
	public static AnuncioDAO getAnuncioDao(){
		return new AnuncioJpaDAO();
	}
	
	public static CarteleraDAO getCarteleraDao(){
		return new CarteleraJpaDAO();
	}
	
	public static ComentarioDAO getComentarioDao(){
		return new ComentarioJpaDAO();
	}
	
	public static NotificacionDAO getNotificacionDao(){
		return new NotificacionJpaDAO();
	}
	
	public static RolDAO getRolDao(){
		return new RolJpaDAO();
	}
	
	public static UsuarioJpaDAO getUsuarioDao(){
		return new UsuarioJpaDAO();
	}
	
	public static AnuncioDAO getDao(Anuncio anuncio){
		return new AnuncioJpaDAO();
	}
	
	public static CarteleraDAO getDao(Cartelera cartelera){
		return new CarteleraJpaDAO();
	}
	
	public static ComentarioDAO getDao(Comentario comentario){
		return new ComentarioJpaDAO();
	}
	
	public static NotificacionDAO getDao(Notificacion notificacion){
		return new NotificacionJpaDAO();
	}
	
	public static RolDAO getDao(Rol rol){
		return new RolJpaDAO();
	}
	
	public static UsuarioJpaDAO getDao(Usuario usuario){
		return new UsuarioJpaDAO();
	}
	
}
