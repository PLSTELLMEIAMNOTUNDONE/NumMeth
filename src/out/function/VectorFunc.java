package out.function;

import out.vector.Vector;

public class VectorFunc {
    VectorCalculated f;
    private final int size;

    public VectorFunc(VectorCalculated f, int size) {
        this.f = f;
        this.size = size;
    }

    public Double calc(Vector x) {
        if (x.getSize() != this.size) return null;
        return f.calculate(x);
    }

    public Double limit(Vector x) {
        if (x.getSize() != this.size) return null;
        double acc = Math.pow(10, -6);
        Vector h = new Vector(x.getSize(), 0.005);

        double res = calc(x.plus(h));
        while (true) {
            h = h.mult(0.5);

            double newRes = calc(x.plus(h));
            if (Math.abs(newRes - res) < acc) break;
            res = newRes;
        }
        return res;
    }

    public Func getFI(int i, Vector v) {
        if (v.getSize() != this.size - 1) return null;

        Calculated c = (x -> {
            return f.calculate(v.with(i, x));
        });
        return new Func(c);
    }

    public double calcProdI(int i, Vector v) {
        return getFI(i, v.without(i)).calcProd(v.getI(i));
    }

    public VectorFunc getProdI(int i) {
        return new VectorFunc((Vector v) -> calcProdI(i, v), this.size - 1);
    }


}
