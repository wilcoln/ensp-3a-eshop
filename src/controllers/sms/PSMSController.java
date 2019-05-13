package controllers.sms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Delta;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PSMSController implements Initializable {

    @FXML private Button btnExit;

    @FXML public void handleBtnExitClick(){
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
