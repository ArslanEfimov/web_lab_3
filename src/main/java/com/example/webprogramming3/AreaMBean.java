package com.example.webprogramming3;

public interface AreaMBean {
    void registerNewPoint();
    void registerCorrectNewPoint();
    long getPointsCount();
    void definePointsCount(long pointsCount);
    long getCorrectPointsCount();
    void defineCorrectPointsCount(long correctPointsCount);
    void registerIncorrectPoint();
    void setIsSecondMiss();
    boolean getSecondMiss();
    void defineIncorrectPointsCount(long incorrectPointsCount);
    long getIncorrectPoints();



}
