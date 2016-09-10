package com.pucho.domain;

import com.pucho.PuchoMainService;
import com.pucho.helper.NotificationRequest;
import com.pucho.service.NotificationService;
import com.pucho.service.impl.PushNotificationService;
import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;
import ru.vyarus.dropwizard.guice.injector.lookup.InjectorLookup;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.List;

/**
 * Created by harshmathur on 18/04/16.
 */
public class UserFollowerEventLIstener {

    private PushNotificationService pushNotificationService;

    public UserFollowerEventLIstener() {
        this.pushNotificationService = InjectorLookup.getInjector(PuchoMainService.getPuchoMainService())
                .get().getBinding(PushNotificationService.class).getProvider()
                .get();
    }

    @PostPersist
    protected void postPersist(UserFollower userFollower) {
        sendNotification(userFollower);
    }

    @PostUpdate
    protected void postUpdate(UserFollower userFollower) {
        sendNotification(userFollower);
    }

    private void sendNotification(UserFollower userFollower) {
        User follower = User.findById(userFollower.getFollowerUserId());
        User followed = User.findById(userFollower.getFollowedUserId());
        NotificationRequest notify = new NotificationRequest();
        notify.setUserId(userFollower.getFollowerUserId());
        notify.setNotificationType(NotificationType.NEW_FOLLOWER.getInt());
        notify.setUserName(follower.getUsername());
        this.pushNotificationService.notificationRequest(followed, notify);
    }
}
