package echo;

import java.net.Socket;

public class CacheHandler extends ProxyHandler{
    private SafeTable cache;
    public CacheHandler(Socket s){
        super(s);
        initCache();
    }
    public CacheHandler(){
        super();
        initCache();
    }
    private void initCache(){
        cache = new SafeTable();
    }

    protected String response(String msg) throws Exception {
        String check = cache.get(msg);
        if (check != null){
            System.out.println("Request cached already");
            return check;
        }
        peer.send(msg);
        String response = peer.receive();
        cache.update(msg, response);
        return response;
    }
}
