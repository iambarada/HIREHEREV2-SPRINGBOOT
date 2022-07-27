package com.centroxy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centroxy.entities.Project;

/**
 * @author Jogesh Krupa Dash 06-Jul-2022 7:27:37 pm
 */
public interface ProjectRepository extends JpaRepository<Project, String> {

}
