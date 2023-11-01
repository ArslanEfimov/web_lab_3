package com.example.webprogramming3.DataBase;

import com.example.webprogramming3.AreaResultBean;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


public class HibernateUtils {

    private static SessionFactory sessionFactory;


    private static void setSessionFactory() throws SQLException {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AreaResultBean.class).buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Something with configuration was wrong..." + e);
            throw new SQLException(e);

        }
    }

    public static SessionFactory getSessionFactory() throws SQLException {
        setSessionFactory();
        return sessionFactory;
    }
}
