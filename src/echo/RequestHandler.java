package echo;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler extends Correspondent implements Runnable {
    protected boolean active; // response can set to false to terminate thread
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }
    public RequestHandler() {
        super();
        active = true;
    }
    // override in a subclass:
    protected String response(String msg) throws Exception {
        System.out.println("received: " + msg);
        System.out.println("sending: echo " + msg);
        return "echo: " + msg;
    }
    // any housekeeping can be done by an override of this:
    protected void shutDown() {
        if (Server.DEBUG) System.out.println("request handler shutting down");
    }
    public void run() {
        while(active) {
            try {
                String request = this.receive();
                // receive request
                if(request.equals("quit")) {
                    System.out.println("received: quit");
                    shutDown();
                    break;
                }
                // send response
                String response = response(request);
                send(response);
                // sleep
                Thread.sleep(50);
            } catch(Exception e) {
                send(e.getMessage() + "... ending session");
                break;
            }
        }
        // close
        close();
    }

}