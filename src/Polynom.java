import java.util.ArrayList;

public class Polynom extends Func{
    protected ArrayList<Double> params;
    protected int size=0;
    public Polynom(ArrayList<Double> arr){
        this.params=arr;
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
            if(i<y.size){
                ret.params.set(i,y.params.get(i)+x.params.get(i));
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
            if(i!=this.size-1&&params.get(i+1)>0){
                ret.append("+");
            }

        }
        return ret.toString();
    }
}
