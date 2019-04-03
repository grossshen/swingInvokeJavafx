package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/3 13:32
 */
public class Temp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox();
        Button button=new Button("hhhhhhhhhhhhh");
        button.setOnMouseDragged(handler->{
            while (true){
                System.out.println(
                        "xxx"
                );
            }
        });
        button.setOnMouseClicked(handler->{
            System.out.println("hijojofjioeiohewg");

        });
        box.getChildren().addAll(button);
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
