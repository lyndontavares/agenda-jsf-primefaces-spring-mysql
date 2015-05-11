package com.technos.teste;

import com.technos.dao.Conexao;

public class CriarRegras {

	public static void main(String[] args) {
		
		Conexao conexao = new Conexao();

		conexao.getEntityManager().getTransaction().begin();

		//LIMPAR TABELAS
		conexao.getEntityManager().createNativeQuery("delete from tab_permissao").executeUpdate();;
		conexao.getEntityManager().createNativeQuery("delete from tab_funcionalidade").executeUpdate();
		conexao.getEntityManager().createNativeQuery("delete from user_auth").executeUpdate();;
		conexao.getEntityManager().createNativeQuery("delete from authority").executeUpdate();;
		conexao.getEntityManager().createNativeQuery("delete from user").executeUpdate();;
		
		//inserir registros dando permisao ao usuario admin
		int i=1;
		String[] listaFuncionalidades = {
				
				    "Incluir Contato",
				    "Editar Contato",
					"Confirmar Contato",
					"Cancelar Contato",
					"Expurgar Contatos Cancelado",
	
					"Incluir Evento",
				    "Editar Evento",
					"Confirmar Evento",
					"Realizar Evento",
					"Cancelar Evento",
					"Expurgar Evento Cancelado",
					"Quitar Evento",

					"Gerar Parcela Evento",
				    "Incluir Parcela Evento",
				    "Editar Parcela Evento",

					"Quitar Parcela Evento",
					"Cancelar Parcela Evento",
					"Expugar Parcela Evento",
				
				    "Tipo Contato Incluir",
				    "Tipo Contato Editar",
					"Tipo Contato Ativar",
					"Tipo Contato Inativar",
					"Tipo Contato Cancelar",
					"Tipo Contato Expurgar",

				    "Usuario Incluir",
				    "Usuario Editar",
					"Usuario Ativar",
					"Usuario Inativar",
					"Usuario Cancelar",
					"Usuario Expurgar"
				};
		
		for ( String f : listaFuncionalidades ){
			
			conexao.getEntityManager().createNativeQuery("insert into tab_funcionalidade (id, nome) values (:id, :nome)")
			.setParameter("nome", f)
			.setParameter("id", i++).executeUpdate();
			
		}

		conexao.getEntityManager().createNativeQuery("insert into user (username,enable,password) values (:u,:e,:p)").
		setParameter("u", "123").
		setParameter("e", 1).
		setParameter("p", "123").
		executeUpdate();
		
		conexao.getEntityManager().createNativeQuery("insert into authority values ( :r )").
		setParameter("r", "ROLE_USER").
		executeUpdate();
		
		conexao.getEntityManager().createNativeQuery("insert into user_auth  values  ( :u, :r )").
		setParameter("u", "123").
		setParameter("r", "ROLE_USER").
		executeUpdate();
		
		//inserir permissoes para usuario 123
		for (int j=1;j<=listaFuncionalidades.length;j++ ){
			conexao.getEntityManager().createNativeQuery("insert into tab_permissao  values  ( :u, :f )").
			setParameter("u", "123").
			setParameter("f", j).
			executeUpdate();
		}
		
		conexao.getEntityManager().getTransaction().commit();
		conexao.getEntityManager().close();
		
		
	}
	
}
