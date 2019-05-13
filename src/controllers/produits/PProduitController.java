package controllers.produits;

import controllers.categories.MCategorieController;
import controllers.produits.VProduitController;
import controllers.produits.VProduitController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import models.Produit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PProduitController implements Initializable {

    @FXML Button btnExit;
    @FXML ImageView btnUp;
    @FXML ImageView btnDown;
    @FXML ImageView imgProduit;
    @FXML Text slideStatus;

    @FXML Label nom;
    @FXML Label fournisseur;
    @FXML Label quantite;
    @FXML Label prixVente;
    @FXML Label prixAchat;
    @FXML Label categorie;
    @FXML Label dateCreation;
    @FXML Label dateModification;
    @FXML Label taille;
    @FXML Label etat;
    @FXML Text code;
    int currentImgIndex = 1;
    Delta dragDelta = new Delta();

    private Produit mProduit;
    private VProduitController mContextController;

    @FXML public void handleBtnExitClick(){
        ((Stage)(btnExit.getScene().getWindow())).close();
        mContextController.refreshResults();
    }

    public void handleBtnModifierClick() {
        showModificationDialog();
    }
    void showModificationDialog(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/produits/mProduit.fxml"));
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

        MProduitController controller =
                loader.<MProduitController>getController();
        controller.initialize(mProduit,this);
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
    @FXML private void displayPrevious() {
        if (currentImgIndex > 1) {
            currentImgIndex--;
            String stri = String.valueOf(currentImgIndex);
            imgProduit.setImage(new Image(String.valueOf(new File(mProduit.getAllPhoto().get(currentImgIndex).getLienPhoto()))));
            slideStatus.setText(stri + " sur " + String.valueOf(mProduit.getAllPhoto().size()));
        }
    }

    @FXML private void displayNext() {
        if (currentImgIndex < mProduit.getAllPhoto().size()) {
            currentImgIndex++;
            String stri = String.valueOf(currentImgIndex);
            imgProduit.setImage(new Image(String.valueOf(new File(mProduit.getAllPhoto().get(currentImgIndex).getLienPhoto()))));
            slideStatus.setText(stri + " sur " + String.valueOf(mProduit.getAllPhoto().size()));
        }
    }
    public void initialize(Produit produit, VProduitController contextController){
        mProduit = produit;
        mContextController = contextController;
        refreshDisplay();
    }
    public void refreshDisplay(){
        slideStatus.setText(String.valueOf(currentImgIndex) + " sur " + String.valueOf(mProduit.getAllPhoto().size()));
//        imgProduit.setImage(new Image(String.valueOf(new File(mProduit.getAllPhoto().get(currentImgIndex).getLienPhoto()))));

        code.setText(String.valueOf(mProduit.getIdPro()));
        nom.setText(mProduit.getNomPro());
        categorie.setText(mProduit.getSaCategorie().getNomCa());
        fournisseur.setText(mProduit.getSonFournisseur().getNomFour());
        quantite.setText(String.valueOf(mProduit.getQuantite()));
        prixVente.setText(Utilities.formatPrix(mProduit.getPrixVente()));
        prixAchat.setText(Utilities.formatPrix(mProduit.getPrixAchat()));
        dateCreation.setText(mProduit.getDateCreation());
        dateModification.setText(mProduit.getDateDernierArr());
        taille.setText(String.valueOf(mProduit.getTaille()));
        String sEtat = mProduit.getEtat()? "Présent": "Supprimé";
        etat.setText(sEtat);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}