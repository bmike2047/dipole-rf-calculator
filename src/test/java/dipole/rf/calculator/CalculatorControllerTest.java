package dipole.rf.calculator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class CalculatorControllerTest {

    @Start
    public void start(Stage stage) throws IOException {
        Pane pane = FXMLLoader.load(CalculatorApplication.class.getResource("dipole-view.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
        stage.toFront();
    }


    @Test
    void testCalculateAll(FxRobot robot) {
        Assertions.assertThat(robot.lookup("#freq").queryAs(TextField.class)).hasText("2.4");
        Assertions.assertThat(robot.lookup("#Ptx").queryAs(TextField.class)).hasText("0.1");
        Assertions.assertThat(robot.lookup("#r").queryAs(TextField.class)).hasText("10");

        Button button = robot.lookup("#calculate").queryAs(Button.class);
        robot.clickOn(button);

        Assertions.assertThat(robot.lookup("#Ia").queryAs(Label.class)).hasText("52mA");
        Assertions.assertThat(robot.lookup("#Ptxdbm").queryAs(Label.class)).hasText("20dbm");
        Assertions.assertThat(robot.lookup("#l").queryAs(Label.class)).hasText("62mm");
        Assertions.assertThat(robot.lookup("#Ez").queryAs(Label.class)).hasText("314mV/m");
        Assertions.assertThat(robot.lookup("#VOC").queryAs(Label.class)).hasText("12mV");
        Assertions.assertThat(robot.lookup("#Pload").queryAs(Label.class)).hasText("266nW");
        Assertions.assertThat(robot.lookup("#Prx").queryAs(Label.class)).hasText("266nW");
        Assertions.assertThat(robot.lookup("#Prxdbm").queryAs(Label.class)).hasText("-36dbm");
        Assertions.assertThat(robot.lookup("#attenuation").queryAs(Label.class)).hasText("56db");
        Assertions.assertThat(robot.lookup("#status").queryAs(Label.class)).hasText("Done");

    }

    @Test
    void testInvalidInput(FxRobot robot) {
        TextField freq = robot.lookup("#freq").queryAs(TextField.class);
        Button button = robot.lookup("#calculate").queryAs(Button.class);
        freq.setText("gigahertz");
        robot.clickOn(button);
        Assertions.assertThat(robot.lookup("#status").queryAs(Label.class)).hasText("Error - For input string: \"gigahertz\"");
    }
}
