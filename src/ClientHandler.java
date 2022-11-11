

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import models.Compte;
import models.User;

//this thread is created each time the serever receives a new socket;
public class ClientHandler extends Thread {
     
    public Socket socket;

    public ClientHandler(Socket s){
        socket=s;
    }


    public void run(){
        PrintWriter out;
        BufferedReader in;
        Server server=new Server();
        while (true) {
            try {
                in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 String cmd=in.readLine();
                 String response="";
                 if (cmd.startsWith("CREATION")) {
                     String name=cmd.substring(9);
                     int index=Server.checkUser(name);
                     if (index == -1) {
                        User newUser=new User();
                        newUser.setNom(name);
                        newUser.setIpAddress(socket.getInetAddress());
                        Compte c=new Compte();
                        newUser.setCompte(c);
                        Server.listOfUsers.add(newUser);
                        System.out.println(Server.listOfUsers.size());
                         response="successfully created a new account , here is your account number : "+c.getId();
                     }
                     else {
                        response =name+" has already registred !";
                     }
                                          
                 }

                 if (cmd.startsWith("CREDIT")) {
                       float f=Float.parseFloat(cmd.substring(7));
                       User user=Server.getUserByIpAddress(socket.getInetAddress());
                       if (user==null) {
                          response="you need to create an account first";
                       } else {
                        
                           int userindex=Server.isConnected(user.getNom());
                           if (userindex!=-1) {
                               Server.credit(Server.checkUser(user.getNom()), f);
                               response="succeffully added "+f+" to your account";
                            } else {
                                response="you need to connect first !";
                            }                  
                        }
                 }

                 if (cmd.startsWith("DEBIT")) {
                    float f=Float.parseFloat(cmd.substring(6));
                    User user=Server.getUserByIpAddress(socket.getInetAddress());
                    if (user==null) {
                         response="you need to create an account first";
                    } else {
                        
                        int userindex=Server.isConnected(user.getNom());
                        if (userindex!=-1) {
                            if (Server.debit(Server.checkUser(user.getNom()), f)) {
                             response="operation done succesfully";
                            
                         } else {
                             response="you don't have enough credit";
                            }
                        } else {
                            response="you need to connect first !";
                        }                  
                    }
                 }

                 if (cmd.startsWith("CONNECT")) {
                    String username=cmd.substring(8);
                    int index=Server.checkUser(username);
                     if (index!=-1) {
                           int conIndex=Server.isConnected(username);
                           if (conIndex!=-1) {
                              response="you are already connected !";
                           }
                           else{
                            User newUser=new User();
                            newUser.setNom(username);
                            newUser.setIpAddress(socket.getInetAddress());
                            newUser.setCompte(new Compte());
                            Server.listOfConnected.add(newUser);
                            response="successfully connected !";
                           }
                     } else {
                        response="you don't have an account yet";
                     }
                 }

                 if (cmd.startsWith("SOLDE")) {
                    User u=Server.getUserByIpAddress(socket.getInetAddress());
                    if (u==null) {
                        response="you need to create an account first";
                        
                    }
                    else {

                        int index=Server.isConnected(u.getNom());
                        if (index!=-1) {
                         response="you have "+u.getCompte().getSolde()+" in your account";
                        }
                        else {
                            response="you need to connect first";
                        }
                    }
                 }

                 if (cmd.equals("OPERATION")) {
                     User u=Server.getUserByIpAddress(socket.getInetAddress());
                    if (u==null) {
                        response="you need to create an account first";
                    } else {
                        
                        int index=Server.isConnected(u.getNom());
                        if (index!=-1) {
                            response=Server.formatListOfOps(u.getNom());
                        }
                        else {
                            response="you need to connect first";
                        }   
                    }
                 }

                if (cmd.startsWith("TRANSFERT")) {
                    User sender=Server.getUserByIpAddress(socket.getInetAddress());
                    if (sender==null) {
                         response="you need to create an account first";
                    } else {
                        
                        int indexSender=Server.isConnected(sender.getNom());
                        
                        String[] arr=cmd.substring(0,10).split(" ");
                    String recvUsername=arr[0];
                    float amount=Float.parseFloat(arr[1]);
                    if (indexSender!=-1) {
                            int rcvindex=Server.checkUser(recvUsername);
                            if (rcvindex !=-1) {
                                  Server.transfertTo(Server.checkUser(sender.getNom()),rcvindex, amount);
                                  response="operation done successfully";
                                } else {
                                    response=recvUsername+" doesn't have an account !";
                                }
                            }
                            else {
                                response="you need to connect first";
                            }
                        }
                }

                
                if (cmd.startsWith("HELP")) {
                       response=Server.displayHelp();
                }
                
                if (cmd.startsWith("DISCONNECT")) {
                    User u=Server.getUserByIpAddress(socket.getInetAddress());
                    if (u==null) {
                        response="you don't have an account !";
                    } else {
                        
                        int index=Server.isConnected(u.getNom());
                        if (index!=-1) {
                            Server.listOfConnected.remove(index);
                            response="successfully disconnected";
                        }
                        else {
                            response="you are not connected yet";
                        }   
                    }
                }

                if (response.isEmpty()) {
                     response=Server.displayHelp();
                }
                out=new PrintWriter(socket.getOutputStream(),true);
                out.println(response);
    
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
        //treatment



    }


}
