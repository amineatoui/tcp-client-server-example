import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.stream.Collectors;

public class ClientSRThread extends Thread  {
    
    public Socket socket;

    public ClientSRThread(Socket soc){
         socket=soc;
    }

    public void run(){
     BufferedReader bf;
     PrintWriter out;
     while (true) {
          //read input then send command to the sever
        
               try {

                System.out.println("Enter your command ");
                 bf=new BufferedReader(new InputStreamReader(System.in));
                  out = new PrintWriter(socket.getOutputStream(), true);
                 out.println(bf.readLine());
              
               BufferedReader   in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               String resp=in.readLine().replaceAll("delimiter", "\n");
               System.out.println("server has responded with : \n"+ resp);
          
               
          
               } catch (Exception e) {
                    e.printStackTrace();
               }
               
                               
                  
         }
    }
}
