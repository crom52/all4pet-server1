package com.all4pet;

import java.io.IOException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.all4pet.mapper")
@SpringBootApplication	
public class App 
{
    public static void main( String[] args ) throws IOException {	  	
        SpringApplication.run(App.class, args);
        }              
}
