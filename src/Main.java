import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    public static Matrix scanMatrix(int n,int m){
        FileReader fileReader= null;
        try {
            fileReader = new FileReader("input.txt");

        BufferedReader bufferedReader=new BufferedReader(fileReader);
        StringTokenizer s=new StringTokenizer("");
        Matrix A=new Matrix(n,m);
        for(int i=0;i<n;i++){
            s=new StringTokenizer(bufferedReader.readLine());
            for(int j=0;j<m;j++){
                A.setE(i,j,Double.parseDouble(s.nextToken()));
            }
        }
        return  A;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {

        double N=2;
        Matrix matrix=scanMatrix(3,3);
        System.out.println(matrix.PowerMethod(new Vector(new double[]{5, 5, 5})));
        System.out.println(matrix.ReversePowerMethod(new Vector(new double[]{5, 5, 5}),3.5));
       // System.out.println(matrix.StaticReversePowerMethod(new Vector(new double[]{5, 5, 5}),3.5));
        System.out.println(Matrix.logger);
        System.out.println(
                matrix.mult(matrix.ReversePowerMethod(new Vector(new double[]{5, 5, 5}),3.5).getFirst())
                +"\n"+
                        matrix.ReversePowerMethod(new Vector(new double[]{5, 5, 5}),3.5).getFirst().mult(matrix.ReversePowerMethod(new Vector(new double[]{5, 5, 5}),3.5).getSecond())
        );

        Func f=new Func(x->1332*Math.sin(x));
        Polynom p1=f.interpolation(Func.getPoints(1000,2000,50));
        Polynom p2=f.interpolation(Func.getPointsSmart(1000,2000,4));
        System.out.println(p1.calc(2000));
        System.out.println(1332*Math.sin(2000));


    }

}


// 1 -2 3