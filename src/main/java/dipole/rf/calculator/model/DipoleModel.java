package dipole.rf.calculator.model;

import dipole.rf.calculator.utils.RFUtils;
import dipole.rf.calculator.utils.Result;

/**
 * RF model of a half-wave dipole antenna.
 */
public class DipoleModel {
    /**
     * Antenna transmitted power (dbm)
     */
    private final String Ptxdbm;
    /**
     * Half-wave dipole length (m)
     */
    private final String l;
    /**
     * Half-wave dipole length adjusted to remove reactance (m)
     */
    private final String lAdjusted;
    /**
     * Half-wave dipole single element length (m)
     */
    private final String element;
    /**
     * Wavelength (m)
     */
    private final String lambda;
    /**
     * Antenna feed current (A)
     */
    private final String Ia;
    /**
     * Antenna feed voltage (V)
     */
    private final String Va;
    /**
     * Far-field region start
     */
    private final String farField;
    /**
     * Electric field on Z axis at a point r (V/m)
     */
    private final String Ez;
    /**
     * Voltage open circuit on a receiving antenna
     */
    private final String VOC;
    /**
     * Power on a receiving load (W)
     */
    private final String Pload;

    /**
     * Received power (dbm)
     */
    private final String Prxdbm;
    /**
     * Free space path attenuation (db)
     */
    private final String attenuation;

    /**
     * Initialize the model.
     *
     * @param freq_simple      source frequency (Ghz)
     * @param Ptx              antenna transmitted power
     * @param r                distance point from the transmit antenna
     * @param wallsAttenuation attenuation from the walls
     */
    public DipoleModel(double freq_simple, double Ptx, double r, double wallsAttenuation) {

        double freq = freq_simple * 1E9;
        Result Ptxdbm = RFUtils.wToDbm(Ptx);
        Result l = RFUtils.getDipoleSize(freq);
        Result lAdjusted = new Result(l.value() * 0.95);
        Result element = new Result(lAdjusted.value() / 2);
        double ptxAttenuated = Ptx * RFUtils.dbToRatio((-1) * wallsAttenuation);
        Result Ia = RFUtils.getAntennaFeedCurrent(Ptx);
        Result IaAttenuated = RFUtils.getAntennaFeedCurrent(ptxAttenuated);
        Result Va = RFUtils.getAntennaFeedVoltage(Ptx);
        Result lambda = RFUtils.getLambda(freq);
        Result farField = RFUtils.getFarField(lAdjusted.value(), lambda.value());
        Result k = RFUtils.getWaveNumber(lambda.value());
        Result Ez = RFUtils.computeEz(IaAttenuated.value(), k.value(), l.value(), r);
        Result VOC = RFUtils.computeVOC(IaAttenuated.value(), k.value(), l.value(), r);
        Result Pload = RFUtils.computePload(VOC.value());
        Result Prxdbm = RFUtils.wToDbm(Pload.value());
        Result attenuation = new Result(Math.abs(Prxdbm.value() - Ptxdbm.value()));

        this.Ptxdbm = Ptxdbm.toDecimal("dbm");
        this.l = l.toEngineering("m");
        this.lAdjusted = lAdjusted.toEngineering("m");
        this.element = element.toEngineering("m");
        this.Ia = Ia.toEngineering("A");
        this.Va = Va.toEngineering("V");
        this.Ez = Ez.toEngineering("V/m");
        this.VOC = VOC.toEngineering("V");
        this.Pload = Pload.toEngineering("W");
        this.Prxdbm = Prxdbm.toDecimal("dbm");
        this.attenuation = attenuation.toDecimal("db");
        this.farField = farField.toEngineering("m");
        this.lambda = lambda.toEngineering("m");

    }

    public String getPtxdbm() {
        return Ptxdbm;
    }

    public String getL() {
        return l;
    }

    public String getIa() {
        return Ia;
    }

    public String getVa() {
        return Va;
    }

    public String getEz() {
        return Ez;
    }

    public String getVOC() {
        return VOC;
    }

    public String getPload() {
        return Pload;
    }

    public String getPrxdbm() {
        return Prxdbm;
    }

    public String getAttenuation() {
        return attenuation;
    }

    public String getlAdjusted() {
        return lAdjusted;
    }

    public String getElement() {
        return element;
    }

    public String getFarField() {
        return farField;
    }

    public String getLambda() {
        return lambda;
    }
}
