package com.centroxy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroxy.entities.Notification;
import com.centroxy.repositories.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements INotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void saveNotification(Notification notification){
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> fetchAllNotificationsForPM() {
        return notificationRepository.findAll().stream().filter(notification -> notification.getReceivedBy().contains("PM")).collect(Collectors.toList());
    }

    @Override
    public List<Notification> fetchAllNotificationsForCEO() {
        return notificationRepository.findAll().stream().filter(notification -> notification.getReceivedBy().contains("CEO")).collect(Collectors.toList());
    }
    @Override
    public List<Notification> fetchAllNotificationsForHR() {
        return notificationRepository.findAll().stream().filter(notification -> notification.getReceivedBy().contains("HR")).collect(Collectors.toList());
    }
}