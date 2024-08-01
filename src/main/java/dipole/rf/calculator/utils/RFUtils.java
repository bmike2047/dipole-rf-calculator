package dipole.rf.calculator.utils;


import org.apache.commons.numbers.complex.Complex;

public class RFUtils {

    public static final double EPSILON = 8.85418782E-12;
    public static final double MU = 1.25663706E-6;
    public static final double ETA = Math.sqrt(MU / EPSILON);
    public static final double C = 1 / Math.sqrt(EPSILON * MU);
    public static final double R_RAD = 73.08;
    public static final double GTX = 1.642;
    public static final double GRX = GTX;


    public static Result getLambda(double freq) {
        return new Result((C / freq));
    }

    public static Result getWaveNumber(double lambda) {
        return new Result((2 * Math.PI / lambda));
    }

    public static Result getDipoleSize(double freq) {
        return new Result(getLambda(freq).value() / 2);
    }

    public static Result getCurrent(double power) {
        return new Result(Math.sqrt(2 * power / R_RAD));
    }


    public static Result computeEz(double Ia, double k, double l, double r) {
        Complex expPart = Complex.ofCartesian(0, k * r).exp().divide(r);
        Complex imgPart = Complex.ofCartesian(0,
                ((ETA * Ia) / (2 * Math.PI)) * ((1 - Math.cos(k * l / 2)) / Math.sin(k * l / 2)));
        Complex Ez = imgPart.multiply(expPart);
        return new Result(Ez.abs());
    }


    public static Result computeVOC(double Ia, double k, double l, double r) {
        Complex expPart = Complex.ofCartesian(0, k * r).exp().divide(k * r);
        Complex imgPart = Complex.ofCartesian(0,
                ((ETA * Ia) / Math.PI) * Math.pow(Math.tan(k * l / 4), 2));
        Complex VOC = imgPart.multiply(expPart);
        return new Result(VOC.abs());
    }


    public static Result computePload(double VOC) {
        return new Result(Math.pow(VOC, 2) / (8 * R_RAD));
    }

    public static Result computePrx(double Ptx, double lambda, double r) {
        return new Result(Ptx * GTX * GRX * Math.pow((lambda / (4 * Math.PI * r)), 2));
    }

    public static Result wToDbm(double power) {
        return new Result(10 * Math.log10(power / 1E-3));
    }

}
