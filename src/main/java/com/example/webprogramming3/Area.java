package com.example.webprogramming3;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;

@Named
@ApplicationScoped
public class Area extends NotificationBroadcasterSupport implements AreaMBean, Serializable {

    private long pointsCount = 0;
    private long correctPointsCount = 0;
    private long incorrectPointsCount = 0;
    private boolean isSecondMiss = false;
    private int sequenceNumber = 0;

    @Inject
    private SimpleAgent simpleAgent;

    public Area(){

    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused){
        simpleAgent.registerBean(this, "area");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused){
        simpleAgent.unregisterBean(this);
    }
    @Override
    public void registerNewPoint() {
        pointsCount++;

    }

    @Override
    public void registerCorrectNewPoint() {
        correctPointsCount++;
        incorrectPointsCount = 0;
        setIsSecondMiss();

    }

    @Override
    public long getPointsCount() {
        return pointsCount;
    }

    @Override
    public void definePointsCount(long pointsCount) {
        this.pointsCount = pointsCount;
    }

    @Override
    public long getCorrectPointsCount() {
        return correctPointsCount;
    }

    @Override
    public void defineCorrectPointsCount(long correctPointsCount) {
        this.correctPointsCount = correctPointsCount;
    }

    @Override
    public void registerIncorrectPoint() {
        incorrectPointsCount++;
        setIsSecondMiss();
    }

    @Override
    public void setIsSecondMiss() {
        if (this.incorrectPointsCount == 2) {
            this.isSecondMiss = true;
            Notification notification = new Notification(
                    "Second Miss",
                    getClass().getSimpleName(),
                    sequenceNumber++,
                    "You are looser!"
            );
            sendNotification(notification);
            this.incorrectPointsCount = 0;
        } else {
            this.isSecondMiss = false;
        }

    }

    @Override
    public boolean getSecondMiss() {
        return isSecondMiss;
    }

    @Override
    public void defineIncorrectPointsCount(long incorrectPointsCount) {
        this.incorrectPointsCount = incorrectPointsCount;
    }

    @Override
    public long getIncorrectPoints() {
        return incorrectPointsCount;
    }
}
