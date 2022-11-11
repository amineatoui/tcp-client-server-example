package models;

import java.net.InetAddress;

public class User{
 
       private int id;
       private String nom;
       private Compte compte;
       private InetAddress ipAddress;


       public InetAddress getIpAddress() {
           return ipAddress;
       }

       public void setIpAddress(InetAddress ipAddress) {
           this.ipAddress = ipAddress;
       }

       public void setCompte(Compte compte) {
           this.compte = compte;
       }

       public void setId(int id) {
           this.id = id;
       }

       public void setNom(String nom) {
           this.nom = nom;
       }

       public Compte getCompte() {
           return compte;
       }
       public int getId() {
           return id;
       }
       public String getNom() {
           return nom;
       }

       public User(){
        
       }
     

}