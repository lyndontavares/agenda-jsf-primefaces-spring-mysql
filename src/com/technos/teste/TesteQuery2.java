package com.technos.teste;

import javax.persistence.TypedQuery;

import com.technos.dao.Conexao;
import com.technos.domain.Evento;

public class TesteQuery2 {

	public static void main(String[] args) {

		TypedQuery<Evento> query = null;

		Conexao conexao = new Conexao();
		
		query = conexao.getEntityManager().createQuery(
				"SELECT p FROM Evento p ", Evento.class);

		for (Evento p : query.getResultList()) {

			System.out.println(p);
		}

		conexao.getEntityManager().close();
	}

}
