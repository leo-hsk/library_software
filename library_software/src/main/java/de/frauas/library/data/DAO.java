package de.frauas.library.data;

import java.util.List;
import java.util.Optional;

/**
 * Interface for CRUD operations.
 * @author Leonard
 *
 * @param <T>
 */
public interface DAO <T>{
	
	Optional<T> get(long id);
	
	List<T> getAll();
	
	void save(T t);
	
	void update(T t, String[] params);
	
	void delete(T t);
	
	void edit(T t, String[] params);

}
