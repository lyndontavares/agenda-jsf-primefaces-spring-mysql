package com.technos.teste;

import javax.persistence.PersistenceException;

import com.technos.dao.Conexao;

public class TesteQuery4 {
public static void main(String[] args) {
	
	Conexao conexao = new Conexao();
 	
	try{
	conexao.getEntityManager().getTransaction().begin();
	conexao.getEntityManager().createNativeQuery("insert into tab_contato (nome,telefone,email,sexo,situacao) values ('er','','',1,0)").executeUpdate();
	conexao.getEntityManager().getTransaction().commit();
	}
	catch ( PersistenceException e)
	{
		conexao.getEntityManager().getTransaction().rollback();
		e.printStackTrace();
	}
	conexao.getEntityManager().clear();
	conexao.getEntityManager().close();
	
}
}
 