package com.technos.grafico;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.PieChartModel;

import com.technos.daoImpl.ContatoDaoImpl;

@ManagedBean(name = "graficoAlunoPorSexo")
public class GraficoContatoPorSexo implements Serializable {

	private static final long serialVersionUID = 1L;

	private PieChartModel model;

	public PieChartModel getModel() {
		return model;
	}

	@PostConstruct
	public void init(){
		criarModel();
	}
	
	private void criarModel() {
		model = new PieChartModel();

		List<Object[]> resultList = new ContatoDaoImpl()
				.OpenQuery(" SELECT case e.sexo when 1 then 'Homens' when 2 then 'Mulheres' else 'N/A' end sexo , sum(e.sexo) from tab_contato e group by e.sexo ");

		 
		for (Object[] e : resultList) {
			String tit = e[0].toString() +": "+e[1].toString();
			model.set(tit , (Number) e[1]);
			
		}
		
		if ( model.getData().size() == 0 ) {
			model.set("SEM INFORMAÇÕES", 100);
		}

		
	}

}
