package com.tracking.notification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tracking.notification.service.NotificationService;

@Component
public class ScheduledTasks {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	private NotificationService service;

	@Scheduled(cron = "0 0/5 * * * ?")
	public void scheduleTaskWithCronExpression() throws Exception {
		service.sentNewNotification();
		logger.info("Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
}
