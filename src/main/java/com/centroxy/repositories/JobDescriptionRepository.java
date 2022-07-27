package com.centroxy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centroxy.entities.JobDescription;

/**
 * @author Sumita Sethy 07-July-2022 11:50:34 am
 */
@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription, String> {

	List<JobDescription> findByIsApprovedTrue();

}

