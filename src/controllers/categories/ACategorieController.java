/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.categories;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Categorie;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author wilcoln
 */
public class ACategorieController implements Initializable {

    @FXML private Button btnExit;
    @FXML private TextField nameId;

    @FXML public void handleBtnExitClick(){
        ((Stage)(btnExit.getScene().getWindow())).close();
    }
    @FXML public void handleBtnValiderClick() {
        String nomCat = nameId.getText();
     if(!nomCat.isEmpty()){
         Categorie newCat = new Categorie(nomCat);
         newCat.save();
     }
        handleBtnExitClick();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
