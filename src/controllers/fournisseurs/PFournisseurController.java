package controllers.fournisseurs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Delta;
import java.io.IOException;

public class PFournisseurController {

    @FXML private Button btnExit;
    private Delta dragDelta = new Delta();

    @FXML public void handleBtnExitClick() {
        ((Stage) btnExit.getScene().getWindow()).close();
    }

    @FXML public void handleBtnModifyClick() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        try {
            Parent modification = FXMLLoader.load(getClass().getResource("../../views/fournisseurs/mFournisseur.fxml"));
            Scene scene = new Scene(modification);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            modification.setOnMousePressed(e -> {
                dragDelta.setX(modal.getX() - e.getScreenX());
                dragDelta.setY(modal.getY() - e.getScreenY());
            });

            modification.setOnMouseDragged(e -> {
                modal.setX(e.getScreenX() + dragDelta.getX());
                modal.setY(e.getScreenY() + dragDelta.getY());
            });

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
