package net.mips.compiler;

import java.io.*;
import java.util.*;

public class Scanner {
	List<Symboles> motsCles;
	Symboles symbCour;
	char carCour;
	FileReader fluxSour;
	
	//exists => methode permet de verifier si un fichier existe
	//read() => methode permet de lire un caractere
	//ready() => il nous rendre un booleen pour savoir si il y a des donnee a lire
	// on doit obligatoirement faire un throws a l'entete de la methode car c'est deux methodes peuvent lever une exception de typr IOException
	
	final char EOF = '0';
	
	
	public Scanner(String f) throws IOException, ErreurCompilation {
		File file= new File(f);
		if(!file.exists()) {
		    throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
		}
		this.fluxSour = new FileReader(file);
		this.motsCles = new ArrayList<Symboles>();
	}


	public List<Symboles> getMotsCles() {
		return motsCles;
	}


	public void setMotsCles(List<Symboles> motsCles) {
		this.motsCles = motsCles;
	}


	public Symboles getSymbCour() {
		return symbCour;
	}


	public void setSymbCour(Symboles symbCour) {
		this.symbCour = symbCour;
	}


	public char getCarCour() {
		return carCour;
	}


	public void setCarCour(char carCour) {
		this.carCour = carCour;
	}


	public FileReader getFluxSour() {
		return fluxSour;
	}


	public void setFluxSour(FileReader fluxSour) {
		this.fluxSour = fluxSour;
	}


	public void initMotsCles() {
		this.motsCles.add(new Symboles(Tokens.PROGRAM_Token, "program"));
		this.motsCles.add(new Symboles(Tokens.CONST_Token, "const"));
		this.motsCles.add(new Symboles(Tokens.VAR_Token, "var"));
		this.motsCles.add(new Symboles(Tokens.BEGIN_Token, "begin"));
		this.motsCles.add(new Symboles(Tokens.END_Token, "end"));
		this.motsCles.add(new Symboles(Tokens.IF_Token, "if"));
		this.motsCles.add(new Symboles(Tokens.THEN_Token, "then"));
		this.motsCles.add(new Symboles(Tokens.WHILE_Token, "while"));
		this.motsCles.add(new Symboles(Tokens.DO_Token, "do"));
		this.motsCles.add(new Symboles(Tokens.WRITE_Token, "write"));
		this.motsCles.add(new Symboles(Tokens.READ_Token, "read"));
	}
	
	public void codageLex() {
		String nom1 = symbCour.getNom();
		for(Symboles symb:motsCles) {
			String nom2 = symb.getNom();
			if(nom1.equalsIgnoreCase(nom2)) {
				symbCour.setToken(symb.getToken());
				return;
			}
		}
		symbCour.setToken(Tokens.ID_Token);
	}
	
	public void LireCar() throws IOException {
		if(this.fluxSour.ready()) {
			this.carCour = (char) this.fluxSour.read();
		}
		else {
			this.carCour = this.EOF;
		}
	}
	
	public void Lire_Mot() throws IOException {
		this.symbCour.setNom(this.symbCour.getNom() + this.carCour);
		LireCar();
		while(Character.isLetterOrDigit(carCour)) {
			this.symbCour.setNom(this.symbCour.getNom() + this.carCour);
			LireCar();
		}
		codageLex();
	}
	
	public void Lire_Nombre() throws IOException {
		this.symbCour.setNom(this.symbCour.getNom() + this.carCour);
		LireCar();
		while(Character.isDigit(carCour)) {
			this.symbCour.setNom(this.symbCour.getNom() + this.carCour);
			LireCar();
		}
		this.symbCour.setToken(Tokens.NUM_Token);
	}
	
	public void SymbSuiv() throws IOException, ErreurLexicale {
		while(Character.isWhitespace(carCour)) 
			LireCar();
		
		if(Character.isDigit(carCour)) {
			Lire_Nombre();
		}
		
		if(Character.isLetter(carCour)) {
			Lire_Mot();
		}
		
		switch(this.carCour) {
		case '+' : 
			 this.symbCour.setToken(Tokens.PLUS_Token);
		     LireCar();
		     break;
		case '-':
			 this.symbCour.setToken(Tokens.MOINS_Token);
		     LireCar();
		     break;
		case '*' :
			 this.symbCour.setToken(Tokens.MUL_Token);
		     LireCar();
		     break;
		case '/' :
			 this.symbCour.setToken(Tokens.DIV_Token);
		     LireCar();
		     break;
		case '=' : 
			 this.symbCour.setToken(Tokens.EG_Token);
		     LireCar();
		     break;
		case '(' : 
			 this.symbCour.setToken(Tokens.PARG_Token);
		     LireCar();
		     break;
		case ')' :
			 this.symbCour.setToken(Tokens.PARD_Token);
		     LireCar();
		     break;
		case '.' :
			 this.symbCour.setToken(Tokens.PNT_Token);
		     LireCar();
		     break;
		case ';' :
			 this.symbCour.setToken(Tokens.PVIR_Token);
		     LireCar();
		     break;
		case ',' :
			 this.symbCour.setToken(Tokens.VIR_Token);
		     LireCar();
		     break;
		case EOF :
			 this.symbCour.setToken(Tokens.EOF_Token);
		     LireCar();
		     break;     
		case ':' :
			LireCar();
			switch(carCour) {
			case '=':
				symbCour.setToken(Tokens.AFFEC_Token);
				LireCar();
				break;
			default:
				symbCour.setToken(Tokens.ERR_Token);
				throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
			}
			break;
		case '>':
			Lire_Mot();
			switch(carCour) {
			case '=':
				symbCour.setToken(Tokens.SUPEG_Token);
				Lire_Mot();
				break;
			default:
				symbCour.setToken(Tokens.SUP_Token);
				break;
			}
			break;
		case '<' :
			Lire_Mot();
			switch(carCour) {
			case '=':
				symbCour.setToken(Tokens.INFEG_Token);
				Lire_Mot();
				break;
			case '>':
				symbCour.setToken(Tokens.DIFF_Token);
				Lire_Mot();
				break;
			default:
				symbCour.setToken(Tokens.INF_Token);
				break;
			}
			break;
		default:
			throw new ErreurLexicale(CodesErr.CAR_INC_ERR);	
		}
	}
	
}
