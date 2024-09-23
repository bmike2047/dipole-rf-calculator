package dipole.rf.calculator.utils;


import org.apache.commons.numbers.complex.Complex;

/**
 * Perform various RF calculations.
 */
public class RFUtils {
    /**
     * Permittivity of free space (Farad/m)
     */
    public static final double EPSILON = 8.85418782E-12;
    /**
     * Permeability of free space (H/m)
     */
    public static final double MU = 1.25663706E-6;
    /**
     * Free-space impedance (ohm)
     */
    public static final double ETA = Math.sqrt(MU / EPSILON);
    /**
     * Speed of light in free-space (m/s)
     */
    public static final double C = 1 / Math.sqrt(EPSILON * MU);
    /**
     * Half-wave dipole radiation resistance (ohm)
     */
    public static final double R_RAD = 73.08;
    /**
     * Half-wave dipole gain
     */
    public static final double GTX = 1.642;
    public static final double GRX = GTX;

    /**
     * Calculate wavelength (m)
     *
     * @param freq source frequency
     * @return Result
     */
    public static Result getLambda(double freq) {
        return new Result((C / freq));
    }

    /**
     * Calculate wave number (rad/m)
     *
     * @param lambda wavelength
     * @return Result
     */
    public static Result getWaveNumber(double lambda) {
        return new Result((2 * Math.PI / lambda));
    }

    /**
     * Calculate half-wave dipole length (m)
     *
     * @param freq source frequency
     * @return Result
     */
    public static Result getDipoleSize(double freq) {
        return new Result(getLambda(freq).value() / 2);
    }

    /**
     * Calculate antenna feed current (A)
     *
     * @param power input power
     * @return Result
     */
    public static Result getCurrent(double power) {
        return new Result(Math.sqrt(2 * power / R_RAD));
    }

    /**
     * Calculate electric field on Z axis at a point r (V/m)
     *
     * @param Ia antenna feed current
     * @param k  wave number
     * @param l  antenna length
     * @param r  distance point from the transmit antenna
     * @return Result
     */
    public static Result computeEz(double Ia, double k, double l, double r) {
        Complex expPart = Complex.ofCartesian(0, k * r).exp().divide(r);
        Complex imgPart = Complex.ofCartesian(0,
                ((ETA * Ia) / (2 * Math.PI)) * ((1 - Math.cos(k * l / 2)) / Math.sin(k * l / 2)));
        Complex Ez = imgPart.multiply(expPart);
        return new Result(Ez.abs());
    }

    /**
     * Calculate V open circuit on a receiving antenna (V)
     *
     * @param Ia antenna feed current
     * @param k  wave number
     * @param l  antenna length
     * @param r  distance point from the transmit antenna
     * @return Result
     */
    public static Result computeVOC(double Ia, double k, double l, double r) {
        Complex expPart = Complex.ofCartesian(0, k * r).exp().divide(k * r);
        Complex imgPart = Complex.ofCartesian(0,
                ((ETA * Ia) / Math.PI) * Math.pow(Math.tan(k * l / 4), 2));
        Complex VOC = imgPart.multiply(expPart);
        return new Result(VOC.abs());
    }

    /**
     * Calculate power on a receiving load
     *
     * @param VOC V open circuit on a receiving antenna
     * @return Result
     */
    public static Result computePload(double VOC) {
        return new Result(Math.pow(VOC, 2) / (8 * R_RAD));
    }

    /**
     * Calculate received power by a receiving antenna (W) via Friis formula
     *
     * @param Ptx    transmit power
     * @param lambda wavelength
     * @param r      distance point from the transmit antenna
     * @return Result
     */
    public static Result computePrx(double Ptx, double lambda, double r) {
        return new Result(Ptx * GTX * GRX * Math.pow((lambda / (4 * Math.PI * r)), 2));
    }

    /**
     * Convert W to decibel milliwatt (dbm)
     *
     * @param power power to convert
     * @return Result
     */
    public static Result wToDbm(double power) {
        return new Result(10 * Math.log10(power / 1E-3));
    }

}
