package com.centroxy.services;

import java.util.List;

import com.centroxy.entities.Employee;

/**
 * 
 * @author Badal 11-Jul-2022 5:17:34 pm
 * 
 */

public interface IPMService {

	public String assignProject(List<Employee> empNo, String projectId);

}
