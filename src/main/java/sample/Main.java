package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.util.DragUtil;

@SpringBootApplication
@RestController
public class Main extends Application {
    public static Stage stage;

    @Override
    public void init(){
        //springboot 启动
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class);
        new FXMLLoader().setControllerFactory(applicationContext::getBean);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/static/javafxml/main.fxml"));

        root.setStyle("-fx-background-color: rgba(0,0,0,0.0);");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("ultraSonic");
        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Test
    public void test(){
        System.out.println(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
