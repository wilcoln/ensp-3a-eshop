package controllers.produits;

import controllers.produits.PProduitController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import models.Photo;
import models.Produit;
import models.Produit;
import models.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MProduitController implements Initializable {
    /**
     * Dans l'implémentation définitive, il s'agira pour nous de faire arriver le code du produit à cet interface afin de pouvoir le retrouver, car il sera question de modifier directement le dit produit
     * et slider entre ses différentes photos.
     */
    @FXML private Pane photo1;
    @FXML private Button btnExit;
    @FXML ImageView btnNext;
    @FXML ImageView btnPrev;
    @FXML private ImageView imgProduit;
    ArrayList<String> imgUrls = new ArrayList<>();
    @FXML Text slideStatus;
    int currentImgIndex = 1;

    private Produit mProduit;
    private PProduitController mContextController;

    @FXML TextField nom;
    @FXML ComboBox fournisseur;
    @FXML TextField quantite;
    @FXML TextField prixVente;
    @FXML TextField prixAchat;
    @FXML ComboBox categorie;
    @FXML DatePicker dateCreation;
    @FXML DatePicker dateDerArr;
    @FXML TextField taille;
    @FXML ToggleGroup etat;
    @FXML Label code;
    @FXML Button btnValider;

    // ceci est une variable  "@link imgUrls" de deboggage, on aura plus besoin d'elle dans la version définitive, vu qu'on naviguera directement dans l'array liste de photo
    // produit passé ( oar son code ) en paramètre.

    @FXML public void handlePaneHover1() {
        if(imgUrls.size()>0){
            photo1.getChildren().get(1).setVisible(true);
            photo1.getChildren().get(2).setVisible(true);
            photo1.getChildren().get(3).setVisible(true);
        }

    }

    @FXML public void handlePaneOut1() {
        photo1.getChildren().get(1).setVisible(false);
        photo1.getChildren().get(2).setVisible(false);
        photo1.getChildren().get(3).setVisible(false);
    }

    @FXML private void displayPrevious() {
        if (currentImgIndex > 1) {
            currentImgIndex--;
            imgProduit.setImage(new Image(String.valueOf(new File(imgUrls.get(currentImgIndex - 1)))));
            slideStatus.setText(currentImgIndex + " sur " + String.valueOf(imgUrls.size()));
        }
    }

    @FXML private void displayNext() {
        if (currentImgIndex < imgUrls.size()) {
            currentImgIndex++;
            imgProduit.setImage(new Image(String.valueOf(new File(imgUrls.get(currentImgIndex - 1)))));
            System.out.println(String.valueOf(new File(imgUrls.get(currentImgIndex - 1))));
            slideStatus.setText(currentImgIndex + " sur " + String.valueOf(imgUrls.size()));
        }
    }
    @FXML private void displayCurrent(int currentImgIndex) {
            imgProduit.setImage(new Image(String.valueOf(new File(imgUrls.get(currentImgIndex - 1)))));
        System.out.println(String.valueOf(new File(imgUrls.get(currentImgIndex - 1))));
            slideStatus.setText(currentImgIndex + " sur " + String.valueOf(imgUrls.size()));
    }

    @FXML public void handleBtnExitClick(){
        ((Stage)(btnExit.getScene().getWindow())).close();
    }
    @FXML public void handleBtnValiderClick() throws SQLException {
        mProduit.setNomPro(nom.getText());
        mProduit.setPrixAchat(Float.parseFloat(prixAchat.getText()));
        mProduit.setPrixVente(Float.parseFloat(prixVente.getText()));
        mProduit.setTaille(taille.getText());
        mProduit.update();
        mContextController.refreshDisplay();
       handleBtnExitClick();
    }

    @FXML public void ajouter() {
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new ExtensionFilter("Images","*.png","*.jpg","*.jpeg"));
        File image = choose.showOpenDialog(new Stage());
        try {
            imgUrls.add("file:"+image.getPath());
            System.out.println(image.getPath());
            currentImgIndex = imgUrls.size();
            displayCurrent(currentImgIndex);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void modification() {
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new ExtensionFilter("Images","*.png","*.jpg","*.jpeg"));
        File image = choose.showOpenDialog(new Stage());
        try {
            imgProduit.setImage(new Image(new FileInputStream(image.getPath())));
           imgUrls.set(currentImgIndex - 1, "file:" + image.getPath());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML  void delCurrentImage(){
        if(imgUrls.size()>0){
            imgUrls.remove(currentImgIndex - 1);
            if(imgUrls.size()>0){
                if(currentImgIndex>1)
                    currentImgIndex--;
                displayCurrent(currentImgIndex);
            }else {
                imgProduit.setImage(null);
                slideStatus.setText("pas d'images");
            }
        }
    }
    public void initialize(Produit produit, PProduitController contextController){
        mProduit = produit;
        mContextController = contextController;
        ArrayList<Photo> photos = mProduit.getAllPhoto();
        for(Photo p: photos)
            imgUrls.add(p.getLienPhoto());

        code.setText(String.valueOf(mProduit.getIdPro()));
        nom.setText(mProduit.getNomPro());
        categorie.setPromptText(mProduit.getSaCategorie().getNomCa());
        fournisseur.setPromptText(mProduit.getSonFournisseur().getNomFour());
        quantite.setText(String.valueOf(mProduit.getQuantite()));
        prixVente.setText(Utilities.formatPrix(mProduit.getPrixVente()));
        prixAchat.setText(Utilities.formatPrix(mProduit.getPrixAchat()));
//        dateCreation.setPromptText(mProduit.getDateCreation());
//        dateDerArr.setPromptText(mProduit.getDateDernierArr());
        taille.setText(String.valueOf(mProduit.getTaille()));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // temporaire
    }
}

