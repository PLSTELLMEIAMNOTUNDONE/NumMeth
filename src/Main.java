import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void YANA(String[] args){
        Vector x=new Vector(new double[]{
                0,15,30,45,60,70,80,100
        });
        Vector y=new Vector(new double[]{
                1.4449,
                1.4330,
                1.4213,
                1.4093,
                1.3967,
                1.3876,
                1.3779,
                1.3589,
        });
        Vector valsL=new Vector(new double[]{
                1.4444,
                1.4347,
                1.4215,
                1.4078,
                1.3951,
                1.3848,
                1.3795,
                1.3591,
        });
        Vector valsR=new Vector(new double[]{
                1.4445,
                1.4349,
                1.4218,
                1.4070,
                1.3924,
                1.3820,
                1.3752,
                1.3590,
        });
        System.out.println("FIRST");
        System.out.println(Util.YANATASK(x,y,valsL));
        System.out.println("SECOND");
        System.out.println(Util.YANATASK(x,y,valsR));

    }
    public static Matrix scanMatrix(int n,int m,StringTokenizer s,BufferedReader bufferedReader){

        Matrix A=new Matrix(n,m);
        for(int i=0;i<n;i++){
            try {
                s=new StringTokenizer(bufferedReader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(int j=0;j<m;j++){
                A.setE(i,j,Double.parseDouble(s.nextToken()));
            }
        }
        return  A;

    }
    public static void main(String[] args) {
        //test();
        /*System.out.println("task 1\n");
        task1();

        System.out.println("task 3\n");
        task3();*/
        System.out.println("task 2\n");
        task2();


    }
    public static void test() {

        System.out.println(new Vector(new double[]{5,5,5}).mult(0.11547005383792514));

        System.out.println(Math.pow(new Vector(new double[]{5,5,5}).norm(),-1));

    }
    public static void testNumber(Pair<Vector,Double> propertyPair,Matrix A){
        System.out.println(
                "Av= "+A.mult(propertyPair.getFirst())
                        +"xv= \n"+
                        propertyPair.getFirst().mult(propertyPair.getSecond()));
    }
    public static void pushTeest(){

    }

    public static void task1(){
        FileReader fileReader= null;
        try {
            fileReader = new FileReader("input.txt");

            BufferedReader bufferedReader=new BufferedReader(fileReader);
            StringTokenizer s=new StringTokenizer("");
            Matrix L=scanMatrix(3,3,s,bufferedReader);
            Matrix C=scanMatrix(3,3,s,bufferedReader);
            Matrix Cinv=scanMatrix(3,3,s,bufferedReader);


            double N=2;

            Matrix A=Cinv.mult(L).mult(C);
            System.out.println("matrix:= \n"+A);
            System.out.println("power method test");

            Pair<Vector,Double>  bigPrNum=A.PowerMethod(new Vector(new double[]{5, 5, 5}));
            System.out.println("Property number l= "+bigPrNum.getSecond());
            System.out.println("Property vector v= "+bigPrNum.getFirst());

            testNumber(bigPrNum,A);
            System.out.println("Matrix scetrum:");

            Pair<Vector,Double> propertyPair1=A.ReversePowerMethod(new Vector(new double[]{5, 5, 5}),2.06);
            Pair<Vector,Double> propertyPair2=A.ReversePowerMethod(new Vector(new double[]{5, 5, 5}),3.06);
            Pair<Vector,Double> propertyPair3=A.ReversePowerMethod(new Vector(new double[]{5, 5, 5}),3.96);
            System.out.println("Property number1 l= "+propertyPair1.getSecond());
            System.out.println("Property vector1 v= "+propertyPair1.getFirst());
            System.out.println("Property number2 l= "+propertyPair2.getSecond());
            System.out.println("Property vector2 v= "+propertyPair2.getFirst());
            System.out.println("Property number3 l= "+propertyPair3.getSecond());
            System.out.println("Property vector3 v= "+propertyPair3.getFirst());
            System.out.println("testing number:");
            testNumber(propertyPair1,A);
            testNumber(propertyPair2,A);
            testNumber(propertyPair3,A);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void task2FancyPrint(Func f){

        ArrayList<Double> arr1=Util.getPoints(-1,1,4);
        ArrayList<Double> arr2=Util.getPointsSmart(-1,1,4);

        Polynom p1=f.interpolation(arr1);
        Polynom p2=f.interpolation(arr2);
        System.out.println("Langrang interpolation with default separation  5 point \n resutl:"+p1+"\n");

        System.out.println("Langrang interpolation with smart separation 5 point \n resutl:"+p2+"\n"
        );
        arr1=Util.getPoints(-1,1,7);
        arr2=Util.getPointsSmart(-1,1,7);

        p1=f.interpolation(arr1);
        p2=f.interpolation(arr2);
        System.out.println("Langrang interpolation with default separation 10 point \n resutl:"+p1+"\n");

        System.out.println("Langrang interpolation with smart separation  10 point \n resutl:"+p2+"\n"
        );
    }
    public static void task2(){
        task2FancyPrint(new Func(x->Math.pow(x,3)-Math.exp(x)+1));
        System.out.println("|X|*f\n");
        task2FancyPrint(new Func(x->Math.abs(x)*(Math.pow(x,3)-Math.exp(x)+1)));
    }

    public static void  task3(){

      /*
      * define system
      * */
        ArrayList<Func> system=new ArrayList<>();
        system.add(new Func(x->1));
        system.add(new Func(x->x));
        system.add(new Func(x->x*x));
        system.add(new Func(x->x*x*x));


        /*
         * define function
         * */
        Func f=new Func(x->(Math.pow(x,3)+Math.exp(x)));
        /*
         * define vectors
         * */
        double[] arr=new double[5];
        arr[0]=-1;
        for(int i=1;i<arr.length;i++){
            arr[i]=arr[i-1]+0.3333;
        }
        System.out.println(Arrays.toString(arr));
        Vector x=new Vector(arr);
        Vector y=Util.getPoints(x,f);
        y=Util.addEps(y);
        /*
         * define matrix
         * */

        Matrix Q=new Matrix(x.getSize(),4);
        for(int i=0;i<x.getSize();i++){
            for(int j=0;j<4;j++){
                Q.setE(i,j,system.get(j).calc(x.getI(i+1)));
            }
        }
        System.out.println(Q);
        /*
        * define vector a
        * */
        Matrix H=(Q.transPos().mult(Q));
        Vector b=Q.transPos().mult(y);
        Vector a=Matrix.solvSystem(H,b);
        /*
        * define polynom
        * */
        Polynom polynom=new Polynom(a);
        System.out.println(polynom);
        /*
        * define Ln
        * */
        Polynom l0=Func.getLn(0);
        Polynom l1=Func.getLn(1);
        Polynom l2=Func.getLn(2);
        Polynom l3=Func.getLn(3);

        /*
        * define ck;
        * */
        double c0=new Func(X->f.calc(X)*l0.calc(X)).integral(-1).calc(1) /
                new Func(X-> Math.pow(l0.calc(X),2)).integral(-1).calc(1);

        double c1=new Func(X->f.calc(X)*l1.calc(X)).integral(-1).calc(1) /
                new Func(X-> Math.pow(l1.calc(X),2)).integral(-1).calc(1);
        double c2=new Func(X->f.calc(X)*l2.calc(X)).integral(-1).calc(1) /
                new Func(X-> Math.pow(l2.calc(X),2)).integral(-1).calc(1);
        double c3=new Func(X->f.calc(X)*l3.calc(X)).integral(-1).calc(1) /
                new Func(X-> Math.pow(l3.calc(X),2)).integral(-1).calc(1);
        /*
        * define OQ
        * */
        Polynom L0=l0.mult(c0);
        Polynom L1=l1.mult(c1);
        Polynom L2=l2.mult(c2);
        Polynom L3=l3.mult(c3);

        Polynom OQ=L0.add(L1).add(L2).add(L3);
        System.out.println(OQ);


    }
}


// 1 -2 3