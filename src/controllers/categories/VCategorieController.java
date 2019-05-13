package controllers.categories;

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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VCategorieController implements Initializable {
    private int lastIndex, displace;
    @FXML Pagination pagination;
    @FXML Button btnAdd;
    @FXML ToggleGroup groupFiltres;
    @FXML TextField searchBox;
    private final Delta dragDelta = new Delta();
    private ArrayList<Categorie> categories = null;
    final ObservableList<Categorie> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = Categorie.getAll();
        System.out.println(categories.size());
        btnAdd.setOnAction(event -> showAddCategorieDialog());
        refreshTable();
    }
    public void refreshTable(){
        data.clear();
        for(Categorie c: categories){
            data.add(c);
        }
        displace = categories.size() % rowsPerPage();

        if (displace > 0) {
            lastIndex = categories.size() / rowsPerPage();
        }
        else {
            lastIndex = categories.size() / rowsPerPage() - 1;
        }
        pagination.setPageCount(lastIndex + 1);
        pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public VBox createPage(int pageIndex) {
        final int COL_WIDTH = 300;

        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Categorie> table = new TableView<>();

            TableColumn col1 = new TableColumn("ID ");
            col1.setCellValueFactory(new PropertyValueFactory<Categorie, Integer>("idCa"));
            col1.setMaxWidth(COL_WIDTH);

            TableColumn col2 = new TableColumn("Nom ");
            col2.setCellValueFactory(new PropertyValueFactory<Categorie, String>("nomCa"));
            col2.setMaxWidth(COL_WIDTH);

            table.getColumns().addAll(col1, col2);
            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            }
            else if(pageIndex < lastIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }
            table.setPadding(new Insets(10));
            table.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showCategorieDetails(newValue));
            box.getChildren().add(table);
        }
        return box;
    }
    @FXML void refreshResults(){
        categories = Categorie.getAll();
        ToggleButton filtre = (ToggleButton) groupFiltres.getSelectedToggle();
        if(filtre != null){
            String pattern = searchBox.getText();
            if(filtre.getText().equals("Nom")){
                categories = Categorie.find(pattern);
            }else{
                if(!pattern.isEmpty())
                    categories = Categorie.find(pattern);
                else
                    categories = Categorie.getAll();
            }
        }
        refreshTable();
    }
    public void showCategorieDetails(Categorie categorie){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/categories/pCategorie.fxml"));
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

        PCategorieController controller =
                loader.<PCategorieController>getController();
        controller.initialize(categorie,  this);

        modal.show();

        presentation.setOnMousePressed(ev -> {
            dragDelta.setX(modal.getX() - ev.getScreenX());
            dragDelta.setY(modal.getY() - ev.getScreenY());
        });

        presentation.setOnMouseDragged(ev -> {
            modal.setX(ev.getScreenX() + dragDelta.getX());
            modal.setY(ev.getScreenY() + dragDelta.getY()); });
    }
    public void showAddCategorieDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        try {
            Parent presentation = FXMLLoader.load(getClass().getResource("../../views/categories/aCategorie.fxml"));
            presentation.setOnMouseExited(e->{
                refreshResults();
            } );
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
