/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.categories;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Categorie;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author hp
 */
public class MCategorieController implements Initializable {

    @FXML private Button btnExit;
    @FXML private TextField nameId;
    @FXML private Label id;
    private Categorie mCategorie;
    private PCategorieController mContextController;

    @FXML public void handleBtnExitClick(){
        mCategorie.setNomCa(nameId.getText());
        mCategorie.update();
        mContextController.refreshDisplay();
        ((Stage)(btnExit.getScene().getWindow())).close();

    }

    public void initialize(Categorie categorie, PCategorieController contextController){
        mCategorie = categorie;
        mContextController = contextController;
        nameId.setText(mCategorie.getNomCa());
        id.setText(String.valueOf(categorie.getIdCa()));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
