package com.tracking.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracking.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
