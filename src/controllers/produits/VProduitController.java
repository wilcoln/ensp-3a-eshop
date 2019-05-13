package controllers.produits;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Categorie;
import models.Delta;
import models.Produit;
import models.Produit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VProduitController implements Initializable {

    @FXML Pagination pagination;
    @FXML Button btnAdd;
    @FXML TextField searchBox;
    @FXML ToggleGroup groupFiltres;
    final Delta dragDelta = new Delta();
    final ObservableList<Produit> data = FXCollections.observableArrayList();
    private ArrayList<Produit> produits = null;


    private int lastIndex, displace;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produits = Produit.all();
        System.out.println(produits.size());
        btnAdd.setOnAction(event -> showAddProduitDialog());
        refreshTable();
    }

    public void refreshTable() {
        data.clear();
        for (Produit p : produits) {
            data.add(p);
        }
        displace = produits.size() % rowsPerPage();

        if (displace > 0) {
            lastIndex = produits.size() / rowsPerPage();
        }
        else {
            lastIndex = produits.size() / rowsPerPage() - 1;
        }
        pagination.setPageCount(lastIndex + 1);
        pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }
    /**
     * Setting up the table view
     * @return
     */
    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public VBox createPage(int pageIndex) {
        final int COL_WIDTH = 180;

        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Produit> table = new TableView<>();

            TableColumn col1 = new TableColumn("Code ");
            col1.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("codePro"));
            col1.setMaxWidth(COL_WIDTH);

            TableColumn col2 = new TableColumn("Nom ");
            col2.setCellValueFactory(new PropertyValueFactory<Produit, String>("nomPro"));
            col2.setMaxWidth(COL_WIDTH);

            TableColumn col3 = new TableColumn("Code Fournisseur ");
            col3.setCellValueFactory(new PropertyValueFactory<Produit, String>("codeSonFournisseur"));
            col3.setMaxWidth(COL_WIDTH);

            TableColumn col4 = new TableColumn("Quantité");
            col4.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
            col4.setMaxWidth(COL_WIDTH);

            TableColumn col5 = new TableColumn("Prix");
            col5.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prixVente"));
            col5.setMaxWidth(COL_WIDTH);

            TableColumn col6 = new TableColumn("Date de création ");
            col6.setCellValueFactory(new PropertyValueFactory<Produit, String>("dateCreation"));
            col6.setMaxWidth(COL_WIDTH);

            TableColumn col7 = new TableColumn("Dernier arrivage ");
            col7.setCellValueFactory(new PropertyValueFactory<Produit, String>("dateDernierArr"));
            col7.setMaxWidth(COL_WIDTH);

            TableColumn col8 = new TableColumn("Catégorie ");
            col8.setCellValueFactory(new PropertyValueFactory<Produit, String>("nomSaProduit"));
            col8.setMaxWidth(COL_WIDTH);

            TableColumn col9 = new TableColumn("Taille ");
            col9.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("taille"));
            col9.setMaxWidth(COL_WIDTH);

            table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            }
            else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }
            table.setPadding(new Insets(10));
            table.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showProduitDetails(newValue));

            box.getChildren().add(table);
        }
        return box;
    }
   
    @FXML
    void refreshResults() {
        produits = Produit.all();
        ToggleButton filtre = (ToggleButton) groupFiltres.getSelectedToggle();
        if (filtre != null) {
            String pattern = searchBox.getText();
            if (filtre.getText().equals("Nom")) {
                produits = Produit.find(pattern);
            } else {
                if (!pattern.isEmpty())
                    produits = Produit.find(pattern);
                else
                    produits = Produit.all();
            }
        }
        refreshTable();
    }
    
    public void showProduitDetails(Produit produit) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/produits/pProduit.fxml"));
        Parent presentation = null;
        try {
            presentation = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Scene scene = new Scene(presentation);
        scene.setFill(Color.TRANSPARENT);

        Stage modal = new Stage(StageStyle.TRANSPARENT);
        modal.setScene(scene);

        PProduitController controller = loader.<PProduitController>getController();
        controller.initialize(produit, this);

        modal.show();

        presentation.setOnMousePressed(ev -> {
            dragDelta.setX(modal.getX() - ev.getScreenX());
            dragDelta.setY(modal.getY() - ev.getScreenY());
        });

        presentation.setOnMouseDragged(ev -> {
            modal.setX(ev.getScreenX() + dragDelta.getX());
            modal.setY(ev.getScreenY() + dragDelta.getY());
        });
    }
    
    public void showAddProduitDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        try {
            Parent presentation = FXMLLoader.load(getClass().getResource("../../views/produits/aProduit.fxml"));
            presentation.setOnMouseExited(e -> refreshResults());
            Scene scene = new Scene(presentation);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            presentation.setOnMousePressed(ev -> {
                dragDelta.setX(modal.getX() - ev.getScreenX());
                dragDelta.setY(modal.getY() - ev.getScreenY());
            });

            presentation.setOnMouseDragged(ev -> {
                modal.setX(ev.getScreenX() + dragDelta.getX());
                modal.setY(ev.getScreenY() + dragDelta.getY());
            });

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
