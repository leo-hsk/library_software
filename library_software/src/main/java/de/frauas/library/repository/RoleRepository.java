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

package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.frauas.library.model.Role;

/**
 * RoleRepository defines methods to retrieve role related data from the database.
 * @author Leonard
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long>{

}
