package out.function;

import out.polynom.Polynom;
import out.utility.Util;

import java.util.ArrayList;

public class Func {
    protected Calculated f;


    public Func(Calculated f) {
        this.f = f;

    }

    public Func() {
        this.f = x -> 0;

    }


    public double calc(double x) {
        return f.calculate(x);
    }
    /*
     *
     *
     * (x->x0)limit f(x)
     * */

    public double limit(double x) {
        double acc = Math.pow(10, -6);
        double h = 0.005;

        double res = calc(x + h);
        while (true) {
            h /= 2;

            double newRes = calc(x + h);
            if (Math.abs(newRes - res) < acc) break;
            res = newRes;
        }
        return res;
    }


    public double calcProd(double x0) {
        Calculated c = (x -> (calc(x + x0) - calc(x0)) / x);
        return new Func(c).limit(0);
    }

    public Func getProd() {
        Calculated c = (this::calcProd);
        return new Func(c);
    }

    public double root(double min, double max, double acc) {

        double x = min;
        double newX = x - (calc(x) / calcProd(x));
        while (true) {
            newX = x - (calc(x) / calcProd(x));

            if (newX < min || newX > max) {
                newX = (min + max) / 2;
                double newBound = calc(newX);
                if (newBound > 0) max = newX;
                if (newBound <= 0) min = newX;
                return root(min, max, acc);
            }
            if (Math.abs(x - newX) < acc) break;
            x = newX;
        }
        return x;

    }


    private Polynom getLi(ArrayList<Double> points, int j) {
        Polynom ret = new Polynom(new double[]{1});
        for (int i = 0; i < points.size(); i++) {
            if (i != j) {
                Polynom toMult = new Polynom(new double[]{1, -points.get(i)});
                ret = ret.mult(toMult.mult(Math.pow((points.get(j) - points.get(i)), -1)));
            }
        }


        return ret;
    }

    public Polynom interpolation(ArrayList<Double> points) {
        Polynom ret = new Polynom(0);
        for (int j = 0; j < points.size(); j++) {
            Polynom li = getLi(points, j);
            ret = ret.add(li.mult(this.calc(points.get(j))));
        }
        return ret;
    }

    public Func integral(double a) {

        return new Func(x -> {

            Func ryad = new Func(N -> {
                double res = 0d, h = (x - a) / N;
                for (int i = 0; i <= N - 2; i += 2) {
                    res += this.calc(a + i * h) + 4 * this.calc(a + (i + 1) * h) + this.calc(a + (i + 2) * h);
                }
                return (h * res) / 3;
            });
            return ryad.calc(100d);
        });
    }

    public Func getDifN(int n) {
        if (n == 0) return this;
        if (n == 1) return this.getProd();
        return this.getProd().getDifN(n - 1);
    }

    public static Polynom getLn(int n) {
        Polynom f = new Polynom(new double[]{-1, 0, 1});
        f = f.pow(n);
        return f.getDifN(n).mult(1 / (Util.fac(n) * Math.pow(2, n)));
    }
}
