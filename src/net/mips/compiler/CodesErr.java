package net.mips.compiler;

public enum CodesErr {
      
	   CAR_INC_ERR("Symbole inconnu") ,
	   FIC_VID_ERR("Erreur d'ouverture du fichier"),
	   ID_ERR("ID erruer"),
	   NUM_ERR("Num erreur"),
	   PLUS_ERR("Plus erreur"),
	   MOINS_ERR("Moins erreur"),
	   MUL_ERR("Multiplication erreur"),
	   DIV_ERR("Division erreur"),
	   EG_ERR("Egale erreur"),
	   DIFF_ERR("diffenrence erreur"),
	   INF_ERR("inferieur erreur"),
	   SUP_ERR("superieur erreur"),
	   INFEG_ERR("inferieur ou egale erreur"),
	   SUPEG_ERR("superieur ou egale erreur"),
	   PARG_ERR("parantese gauche erreur"),
	   PARD_ERR("parantese droite erreur"),
       VIR_ERR("virgule erreur"),
       PVIR_ERR ("point virgule erreur"),
       PNT_ERR("point erreur"),
       AFFEC_ERR("affectation erreur"),
       BEGIN_ERR("begin erreur"),
       END_ERR("end erreur"),
       IF_ERR("if erreur"),
       WHILE_ERR("while erreur"),
       THEN_ERR("then erreur"),
       DO_ERR("do erreur"),
       WRITE_ERR("write erreur"),
       READ_ERR("read erreur"),
       CONST_ERR("const erreur"),
       VAR_ERR("var erreur"),
       PROGRAM_ERR("program erreur"),
       EOF_ERR("EOF erreur"),
       ERR_ERR("ERREUR erreur");
	   
	
	   String Message;
	   
	   private CodesErr(String Message) {
		   this.Message = Message;
	   }
	   
	   public String getMessage() {
		   return this.Message;
	   }
	   
	   public void setMessage(String m) {
		   this.Message = m;
	   }
	
}
