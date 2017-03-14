package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String resourcePath = "/App.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(location);
        Scene scene = new Scene(loader.load(), 600, 400);

        //Set Icons, 16x16 for titlebar, 32x32 for taskbar.
        List<Image> icons = new ArrayList<Image>();
        icons.add(new Image(Main.class.getResourceAsStream("/FleetScraper16x16.png")));
        icons.add(new Image(Main.class.getResourceAsStream("/FleetScraper32x32.png")));
        primaryStage.getIcons().setAll(icons);


        primaryStage.setTitle("Fleet Usage Count");
        primaryStage.setScene(scene);


        primaryStage.show();
    }
}
