package com.centroxy.services;

import java.util.List;

import com.centroxy.entities.Project;

/**
 * @author Jogesh Krupa Dash 06-Jul-2022 7:28:11 pm
 */
public interface ICEOService {
	// to add new project
	Project addNewProject(Project project);

	// fetch project details by projectId
	public Project findProjectById(String projectId);

	// fetch all projects
	public List<Project> fetchAllProjects();

}
