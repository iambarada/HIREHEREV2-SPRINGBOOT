package com.centroxy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroxy.entities.JobDescription;
import com.centroxy.exceptions.DataNotFoundException;
import com.centroxy.repositories.JobDescriptionRepository;

/**
 * @author Sumita Sethy 07-July-2022 11:56:30 am
 */

@Service
public class JobDescriptionServiceImpl implements JobDescriptionService {

	private final JobDescriptionRepository jobDescriptionRepository;

	@Autowired
	public JobDescriptionServiceImpl(JobDescriptionRepository jobDescriptionRepository) {
		this.jobDescriptionRepository = jobDescriptionRepository;
	}

	// Creation of Resource Demand
	@Override
	public JobDescription saveJd(JobDescription jobDescription) {

		return jobDescriptionRepository.save(jobDescription);

	}

	/**
	 * @author Subhasmita Panda 07-July-2022 11:56:30 am
	 */

	// Fetch the all Demands
	@Override
	public List<JobDescription> getDemands() {
		List<JobDescription> demands = jobDescriptionRepository.findAll();
		return demands;

	}

	/**
	 * @author Subhasmita Panda 07-July-2022 11:56:30 am
	 */
	// Fetch the particular demand findById
	@Override
	public JobDescription getDemandById(String jdId) {
		JobDescription jobDescription = null;
		Optional<JobDescription> demand = jobDescriptionRepository.findById(jdId);

		if (demand == null) {
			throw new DataNotFoundException();
		} else {
			jobDescription = demand.get();
		}

		return jobDescription;
	}
	
	/**
	 * @author Diptiranjan Balabanataray 15-July-2022 13:56:30 am
	 */
	//fetching Requirements approved demands by CEO
	@Override
	public List<JobDescription> findByApprovedResource() {
		return jobDescriptionRepository.findByIsApprovedTrue();
	}


}
