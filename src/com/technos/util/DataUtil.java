package com.technos.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.GenericValidator;

/**
 * Classe utilitária para manipulações com data/hora.
 * 
 * @author João Lúcio
 * @since 27/10/2008
 */
public final class DataUtil {

	/** {@link Locale} pt-BR */
	public static final Locale LOCALE_BR = new Locale("pt", "BR");
	/** Unidade que representa o segundo */
	public static final int SEGUNDO = 1;
	/** Unidade que representa o minuto */
	public static final int MINUTO = 2;
	/** Unidade que representa a hora */
	public static final int HORA = 3;
	/** Unidade que representa o dia */
	public static final int DIA = 4;
	/** Unidade que representa o mês */
	public static final int MES = 5;
	/** Unidade que representa o ano */
	public static final int ANO = 6;
	/** Data no formato dd/MM/yyyy */
	public static final DateFormat DATA_MEDIUM = DateFormat.getDateInstance(
			DateFormat.MEDIUM, LOCALE_BR);
	/** Data-hora no formato dd/MM/yyyy HH:mm:ss */
	public static final DateFormat DATA_HORA_MEDIUM = DateFormat
			.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,
					LOCALE_BR);
	/** Hora no formato HH:mm:ss */
	public static final DateFormat HORA_MEDIUM = DateFormat.getTimeInstance(
			DateFormat.MEDIUM, LOCALE_BR);
	/** Data no formato Quinta, 1 de Janeiro de 2009 */
	public static final DateFormat DATA_FULL = DateFormat.getDateInstance(
			DateFormat.FULL, LOCALE_BR);

	/**
	 * Construtor privado (classe utilitária).
	 */
	private DataUtil() {
	}

	/**
	 * Adiciona a data original o valor informado
	 * 
	 * @param tipo
	 *            tipo a adicionar: {@link #SEGUNDO}, {@link #MINUTO},
	 *            {@link #HORA}, {@link #DIA}, {@link #MES}, {@link #ANO}
	 * @param valor
	 *            valor a adicionar
	 * @param data
	 *            data inicial
	 * @return data com os valores adicionados
	 */
	public static Date adicionar(int tipo, int valor, Date data) {
		Calendar data2 = Calendar.getInstance(LOCALE_BR);
		data2.setTime(data);

		if (tipo == SEGUNDO) {
			data2.add(Calendar.SECOND, valor);
		} else if (tipo == MINUTO) {
			data2.add(Calendar.MINUTE, valor);
		} else if (tipo == HORA) {
			data2.add(Calendar.HOUR_OF_DAY, valor);
		} else if (tipo == DIA) {
			data2.add(Calendar.DAY_OF_MONTH, valor);
		} else if (tipo == MES) {
			data2.add(Calendar.MONTH, valor);
		} else if (tipo == ANO) {
			data2.add(Calendar.YEAR, valor);
		}
		return data2.getTime();
	}

	/**
	 * Recupera um {@link java.util.Date} da data do final do mês
	 * 
	 * @param data
	 *            data que deseja ver o último dia do mês
	 * @return data com o último dia do mês
	 */
	public static Date calculaDataFimMes(Date data) {
		Date dt = calculaDataInicioMesSeguinte(data);
		return adicionar(DIA, -1, dt);
	}

	/**
	 * Recupera um {@link java.util.Date} da data do final do próximo mês
	 * 
	 * @return data do final do próximo mês
	 */
	public static Date calculaDataFimMesSeguinte() {
		Date data = new Date();
		data = adicionar(MES, 1, data);
		return calculaDataFimMes(data);
	}

	/**
	 * Recupera o primeiro dia do próximo mês da data informada
	 * 
	 * @param data
	 *            data informada
	 * @return nova data
	 */
	public static Date calculaDataInicioMesSeguinte(Date data) {
		Calendar data2 = Calendar.getInstance();
		data2.setTime(data);
		data2.set(Calendar.DAY_OF_MONTH, 1);
		data2.add(Calendar.MONTH, 1);
		return data2.getTime();
	}

	/**
	 * Concatena a hora atual a data informada.
	 * 
	 * @param txtData
	 *            data no formato dd/MM/yyyy
	 * @return data com a hora atual
	 */
	public static Date concatenaHoraAtual(String txtData) {
		String txtHora = getStringHoraAtual();
		synchronized (DATA_HORA_MEDIUM) {
			try {
				return DATA_HORA_MEDIUM.parse(txtData + " " + txtHora);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * Verifica se a data no padrão dd/MM/yyyy HH:mm:ss é válida.
	 * 
	 * @param data
	 *            horário formatado a ser validado.
	 * @return <code>true</code> se o horário for válido, <code>false</code>
	 *         caso contrário.
	 */
	public static boolean ehDataHoraValida(String data) {
		return GenericValidator.isDate(data, "dd/MM/yyyy HH:mm:ss", true);
	}

	/**
	 * Verifica se uma data no formato dd/MM/yyyy é válida.
	 * 
	 * @param data
	 *            data a validar
	 * @return <code>true</code> caso seja válida, <code>false</code> caso
	 *         contrário
	 */
	public static boolean ehDataValida(String data) {
		return GenericValidator.isDate(data, "dd/MM/yyyy", true);
	}

	/**
	 * Verifica se um horário no formato HH:mm:sss é válida.
	 * 
	 * @param hora
	 *            horário formatado a ser validado.
	 * @return true se o horário for válido, false, caso contrário.
	 */
	public static boolean ehHorarioValidoHHMMSS(String hora) {
		return GenericValidator.isDate(hora, "HH:mm:ss", true);
	}

	/**
	 * Verifica se um horário no formato HH:mm é válida.
	 * 
	 * @param hora
	 *            horário formatado a ser validado.
	 * @return <code>true</code> se o horário for válido, <code>false</code>
	 *         caso contrário.
	 */
	public static boolean ehHoraValida(String hora) {
		return GenericValidator.isDate(hora, "HH:mm", true);
	}

	/**
	 * Verifica se uma data está no intervalo informado
	 * 
	 * @param data
	 *            data a validar
	 * @param dataInicio
	 *            data de início
	 * @param dataTermino
	 *            data final
	 * @param ignoraHora
	 *            se ignorará as horas
	 * @return <code>true</code> caso a data esteja no intervalo,
	 *         <code>false</code> caso contrário
	 */
	public static boolean entre(Date data, Date dataInicio, Date dataTermino,
			boolean ignoraHora) {
		return maiorOuIgual(data, dataInicio, ignoraHora)
				&& menorOuIgual(data, dataTermino, ignoraHora);
	}

	/**
	 * Retorna a data, recebida "quebrada", em uma data válida no formato
	 * "dd/MM/yyyy". Se a data não for válida, retorna <code>null</code>.
	 * 
	 * @param dia
	 *            dia da data
	 * @param mes
	 *            mês da data
	 * @param ano
	 *            ano da data
	 * 
	 * @return String da data formatada
	 * 
	 */
	public static String formataData(String dia, String mes, String ano) {
		String data = dia + '/' + mes + '/' + ano;
		if (ehDataValida(data)) {
			return data;
		}
		return null;
	}

	/**
	 * Recupera o ano de uma data informada
	 * 
	 * @param data
	 *            data para verificar
	 * @return ano da data
	 */
	public static int getAno(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_BR);
		cal.setTime(data);
		int yy = cal.get(Calendar.YEAR);
		// caso o ano 1900 seja o 0
		if (yy < 100) {
			yy += 1900;
		}
		return yy;
	}

	/**
	 * Retorna a data atual do sistema.
	 * 
	 * @return a data atual
	 */
	public static Date getDataAtual() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Retorna a data atual do sistema com as horas, minutos e segundos zerados
	 * 
	 * @return a data atual no primeiro segundo do dia
	 */
	public static Date getDataAtualSemHHMMSS() {
		Calendar calendar = Calendar.getInstance(LOCALE_BR);
		return getDataSemHHMMSS(calendar);
	}

	/**
	 * Retorna um {@link Timestamp} da data e hora atual.
	 * 
	 * @return data e hora atual
	 */
	public static Timestamp getDataHoraAtual() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Zera a hora, minuto, segundo, milisegundo do {@link Calendar} informado
	 * 
	 * @param calendar
	 *            {@link Calendar} a zerar
	 * @return objeto {@link Date} com a hora zerada
	 */
	public static Date getDataSemHHMMSS(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTime().getTime());
	}

	/**
	 * Retorna a data com as horas, minutos e segundos zerados.
	 * 
	 * @param data
	 *            data inicial
	 * @return a data no primeiro segundo do dia
	 */
	public static Date getDataSemHHMMSS(Date data) {
		Calendar calendar = Calendar.getInstance(LOCALE_BR);
		calendar.setTime(data);
		return getDataSemHHMMSS(calendar);
	}

	/**
	 * Recupera um {@link Date} a partir dos parâmentros.
	 * 
	 * @param dia
	 *            dia da data
	 * @param mes
	 *            mês da data
	 * @param ano
	 *            ano da data
	 * @return {@link Date} gerado
	 */
	public static Date getDate(int dia, int mes, int ano) {
		Calendar calendar = Calendar.getInstance(LOCALE_BR);
		calendar.set(Calendar.DAY_OF_MONTH, dia);
		calendar.set(Calendar.MONTH, mes - 1);
		calendar.set(Calendar.YEAR, ano);
		return getDataSemHHMMSS(calendar);
	}

	/**
	 * Recupera o dia do mês de uma data informada
	 * 
	 * @param data
	 *            data para verificar
	 * @return dia da data
	 */
	public static int getDia(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_BR);
		cal.setTime(data);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna a maior data passada na coleção
	 * 
	 * @param datas
	 *            {@link List} de datas a verificar
	 * @return a maior data da lista
	 */
	public static Date getMaxDate(List<Date> datas) {
		if (datas.isEmpty()) {
			return null;
		}
		return Collections.max(datas);
	}

	/**
	 * Recupera o mês de uma data informada
	 * 
	 * @param data
	 *            data para verificar
	 * @return mês da data
	 */
	public static int getMes(Date data) {
		Calendar cal = Calendar.getInstance(LOCALE_BR);
		cal.setTime(data);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * Retorna o número de entidades de diferenca entre duas datas <br />
	 * As entidades de retorno são: {@link #SEGUNDO}, {@link #MINUTO},
	 * {@link #HORA}, {@link #DIA}, {@link #MES}, {@link #ANO}
	 * 
	 * @param entidadeRetorno
	 *            unidade de retorno
	 * @param data1
	 *            1ª data a comparar
	 * @param data2
	 *            2ª data a comparar
	 * @return quantidade de unidades informada
	 */
	public static int getNumEntreData(int entidadeRetorno, Date data1,
			Date data2) {
		double diferenca = data1.getTime() - data2.getTime();
		int anoInicial, anoFinal;
		int mesInicial, mesFinal;

		if (diferenca < 0) {
			diferenca = -diferenca;
		}
		diferenca /= 1000;
		if (entidadeRetorno == SEGUNDO) { // entidade de retorno é segundo
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 60;
		if (entidadeRetorno == MINUTO) { // entidade de retorno é minuto
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 60;
		if (entidadeRetorno == HORA) { // entidade de retorno é hora
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 24;
		if (entidadeRetorno == DIA) { // entidade de retorno é dia
			return (int) Math.round(Math.ceil(diferenca));
		}
		diferenca = diferenca / 30;
		if (entidadeRetorno == MES) { // entidade de retorno é mes
			Calendar cal = Calendar.getInstance(LOCALE_BR);
			cal.setTime(data1);
			anoInicial = cal.get(Calendar.YEAR);
			mesInicial = cal.get(Calendar.MONTH);
			cal.setTime(data2);
			anoFinal = cal.get(Calendar.YEAR);
			mesFinal = cal.get(Calendar.MONTH);
			return (anoFinal - anoInicial) * 12 + (mesFinal - mesInicial);
		}
		diferenca = diferenca / 12;
		if (entidadeRetorno == ANO) { // entidade de retorno é ano
			return (int) Math.round(Math.ceil(diferenca));
		}
		return 0; // caso nao seja nenhuma das opcoes retorna o valor default 0
	}

	/**
	 * Retorna a data atual do sistema no formato dd/MM/yyyy.
	 * 
	 * @return data atual no formato dd/MM/yyyy
	 */
	public static String getStringDataAtual() {
		synchronized (DATA_MEDIUM) {
			return DATA_MEDIUM.format(getDataAtual());
		}
	}

	/**
	 * Retorna a String da data atual no formato: Quinta, 1 de Janeiro de 2009.
	 * 
	 * @return a {@link String} formatada da data atual completa
	 */
	public static String getStringDataAtualCompleta() {
		synchronized (DATA_FULL) {
			return DATA_FULL.format(new Timestamp(System.currentTimeMillis()));
		}
	}

	/**
	 * Obtem a data/hora atual em String no formato "dd/MM/yyyy HH:mm:ss"
	 * 
	 * @return String da data/hora atual
	 */
	public static String getStringDataHoraAtual() {
		return getStringDataAtual() + " " + getStringHoraAtual();
	}

	/**
	 * Obtem a hora atual em String no formato "HH:mm:ss"
	 * 
	 * @return String da hora atual
	 */
	public static String getStringHoraAtual() {
		synchronized (HORA_MEDIUM) {
			return HORA_MEDIUM.format(getDataAtual());
		}
	}

	/**
	 * Verifica se as datas são iguais
	 * 
	 * @param data1
	 *            1ª data a comparar
	 * @param data2
	 *            2ª data a comparar
	 * @param ignoraHora
	 *            se ignorará as horas
	 * @return <code>true</code> caso sejam, <code>false</code> caso contrário
	 */
	public static boolean igual(Date data1, Date data2, boolean ignoraHora) { // NOPMD
																				// by
																				// João
																				// Lúcio
																				// -
																				// caso
																				// tenha
																				// que
																				// ignorar
																				// hora
		if (ignoraHora) {
			data1 = getDataSemHHMMSS(data1);
			data2 = getDataSemHHMMSS(data2);
		}
		return data1.equals(data2);
	}

	/**
	 * Verifica se a primeira data é maior do que a segunda
	 * 
	 * @param data1
	 *            1ª data a comparar
	 * @param data2
	 *            2ª data a comparar
	 * @param ignoraHora
	 *            se ignorará as horas
	 * @return <code>true</code> caso seja, <code>false</code> caso contrário
	 */
	public static boolean maior(Date data1, Date data2, boolean ignoraHora) { // NOPMD
																				// by
																				// João
																				// Lúcio
																				// -
																				// caso
																				// tenha
																				// que
																				// ignorar
																				// hora
		if (ignoraHora) {
			data1 = getDataSemHHMMSS(data1);
			data2 = getDataSemHHMMSS(data2);
		}
		return data1.after(data2);
	}

	/**
	 * Verifica se a primeira data é maior ou igual do que a segunda data
	 * 
	 * @param data1
	 *            1ª data a comparar
	 * @param data2
	 *            2ª data a comparar
	 * @param ignoraHora
	 *            se ignorará as horas
	 * @return <code>true</code> caso a primeira seja maior ou igual,
	 *         <code>false</code> caso contrário
	 */
	public static boolean maiorOuIgual(Date data1, Date data2,
			boolean ignoraHora) {
		return maior(data1, data2, ignoraHora)
				|| igual(data1, data2, ignoraHora);
	}

	/**
	 * Verifica se a primeira data é menor do que a segunda
	 * 
	 * @param data1
	 *            1ª data a comparar
	 * @param data2
	 *            2ª data a comparar
	 * @param ignoraHora
	 *            se ignorará as horas
	 * @return <code>true</code> caso seja, <code>false</code> caso contrário
	 */
	public static boolean menor(Date data1, Date data2, boolean ignoraHora) { // NOPMD
																				// by
																				// João
																				// Lúcio
																				// -
																				// caso
																				// tenha
																				// que
																				// ignorar
																				// hora
		if (ignoraHora) {
			data1 = getDataSemHHMMSS(data1);
			data2 = getDataSemHHMMSS(data2);
		}
		return data1.before(data2);
	}

	/**
	 * Verifica se a primeira data é menor ou igual do que a segunda data
	 * 
	 * @param data1
	 *            1ª data a comparar
	 * @param data2
	 *            2ª data a comparar
	 * @param ignoraHora
	 *            se ignorará as horas
	 * @return <code>true</code> caso a primeira seja menor ou igual,
	 *         <code>false</code> caso contrário
	 */
	public static boolean menorOuIgual(Date data1, Date data2,
			boolean ignoraHora) {
		return menor(data1, data2, ignoraHora)
				|| igual(data1, data2, ignoraHora);
	}

	/**
	 * Substituí a data caso a primeira seja <code>null</code>
	 * 
	 * @param dataOriginal
	 *            data original
	 * @param dataSubstituta
	 *            data que substituirá caso a primeira seja <code>null</code>
	 * @return a dataOriginal ou a dataSubstituta
	 */
	public static Date nvl(Date dataOriginal, Date dataSubstituta) {
		if (dataOriginal == null) {
			return dataSubstituta;
		}
		return dataOriginal;
	}

	/**
	 * Soma uma determinada quantidade de dias, meses e/ou anos a data Atual.
	 * (Subtrai se a quantidade for negativa)
	 * 
	 * @param qtdDias
	 *            quantidade de dias a adicionar
	 * @param qtdMeses
	 *            quantidade de meses a adicionar
	 * @param qtdAnos
	 *            quantidade de anos a adicionar
	 * 
	 * @return String que representa a data modificada
	 */
	public static String somaDiasDataAtual(int qtdDias, int qtdMeses,
			int qtdAnos) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_MONTH, qtdDias);
		calendario.add(Calendar.MONTH, qtdMeses);
		calendario.add(Calendar.YEAR, qtdAnos);
		return utilDateToString(calendario.getTime());
	}

	/**
	 * Converte uma data {@link java.sql.Date} para String no formato
	 * "dd/MM/yyyy".
	 * 
	 * @param inData
	 *            data a converter
	 * @return String formada
	 */
	public static String sqlDateToString(java.sql.Date inData) {
		synchronized (DATA_MEDIUM) {
			return DATA_MEDIUM.format(inData);
		}
	}

	/**
	 * Transforma uma data {@link java.sql.Date} em {@link java.util.Date}.
	 * 
	 * @param inData
	 *            String da data a converter
	 * @return data em java.sql.Date
	 */
	public static Date sqlDateToUtilDate(java.sql.Date inData) {
		return new Date(inData.getTime());
	}

	/**
	 * Converte uma data String no formato "dd/MM/yyyy HH:mm:ss" para
	 * {@link java.util.Date}.
	 * 
	 * @param inDataHora
	 *            String da data a converter
	 * @return data convertida
	 */
	public static java.util.Date stringToDateTime(String inDataHora) {
		synchronized (DATA_HORA_MEDIUM) {
			try {
				return DATA_HORA_MEDIUM.parse(inDataHora);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * Converte uma data String no formato "dd/MM/yyyy" para o formato
	 * {@link java.sql.Date}
	 * 
	 * @param inData
	 *            String a converter
	 * @return java.sql.Date Data convertida
	 */
	public static java.sql.Date stringToSqlDate(String inData) {
		java.util.Date auxData = stringToUtilDate(inData);
		return utilDateToSqlDate(auxData);
	}

	/**
	 * Converte uma string no formato 'dd/MM/yyyy' para {@link Timestamp}
	 * 
	 * @param inData
	 *            String a converter
	 * @return Timestamp convertido
	 */
	public static java.sql.Timestamp stringToTimestamp(String inData) {
		java.util.Date auxData = stringToUtilDate(inData);
		return utilDateToTimestamp(auxData);
	}

	/**
	 * Recebe uma data no formato dd/MM/yyyy e retorna um {@link Date}
	 * 
	 * @param data
	 *            data em formato brasileiro
	 * @return objeto {@link Date} correspondente
	 */
	public static Date stringToUtilDate(String data) {
		synchronized (DATA_MEDIUM) {
			try {
				return new Date(DATA_MEDIUM.parse(data).getTime());
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * Transforma um {@link Timestamp} na String no formato 'dd/MM/yyyy'
	 * 
	 * @param inData
	 *            {@link Timestamp} a transformar
	 * @return String do Timestamp formatado
	 */
	public static String timestampToString(java.sql.Timestamp inData) {
		return timestampToString(inData, "dd/MM/yyyy");
	}

	/**
	 * Transforma um {@link Timestamp} em uma string formatada
	 * 
	 * @param inData
	 *            {@link Timestamp} a transformar
	 * @param formato
	 *            formato da String a transformar
	 * 
	 * @return String do Timestamp formatado
	 */
	public static String timestampToString(java.sql.Timestamp inData,
			String formato) {
		SimpleDateFormat formatador = new SimpleDateFormat(formato, LOCALE_BR);
		formatador.setLenient(false);
		return formatador.format(inData);
	}

	/**
	 * Transforma um {@link Timestamp} em {@link java.util.Date}
	 * 
	 * @param dataHora
	 *            {@link Timestamp} a transformar
	 * @return Data transformada
	 */
	public static java.util.Date timestampToUtilDate(Timestamp dataHora) {
		return new java.util.Date(dataHora.getTime());
	}

	/**
	 * Transforma uma {@link java.util.Date} para {@link java.sql.Date}
	 * 
	 * @param inData
	 *            data a converter
	 * @return data do tipo java.sql.Date
	 */
	public static java.sql.Date utilDateToSqlDate(java.util.Date inData) {
		return new java.sql.Date(inData.getTime());
	}

	/**
	 * Converte uma Data para String no formato dd/MM/yyyy
	 * 
	 * @param date
	 *            {@link Date} a converter
	 * @return data formatada em {@link String}
	 */
	public static String utilDateToString(Date date) {
		synchronized (DATA_MEDIUM) {
			return DATA_MEDIUM.format(date);
		}
	}

	/**
	 * Converte um {@link Date} para String no formato HH:mm:ss.
	 * 
	 * @param date
	 *            {@link Date} a converter
	 * @return hora formatada em {@link String}
	 */
	public static String utilDateToStringHora(Date date) {
		synchronized (HORA_MEDIUM) {
			return HORA_MEDIUM.format(date);
		}
	}

	/**
	 * Converte uma data no formato java.util.Date para String no formato
	 * "dia de mês de ano" <br>
	 * Ex: "25 de janeiro de 2002"
	 * 
	 * @param inData
	 *            data a pegar a String
	 * @return String
	 */
	public static String utilDateToStringPorExtenso(java.util.Date inData) {
		StringBuilder descricao = new StringBuilder("");
		descricao.append(getDia(inData) + " de ");

		switch (getMes(inData)) {
		case 1:
			descricao.append("janeiro");
			break;
		case 2:
			descricao.append("fevereiro");
			break;
		case 3:
			descricao.append("março");
			break;
		case 4:
			descricao.append("abril");
			break;
		case 5:
			descricao.append("maio");
			break;
		case 6:
			descricao.append("junho");
			break;
		case 7:
			descricao.append("julho");
			break;
		case 8:
			descricao.append("agosto");
			break;
		case 9:
			descricao.append("setembro");
			break;
		case 10:
			descricao.append("outubro");
			break;
		case 11:
			descricao.append("novembro");
			break;
		case 12:
			descricao.append("dezembro");
			break;
		default:
		}

		descricao.append(" de " + getAno(inData));
		return descricao.toString();
	}

	/**
	 * Recupera o {@link Timestamp} da data informada
	 * 
	 * @param inData
	 *            data informada
	 * @return Timestamp da data
	 */
	public static java.sql.Timestamp utilDateToTimestamp(java.util.Date inData) {
		return new java.sql.Timestamp(inData.getTime());
	}

	/**
	 * Retorna a hora formatada no formato 'HH:mm:ss:SS' da data informada
	 * 
	 * @param inData
	 *            data informada
	 * @return hora formatada
	 */
	public static String utilTimeToStringComMilisegundos(java.util.Date inData) {
		return new SimpleDateFormat("HH:mm:ss:SS", LOCALE_BR).format(inData);
	}

	/**
	 * Retorna a hora formatada no formato 'HH:mm' da data informada
	 * 
	 * @param inData
	 *            data informada
	 * @return hora formatada
	 */
	public static String utilTimeToStringSemSegundos(java.util.Date inData) {
		return new SimpleDateFormat("HH:mm", LOCALE_BR).format(inData);
	}
}