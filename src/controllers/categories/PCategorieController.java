package controllers.categories;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Categorie;
import models.Delta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PCategorieController implements Initializable {

    @FXML Button btnExit;
    Delta dragDelta = new Delta();
    @FXML Text id;
    @FXML Label nom;

    private Categorie mCategorie;
    private VCategorieController mContextController;

    @FXML public void handleBtnExitClick() {
        mContextController.refreshResults();
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnModifyClick() {
        showModificationDialog();
    }

    void showModificationDialog(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/categories/mCategorie.fxml"));
        Parent modification = null;
        try {
            modification = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Scene scene = new Scene(modification);
        scene.setFill(Color.TRANSPARENT);

        Stage modal = new Stage(StageStyle.TRANSPARENT);
        modal.setScene(scene);

        MCategorieController controller =
                    loader.<MCategorieController>getController();
            controller.initialize(mCategorie,this);
            modal.show();

            modification.setOnMousePressed(ev -> {
                dragDelta.setX(modal.getX() - ev.getScreenX());
                dragDelta.setY(modal.getY() - ev.getScreenY());
            });

            modification.setOnMouseDragged(ev -> {
                modal.setX(ev.getScreenX() + dragDelta.getX());
                modal.setY(ev.getScreenY() + dragDelta.getY());
            });
    }

    public void initialize(Categorie categorie, VCategorieController contextController){
        mCategorie = categorie;
        mContextController = contextController;
        refreshDisplay();
    }
    public void refreshDisplay(){
        id.setText(String.valueOf(mCategorie.getIdCa()));
        nom.setText(mCategorie.getNomCa());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
