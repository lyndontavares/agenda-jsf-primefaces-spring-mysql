package com.technos.teste;

import javax.persistence.TypedQuery;

import com.technos.dao.Conexao;
import com.technos.domain.Evento;
import com.technos.domain.Pagamento;

public class TesteQuery {

	public static void main(String[] args) {

		TypedQuery<Pagamento> query = null;

		Conexao conexao = new Conexao();

		Evento evento = new Evento();

		evento.setId(1);


		 query = conexao.getEntityManager().createQuery(
			 		"SELECT p FROM Pagamento p WHERE p.evento = :e",
			 		Pagamento.class).setParameter("e", evento);
		


		for (Pagamento p : query.getResultList()) {

			System.out.println( p);
		}

		
		conexao.getEntityManager().close();
	}

}
