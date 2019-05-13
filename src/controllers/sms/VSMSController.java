package controllers.sms;

import javafx.scene.Parent;
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

public class VSMSController implements Initializable {

    @FXML Pagination pagination;
    @FXML Button btnAdd;
    final Delta dragDelta = new Delta();
    final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("1", "Joe", "Pesci"),
            new Person("2", "Audrey", "Hepburn"),
            new Person("3", "Gregory", "Peck"),
            new Person("4", "Cary", "Grant"),
            new Person("5", "De", "Niro"),
            new Person("6", "Katharine", "Hepburn"),
            new Person("7", "Jack", "Nicholson"),
            new Person("8", "Morgan", "Freeman"),
            new Person("9", "Elizabeth", "Taylor"),
            new Person("10", "Marcello", "Mastroianni"),
            new Person("11", "Innokenty", "Smoktunovsky"),
            new Person("12", "Sophia", "Loren"),
            new Person("13", "Alexander", "Kalyagin"),
            new Person("14", "Peter", "OToole"),
            new Person("15", "Gene", "Wilder"),
            new Person("16", "Evgeny", "Evstegneev"),
            new Person("17", "Michael", "Caine"),
            new Person("18", "Jean-Paul", "Belmondo"),
            new Person("19", " Julia", "Roberts"),
            new Person("20", "James", "Stewart"),
            new Person("21", "Sandra", "Bullock"),
            new Person("22", "Paul", "Newman"),
            new Person("23", "Oleg", "Tabakov"),
            new Person("24", "Mary", "Steenburgen"),
            new Person("25", "Jackie", "Chan"),
            new Person("26", "Rodney", "Dangerfield"),
            new Person("27", "Betty", "White"),
            new Person("28", "Eddie", "Murphy"),
            new Person("29", "Amitabh", "Bachchan"),
            new Person("30", "Nicole", "Kidman"),
            new Person("31", "Adriano", "Celentano"),
            new Person("32", "Rhonda", " Fleming's"),
            new Person("32", "Humphrey", "Bogart"));

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
            TableView<Person> table = new TableView<>();

            TableColumn col1 = new TableColumn("ID ");
            col1.setCellValueFactory(new PropertyValueFactory<Person, String>("num"));
            col1.setMaxWidth(COL_WIDTH);

            TableColumn col2 = new TableColumn("Message ");
            col2.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
            col2.setMaxWidth(COL_WIDTH);

            TableColumn col3 = new TableColumn("Code Client ");
            col3.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
            col3.setMaxWidth(COL_WIDTH);

            TableColumn col4 = new TableColumn("Date ");
            col4.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
            col4.setMaxWidth(COL_WIDTH);

            TableColumn col5 = new TableColumn("Téléphone Client ");
            col5.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
            col5.setMaxWidth(COL_WIDTH);

            TableColumn col6 = new TableColumn("Type ");
            col6.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
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
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/sms/pSMS.fxml"));
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
                    Parent presentation = FXMLLoader.load(getClass().getResource("../../views/sms/aSMS.fxml"));
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
