package out.utility;

import out.function.Func;
import out.vector.Vector;

import java.util.ArrayList;

public class Util {
    static double acc = Math.pow(10, -4);

    public static Func YANA(double x1, double x2, double y1, double y2) {
        return new Func(y -> ((y - y2) * (x1 - x2) / (y1 - y2)) + x2);
    }

    public static Vector YANATASK(Vector x, Vector y, Vector vals) {
        ArrayList<Double> arr = new ArrayList<>();
        Func f = new Func();
        for (int i = 1; i < x.getSize(); i++) {
            f = YANA(x.getI(i), x.getI(i + 1), y.getI(i), y.getI(i + 1));
            arr.add(f.calc(vals.getI(i)));
        }
        arr.add(f.calc(vals.getI(vals.getSize())));
        return new Vector(arr);
    }

    public Util() {

    }

    public static void setAcc(double acc) {
        Util.acc = acc;
    }

    public static Vector getPoints(ArrayList<Double> x, Func f) {
        ArrayList<Double> y = new ArrayList<>();
        for (double xi : x) {
            y.add(f.calc(xi));
        }
        return new Vector(y);
    }

    public static Vector getPoints(Vector x, Func f) {
        ArrayList<Double> y = new ArrayList<>();
        for (double xi : x.getEls()) {
            y.add(f.calc(xi));
        }
        return new Vector(y);
    }

    public static double getEps() {
        return (Math.random() * (2 * acc) - acc);

    }

    public static Vector addEps(Vector v) {
        Vector e = new Vector(v.getSize());
        for (int i = 1; i <= e.getSize(); i++) {
            e.setI(i, getEps());
        }
        return v.plus(e);
    }

    public static double fac(int n) {
        if (n <= 1) return 1d;
        return n * fac(n - 1);
    }

    public static ArrayList<Double> getPoints(double a, double b, int n) {
        ArrayList<Double> ret = new ArrayList<>();
        double xi = a;
        for (int i = 1; i <= n; i++) {
            xi = a + ((b - a) * i / n);
            ret.add(xi);
        }
        return ret;
    }

    public static ArrayList<Double> getPointsSmart(double a, double b, int n) {
        ArrayList<Double> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double xi = 0.5 * ((b - a) * Math.cos((float) (2 * i + 1) / (2 * n + 2)) + (b + a));
            ret.add(xi);
        }
        return ret;
    }
}
