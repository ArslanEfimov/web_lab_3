package com.example.webprogramming3;


public class AreaChecker {

    public static boolean getAreaResult(double x, double y, double r){
        return (isInSquare(x, y, r) || isInTriangle(x, y, r) || isInCircle(x, y, r));
    }

    private static boolean isInSquare(double x, double y, double r){
        return ((x<=0 && y>=0) && (y<=r/2) && (x>=-r));
    }

    private static boolean isInTriangle(double x, double y, double r){
       return ((x>=0 && y<=0) && (y>=-r+x));
    }

    private static boolean isInCircle(double x, double y, double r){
        return ((x<=0 && y<=0) && (Math.pow(r, 2) >= Math.pow(x,2) + Math.pow(y,2)));
    }
}
