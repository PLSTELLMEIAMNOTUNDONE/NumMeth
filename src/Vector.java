import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Vector {
    private ArrayList<Double> els;
    private final int size;
    public Vector(int size){
        this.size=size;
        this.els=new ArrayList<>(size);
    }
    public  Vector(){
        this.size=0;
        this.els=new ArrayList<>();
    }
    public Vector(int size,ArrayList<Double> arr){
        this.size=size;
        this.els=arr;
    }
    public Vector(ArrayList<Double> arr){

        this.els=arr;
        this.size=arr.size();
    }
    public Vector(Vector v){
        this.size=v.size;
        this.els=v.els;
    }
    public Vector(int size,double x){
        this.size=size;
        this.els=new ArrayList<>();
        for(int i=0;i<size;i++){
            els.add(x);
        }
    }
    public Vector(double[] arr){
        this.size=arr.length;
        this.els=new ArrayList<>();
        for(int i=0;i<size;i++){
            els.add(arr[i]);
        }
    }
    public Vector plus(Vector v){
        if(v.size!=this.size)return null;
        Vector ret=new Vector(this.size);
        for(int i=0;i<size;i++){
            ret.els.add(this.els.get(i)+v.els.get(i));
        }
        return ret;
    }
    public Vector mult(double x){
        Vector ret=new Vector(this.size);
        for(double el:this.els)ret.els.add(el*x);
        return  ret;
    }
    public void setI(int i,double x){
        this.els.set(i-1,x);
    }
    public double getI(int i){
        return this.els.get(i-1);
    }
    public int getSize(){
        return this.size;
    }
    public ArrayList<Double> getEls(){
        return  new ArrayList<>(this.els);
    }
    public  Vector without(int i){
        i--;
        ArrayList<Double> arr=new ArrayList<>(this.els);
        arr.remove(i);
        return new Vector(size-1,arr);
    }
    public  Vector with(int i,double x){
        i--;
        ArrayList<Double> arr=new ArrayList<>(this.els);
        arr.add(i,x);
        return new Vector(size+1,arr);
    }
    public Double mult(Vector v){
        double ret=0;
        if(v.size!=this.size)return null;
        for(int i=1;i<=this.size;i++){
            ret+=v.getI(i)*this.getI(i);
        }
        return ret;
    }
    public static @NotNull Vector mult(Matrix A, Vector v){
        if(v.size!=A.getRows())return null;
        return new Vector((A.multVec(v.els)));
    }
    public double norm(){
        return Math.sqrt(this.mult(this));
    }
    public Vector normVersion(){
        return new Vector(this).mult(1/this.norm());
    }
    public double max(){
        double ans=0;
        for(double el:els)ans=Math.max(ans,Math.abs(el));
        return ans;
    }
    @Override
    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append("[");
        for(int i=0;i<size;i++){
            if(i!=size-1)str.append(els.get(i)+", ");
            else str.append(els.get(i)+"]\n");
        }
        return str.toString();
    }

}
