package com.bugTracker.Bug.Tracker.repository;

import com.bugTracker.Bug.Tracker.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    @Query(value = "{$and:[{'user_id':?0}, {'status':{$ne:5}},{'is_opened':false}]}", count = true)
    int isThereUnread(String userId);

    @Query(value = "{$and:[{'user_id':?0}, {'status':{$ne:5}},{'is_opened':false}]}", sort = "{notification_id:-1}")
    List<Notification> getUnreadNotifications(String userId);

    @Query(value = "{$and:[{'user_id':?0}, {'status':{$ne:5}}]}")
    List<Notification> getAllNotifications(String userId);

    @Query(value = "{$and:[{'status':{$ne:5}},{'notification_id':?0}]}")
    Notification getNotificationById(String notificationId);
}
