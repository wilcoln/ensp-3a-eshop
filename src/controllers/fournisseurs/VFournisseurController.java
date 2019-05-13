package controllers.fournisseurs;

import javafx.scene.Parent;
import models.Delta;
import models.Fournisseur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VFournisseurController implements Initializable {

    @FXML Pagination pagination;
    @FXML Button btnAdd;
    final Delta dragDelta = new Delta();
    final ObservableList<Fournisseur> data = FXCollections.observableArrayList(
            new Fournisseur(1,"Mango","Akwa","12321545553","12345645","www.mango.cm"),
            new Fournisseur(2,"Nikki","Mokolo","1111111","111111","www.nikki.cm"),
            new Fournisseur(3,"Tsekenis","Pharmacie du Soleil","1000033","1000033","www.tsekenis.org")
            );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public VBox createPage(int pageIndex) {
        final int COL_WIDTH = 150;
        int lastIndex;
        int displace = data.size() % rowsPerPage();

        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        }
        else {
            lastIndex = data.size() / rowsPerPage() - 1;
        }

        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for (int i = page; i < page + itemsPerPage(); ++i) {
            TableView<Fournisseur> table = new TableView<>();

            TableColumn col1 = new TableColumn("ID ");
            col1.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("idFour"));
            col1.setMaxWidth(COL_WIDTH);

            TableColumn col2 = new TableColumn("Nom ");
            col2.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("nomFour"));
            col2.setMaxWidth(COL_WIDTH);

            TableColumn col3 = new TableColumn("Adresse ");
            col3.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("adresse"));
            col3.setMaxWidth(COL_WIDTH);

            TableColumn col4 = new TableColumn("Téléphone ");
            col4.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("telFour"));
            col4.setMaxWidth(COL_WIDTH);

            TableColumn col5 = new TableColumn("Whatsapp ");
            col5.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("whatsap"));
            col5.setMaxWidth(COL_WIDTH);

            TableColumn col6 = new TableColumn("Site Web ");
            col6.setCellValueFactory(new PropertyValueFactory<Fournisseur, String>("siteWeb"));
            col6.setMaxWidth(COL_WIDTH);

            table.getColumns().addAll(col1, col2, col3, col4, col5, col6);
            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            }
            else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }
            table.setPadding(new Insets(10));
            table.setOnMousePressed(e -> {
                Stage modal = new Stage(StageStyle.TRANSPARENT);
                try {
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/fournisseurs/pFournisseur.fxml"));
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
            });

            btnAdd.setOnAction(event -> {
                Stage modal = new Stage(StageStyle.TRANSPARENT);
                try {
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/fournisseurs/aFournisseur.fxml"));
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
            });

            box.getChildren().add(table);
        }
        return box;
    }
}
