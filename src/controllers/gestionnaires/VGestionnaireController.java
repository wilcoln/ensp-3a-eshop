package controllers.gestionnaires;

import javafx.scene.Parent;
import models.Delta;
import models.Gestionnaire;
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

public class VGestionnaireController implements Initializable {

    @FXML Pagination pagination;
    @FXML Button btnAdd;
    final Delta dragDelta = new Delta();
    final ObservableList<Gestionnaire> data = FXCollections.observableArrayList(
           new Gestionnaire(1,"Kotto Sylvie","69955442","Logpom","Sylvie Kot","SK0000",1,"111122233","0"),
            new Gestionnaire(2,"Eru Samuel","68899445","Bp-cité","Sami","ERS1123",2,"222244433","1"));

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
            TableView<Gestionnaire> table = new TableView<>();

            TableColumn col1 = new TableColumn("ID ");
            col1.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("idGest"));
            col1.setMaxWidth(COL_WIDTH);

            TableColumn col2 = new TableColumn("Nom ");
            col2.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("nomGest"));
            col2.setMaxWidth(COL_WIDTH);

            TableColumn col3 = new TableColumn("Date de naissance ");
            col3.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("lastName"));
            col3.setMaxWidth(COL_WIDTH);

            TableColumn col4 = new TableColumn("Mobile ");
            col4.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("telephone"));
            col4.setMaxWidth(COL_WIDTH);

            TableColumn col5 = new TableColumn("Adresse ");
            col5.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("adresse"));
            col5.setMaxWidth(COL_WIDTH);

            TableColumn col6 = new TableColumn("Username ");
            col6.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("username"));
            col6.setMaxWidth(COL_WIDTH);

            TableColumn col7 = new TableColumn("Type ");
            col7.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("type"));
            col7.setMaxWidth(COL_WIDTH);

            TableColumn col8 = new TableColumn("CNI ");
            col8.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("cni"));
            col8.setMaxWidth(COL_WIDTH);

            table.getColumns().addAll(col1, col2, /* col4,*/ col5, col6, col7, col8);
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
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/gestionnaires/pGestionnaire.fxml"));
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
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/gestionnaires/aGestionnaire.fxml"));
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
