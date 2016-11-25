package modeloDAOJPA;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import modelo.Rol;
import modeloDAO.RolDAO;
@Repository
public class RolJpaDAO extends JpaDao<Rol> implements RolDAO {

	public RolJpaDAO() {
		super(Rol.class);
	}

	@Override
	public Rol getByNombre(String nombre) {
		Rol resultado = null;
	
			Query consulta = this.getEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.nombre = :nombre");
			consulta.setParameter("nombre", nombre);
			resultado = (Rol) consulta.getSingleResult();
		
		return resultado;
	}
	
}
