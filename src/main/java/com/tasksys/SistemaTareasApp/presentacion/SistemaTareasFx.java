package com.tasksys.SistemaTareasApp.presentacion;

import com.tasksys.SistemaTareasApp.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SistemaTareasFx extends Application {
    private ConfigurableApplicationContext contexto;
    @Override
    public void init(){
        this.contexto = new SpringApplicationBuilder(Main.class).run();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(contexto::getBean);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        contexto.close();
        Platform.exit();
    }
}
