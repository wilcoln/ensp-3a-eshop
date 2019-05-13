package controllers.clients;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Delta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PClientController implements Initializable {

    @FXML Button btnExit;
    Delta dragDelta = new Delta();

    @FXML public void handleBtnExitClick(){
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnModifyClick() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        try {
            Parent modification = FXMLLoader.load(getClass().getResource("../../views/clients/mClient.fxml"));
            Scene scene = new Scene(modification);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.show();

            modification.setOnMousePressed(ev -> {
                dragDelta.setX(modal.getX() - ev.getScreenX());
                dragDelta.setY(modal.getY() - ev.getScreenY());
            });

            modification.setOnMouseDragged(ev -> {
                modal.setX(ev.getScreenX() + dragDelta.getX());
                modal.setY(ev.getScreenY() + dragDelta.getY());
            });
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
