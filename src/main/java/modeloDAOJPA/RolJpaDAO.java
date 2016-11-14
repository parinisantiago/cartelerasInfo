package modeloDAOJPA;

import javax.persistence.Query;

import modelo.Rol;
import modeloDAO.RolDAO;

public class RolJpaDAO extends JpaDao<Rol> implements RolDAO {

	@Override
	public Rol getById(Long id) {
		Query consulta = EMF.getEMF().createEntityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id");
		consulta.setParameter("id", id);
		System.out.println(consulta.toString());
		Rol resultado = (Rol) consulta.getSingleResult();
		return resultado;
	}
	
}
