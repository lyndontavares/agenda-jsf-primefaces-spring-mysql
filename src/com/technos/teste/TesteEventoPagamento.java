package com.technos.teste;

 

import java.math.BigDecimal;
import java.util.Date;

import com.technos.daoImpl.EventoDaoImpl;
import com.technos.domain.Contato;
import com.technos.domain.Evento;
import com.technos.domain.Pagamento;

public class TesteEventoPagamento {
public static void main(String[] args) {
	
	 EventoDaoImpl dao = new EventoDaoImpl();
	 
	 Contato c = new Contato();
	 c.setId(1);
	 
	 Evento e = new Evento();
	 e.setContato(c);
	 e.setDataInicio(new Date());
	 e.setNome("teste");
	 e.setValor(  BigDecimal.valueOf( 1000.0 ) );
	 e.setSituacao(0);
	 
	 dao.getEntityManager().getTransaction().begin();
 	 e = dao.getEntityManager().merge(e);
	 
	 for(int i=1; i <= 10; i++) {
	        Pagamento p = new Pagamento();
	        p.setDataVencimento(new Date());
	        p.setNome("nome "+i);
	        p.setEvento(e);
	        p.setValor(BigDecimal.valueOf(100));
	        p.setSituacao(0);
	        dao.getEntityManager().merge(p);
	    }
	    dao.getEntityManager().getTransaction().commit();
	    dao.getEntityManager().clear();
	    dao.getEntityManager().close();
}
}
