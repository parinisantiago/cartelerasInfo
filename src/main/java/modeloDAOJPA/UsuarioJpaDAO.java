package modeloDAOJPA;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import modelo.Rol;
import modelo.Usuario;
import modeloDAO.UsuarioDAO;
@Repository
public class UsuarioJpaDAO extends JpaDao<Usuario> implements UsuarioDAO {

	public UsuarioJpaDAO() {
		super(Usuario.class);
	}

	@Override
	public Usuario getByUser(String user) {
		Usuario resultado = null;
		Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.user = :user");
			consulta.setParameter("user", user);
			resultado = (Usuario) consulta.getSingleResult();
		
		return resultado;
	}

	@Override
	public List<Usuario> getAllWithRol(Rol rol) {
		List<Usuario> resultado = new ArrayList<Usuario>();
	
			Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.rol = :rol");
			consulta.setParameter("rol", rol);
			resultado = (List<Usuario>) consulta.getResultList();
		
		return resultado;
	}

	@Override
	public Usuario getById(Long id) {
		Usuario resultado = null;
			Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id");
			consulta.setParameter("id", id);
			resultado = (Usuario) consulta.getSingleResult();
			resultado.getNotificaciones().size();
			resultado.getComentarios().size();
			resultado.getIntereses().size();
			resultado.getMisAnuncios().size();
		
		return resultado;
	}
	
	
}
