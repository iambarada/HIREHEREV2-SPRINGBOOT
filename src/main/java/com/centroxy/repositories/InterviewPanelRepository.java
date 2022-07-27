package com.centroxy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centroxy.entities.InterviewPanel;

@Repository
public interface InterviewPanelRepository extends JpaRepository<InterviewPanel, Long> {

}

