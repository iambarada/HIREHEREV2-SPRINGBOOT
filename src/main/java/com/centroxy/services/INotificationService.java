package com.centroxy.services;

import com.centroxy.entities.Notification;

import java.util.List;

public interface INotificationService {
    public void saveNotification(Notification notification);

    public List<Notification> fetchAllNotificationsForPM();

    public List<Notification> fetchAllNotificationsForCEO();

    public List<Notification> fetchAllNotificationsForHR();
}
