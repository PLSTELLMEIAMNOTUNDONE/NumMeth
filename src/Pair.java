public class Pair<F,S> {
    F f;
    S s;
    Pair(F f,S s){
        this.f=f;
        this.s=s;
    }

    public F getFirst(){
        return f;
    }
    public void setFirst(F f){
        this.f=f;
    }
    public S getSecond(){
        return s;
    }
    public void setSecond(S s){
        this.s=s;
    }
    public String toString(){
        return "("+f.toString()+";"+s.toString()+")";
    }
}
