module dipole.rf.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.apache.commons.numbers.complex;


    opens dipole.rf.calculator to javafx.fxml;
    exports dipole.rf.calculator;
    exports dipole.rf.calculator.utils;
    opens dipole.rf.calculator.utils to javafx.fxml;
}