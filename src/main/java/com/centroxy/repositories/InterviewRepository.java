package com.centroxy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.centroxy.entities.Interview;

/**
 * @author Jogesh Krupa Dash 06-Jul-2022 7:27:51 pm
 */
public interface InterviewRepository extends JpaRepository<Interview, String> {
}
