/*
 * Copyright (c) 2020 Leonard Hußke. All rights reserved.
 * 
 * University:		Frankfurt University of Applied Sciences
 * Study program:	Engineering Business Information Systems
 * Semester:		Web-basierte Anwendungenssysteme 20/21
 * Professor:		Prof. Dr. Armin Lehmann
 * Date:			30.12.2020
 * 
 * Author:			Leonard Hußke
 * Email:			leonard.husske@stud.fra-uas.de
 */

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
