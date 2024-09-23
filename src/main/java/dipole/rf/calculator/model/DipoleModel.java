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
     * Antenna feed current (A)
     */
    private final String Ia;
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
     * Received power (W)
     */
    private final String Prx;
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
     * @param freq_simple source frequency (Ghz)
     * @param Ptx         antenna transmitted power
     * @param r           distance point from the transmit antenna
     */
    public DipoleModel(double freq_simple, double Ptx, double r) {

        double freq = freq_simple * 1E9;
        Result Ptxdbm = RFUtils.wToDbm(Ptx);
        Result l = RFUtils.getDipoleSize(freq);
        Result Ia = RFUtils.getCurrent(Ptx);
        Result lambda = RFUtils.getLambda(freq);
        Result k = RFUtils.getWaveNumber(lambda.value());
        Result Ez = RFUtils.computeEz(Ia.value(), k.value(), l.value(), r);
        Result VOC = RFUtils.computeVOC(Ia.value(), k.value(), l.value(), r);
        Result Pload = RFUtils.computePload(VOC.value());
        Result Prx = RFUtils.computePrx(Ptx, lambda.value(), r);
        Result Prxdbm = RFUtils.wToDbm(Prx.value());
        Result attenuation = new Result(Math.abs(Prxdbm.value() - Ptxdbm.value()));

        this.Ptxdbm = Ptxdbm.toDecimal("dbm");
        this.l = l.toEngineering("m");
        this.Ia = Ia.toEngineering("A");
        this.Ez = Ez.toEngineering("V/m");
        this.VOC = VOC.toEngineering("V");
        this.Pload = Pload.toEngineering("W");
        this.Prx = Prx.toEngineering("W");
        this.Prxdbm = Prxdbm.toDecimal("dbm");
        this.attenuation = attenuation.toDecimal("db");

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

    public String getEz() {
        return Ez;
    }

    public String getVOC() {
        return VOC;
    }

    public String getPload() {
        return Pload;
    }

    public String getPrx() {
        return Prx;
    }

    public String getPrxdbm() {
        return Prxdbm;
    }

    public String getAttenuation() {
        return attenuation;
    }
}
