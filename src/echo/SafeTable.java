package echo;
import java.util.HashMap;

public class SafeTable extends HashMap<String, String>{
    public synchronized String get(String request){
        return super.get(request);
    }
    public synchronized void update(String request, String response){
        super.put(request, response);
    }
}
