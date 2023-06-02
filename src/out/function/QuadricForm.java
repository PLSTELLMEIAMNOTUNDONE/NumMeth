package out.function;

import out.function.VectorCalculated;
import out.function.VectorFunc;
import out.matrix.Matrix;
import out.vector.Vector;

public class QuadricForm extends VectorFunc {
    Matrix A;
    Vector b;

    public QuadricForm(VectorCalculated f, int size) {
        super(f, size);
    }

    public QuadricForm(Matrix A, Vector b) {

        super(v -> (v.mult(Vector.mult(A, v)) + b.mult(v)) / 2, b.getSize());
        this.A = A;
        this.b = b;
    }

    public Vector findMinPoint(Vector x0, double acc) {

        Vector xk = new Vector(x0);
        Vector qk = Vector.mult(A, xk).plus(b);

        while (qk.norm() > acc) {
            double uk = (-1) * qk.mult(Vector.mult(A, xk).plus(b)) / qk.mult(Vector.mult(A, qk));
            xk = xk.plus(qk.mult(uk));
            qk = Vector.mult(A, xk).plus(b);
        }
        return xk;
    }

    public Vector findMinPoint(Vector x0) {
        double acc = Math.pow(10, -6);
        Vector xk = new Vector(x0);
        Vector qk = Vector.mult(A, xk).plus(b);

        while (qk.norm() > acc) {
            double uk = (-1) * qk.mult(Vector.mult(A, xk).plus(b)) / qk.mult(Vector.mult(A, qk));
            xk = xk.plus(qk.mult(uk));
            qk = Vector.mult(A, xk).plus(b);
        }
        return xk;
    }

    public Vector mngs(Vector x0) {
        double acc = Math.pow(10, -6);
        return findMinPoint(x0, acc);
    }

    public Vector mngs(Vector x0, double acc) {


        return findMinPoint(x0, acc);
    }

    public Vector mnps(int i) {
        Vector x0 = new Vector(b.getSize(), 0d);
        x0.setI(i, 1);
        return findMinPoint(x0);
    }

    public Vector mnps(int i, double acc) {
        Vector x0 = new Vector(b.getSize(), 0d);
        x0.setI(i, 1);
        return findMinPoint(x0, acc);
    }
}
