package com.technos.enums;


public enum SituacaoContato {

	ATIVO("0"), INATIVO("1"), CANCELADO("-1"); 

	private static SituacaoContato[] allValues = values();
	public static SituacaoContato fromOrdinal(int n) {return allValues[n];}
	
	private String value;

	private SituacaoContato(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int toInt ( ){
		long ret = Long.valueOf(  value.toString() );
		return (int) ret;
	}

	public String toCss() {
		String ret = "";
		switch (this) {
		case ATIVO:
			ret = "background-color:#D6EBFF;color:#000000";
			break;
		case INATIVO:
			ret = "background-color:#40E0D0;color:#000000";
			break;
		case CANCELADO:
			ret = "background-color:#FEEFB3;color:#000000";
			break;
		default:
			ret = "background-color:#708090;color:#000000";
			break;
		}
		return ret;

	}


}
