package dipole.rf.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("dipole-view.fxml"));
        final String dipoleCss = getClass().getResource("css/dipole.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load(), 900, 640);
        scene.getStylesheets().add(dipoleCss);
        stage.setTitle("Dipole RF Calculator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main entry point.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}