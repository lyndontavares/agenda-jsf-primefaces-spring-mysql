package com.technos.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Lyndon Tavares
 *
 */
public class Data {
	
	
	/**
	 * 
	 * Método para somar dias a uma data.
	 * 
	 * @param data 
	 * @param dias
	 * @return data
	 */
	public static Date somaDias( Date data, int dias){
		//1000 * 24 * 60 * 60 = 86400000L
		return new Date(  data.getTime() + ((86400000L) * dias )  );
	}

	/**
	 * 
	 * Método para retornar quantodade de dias entre datas
	 * 
	 * @param d1
	 * @param d2
	 * @return quantidade de dias
	 */
	public static int diasEntreDatas( Date d1, Date d2){
		return  (int) ((d1.getTime() - d2.getTime()) / 86400000L);
	}
	

	/**
	 * 
	 * Método para retornar próximo mês
	 * 
	 * @param data
	 * @return data do próximo mês
	 */
	public static Date proximoMes(Date nowDate){
		    Calendar c = Calendar.getInstance();
		    c.setTime(nowDate);
		    c.add(Calendar.MONTH, 1);
		    c.set(Calendar.DATE, c.getMaximum(Calendar.DATE));
		    return c.getTime();
		 
	}
	
	/*
	 
	 { // Corpo da classe base, ou classe pai, ou superclasse "Data"
 
		// atributos privados (private) da classe "Data", portanto, não poderão ser
		// referenciados nas subclasses e nas instâncias ou objetos.
		  private int dia, mes, ano;
		 
		// método construtor
		  public Data(int d, int m, int a) {
		    dia = d;
		    mes = m;
		    ano = a;
		  }
		 
		// sobrepondo (polimorfismo de sobrecarga) o método construtor
		// assinaturas diferentes (quantidade de parâmetros)
		  public Data() {
		    java.util.Date hoje = new java.util.Date();
		    dia = hoje.getDate();
		    mes = hoje.getMonth() + 1; // retorna um valor de mes entre 0..11
		    ano = hoje.getYear() + 1900;
		  }
		 
		// métodos implementados para retornar o valor dos atributos privados
		  public int getDia() {
		    return(dia);
		  }
		 
		  public int getMes() {
		    return(mes);
		  }
		 
		  public int getAno() {
		    return(ano);
		  }
		 
		// visibilidade "protected" = classe e subclasses
		  protected boolean anoBissexto(int a) {
		    if ((a % 4) != 0)
		       return(false);
		    else if ((a % 100) != 0)
		       return(true);
		    else if ((a % 400) != 0)
		       return(false);
		    else
		       return(true);
		  }
		 
		  protected int diasNoMes(int m) {
		    switch (m) {
		      case 1:
		      case 3:
		      case 5:
		      case 7:
		      case 8:
		      case 10:
		      case 12: return(31);
		 
		      case 4:
		      case 6:
		      case 9:
		      case 11: return(30);
		 
		      default: if (anoBissexto(ano) == true)
		                  return(29);
		               else return(28);
		    }
		  }
		 
		// visibilidade "public" = classe, subclasse e instância (ou objeto)
		  public void incrementa(int n) {
		    for (int i=1; i<=n; i++)
		      if (dia < diasNoMes(mes))
		         dia++;
		     else if (mes < 12) { // se for o último dia do mês,
		             mes++;       // então, primeiro dia do mês seguinte.
		             dia = 1;
		          }
		          else {      // último dia do ano, portanto,
		            dia = 1;  // atribui o primeiro dia
		            mes = 1;  // do ano seguinte
		            ano++;
		          }
		  }
		 
		  public void decrementa(int n) {
		    for (int i=1; i<=n; i++)
		      if (dia > 1)
		         dia--;
		      else if (mes > 1) { // se for o primeiro dia do mês,
		              mes--;      // então, último dia do mês anterior.
		              dia = diasNoMes(mes);
		           }
		         else {        // primeiro dia do ano, portanto,
		           dia = 31;   // atribui o último dia do ano anterior.
		           mes = 12;
		           ano--;
		         }
		  }
		 
		  public String imprimeData() {
		    return(dia + "/" + mes + "/" + ano);
		  }
		 
		} // Fim do corpo da superclasse "Data"
		 
		 
		// implementando o mecanismo de herança = a nova classe "NovaData"
		// estende (extends) a classe base "Data"
		class NovaData extends Data
		{ // Corpo da classe derivada, ou da classe filha, ou subclasse "NovaData".
		 
		// visibilidade "private" = classe
		  private int totalDeDias() {
		    int ttAnos = (getAno() - 1) * 365;
		    for (int i=1; i<=(getAno()-1); i++) // Número de anos bissextos até a data.
		      if (anoBissexto(i) == true)
		          ttAnos++;
		 
		    int ttMeses = 0;
		    for (int i=1; i<=(getMes()-1); i++)
		      ttMeses += diasNoMes(i);
		 
		    return(ttAnos + ttMeses + getDia());
		  }
		 
		  public String diaDaSemana() {
		    String nomeDiaDaSemana[] = {"Domingo", "Segunda-Feira", "Terça-Feira",
		      "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sábado"};
		    return(nomeDiaDaSemana[totalDeDias() % 7]);
		  }
		} // Fim do corpo da subclasse "NovaData".
			 
			 
	  
	 */
	
}
