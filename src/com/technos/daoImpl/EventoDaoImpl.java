package com.technos.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;

import com.technos.dao.GenericDao;
import com.technos.dao.GenericDaoImpl;
import com.technos.domain.Evento;
import com.technos.domain.Pagamento;

public class EventoDaoImpl extends GenericDaoImpl<Evento, Integer> implements
		GenericDao<Evento, Integer> {

	public List<Pagamento> listarPagamentos(Evento evento) {

		TypedQuery<Pagamento> query = null;

		try {

			 query =  getEntityManager().createQuery(
				 		"SELECT p FROM Pagamento p WHERE p.evento = :e",
				 		Pagamento.class).setParameter("e", evento);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		return query.getResultList();
	}

}
