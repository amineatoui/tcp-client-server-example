package models;

import java.util.Set;

public class Compte {
    

    public static int id=1;
    private float solde;

 
    public void setSolde(float solde) {
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public float getSolde() {
        return solde;
    }

    public Compte(){
         id++; 
    }
}
