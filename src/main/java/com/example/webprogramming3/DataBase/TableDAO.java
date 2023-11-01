package com.example.webprogramming3.DataBase;

import com.example.webprogramming3.AreaResultBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface TableDAO {

    void addValuesToTable(AreaResultBean resultBean) throws SQLException;
    void clearTable() throws SQLException;
    List<AreaResultBean> getAllResultsInDAO() throws SQLException;
}
