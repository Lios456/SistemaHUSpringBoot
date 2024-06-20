package com.tasksys.SistemaTareasApp;

import com.tasksys.SistemaTareasApp.presentacion.SistemaTareasFx;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main{
	public static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		Application.launch(SistemaTareasFx.class, args);
		//SpringApplication.run(Main.class, args);
	}


}
