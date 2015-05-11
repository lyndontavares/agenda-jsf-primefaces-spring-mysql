package com.technos.grafico;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.PieChartModel;

import com.technos.daoImpl.ContatoDaoImpl;

@ManagedBean(name = "graficoAlunoPorIdade")
public class GraficoContatoPorIdade implements Serializable {

	private static final long serialVersionUID = 1L;

	private PieChartModel model;

	public PieChartModel getModel() {
		return model;
	}

	@PostConstruct
	public void init(){
		criarModel() ;
	}

	private void criarModel() {
		model = new PieChartModel();

		List<Object[]> resultList = new ContatoDaoImpl()
				.OpenQuery("  select extract(year from current_date() ) - extract(year from dataNascimento) idade , count(*) quantidade from tab_contato group by idade");

		for (Object[] e : resultList) {
			String tit = "Idade: " + e[0].toString() +" Quantidade: "+e[1].toString();
			model.set(tit, (Number) e[1]);
			
		}
		
		System.out.println(model.getData().size());
		
		if ( model.getData().size() == 0 ) {
			model.set("SEM INFORMAÇÕES", 100);
		}
		
	}

}
