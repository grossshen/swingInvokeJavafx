package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/3 17:07
 */
@SpringBootApplication
@RestController
public class NewMain extends Application{
    private static WebView webViewLeft;
    private static WebView webViewRight;

    @Override
    public void init(){
        //启动springboot
        ConfigurableApplicationContext applicationContext=SpringApplication.run(Main.class);
//        new FXMLLoader().setControllerFactory(applicationContext::getBean);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        HBox hBoxUp = new HBox();
        webViewLeft=new WebView();
        webViewLeft.setDisable(true);
        webViewLeft.setVisible(false);
        System.out.println(webViewLeft);
        WebEngine webEngineLeft = webViewLeft.getEngine();
        String urlLeft = this.getClass().getResource("/static/html/leftHtml.html").toExternalForm();
        webEngineLeft.load(urlLeft);
        Separator separator = new Separator();
        webViewRight = new WebView();
        webViewRight.setPrefWidth(200);
        webViewRight.setDisable(true);
        webViewRight.setVisible(false);
        WebEngine webEngineRight=webViewRight.getEngine();
        String urlRight = this.getClass().getResource("/static/html/rightHtml.html").toExternalForm();
        webEngineRight.load(urlRight);
        hBoxUp.getChildren().addAll(webViewLeft, separator, webViewRight);


        HBox hBoxDown = new HBox();
        //右对齐直接设置失效，使用不可见的button替代
        hBoxDown.setAlignment(Pos.CENTER);
        Button block = new Button("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        block.setVisible(false);
        block.setDisable(true);
        //初始化小红点
        //增加小红点拖拽功能
        Circle circle = new Circle();
        circle.setRadius(15);
        circle.setFill(Color.RED);
        DragUtil.addDragListener(primaryStage, circle);
        //设置小红点右键菜单
        ContextMenu contextMenu = new ContextMenu();
        MenuItem close = new MenuItem("关闭");
        close.setOnAction(actionEvent -> Platform.exit());
        contextMenu.getItems().add(close);
        circle.setOnContextMenuRequested(
                event -> contextMenu.show(circle, event.getScreenX(), event.getScreenY())
        );
//        小红点点击开关webview
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent handler) {
                if (handler.getButton()== MouseButton.PRIMARY/*&&handler.getEventType()==MouseEvent.MOUSE_RELEASED*/){
                    if (webViewRight.isVisible() || webViewLeft.isVisible()) {
                        webViewLeft.setVisible(false);
                        webViewLeft.setDisable(true);
                        webViewRight.setVisible(false);
                        webViewRight.setDisable(true);
                        separator.setVisible(false);
                        separator.setDisable(true);
                    } else if (!webViewRight.isVisible()) {
                        webViewRight.setVisible(true);
                        webViewRight.setDisable(false);
                        separator.setVisible(true);
                        separator.setDisable(false);
                    }
                }
            }
        });
        hBoxDown.getChildren().addAll(block,circle);

        root.getChildren().addAll(hBoxUp,hBoxDown);

        Scene scene = new Scene(root);
        //隐藏各种不美观的控件
        scene.setFill(null);
        root.setStyle("-fx-background-color: rgba(0,0,0,0.0);");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //esc退出
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                Platform.exit();
            }
        });

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void stop(){
//        System.out.println(webViewLeft);
    }
    public static void main(String[] args){
        launch(args);
    }
    @GetMapping("/openLeftWebview")
    public void openleftWebview(){
        System.out.println("打开左边的界面");
        webViewLeft.setVisible(true);
        webViewLeft.setDisable(false);
    }
    @Test
    public void test(){
        System.out.println(webViewLeft);
    }
    @Test
    public void test1(){
        System.out.println("something");
    }
}
