import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        

        try {
             ServerSocket ss=new ServerSocket(Server.serverPort);
         
             while (true) {
                  Socket s=ss.accept();
                  ClientHandler clh=new ClientHandler(s);
                  clh.start();
                
             }
            

        } catch (Exception e) {
            // TODO: handle exception
        }


    }
}
