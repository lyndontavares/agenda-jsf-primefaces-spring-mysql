package com.technos.teste;

import com.technos.dao.Conexao;
import com.technos.daoImpl.AgendaDaoImpl;
import com.technos.domain.Agenda;
import com.technos.domain.Evento;

public class TesteQuery3 {

	public static void main(String[] args) {

		Conexao conexao = new Conexao();
		
		Agenda agenda = new Agenda();
		
		Evento evento = new Evento();
		
		evento.setId(2);
		
		agenda = new AgendaDaoImpl().getById( Agenda.class , evento.getId() );
		
		System.out.println(agenda);
		
		System.out.println( new AgendaDaoImpl().getById( Agenda.class , evento.getId() ).getEvento().getId()  );

		conexao.getEntityManager().close();
	}

}
