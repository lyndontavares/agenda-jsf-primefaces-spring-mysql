package com.technos.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.technos.bo.AgendaBO;
import com.technos.daoImpl.AgendaDaoImpl;
import com.technos.daoImpl.EventoDaoImpl;
import com.technos.domain.Agenda;
import com.technos.domain.Evento;
import com.technos.enums.TipoAgenda;

@ManagedBean(name = "agendaForm")
@ViewScoped
public class AgendaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Agenda agenda;

	private TipoAgenda tipoAgenda;

	private ScheduleModel eventModel;

	private List<Agenda> listaAgenda;

	private AgendaBO agendaBO;

	// Eventos relacionados ao Contato
	private int eventoSelecionado;

	private List<Evento> listaEvento;

	@PostConstruct
	private void inicializar() {

		agenda = new Agenda();
		agendaBO = new AgendaBO();

		eventModel = new DefaultScheduleModel();
		tipoAgenda = TipoAgenda.PESSOAL;

		// recupera a lista de eventos
		listaAgenda = agendaBO.buscarTodasAgendas();

		// percorre a lista de eventos e popula o calendario
		for (Agenda agenda : listaAgenda) {

			DefaultScheduleEvent evento = new DefaultScheduleEvent();
			evento.setAllDay(agenda.isDiaTodo());
			evento.setEndDate(agenda.getDataFim());
			evento.setStartDate(agenda.getDataInicio());
			evento.setTitle(agenda.getDescricao());
			evento.setData(agenda.getId());
			evento.setEditable(true);

			// aqui e setado um tipo especifico de css para cada tipo de evento
			if (agenda.getTipoAgenda().equals(TipoAgenda.GLOBAL)) {
				evento.setStyleClass("global");
			} else {
				evento.setStyleClass("pessoal");
			}

			eventModel.addEvent(evento); // o evento e adicionado na lista
		}
	}

	/**
	 * Autocompletar evento
	 * 
	 * @param query
	 * @return
	 */

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 0; i < 10; i++) {
            results.add(query + i);
        }
         
        return results;
    }
	
	/**
	 * Metodo para salvar o evento
	 */
	public void salvar() {

		if (agenda.getDataInicio().getTime() <= agenda.getDataFim().getTime()) {

			if (eventoSelecionado != 0) {
				agenda.setEvento(new EventoDaoImpl().getById(Evento.class,
						eventoSelecionado));
				eventoSelecionado = 0;
			}

			agenda.setTipoAgenda(tipoAgenda);
			agendaBO.adicionarAgenda(agenda);
			agenda = new Agenda();

			inicializar();

		} else {
			FacesMessage msg = new FacesMessage(
					"Data de fim deve ser maior ou iguala data de início");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	/**
	 * Evento para quando o usuario clica em um espaco em branco no calendario
	 * 
	 * @param selectEvent
	 */
	public void quandoNovo(SelectEvent selectEvent) {

		ScheduleEvent event = new DefaultScheduleEvent("",
				(Date) selectEvent.getObject(), (Date) selectEvent.getObject());

		agenda = new Agenda();
		eventoSelecionado = 0;
		// recupero a data em q ele clicou
		agenda.setDataInicio(event.getStartDate());
		agenda.setDataFim(event.getEndDate());
	}

	/**
	 * Evento para quando usuario clica em um enveto ja existente
	 * 
	 * @param selectEvent
	 */
	public void quandoSelecionado(SelectEvent selectEvent) {

		ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();

		for (Agenda agda : listaAgenda) {
			if (agda.getId() == (Integer) event.getData()) {
				agenda = agda;

				// teste
				System.out.println(agda.getId());
				Agenda ag = new AgendaDaoImpl().getById(Agenda.class,
						agda.getId());
				System.out.println(ag.getEvento());
				System.out.println("eventoselecdionado: " + eventoSelecionado);

				agenda.setEvento((Evento) new AgendaDaoImpl().getById(
						Agenda.class, agda.getId()).getEvento());
				break;
			}
		}
	}

	/**
	 * Evento para quando o usuario 'move' um evento atraves de 'drag and drop'
	 * 
	 * @param event
	 */
	public void quandoMovido(ScheduleEntryMoveEvent event) {

		for (Agenda agda : listaAgenda) {

			if (agda.getId() == (Integer) event.getScheduleEvent().getData()) {
				agenda = agda;
				agenda.setEvento((Evento) new AgendaDaoImpl().getById(
						Agenda.class, agda.getId()).getEvento());

				// salvar sua agenda no banco
				if (agenda.getDataInicio().getTime() <= agenda.getDataFim()
						.getTime()) {
					agendaBO.adicionarAgenda(agenda);
					listaAgenda = agendaBO.buscarTodasAgendas();
				} else {
					FacesMessage msg = new FacesMessage(
							"Data de fim deve ser maior ou iguala data de início");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

				break;
			}
		}
	}

	/**
	 * Evento para quando o usuario 'redimenciona' um evento
	 * 
	 * @param event
	 */
	public void quandoRedimensionado(ScheduleEntryResizeEvent event) {

		for (Agenda agda : listaAgenda) {
			if (agda.getId() == (Integer) event.getScheduleEvent().getData()) {
				agenda = agda;
				agenda.setEvento((Evento) new AgendaDaoImpl().getById(
						Agenda.class, agda.getId()).getEvento());
				// salvar sua agenda no banco
				if (agenda.getDataInicio().getTime() <= agenda.getDataFim()
						.getTime()) {
					agenda.setEvento(new EventoDaoImpl().getById(Evento.class,
							Integer.valueOf(eventoSelecionado)));
					agendaBO.adicionarAgenda(agenda);
					listaAgenda = agendaBO.buscarTodasAgendas();
				} else {
					FacesMessage msg = new FacesMessage(
							"Data de fim deve ser maior ou iguala data de início");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
				break;
			}
		}
	}

	// getters and setters
	public List<Agenda> getListaAgenda() {
		return listaAgenda;
	}

	public void setListaAgenda(List<Agenda> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public TipoAgenda getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(TipoAgenda tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	public List<TipoAgenda> getTiposAgenda() {
		return Arrays.asList(TipoAgenda.values());
	}

	public int getEventoSelecionado() {
		return eventoSelecionado;
	}

	public void setEventoSelecionado(int eventoSelecionado) {
		this.eventoSelecionado = eventoSelecionado;
	}

	public List<Evento> getListaEvento() {
		if (listaEvento == null) {
			listaEvento = new EventoDaoImpl().getAll(Evento.class);
		}
		return listaEvento;
	}

	public void setListaEvento(List<Evento> listaEvento) {
		this.listaEvento = listaEvento;
	}

}