package modeloDAOJPA;

public class DAOFactory {
	
	public static AnuncioJpaDAO getAnuncioDao(){
		return new AnuncioJpaDAO();
	}
	
	public static CarteleraJpaDAO getCarteleraDao(){
		return new CarteleraJpaDAO();
	}
	
	public static ComentarioJpaDAO getComentarioDao(){
		return new ComentarioJpaDAO();
	}
	
	public static NotificacionJpaDAO getNotificacionDao(){
		return new NotificacionJpaDAO();
	}
	
	public static RolJpaDAO getRolDao(){
		return new RolJpaDAO();
	}
	
	public static UsuarioJpaDAO getUsuarioDao(){
		return new UsuarioJpaDAO();
	}
	
}
