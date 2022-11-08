package net.mips.compiler;

public class Symboles {
     Tokens token;
     String nom;
     
     
     public Symboles(Tokens t, String n) {
    	 this.token = t;
    	 this.nom = n;
     }
     
     public Tokens getToken() {
    	 return this.token;
     }
     
     public void setToken(Tokens t) {
    	 this.token = t;
     }
     
     public String getNom() {
    	 return this.nom;
     }
     
     public void setNom(String n) {
    	 this.nom = n;
     }
     
}
