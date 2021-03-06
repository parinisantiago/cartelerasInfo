package modeloDAO;

import java.util.List;

public interface Dao<T> {
	
	boolean persist(T entity);
	boolean remove(T entity);
	boolean update(T entity);
	T getById(Long id);
	List<T> selectAll();
}
