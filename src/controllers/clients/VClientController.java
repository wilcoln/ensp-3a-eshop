package controllers.clients;

import javafx.scene.Parent;
import models.Client;
import models.Delta;
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

public class VClientController implements Initializable {
    @FXML Pagination pagination;
    @FXML Button btnAdd;
    final Delta dragDelta = new Delta();
    final ObservableList<Client> data = FXCollections.observableArrayList(
            new Client("Anne Marie Chana"),
            new Client("Toto Jean parpaign"),
            new Client("Doyou Nana rogerson"),
            new Client("Larissa Nkoa"),
            new Client("Feukou Berthold"),
            new Client("Philippe Ekwabi"),
            new Client("Mabath Merveille"),
            new Client("Manuela Uria"),
            new Client("Tchappo Jean roger"),
            new Client("Wilfried Lincoln"),
            new Client("Jean Colbin"),
            new Client("Antangana Jean")
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        final int COL_WIDTH = 150;
        int lastIndex;
        int displace = data.size() % rowsPerPage();

        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        } else {
            lastIndex = data.size() / rowsPerPage() - 1;
        }

        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Client> table = new TableView<>();

            TableColumn col1 = new TableColumn("Code ");
            col1.setCellValueFactory(new PropertyValueFactory<Client, Integer>("codeClient"));
            col1.setMaxWidth(COL_WIDTH);

            TableColumn col2 = new TableColumn("Nom ");
            col2.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));
            col2.setMaxWidth(COL_WIDTH);

            TableColumn col3 = new TableColumn("Téléphone ");
            col3.setCellValueFactory(new PropertyValueFactory<Client,String>("telephone"));
            col3.setMaxWidth(COL_WIDTH);

            TableColumn col4 = new TableColumn("Whatsapp ");
            col4.setCellValueFactory(new PropertyValueFactory<Client,String>("whatsap"));
            col4.setMaxWidth(COL_WIDTH);

            TableColumn col5 = new TableColumn("Abonnement ");
            col5.setCellValueFactory(new PropertyValueFactory<Client, String>("dateAbonnement"));
            col5.setMaxWidth(COL_WIDTH);

            TableColumn col6 = new TableColumn("Point de Fidelité ");
            col6.setCellValueFactory(new PropertyValueFactory<Client, String>("pointFidelite"));
            col6.setMaxWidth(COL_WIDTH);

            TableColumn col7 = new TableColumn("Type ");
            col7.setCellValueFactory(new PropertyValueFactory<Client, String>("typeCli"));
            col7.setMaxWidth(COL_WIDTH);

            TableColumn col8 = new TableColumn("Email ");
            col8.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
            col8.setMaxWidth(COL_WIDTH);

            table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8);
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
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/clients/pClient.fxml"));
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
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/clients/aClient.fxml"));
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
