package com.technos.teste;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.technos.dao.Conexao;

public class TesteStoreProcedure {
public static void main(String[] args) {

	Conexao conexao = new Conexao();
	
	//store procedures
	StoredProcedureQuery storedProcedure = conexao.getEntityManager().createStoredProcedureQuery("contato_cancelar");
	storedProcedure.registerStoredProcedureParameter("user", String.class, ParameterMode.IN);
	storedProcedure.registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN);
	storedProcedure.registerStoredProcedureParameter("msg", String.class, ParameterMode.INOUT);
	
	storedProcedure.setParameter("user", "123");
	storedProcedure.setParameter("id", 1 );
	storedProcedure.setParameter("msg", "" );
 	storedProcedure.execute();
 	System.out.println(storedProcedure.getOutputParameterValue("msg"));
    //Double tax = (Double)storedProcedure.getOutputParameterValue("tax");

}
}
