import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import models.Compte;
import models.Operation;
import models.User;

public class Server {
    
    public InetAddress ipSerever;
    public static int serverPort=9000;

    public static List<User> listOfUsers;
    public static List<Operation> listOfOps;
    public static List<User> listOfConnected;

    public Server(){
        listOfOps=new ArrayList<>();
      listOfUsers=new ArrayList<>();
      listOfConnected=new ArrayList<>();
        try {
            ipSerever=InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int checkUser(String name){
        List<User> l=listOfUsers;

        for (int i = 0; i < l.size(); i++) {
        if (l.get(i).getNom().equals(name)) {
              
            return i;
        }
   }

        return -1;
   }

   public static void credit(int userIndex,float montant){
     User u= listOfUsers.get(userIndex);
     Compte c=new Compte();
     c.setSolde(u.getCompte().getSolde()+montant);
     listOfUsers.get(userIndex).setCompte(c);

     Operation op=new Operation();
     op.setSender(u);
     op.setType("credit");
     op.setAmount(montant);
     listOfOps.add(op);
     System.out.println("size : "+listOfOps.size());         

   }

   public static boolean debit(int userIndex,float montant){
        User u= listOfUsers.get(userIndex);
        Compte c=new Compte();
        if (u.getCompte().getSolde()<montant) {
             return false;
        }
        c.setSolde(u.getCompte().getSolde()-montant);
        listOfUsers.get(userIndex).setCompte(c);
 
        Operation op=new Operation();
        op.setSender(u);
        op.setType("debit");
        op.setAmount(montant);
        listOfOps.add(op);

        System.out.println("size : "+listOfOps.size());
        return true;
   }

   public static int isConnected(String username){
    List<User> l=listOfConnected;
     for (int i = 0; i < l.size(); i++) {
         if (l.get(i).getNom().equals(username)) {
               return i;
         }
    }    
     return -1;
   }


   public static User getUserByIpAddress(InetAddress ip){
    for(User u: Server.listOfUsers){
         if (u.getIpAddress()==ip) {
              return u;
         }
     }

    return null;
   }


   public static String formatListOfOps(String username){
       String toReturn="";
       for (Operation operation : listOfOps) {
           if (operation.getSender().getNom().equals(username) || operation.getReciever().getNom().equals(username) ) { 
               toReturn +="type of operation : "+operation.getType()+" delimiter ";
               if (operation.getType().equals("transfert")) {
                   toReturn+="sender : "+operation.getSender().getNom()+"delimiter "; 
                   toReturn+="sender : "+operation.getReciever().getNom()+"delimiter ";
                                        
               }
                
            toReturn+="amount "+operation.getAmount()+" delimiter "; 
            } 
       }
       
       return toReturn;

   }
   

   public static boolean transfertTo(int indexSender,int indexReceiver,float amount){
      
    User sender=listOfUsers.get(indexSender);
    User receiver=listOfUsers.get(indexReceiver);
      if (sender.getCompte().getSolde()>=amount) {
             listOfUsers.get(indexReceiver).getCompte().setSolde(receiver.getCompte().getSolde()+amount);    
             listOfUsers.get(indexSender).getCompte().setSolde(sender.getCompte().getSolde()-amount);    
               
             Operation op=new Operation();
             op.setAmount(amount);
             op.setReciever(receiver);
             op.setSender(sender);
             op.setType("transfert");
             listOfOps.add(op);

        return true;
      }  


    return false;
   }

   
   public static String displayHelp(){
      String help="";
       help+="CREATION [username] delimiter";
       help+="  create an account delimiter";
       help+="CONNECT [username] delimiter";
       help+="  initialize a session delimiter";
       help+="DISCONNECT delimiter";
       help+="  close a session delimiter";
       help+="CREDIT [amount] delimiter";
       help+="  add money to an account delimiter";
       help+="DEBIT [amount] delimiter";
       help+="  debit money from an account delimiter";
       help+="SOLDE delimiter";
       help+="  display account balance delimiter";
       help+="TRANSFERT [to-username] [amount] delimiter";
       help+="  transfert money to an account delimiter";
       help+="OPERATION delimiter";
       help+="  display all recent operations delimiter";
       help+="HELP delimiter";
       help+="  display help delimiter";


      return help;
   }





}
