package com.centroxy.services;

import java.util.List;

import java.util.NoSuchElementException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.centroxy.entities.Employee;

import com.centroxy.entities.Project;

import com.centroxy.exceptions.EmployeeNotFoundException;

import com.centroxy.exceptions.ProjectNotFoundException;

import com.centroxy.repositories.EmployeeRepository;

import com.centroxy.repositories.ProjectRepository;

/**
 * 
 * @author Badal 11-Jul-2022 5:18:45 pm
 * 
 */

@Service

public class PMServiceImpl implements IPMService {

	private EmployeeRepository employeeRepository;

	private ProjectRepository projectRepository;

	@Autowired
	public PMServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
		this.employeeRepository = employeeRepository;
		this.projectRepository = projectRepository;
	}

	/**
	 * 
	 * @author Badal 12-Jul-2022 1:00:18 pm
	 * 
	 */

	// This method is used for assign the project to employee

	@Override

	public String assignProject(List<Employee> listOfEmp, String projectId) {

		try {

			Optional<Project> findProjectById = projectRepository.findById(projectId);

			Project project = findProjectById.get();

			for (int i = 0; i < listOfEmp.size(); i++) {

				try {

					Optional<Employee> findEmployeeById = employeeRepository.findById(listOfEmp.get(i).getEmpNo());

					Employee employee = findEmployeeById.get();

					employee.setAvailable(false);

					employee.setProject(project);

					employeeRepository.save(employee);

				} catch (Exception e) {

					throw new EmployeeNotFoundException();

				}

			}

		} catch (NoSuchElementException e) {

			throw new ProjectNotFoundException();

		}

		return "Project assigned to Employees";

	}

}
