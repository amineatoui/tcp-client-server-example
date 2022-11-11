import java.net.Socket;

public class ClientAPP {
     
    public static void main(String[] args) {
     
        
        try {
            Server s=new Server();
            Socket soc=new Socket(s.ipSerever,Server.serverPort);

            ClientSRThread t1=new ClientSRThread(soc);
            t1.start();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
