import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Matrix {
    public static final Logger logger=new Logger();
    int n;
    int m;
    private ArrayList<ArrayList<Double>> v;

    public double getE(int i, int j) {
        return v.get(i).get(j);
    }

    public void setE(int i, int j, double d) {
        v.get(i).set(j, d);
    }

    public void addMatrix(Matrix matrix) {
        for (int i = 0; i < getCols(); i++) {
            for (int j = 0; j < getRows(); j++) {
                this.v.get(i).set(j, v.get(i).get(j) + matrix.v.get(i).get(j));
            }
        }
    }
    public Matrix add(Matrix matrix) {
        Matrix ret=new Matrix(this);
        ret.addMatrix(matrix);
        return ret;
    }
    public void multNum(double d) {
        for (int i = 0; i < getCols(); i++) {
            for (int j = 0; j < getRows(); j++) {
                this.v.get(i).set(j, v.get(i).get(j) * d);
            }
        }
    }
    public Matrix mult(double d) {
        Matrix ret=new Matrix(this);
        ret.multNum(d);
        return ret;
    }

    public ArrayList<Double> multVec(ArrayList<Double> vec) {
        ArrayList<Double> res = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            double x = 0;
            for (int j = 0; j < getCols(); j++) {
                x += v.get(i).get(j) * vec.get(j);
            }
            res.add(x);
        }
        return res;
    }
    public Vector mult(Vector vec) {
        return new Vector(multVec(vec.getEls()));
    }
    public Matrix mult(Matrix A){
        Matrix B=new Matrix(this.n,this.m);
        if(A.n!=B.m)return null;
        for(int i=0;i<n;i++){

            for(int j=0;j<A.m;j++){
                for(int k=0;k<m;k++){
                    B.setE(i,j,B.getE(i,j)+this.getE(i,k)*A.getE(k,j));
                }
            }
        }
        return B;
    }

    public void setV(Matrix m) {
        this.v = m.v.stream().map(ArrayList::new).collect(Collectors.toCollection(ArrayList::new));
    }

    public int getRows() {
        return v.size();
    }

    public int getCols() {
        return v.get(0).size();
    }

    public Matrix(int n, int m) {
        this.n=n;
        this.m=m;
        ArrayList<ArrayList<Double>> u = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Double> ui = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                ui.add((double) 0);
            }
            u.add(ui);
        }
        v = u;
    }

    public Matrix() {
        ArrayList<ArrayList<Double>> u = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        this.n=n;
        this.m=m;
        for (int i = 0; i < n; i++) {
            ArrayList<Double> ui = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                double x = sc.nextDouble();
                ui.add(x);
            }
            u.add(ui);
        }
        v = u;
    }

    public Matrix(Matrix ma) {
        this.n=ma.n;
        this.m=ma.m;
        this.setV(ma);
    }

    private int getPvt(int col) throws Exception {
        for (int i = col; i < this.getRows(); i++) {
            if (v.get(i).get(col) != 0) return i;
        }
        throw new Exception();
    }

    private void rowsSwap(int j, int i) {
        ArrayList<Double> tmp = new ArrayList<>();
        tmp = v.get(i);
        v.set(i, v.get(j));
        v.set(j, tmp);
    }

    public void rowsAdd(int j, ArrayList<Double> u) {
        for (int k = 0; k < u.size(); k++) {
            v.get(j).set(k, v.get(j).get(k) + u.get(k));
        }
    }

    public void print() {
            System.out.println(this.toString());
    }
    public String toString() {
        StringBuilder ans=new StringBuilder();
        for (int i = 0; i < getRows(); i++) {
            ans.append("[");
            for (int j = 0; j < getCols(); j++) {
                if (j == getCols() - 1) ans.append((v.get(i).get(j)));
                else ans.append((v.get(i).get(j) + " "));
            }
            ans.append(("]"));
        }
        return  ans.toString();
    }
    public ArrayList<Double> multRow(double x, int i) {
        ArrayList<Double> u = new ArrayList<>(v.get(i));
        u.replaceAll(aDouble -> x * aDouble);
        return u;
    }

    public Matrix getUpperTriangle() {
        Matrix matrix = new Matrix(this);
        for (int i = 0; i < matrix.getCols(); i++) {
            int pvtInd;
            try {
                pvtInd = matrix.getPvt(i);
            } catch (Exception e) {
                continue;
            }
            matrix.rowsSwap(pvtInd, i);
            for (int j = i + 1; j < matrix.getRows(); j++) {
                double k = (-1) * matrix.getE(j, i) / matrix.getE(i, i);
                ArrayList<Double> x = matrix.multRow(k, i);
                matrix.rowsAdd(j, x);
            }

        }
        return matrix;
    }

    public Matrix concat(ArrayList<Double> b) {
        Matrix matrix = new Matrix(this);

        for (int i = 0; i < getRows(); i++) {
            matrix.v.get(i).add(b.get(i));
        }
        if(getRows()==0){
            for(int i=0;i<b.size();i++){
                matrix.v.add(new ArrayList<>());
                matrix.v.get(i).add(b.get(i));
            }
        }
        return matrix;
    }

    public ArrayList<Double> solution(ArrayList<Double> b) {
        Matrix matrix = new Matrix(this).concat(b).getUpperTriangle();
        ArrayList<Double> roots = new ArrayList<>();

        for (int i = getRows() - 1; i >= 0; i--) {
            double x = matrix.getE(i, getCols());
            for (int j = 0; j < roots.size(); j++) {
                x -= matrix.getE(i, i + j + 1) * roots.get(roots.size() - j - 1);
            }
             if(Math.abs(matrix.getE(i, i))<Math.pow(10,-9)){
                 return null;
             } else {
                 x /= matrix.getE(i, i);
             }
            roots.add(x);
        }
        Collections.reverse(roots);
        return roots;
    }

    private Matrix createC() {
        Matrix matrixC = new Matrix(getRows(), getCols());
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if (i != j) matrixC.setE(i, j, (-1) * getE(i, j) / getE(i, i));
            }
        }
        return matrixC;
    }

    private ArrayList<Double> createD(ArrayList<Double> b) {
        ArrayList<Double> d = new ArrayList<>();
        for (int i = 0; i < b.size(); i++) {
            d.add(b.get(i) / getE(i, i));
        }
        return d;
    }

    public static boolean cheackAccuracy(double acc, ArrayList<Double> y, ArrayList<Double> x) {

        double sum = 0;
        for (int i = 0; i < y.size(); i++) {
             if(Math.abs(x.get(i) - y.get(i))>=acc)return false;
        }
        return true;
    }

    public ArrayList<Double> zeidelSolution(ArrayList<Double> b) {
        ArrayList<Double> x = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) x.add(1.0);
        Matrix c = createC();
        ArrayList<Double> d = createD(b);
        double acc = Math.pow(10, -10);
        while (true) {
            ArrayList<Double> newX = new ArrayList<>();
            ArrayList<Double> cx = c.multVec(x);
            for (int i = 0; i < d.size(); i++) {
                newX.add(cx.get(i) + d.get(i));
            }
            if(cheackAccuracy(acc, newX, x)) {
                break;
            }
            x = newX;
        }
        return x;
    }
    public static Matrix goodMatrix(double N){
        Matrix matrix = new Matrix(3,3);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
               if(i!=j)matrix.setE(i,j,1);
            }
        }
        matrix.setE(0,0,N+2);
        matrix.setE(1,1,N+4);
        matrix.setE(2,2,N+6);
        return matrix;
    }
    public static Matrix badMatrix(double N,double eps,int n){
        Matrix matrix1 = new Matrix(n,n);
        Matrix matrix2 = new Matrix(n,n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i>j){
                    matrix1.setE(i,j,-1);
                    matrix2.setE(i,j,-1);
                }
                else if(i==j){
                    matrix1.setE(i,j,1);
                    matrix2.setE(i,j,1);
                }
                else{
                    matrix2.setE(i,j,1);
                }
            }
        }
        matrix2.multNum(N*eps);
        matrix1.addMatrix(matrix2);
        return matrix1;
    }
    public static ArrayList<Double> badV(int n){
        ArrayList<Double> V=new ArrayList<>();
        for(int i=0;i<n-1;i++)V.add(-1.0);
        V.add(1.0);
        return V;
    }
    public static ArrayList<Double> goodV(double N){
        ArrayList<Double> goodB=new ArrayList<>();
        goodB.add(N+4);
        goodB.add(N+6);
        goodB.add(N+8);
        return goodB;
    }
    public static void  main(String[] args){
        double N=2.0;
        int n=5;
        ArrayList<Double> goodB=goodV(N);
        ArrayList<Double> badB=badV(n);
        System.out.println("good matrix test Gause"+goodMatrix(N).solution(goodB));
        System.out.println("good matrix test Zeidel"+goodMatrix(N).zeidelSolution(goodB));
        System.out.println("bad matrix test Gause"+badMatrix(N,Math.pow(10,-4),n).solution(badB));
        System.out.println("bad matrix test Zeidel"+badMatrix(N,Math.pow(10,-4),n).zeidelSolution(badB));

    }
    public Vector getVector(int i){
        return new Vector(this.v.get(i-1));
    }
    public Double PowerMethod(Vector y0){
        if((m)!=y0.getSize())return null;

        Matrix A=new Matrix(this);
        Vector yk=new Vector(y0);
        Vector zk=y0.normVersion();
        Vector lk=new Vector(zk);
        Vector nlk=new Vector(y0.getSize(),0);
        double delt=0.000005,acc=Math.pow(10,-6);
        while((lk.plus(nlk.mult(-1))).max()>acc*Math.max(lk.max(),nlk.max())){
            lk=nlk;
            yk=new Vector(A.mult(zk));
            Vector nzk=yk.normVersion();
            nlk=new Vector(y0.getSize(),0);
            for(int i=1;i<=yk.getSize();i++){
                if(Math.abs(zk.getI(i))>delt)nlk.setI(i,yk.getI(i)/zk.getI(i));
                else nlk.setI(i,0);
            }

            zk=nzk;
        }
        double ans=0,count=0;
        for(double el:lk.getEls()){
            if(el==0)continue;
            ans+=el;
            count++;
        }
        return ans/count;

    }
    public static Matrix id(int n,int m){
        Matrix ret=new Matrix(n,m);
        int i=0;
        while(i<n&&i<m){
            ret.setE(i,i,1);
            i++;
        }
        return ret;
    }
    public Pair<Vector,Double> ReversePowerMethod(Vector y0,double v0){
        if((m)!=y0.getSize())return null;
        logger.register(this);
        Matrix A=new Matrix(this);
        Vector yk=new Vector(y0);
        Vector zk= new Vector(y0.getSize(),0);
        Vector uk=new Vector(zk);
        Vector nuk=new Vector(y0.getSize(),0);
        Vector nzk=y0.normVersion();
        double vk=v0;
        double nvk=v0;
        double delt=0.000005,acc=Math.pow(10,-6);
        int steps=0;
        while((zk.plus(nzk.mult(-1))).norm()>acc || Math.abs(vk-nvk)>acc){
            vk=nvk;

            ArrayList<Double> arr=A.add(id(A.n,A.m).mult(-vk)).solution(nzk.getEls());
            if(arr==null)break;
            else yk=new Vector(arr);

            nuk=new Vector(y0.getSize(),0);
            for(int i=1;i<=yk.getSize();i++){
                if(Math.abs(yk.getI(i))>delt)nuk.setI(i,nzk.getI(i)/yk.getI(i));
                else nuk.setI(i,0);
            }
            zk=nzk;
            nzk=yk.normVersion();
            uk=nuk;

            double sum=0,count=0;
            for(double el:uk.getEls()){
                if(Math.abs(el)<Math.pow(10,-9))continue;
                sum+=el;
                count++;
            }
            nvk=vk+sum/count;
            steps++;

        }
        logger.write("ReversePowerMethod",this,steps);
        return new Pair<>(nzk,nvk);

    }
    public Pair<Vector,Double> StaticReversePowerMethod(Vector y0,double v0){
        if((m)!=y0.getSize())return null;
        logger.register(this);
        Matrix A=new Matrix(this);

        Vector yk=new Vector(y0);
        Vector zk=new Vector(y0.getSize(),0);
        Vector uk=new Vector(zk);
        Vector nuk=new Vector(y0.getSize(),0);
        Vector nzk=y0.normVersion();

        double vk=0;
        double nvk=v0;
        int steps=0;
        A=A.add(id(A.n,A.m).mult(-vk));
        double delt=0.00005,acc=Math.pow(10,-6);
        while( Math.abs(vk-nvk)>acc){


            ArrayList<Double> arr=A.solution(nzk.getEls());
            if(arr==null)break;
            else yk=new Vector(arr);

            nuk=new Vector(y0.getSize(),0);
            for(int i=1;i<=yk.getSize();i++){
                if(Math.abs(yk.getI(i))>delt)nuk.setI(i,nzk.getI(i)/yk.getI(i));
                else nuk.setI(i,0);
            }
            zk=nzk;
            nzk=yk.normVersion();
            uk=nuk;

            double sum=0,count=0;
            for(double el:uk.getEls()){
                if(Math.abs(el)<Math.pow(10,-9))continue;
                sum+=el;
                count++;
            }
            nvk=vk+sum/count;
            steps++;

        }
        logger.write("StaticReversePowerMethod",this,steps);
        return new Pair<>(nzk,vk);

    }

    public ArrayList<Pair<Vector,Double>> spec1(Vector y0,Vector v0){

        ArrayList<Pair<Vector,Double>> arr=new ArrayList<>();
        for(int i=1;i<=m;i++){
            arr.add(this.ReversePowerMethod(y0,v0.getI(i)));
        }
        return arr;

    }
    public ArrayList<Pair<Vector,Double>> spec2(Vector y0,Vector v0){

        ArrayList<Pair<Vector,Double>> arr=new ArrayList<>();
        for(int i=1;i<=m;i++){
            arr.add(this.StaticReversePowerMethod(y0,v0.getI(i)));
        }
        return arr;

    }
}
