package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/db-test")
public class DBTestController {
    @Autowired
    public DataSource dataSource = null;
    @GetMapping
    public String testConnection(){
        try{
            if(dataSource.getConnection() != null){
                return "Database connection is successful!\n"
                        + "Database URL: " + dataSource.getConnection().getMetaData().getURL() + "\n"
                        + "Database User: " + dataSource.getConnection().getMetaData().getUserName();
            }else{
                return "Database connection failed!";
            }
        }catch (Exception e){
            throw new RuntimeException("DB connection failed: "+e);
        }
    }
}
