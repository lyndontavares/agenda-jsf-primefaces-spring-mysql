package com.technos.bo;

import java.util.List;

import com.technos.daoImpl.AgendaDaoImpl;
import com.technos.domain.Agenda;


public class AgendaBO{
    
    private List<Agenda> listaAgenda;
    
    public AgendaBO(){
    	setListaAgenda((List<Agenda>) new AgendaDaoImpl().getAll(Agenda.class));
    }
    
    public List<Agenda> buscarTodasAgendas(){
        return new AgendaDaoImpl().getAll(Agenda.class);
    }
    
    public void adicionarAgenda(Agenda agenda){
    	AgendaDaoImpl nova = new AgendaDaoImpl();
		nova.save(agenda);
		setListaAgenda(new AgendaDaoImpl().getAll(Agenda.class));
    }
    
    public void removerAvenda(Agenda agenda){
    	AgendaDaoImpl nova = new AgendaDaoImpl();
		nova.remove(agenda);
		setListaAgenda(new AgendaDaoImpl().getAll(Agenda.class));
    }

	public List<Agenda> getListaAgenda() {
		return listaAgenda;
	}

	public void setListaAgenda(List<Agenda> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}
}