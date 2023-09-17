package echo;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.logging.Handler;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerType) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = (Class.forName(handlerType));
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() {
        while(true) {
            // accept a connection
            try {
                Socket request = mySocket.accept();
                // make handler
                RequestHandler slave = makeHandler(request);
                // start handler in its own thread
                Thread t = new Thread(slave);
                t.start();
            } catch (Exception e){
                e.getMessage();
            }
        } // while
    }

    public RequestHandler makeHandler(Socket s) {
        // handler = a new instance of handlerType
        RequestHandler handler = new RequestHandler();
        //    use: try { handlerType.getDeclaredConstructor().newInstance() } catch ...
        try {
            handler = (RequestHandler) handlerType.getDeclaredConstructor().newInstance();
            // set handler's socket to s
            handler.setSocket(s);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        // return handler
        return handler;
    }



    public static void main(String[] args) {
        int port = 5555;
        String service = "echo.RequestHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        System.out.println("Server address: 0.0.0.0/0.0.0.0");
        System.out.println("Server listening at port " + port);
        server.listen();
    }
}