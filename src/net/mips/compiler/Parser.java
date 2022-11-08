package net.mips.compiler;

import java.io.IOException;

public class Parser {
	Scanner scanner;

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public Parser(String file) throws IOException, ErreurCompilation {
		this.scanner = new Scanner(file);
	}
	
	public void testAccept(Tokens t, CodesErr c) throws ErreurLexicale, IOException {
		
		if(this.scanner.symbCour.token.equals(t)) {
			this.scanner.SymbSuiv();
		}
		else {
			Erreur(c);
		}	
	}
	
	public void Erreur(CodesErr c) {
		ErreurSyntaxique e = new ErreurSyntaxique(c.Message);
	}
	
	public void consts() throws ErreurLexicale, IOException {
		if(this.scanner.symbCour.token.equals(Tokens.CONST_Token)) {
			testAccept(Tokens.CONST_Token, CodesErr.CONST_ERR);
			while(this.scanner.symbCour.token.equals(Tokens.ID_Token)){
			testAccept(Tokens.ID_Token, CodesErr.ID_ERR);
			testAccept(Tokens.EG_Token, CodesErr.EG_ERR);
			testAccept(Tokens.NUM_Token, CodesErr.NUM_ERR);
			testAccept(Tokens.PVIR_Token, CodesErr.PVIR_ERR);
		}
	}
}
	
	public void vars() throws ErreurLexicale, IOException {
		if(this.scanner.symbCour.token.equals(Tokens.VAR_Token)) {
			testAccept(Tokens.VAR_Token, CodesErr.VAR_ERR);	
			testAccept(Tokens.ID_Token, CodesErr.ID_ERR);
			while(this.scanner.symbCour.token.equals(Tokens.VIR_Token)) {
				testAccept(Tokens.VIR_Token, CodesErr.VIR_ERR);
				testAccept(Tokens.ID_Token, CodesErr.ID_ERR);
			}
		   testAccept(Tokens.PVIR_Token, CodesErr.PVIR_ERR);
		}
}
	
	public void INSTS() throws ErreurLexicale, IOException  {
		testAccept(Tokens.BEGIN_Token, CodesErr.BEGIN_ERR);
		INST();
		while(this.scanner.symbCour.equals(Tokens.PVIR_Token)) {
			testAccept(Tokens.PVIR_Token, CodesErr.PVIR_ERR);
			INST();
		}
		testAccept(Tokens.END_Token, CodesErr.END_ERR);
	}
	
	public void AFFEC() throws ErreurLexicale, IOException {
		testAccept(Tokens.ID_Token, CodesErr.ID_ERR);
		testAccept(Tokens.AFFEC_Token, CodesErr.AFFEC_ERR);
		EXPR();
	}
	
	public void SI() throws ErreurLexicale, IOException {
		testAccept(Tokens.IF_Token, CodesErr.AFFEC_ERR.IF_ERR);
		COND();
		testAccept(Tokens.THEN_Token, CodesErr.THEN_ERR);
		INST();
	}
	
	public void TANTQUE() throws ErreurLexicale, IOException{
		testAccept(Tokens.WHILE_Token, CodesErr.WHILE_ERR);
		COND();
		testAccept(Tokens.DO_Token, CodesErr.DO_ERR);
		INST();
	}
	
	public void ECRIRE() throws ErreurLexicale, IOException{
		testAccept(Tokens.WRITE_Token, CodesErr.WRITE_ERR);
		testAccept(Tokens.PARG_Token, CodesErr.PARG_ERR);
		EXPR();
		while(this.scanner.symbCour.equals(Tokens.VIR_Token)) {
			testAccept(Tokens.VIR_Token, CodesErr.VIR_ERR);
			EXPR();
		}
		testAccept(Tokens.PARD_Token, CodesErr.PARD_ERR);
	}
	
	public void LIRE() throws ErreurLexicale, IOException {
		testAccept(Tokens.READ_Token, CodesErr.READ_ERR);
		testAccept(Tokens.PARG_Token, CodesErr.PARG_ERR);
		testAccept(Tokens.ID_Token, CodesErr.ID_ERR);
		while(this.scanner.symbCour.equals(Tokens.VIR_Token)) {
			testAccept(Tokens.VIR_Token, CodesErr.VIR_ERR);
		}
		testAccept(Tokens.PARD_Token, CodesErr.PARD_ERR);
	}
	
