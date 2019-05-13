import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane root = FXMLLoader.load(getClass().getResource("views/menu.fxml"));
        primaryStage.setTitle("eShop");
        primaryStage.setScene(new Scene(root,1150,700));
//
        Parent[] interfaces = new Parent[7];
        interfaces[0] = FXMLLoader.load(getClass().getResource("views/categories/vCategorie.fxml"));
        interfaces[1] = FXMLLoader.load(getClass().getResource("views/produits/vProduit.fxml"));
        interfaces[3] = FXMLLoader.load(getClass().getResource("views/gestionnaires/vGestionnaire.fxml"));
        interfaces[4] = FXMLLoader.load(getClass().getResource("views/fournisseurs/vFournisseur.fxml"));
        interfaces[2] = FXMLLoader.load(getClass().getResource("views/clients/vClient.fxml"));
        interfaces[5] = FXMLLoader.load(getClass().getResource("views/sms/vSMS.fxml"));
        interfaces[6] = FXMLLoader.load(getClass().getResource("views/whatsapp/vWhatsapp.fxml"));

        for(int i = 0; i < 7 ; i++){
            root.getTabs().get(i).setContent(interfaces[i]);
        }
//
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
