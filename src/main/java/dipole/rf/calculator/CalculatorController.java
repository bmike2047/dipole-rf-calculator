package dipole.rf.calculator;

import dipole.rf.calculator.model.DipoleModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorController {
    public static final Logger LOG = Logger.getLogger("dipole.rf.calculator");
    @FXML
    private Label Ez;
    @FXML
    private Label VOC;
    @FXML
    private Label Pload;
    @FXML
    private Label Prx;
    @FXML
    private Label Prxdbm;
    @FXML
    private Label attenuation;
    @FXML
    private Label Ia;
    @FXML
    private Label Ptxdbm;
    @FXML
    private Label l;
    @FXML
    private Label status;
    @FXML
    private TextField freq;
    @FXML
    private TextField Ptx;
    @FXML
    private TextField r;

    /**
     * Setup components before use.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Calculate button logic
     */
    @FXML
    public void onCalculateButtonClick() {
        try {
            double freq = Double.parseDouble(this.freq.getText().trim());
            double Ptx = Double.parseDouble(this.Ptx.getText().trim());
            double r = Double.parseDouble(this.r.getText().trim());

            DipoleModel dipoleModel = new DipoleModel(freq, Ptx, r);

            this.Ptxdbm.setText(dipoleModel.getPtxdbm());
            this.l.setText(dipoleModel.getL());
            this.Ia.setText(dipoleModel.getIa());
            this.Ez.setText(dipoleModel.getEz());
            this.VOC.setText(dipoleModel.getVOC());
            this.Pload.setText(dipoleModel.getPload());
            this.Prx.setText(dipoleModel.getPrx());
            this.Prxdbm.setText(dipoleModel.getPrxdbm());
            this.attenuation.setText(dipoleModel.getAttenuation());

            success();
        } catch (Exception e) {
            error(e);
        }

    }


    /**
     * Helper method to handle success.
     */
    private void success() {
        status.setText("Done");
        status.setTextFill(Color.web("#05730a"));
    }

    /**
     * Helper method to handle errors.
     *
     * @param ex Exception
     */
    private void error(final Exception ex) {
        status.setText("Error - " + ex.getMessage());
        status.setTextFill(Color.web("#fa1105"));
        LOG.log(Level.SEVERE, ex.toString());
    }

}