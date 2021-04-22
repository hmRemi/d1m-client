package com.d1m.client.hud.render.notifications;

import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager
{
    private static CopyOnWriteArrayList<Notification> pendingNotifications;
    private static Notification currentNotification;
    public Notification lastNotif;

    public NotificationManager() {
        this.lastNotif = null;
    }

    public Notification getLastNotif() {
        return this.lastNotif;
    }

    public CopyOnWriteArrayList<Notification> getNotifications() {
        return NotificationManager.pendingNotifications;
    }

    public void setLastNotif(final Notification lastNotif) {
        this.lastNotif = lastNotif;
    }

    public static void show(final NotificationType type, final String name, final String text, final int length) {
        final Notification newNotification = new Notification(type, name, text, length);
        NotificationManager.pendingNotifications.add(newNotification);
        newNotification.show();
    }

    public void removeFromList(final int index) {
        NotificationManager.pendingNotifications.remove(index);
    }

    public void removeFromList(final Notification object) {
        NotificationManager.pendingNotifications.remove(object);
    }

    public int getIndex(final Notification notification) {
        return NotificationManager.pendingNotifications.indexOf(notification);
    }

    public Notification getObject(final int index) {
        return NotificationManager.pendingNotifications.get(index);
    }

    public static void notificationUpdate() {
        final CopyOnWriteArrayList<Notification> notificationList = NotificationManager.pendingNotifications;
        for (final Notification notification : notificationList) {
            notification.resetOffset();
        }
        for (final Notification notification : notificationList) {
            notification.updateOffset();
        }
        for (final Notification notification : notificationList) {
            notification.render();
        }
    }

    static {
        NotificationManager.pendingNotifications = new CopyOnWriteArrayList<Notification>();
        NotificationManager.currentNotification = null;
    }
}
