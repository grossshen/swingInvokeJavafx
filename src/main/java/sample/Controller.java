package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.util.DragUtil;

import java.net.URL;
import java.util.ResourceBundle;


@RestController
public class Controller implements Initializable{
    @FXML
    HBox circleHbox;
    @FXML
    Circle circle;

    @FXML
    HBox hboxUp;
    @FXML
    WebView webViewLeft;
    @FXML
    WebView webViewRight;
    @FXML
    Separator separator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //左边面板
        webViewLeft.setDisable(true);
        webViewLeft.setVisible(false);
        WebEngine webEngineLeft = webViewLeft.getEngine();
        String urlLeft = this.getClass().getResource("/static/html/leftHtml.html").toExternalForm();
        webEngineLeft.load(urlLeft);
        //分隔符
        separator.setDisable(true);
        separator.setVisible(false);
        //右边面板
        webViewRight.setDisable(true);
        webViewRight.setVisible(false);
        WebEngine webEngineRight = webViewRight.getEngine();
        String urlRight = this.getClass().getResource("/static/html/rightHtml.html").toExternalForm();
        webEngineRight.load(urlRight);


        //初始化小红点
        //增加小红点拖拽功能
        DragUtil.addDragListener(Main.stage, circle);
        //设置小红点右键菜单
        ContextMenu contextMenu = new ContextMenu();
        MenuItem close = new MenuItem("关闭");
        close.setOnAction(actionEvent -> Platform.exit());
        contextMenu.getItems().add(close);
        circle.setOnContextMenuRequested(
                event -> contextMenu.show(circle, event.getScreenX(), event.getScreenY())
        );
        //左键打开界面//这里我不想新打开界面，准备使webview可见并可用来实现（假装是个单例）
//        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent handler) {
//                if (handler.getButton()== MouseButton.PRIMARY/*&&handler.getEventType()==MouseEvent.MOUSE_RELEASED*/){
//                    if (webViewRight.isVisible() || webViewLeft.isVisible()) {
//                        webViewLeft.setVisible(false);
//                        webViewLeft.setDisable(true);
//                        webViewRight.setVisible(false);
//                        webViewRight.setDisable(true);
//                    } else if (!webViewRight.isVisible()) {
//                        webViewRight.setVisible(true);
//                        webViewRight.setDisable(false);
//                    }
//                }
//            }
//        });
//        小红点对齐用box
        circleHbox.setStyle("-fx-background-color: rgba(0,0,0,0.0);");
    }


    //开关webview
    public void showOrHideWebview(){
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














    @GetMapping("/openLeftWebview")
    public void openleftWebview(){
        webViewLeft.setVisible(true);
        webViewLeft.setDisable(false);
//        System.out.println("接口调用正常");
    }
}