	public void COND() throws ErreurLexicale, IOException {
		EXPR();
		RELOP();
		EXPR();
	}
	
	public void RELOP() throws ErreurLexicale, IOException {
		switch(this.scanner.symbCour.token) {
		case EG_Token : 
		     testAccept(Tokens.EG_Token, CodesErr.EG_ERR);
		     break;
		case PARG_Token : 
			testAccept(Tokens.DIFF_Token, CodesErr.DIFF_ERR);
			break;
		case INF_Token : 
			testAccept(Tokens.INF_Token, CodesErr.INF_ERR);
			break;
		case SUP_Token: 
			testAccept(Tokens.SUP_Token, CodesErr.SUP_ERR);
			break;
		case INFEG_Token :
			testAccept(Tokens.INFEG_Token, CodesErr.INFEG_ERR);
			break;
		case SUPEG_Token : 
			testAccept(Tokens.SUPEG_Token, CodesErr.SUPEG_ERR);
			break;
		default:
			Erreur(CodesErr.ERR_ERR);
		}
	}
	
	public void EXPR() throws ErreurLexicale, IOException {
		TERM();
		while(this.scanner.symbCour.token.equals(Tokens.PLUS_Token) || this.scanner.symbCour.token.equals(Tokens.MOINS_Token)) {
			ADDOP();
			TERM();
		}
	}
	
	public void ADDOP() throws ErreurLexicale, IOException {
		switch(this.scanner.symbCour.token) {
		case PLUS_Token : 
			testAccept(Tokens.PLUS_Token, CodesErr.PLUS_ERR);
			break;
		case MOINS_Token : 
			testAccept(Tokens.MOINS_Token, CodesErr.MOINS_ERR);
			break;
		default:
			Erreur(CodesErr.ERR_ERR);
		}
	}
	
	public void MULOP() throws ErreurLexicale, IOException {
		switch(this.scanner.symbCour.token) {
		case MUL_Token : 
			testAccept(Tokens.MUL_Token, CodesErr.MUL_ERR);
			break;
		case DIV_Token : 
			testAccept(Tokens.DIV_Token, CodesErr.DIV_ERR);
			break;
		default:
			Erreur(CodesErr.ERR_ERR);
		}
	}
	
	public void TERM() throws ErreurLexicale, IOException {
		FACT();
		while(this.scanner.symbCour.token.equals(Tokens.MUL_Token) || this.scanner.symbCour.token.equals(Tokens.DIV_Token)) {
			MULOP();
			FACT();
		}
	}
	
	public void FACT() throws ErreurLexicale, IOException {
		switch(this.scanner.symbCour.token) {
		case ID_Token :
			testAccept(Tokens.ID_Token, CodesErr.ID_ERR);
		case NUM_Token : 
			testAccept(Tokens.NUM_Token, CodesErr.NUM_ERR);
		case PARG_Token :
			testAccept(Tokens.PARG_Token, CodesErr.PARG_ERR);
			EXPR();
			testAccept(Tokens.PARD_Token, CodesErr.PARD_ERR);
		default:
			Erreur(CodesErr.ERR_ERR);
		}
		
	}
	
	public void INST() throws ErreurLexicale, IOException {
		switch(this.scanner.symbCour.token) {
		case ID_Token :
			AFFEC();
			break;
		case BEGIN_Token : 
			INSTS();
			break;
		case IF_Token: 
			SI();
			break;
		case WHILE_Token : 
			TANTQUE();
			break;
		case WRITE_Token : 
			ECRIRE();
			break;
		case READ_Token : 
			LIRE();
			break;
		}
	}
	
	public void block() throws ErreurLexicale, IOException {
		consts();
		vars();
		INSTS();
	}
		
	public void Program() throws ErreurLexicale, IOException {
		testAccept(Tokens.PROGRAM_Token, CodesErr.PROGRAM_ERR);
		testAccept(Tokens.ID_Token, CodesErr.ID_ERR);
		testAccept(Tokens.PVIR_Token, CodesErr.PVIR_ERR);
		block();
		testAccept(Tokens.PNT_Token, CodesErr.PNT_ERR);
	}
	
}
