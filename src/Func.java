import java.util.ArrayList;

public class Func  {
    protected Calculated f;


    public Func(Calculated f) {
        this.f = f;

    }
    public Func() {
        this.f=x->0;

    }



    public double calc(double x){
        return  f.calculate(x);
    }
    public double limit(double x){
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

    double calcProd(double x0) {
        Calculated c=( x ->(calc(x+x0)-calc(x0))/x);
        return new Func(c).limit(0);
    }
    public Func getProd() {
        Calculated c=(this::calcProd);
        return new Func(c);
    }

    public double root(double min, double max, double acc) {

        double x = min;
        double newX=x-(calc(x)/calcProd(x));
        while (true){
            newX=x-(calc(x)/calcProd(x));

            if(newX<min||newX>max){
                newX=(min+max)/2;
                double newBound=calc(newX);
                if(newBound>0)max=newX;
                if(newBound<=0)min=newX;
                return root(min,max,acc);
            }
            if(Math.abs(x-newX) < acc)break;
            x=newX;
        }
        return x;

    }
    public  static  ArrayList<Double> getPointsSmart(double a,double b,int n){
        ArrayList<Double> ret=new ArrayList<>();
        for(int i=0;i<n;i++){
            double xi=0.5*((b-a)*Math.cos((float)(2*i+1)/(2*n+2))+(b+a));
            ret.add(xi);
        }
        return ret;
    }
    public  static  ArrayList<Double> getPoints(double a,double b,int n){
        ArrayList<Double> ret=new ArrayList<>();
        double xi=a;
        for(int i=1;i<=n;i++){
            xi=a+((b-a)*i/n);
            ret.add(xi);
        }
        return ret;
    }
    private Polynom getLi(ArrayList<Double> points,int j){
        Polynom ret=new Polynom(new double[]{1});
        for(int i=0;i<points.size();i++){
            if(i!=j){
                Polynom toMult=new Polynom(new double[]{1,-points.get(i)});
                ret=ret.mult(toMult.mult(Math.pow((points.get(j)-points.get(i)),-1)));
            }
        }


        return ret;
    }
    public  Polynom interpolation(ArrayList<Double> points){
        Polynom ret=new Polynom(0);
        for(int j=0;j<points.size();j++){
            Polynom li=getLi(points,j);
            ret=ret.add(li.mult(this.calc(points.get(j))));
        }
        return ret;
    }

}
