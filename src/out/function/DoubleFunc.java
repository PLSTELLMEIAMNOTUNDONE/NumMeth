package out.function;

import out.matrix.Matrix;

import java.util.ArrayList;

public class DoubleFunc {
    DoubleCalculated f;

    public double calc(double x, double y) {
        return f.calculate(x, y);
    }

    public DoubleFunc(DoubleCalculated f) {
        this.f = f;
    }

    public Func getFx(double y) {
        Calculated c = (x -> f.calculate(x, y));
        return new Func(c);
    }


    public Func getFy(double x) {
        Calculated c = (y -> f.calculate(x, y));
        return new Func(c);
    }

    public DoubleFunc getProdX() {
        return new DoubleFunc(this::calcProdX);
    }


    public double calcProdX(double x, double y) {
        return getFx(y).calcProd(x);
    }

    public double calcProdY(double x, double y) {
        return getFy(x).calcProd(y);
    }

    public static ArrayList<Double> roots(ArrayList<DoubleFunc> funcs, double x0, double y0) {
        double acc = Math.pow(10, -4);
        ArrayList<Double> dfx = new ArrayList<>();
        ArrayList<Double> dfy = new ArrayList<>();
        ArrayList<Double> res = new ArrayList<>();
        for (DoubleFunc func : funcs) {
            dfy.add(func.calcProdY(x0, y0));
            dfx.add(func.calcProdX(x0, y0));
            res.add((-1) * func.calc(x0, y0));
        }
        Matrix matrix = new Matrix(0, 0).concat(dfx).concat(dfy);
        ArrayList<Double> roots = matrix.solution(res);
        ArrayList<Double> newVars = new ArrayList<>();
        double h = roots.get(0);
        double g = roots.get(1);
        newVars.add(x0 + h);
        newVars.add(y0 + g);
        if (Math.abs(h) < acc && Math.abs(g) < acc) return newVars;
        return roots(funcs, x0 + h, y0 + g);


    }

    public static void main(String[] args) {

        DoubleFunc f1 = new DoubleFunc((x, y) -> Math.sin(x) + 2.0 * y - 2.0);
        DoubleFunc f2 = new DoubleFunc((x, y) -> y + Math.cos(x - 1) - 0.7);
        ArrayList<DoubleFunc> F = new ArrayList<>();
        F.add(f1);
        F.add(f2);
        System.out.println("roots " + DoubleFunc.roots(F, 2.33, 0.5).toString());


    }
}
