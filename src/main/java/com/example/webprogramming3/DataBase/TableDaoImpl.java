package com.example.webprogramming3.DataBase;

import com.example.webprogramming3.AreaResultBean;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import javax.management.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableDaoImpl implements TableDAO{


    private static final String TABLE_NAME = "RESULT_TABLE";
    public TableDaoImpl(){

    }
    @Override
    public void addValuesToTable(AreaResultBean results) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(results);
            session.getTransaction().commit();
        }catch (Throwable ex){
            System.err.println("Problems with Data Base..." + ex);
            throw new SQLException(ex);
        }finally {
            if(session!=null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public void clearTable() throws SQLException {
        Session session = null;

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            String sqlQuery = "DELETE FROM " + TABLE_NAME;
            session.createNativeQuery(sqlQuery, this.getClass()).executeUpdate();
            session.getTransaction().commit();
        }catch (Throwable ex){
            System.err.println("Problems with Data Base..." + ex);
            throw new SQLException(ex);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List<AreaResultBean> getAllResultsInDAO() throws SQLException {
        Session session = null;
        List<AreaResultBean> data = null;
        try{
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<AreaResultBean> criteria = criteriaBuilder.createQuery(AreaResultBean.class);
            criteria.from(AreaResultBean.class);
            data = session.createQuery(criteria).getResultList();

        }catch (Throwable ex){
            System.err.println("Problems with Data Base..." + ex);
            throw new SQLException(ex);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
        return data;
    }
}
