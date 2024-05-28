package com.example.webprogramming3;

import com.example.webprogramming3.DataBase.HibernateUtils;
import com.example.webprogramming3.DataBase.TableDaoImpl;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.inject.Inject;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Objects;
import java.util.SortedMap;


@Named
@ApplicationScoped
public class AreaCheckerResultBean implements Serializable {
    @Inject
    private CoordinateBean coordinateBean;
    private LinkedList<AreaResultBean> resultsTable;
    @Inject
    private Area areaBeanImpl;




    public AreaCheckerResultBean() throws SQLException {
        TableDaoImpl tableDao = new TableDaoImpl();
        this.resultsTable = new LinkedList<>();
        try {
                this.resultsTable = new LinkedList<>(tableDao.getAllResultsInDAO());
        }catch (SQLException ex){}
    }

    @PostConstruct
    public void init(){
        areaBeanImpl.definePointsCount(resultsTable.size());
        areaBeanImpl.defineCorrectPointsCount(resultsTable.stream().filter(AreaResultBean::isResult).toList().size());
        areaBeanImpl.defineIncorrectPointsCount(areaBeanImpl.getIncorrectPoints());
    }

    public CoordinateBean getCoordinateBean() {
        return coordinateBean;
    }

    public void setCoordinateBean(CoordinateBean coordinateBean) {
        this.coordinateBean = coordinateBean;
    }

    public LinkedList<AreaResultBean> getResultsTable() {
        return resultsTable;
    }

    public void setResultsTable(LinkedList<AreaResultBean> resultsTable) {
        this.resultsTable = resultsTable;
    }

    public void addNewValues(){
        AreaResultBean areaResultBean = new AreaResultBean();
        long startExecute = System.nanoTime();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        Double x = coordinateBean.getX();
        Double y = coordinateBean.getY();
        if(x!=null) {
            x = Double.parseDouble(decimalFormat.format(coordinateBean.getX()).replace(",", "."));
        }
        if(y!=null) {
            y = Double.parseDouble(decimalFormat.format(coordinateBean.getY()).replace(",", "."));
        }
        Double r = coordinateBean.getR();
        System.out.println("X " + x);
        System.out.println("R: " +r);
        if(!validateY(y)){
            return;
        }
        if(!validateX(x)){
            return;
        }
        if(!validateR(r)){
            return;
        }
        boolean result = AreaChecker.getAreaResult(x, y, r);
        if(result){
            areaBeanImpl.registerCorrectNewPoint();
        }else{
            areaBeanImpl.registerIncorrectPoint();
        }
        areaResultBean.setX(x);
        areaResultBean.setY(y);
        areaResultBean.setR(r);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'Time: 'HH:mm:ss");

        String date = localDateTime.format(formatter);
        areaResultBean.setLocalDateTime(date);
        areaResultBean.setResult(result);
        long executedTime = System.nanoTime()-startExecute;
        areaResultBean.setExecutionTime(executedTime);
        TableDaoImpl tableDao = new TableDaoImpl();
        try {
            tableDao.addValuesToTable(areaResultBean);
        } catch (SQLException ex) {}
        resultsTable.addLast(areaResultBean);
        areaBeanImpl.registerNewPoint();


    }

    public void clearTable(){
        TableDaoImpl tableDao = new TableDaoImpl();
        try {
            tableDao.clearTable();
        }catch (SQLException ex){}

        resultsTable.clear();
        areaBeanImpl.definePointsCount(0);
        areaBeanImpl.defineIncorrectPointsCount(0);
        coordinateBean.setR(null);
    }

    private boolean validateX(Double x){
        boolean flag = true;
        FacesMessage facesMessage = null;
        if(x==null){
            facesMessage = new FacesMessage("Выберете X");
            flag = false;
        }
        if(x!=null && (x<-3 || x>5)){
            facesMessage = new FacesMessage("SUMMARY","X лежит в промежутке от [-3;5]");
            flag = false;
        }
        if(facesMessage!=null) {
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
        return flag;
    }
    private boolean validateY(Double y){
        boolean flag = true;
        FacesMessage facesMessage = null;
        if(y==null){
            facesMessage = new FacesMessage("Y не может быть пустым");
            flag = false;
        }
        if(y!=null && (y<-3 || y>3)){
            facesMessage = new FacesMessage("SUMMARY","Y лежит в отрезке [-3 ; 3]");
            flag = false;
        }
        if(facesMessage!=null) {
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
        return flag;
    }

    private boolean validateR(Double r){
        boolean flag = true;
        FacesMessage facesMessage=null;
        if(r==null){
            facesMessage = new FacesMessage("SUMMARY","Выберете R");
            flag = false;
        }
        if(r!=null && (r<-5 || r>5)){
            facesMessage = new FacesMessage("R лежит в промежутке от [-5;5]");
            flag = false;
        }
        if(facesMessage!=null) {
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaCheckerResultBean that = (AreaCheckerResultBean) o;
        return Objects.equals(coordinateBean, that.coordinateBean) && Objects.equals(resultsTable, that.resultsTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinateBean, resultsTable);
    }

    @Override
    public String toString() {
        return "AreaCheckerResultBean{" +
                "coordinateBean=" + coordinateBean +
                ", resultsTable=" + resultsTable +
                '}';
    }
}
