package modeloDAOJPA;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import modelo.Notificacion;
import modelo.Usuario;
import modeloDAO.NotificacionDAO;
@Repository
public class NotificacionJpaDAO extends JpaDao<Notificacion> implements NotificacionDAO {

	public NotificacionJpaDAO() {
		super(Notificacion.class);
	}

	@Override
	public List<Notificacion> getAllFromUser(Usuario user) {
		List<Notificacion> resultado = new ArrayList<Notificacion>();
		try {
			Query consulta = this.getEntityManager()
					.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.usuario.id = :userId");
			consulta.setParameter("userId", user.getId());
			resultado = (List<Notificacion>) consulta.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return resultado;
	}


}
