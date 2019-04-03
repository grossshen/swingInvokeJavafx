package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.util.DragUtil;

import java.net.URL;
import java.util.ResourceBundle;


@RestController
public class Controller implements Initializable{
    @FXML
    Circle circle;

    @FXML
    WebView webViewLeft;
    @FXML
    WebView webViewRight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //左边面板
        webViewLeft.setDisable(true);
        webViewLeft.setVisible(false);
        WebEngine webEngine = webViewLeft.getEngine();
        String url = this.getClass().getResource("/static/html/leftHtml.html").toExternalForm();
        webEngine.load(url);
        //分界线
        Line line=new Line();
        line.setFill(Color.gray(0.0941, 0.949));
        line.setStrokeWidth(2);
        //右边面板
        WebView webView1=new WebView();
        webView1.setDisable(true);
        webView1.setVisible(false);
        WebEngine webEngine1 = webView1.getEngine();
        String url1 = this.getClass().getResource("/static/html/rightHtml.html").toExternalForm();
        webEngine1.load(url1);


        //增加小红点拖拽功能
        DragUtil.addDragListener(Main.stage, circle);
        //设置右键菜单
        ContextMenu contextMenu = new ContextMenu();
        MenuItem close = new MenuItem("关闭");
        close.setOnAction(actionEvent -> Platform.exit());
        contextMenu.getItems().addAll(close);
        circle.setOnContextMenuRequested(
                event -> contextMenu.show(vBox, event.getScreenX(), event.getScreenY())
        );
        //左键打开界面//这里我不想新打开界面，准备使webview可见并可用来实现（假装是个单例）
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent handler) {
                if (handler.getButton()== MouseButton.PRIMARY/*&&handler.getEventType()==MouseEvent.MOUSE_RELEASED*/){
                    if (webView1.isVisible() || webViewLeft.isVisible()) {
                        webViewLeft.setVisible(false);
                        webViewLeft.setDisable(true);
                        webView1.setVisible(false);
                        webView1.setDisable(true);
                    } else if (!webView1.isVisible()) {
                        webView1.setVisible(true);
                        webView1.setDisable(false);
                    }
                }
            }
        });
//        小红点对齐用box
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.BOTTOM_CENTER);
        Button button=new Button("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        button.setVisible(false);
        hBox1.getChildren().addAll(button,circle);


        vBox.getChildren().addAll(hBox,hBox1);
    }

    @GetMapping("/hello")
    public void hello(){
        System.out.println("hello");
    }
}
