package com.centroxy.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroxy.entities.Project;
import com.centroxy.repositories.ProjectRepository;

/**
 * @author Jogesh Krupa Dash 06-Jul-2022 7:28:18 pm
 */
@Service
public class CEOServiceImpl implements ICEOService {
	private ProjectRepository projectRepository;

	@Autowired
	public CEOServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	// logic for adding new project into the database
	@Override
	public Project addNewProject(Project project) {
		Project savedProject = null;
		try {
			savedProject = projectRepository.save(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedProject;
	}

	/**
	 * @author Badal Jul 7, 2022 11:51:17 AM
	 */
	// This method is used for find one project by ProjectId
	@Override
	public Project findProjectById(String projectId) {
		Project project = null;
		Optional<Project> projectById = projectRepository.findById(projectId);
		if (projectById == null) {
			throw new NoSuchElementException();
		} else {
			project = projectById.get();
		}
		return project;
	}

	/**
	 * @author Badal Jul 7, 2022 11:51:17 AM
	 */
	// This method is used for fetch all the projects
	@Override
	public List<Project> fetchAllProjects() {
		return projectRepository.findAll();
	}

}
