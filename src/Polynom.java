import java.util.ArrayList;

public class Polynom extends Func{
    protected ArrayList<Double> params;
    protected int size=0;
    public Polynom(){
        params=new ArrayList<>();
        params.add(0d);
        size=1;
    }
    public Polynom(ArrayList<Double> arr){
        this.params=new ArrayList<>(arr);
        size=arr.size();
        this.f=(x)->{
            double ret=0;
            for(int i=0;i<size;i++){
                ret+=Math.pow(x,size-i-1)*params.get(i);
            }
            return ret;
        };

    }
    public Polynom(int size){
        params=new ArrayList<>();
        for(int i=0;i<size;i++){
            params.add(0d);
        }
        this.size=size;
        this.f=(x)->{
            double ret=0;
            for(int i=0;i<size;i++){
                ret+=Math.pow(x,size-i-1)*params.get(i);
            }
            return ret;
        };

    }
    public Polynom(Vector v){
        this.params=v.getEls();
        size=v.getSize();
        this.f=(x)->{
            double ret=0;
            for(int i=0;i<size;i++){
                ret+=Math.pow(x,size-i-1)*params.get(i);
            }
            return ret;
        };
    }
    public Polynom(double[] arr){
        this.size=arr.length;
        this.params=new ArrayList<>();
        for(int i=0;i<size;i++){
            params.add(arr[i]);
        }

        this.f=(x)->{
            double ret=0;
            for(int i=0;i<size;i++){
                ret+=Math.pow(x,size-i-1)*params.get(i);
            }
            return ret;
        };
    }
    public Polynom add(Polynom p){
        Polynom x=this.size>p.size?this:p;
        Polynom y=this.size<=p.size?this:p;
        Polynom ret=new Polynom(x.size);
        for(int i=0;i<ret.size;i++){
            if(i>=x.size-y.size) {
                ret.params.set(i, y.params.get(i - x.size + y.size ) + x.params.get(i));
            }
            else{
                ret.params.set(i,x.params.get(i));
            }
        }
        return  ret;
    }
    public Polynom mult(Polynom p){
        if(p.size<=1){
            return this.mult(p.params.get(0));
        }
        if(this.size<=1){
            return p.mult(this.params.get(0));
        }
        Polynom ret=new Polynom(((p.size-1)+(this.size-1))+1);
        for(int i=0;i<this.size;i++){
            for(int j=0;j<p.size;j++){
                ret.params.set(i+j,ret.params.get(i+j)+this.params.get(i)*p.params.get(j));
            }
        }
        return ret;
    }
    public Polynom mult(double d){
        Polynom ret=new Polynom(this.size);
        for(int i=0;i<this.size;i++){
            ret.params.set(i,this.params.get(i)*d);
        }
        return ret;
    }
    public String toString(){
        StringBuilder ret=new StringBuilder();
        for(int i=0;i<this.size;i++){
            ret.append(this.params.get(i)+"x"+"^("+(this.size-i-1)+")");
            if(i!=this.size-1&&params.get(i+1)>=0){
                ret.append("+");
            }

        }
        return ret.toString();
    }
    public Polynom getProd(){
        if(size<=1)return new Polynom(new double[]{0});
        Polynom prod=new Polynom(this.params);
        for(int i=0;i<prod.size;i++){
            prod.params.set(i,(prod.size-i-1)*prod.params.get(i));
        }
        prod.params.remove(prod.size-1);
        prod.size--;
        return prod;


    }
    public Polynom getDifN(int n){

        if(n==0)return this;
        if(n==1)return this.getProd();
        if(size<=1)return new Polynom(new double[]{0});
        return this.getProd().getDifN(n-1);
    }
    public  Polynom pow(int n){
        if(n==0)return  new Polynom(new double[]{1});
        if(n==1)return this;
        return this.mult(this.pow(n-1));
    }
}
