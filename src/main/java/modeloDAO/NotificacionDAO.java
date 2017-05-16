package modeloDAO;

import java.util.List;

import modelo.Notificacion;
import modelo.Usuario;

public interface NotificacionDAO extends Dao<Notificacion> {
	List<Notificacion> getAllFromUser( Usuario user);
}
