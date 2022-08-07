package com.centroxy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centroxy.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, String> {



}
