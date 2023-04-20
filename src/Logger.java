import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    public static int calls=0;
    HashMap<Object,StringBuilder> logs=new HashMap<>();
    public Logger(){
        logs=new HashMap<>();
    }
    public void register(Object o){
        if(!logs.containsKey(o))logs.put(o,new StringBuilder());
    }
    public void write(String method,Object o,int x){
        calls++;
        logs.put(o,logs.get(o).append(
                "\n Call number: "+calls+";\n Object: "+o.toString()+";\n Method: "+method
        ));
        if(method.equals("ReversePowerMethod")){
            logs.put(o,logs.get(o).append(
                    "\n number of steps:"+x
            ));
        }
        if(method.equals("StaticReversePowerMethod")){
            logs.put(o,logs.get(o).append(
                    "\n number of steps:"+x
            ));
        }
        if(method.equals("solvSystem")){
            logs.put(o,logs.get(o).append(
                    "\n Exit code:"+x
            ));
        }
    }
    public void writeReturn(String method,Object o,Object ret){
        if(method.equals("solvSystem")){
            logs.put(o,logs.get(o).append(
                    "\n result:"+ret.toString()
            ));
        }
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        for(Map.Entry e:logs.entrySet()){
            ans.append(e.getValue());
        }
        return ans.toString();
    }

}
