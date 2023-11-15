package com.example.webprogramming3;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

import jakarta.enterprise.context.SessionScoped;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;


@Named
@SessionScoped
public class CoordinateBean implements Serializable {

    private Double x;
    private Double y;
    private Double r;

    public CoordinateBean(){
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateBean that = (CoordinateBean) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y) && Objects.equals(r, that.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r);
    }

    @Override
    public String toString() {
        return "CoordinateBean{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                '}';
    }
}
