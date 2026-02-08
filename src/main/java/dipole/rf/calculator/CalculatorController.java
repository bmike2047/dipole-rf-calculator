package dipole.rf.calculator;

import dipole.rf.calculator.model.DipoleModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main MVC controller.
 */
public class CalculatorController {
    public static final Logger LOG = Logger.getLogger("dipole.rf.calculator");
    /**
     * Electric field on Z axis at a point r (V/m)
     */
    @FXML
    private Label Ez;
    /**
     * Voltage open circuit on a receiving antenna (V)
     */
    @FXML
    private Label VOC;
    /**
     * Power on a receiving load (W)
     */
    @FXML
    private Label Pload;
    /**
     * Received power (dbm)
     */
    @FXML
    private Label Prxdbm;
    /**
     * Free space path attenuation (db)
     */
    @FXML
    private Label attenuation;
    /**
     * Antenna feed current (A)
     */
    @FXML
    private Label Ia;
    /**
     * Antenna feed voltage (V)
     */
    @FXML
    private Label Va;
    /**
     * Antenna transmitted power (dbm)
     */
    @FXML
    private Label Ptxdbm;
    /**
     * Half-wave dipole length adjusted to remove reactance(m)
     */
    @FXML
    private Label lAdjusted;
    /**
     * Far-field region start (m)
     */
    @FXML
    private Label farField;
    /**
     * Wavelength (m)
     */
    @FXML
    private Label lambda;
    /**
     * Half-wave dipole single element length (m)
     */
    @FXML
    private Label element;
    /**
     * Calculations status
     */
    @FXML
    private Label status;
    /**
     * Source frequency (Hz)
     */
    @FXML
    private TextField freq;
    /**
     * Antenna transmitted power (W)
     */
    @FXML
    private TextField Ptx;
    /**
     * Distance point from the transmit antenna (m)
     */
    @FXML
    private TextField r;
    /**
     * Attenuation from the walls (m)
     */
    @FXML
    private TextField wallsAttenuation;

    /**
     * Initialize components before use.
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
            double wallsAttenuation = Double.parseDouble(this.wallsAttenuation.getText().trim());

            DipoleModel dipoleModel = new DipoleModel(freq, Ptx, r, wallsAttenuation);

            this.Ptxdbm.setText(dipoleModel.getPtxdbm());
            this.lAdjusted.setText(dipoleModel.getlAdjusted());
            this.element.setText(dipoleModel.getElement());
            this.Ia.setText(dipoleModel.getIa());
            this.Va.setText(dipoleModel.getVa());
            this.Ez.setText(dipoleModel.getEz());
            this.VOC.setText(dipoleModel.getVOC());
            this.Pload.setText(dipoleModel.getPload());
            this.Prxdbm.setText(dipoleModel.getPrxdbm());
            this.attenuation.setText(dipoleModel.getAttenuation());
            this.farField.setText(dipoleModel .getFarField());
            this.lambda.setText(dipoleModel.getLambda());

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