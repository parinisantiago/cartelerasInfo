package modeloDAOJPA;

public interface Dao<T> {
	
	boolean persist(T entity);
	boolean remove(T entity);
	boolean update(T entity);
	boolean selectAll(T entity);
}
