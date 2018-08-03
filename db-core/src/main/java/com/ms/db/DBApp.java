package com.ms.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DBApp {

     public static void main(String []args){
         SpringApplication app = new SpringApplication(DBApp.class);
         app.run(args);
     }
}
