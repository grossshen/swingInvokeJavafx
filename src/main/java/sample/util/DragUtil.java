package sample.util;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/3 12:54
 */
public class DragUtil {
    public static void addDragListener(Stage stage, Node node) {
        new DragListenerOnStage(stage).enableDrag(node);
    }
    public static class DragListenerOnStage implements EventHandler<MouseEvent> {

        private double xOffset = 0;
        private double yOffset = 0;
        private final Stage stage;

        public DragListenerOnStage(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handle(MouseEvent event) {
            event.consume();
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                stage.setX(event.getScreenX() - xOffset);
                if(event.getScreenY() - yOffset < 0) {
                    stage.setY(0);
                }else {
                    stage.setY(event.getScreenY() - yOffset);
                }
            }
        }

        public void enableDrag(Node node) {
            node.setOnMousePressed(this);
            node.setOnMouseDragged(this);
        }
    }
}
