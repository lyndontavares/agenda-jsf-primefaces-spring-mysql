package com.technos.enums;


public enum Sexo {

	HOMEM("1"), MULHER("2");

	private static Sexo[] allValues = values();
	public static Sexo fromOrdinal(int n) {return allValues[n];}
	
	private String value;

	private Sexo(String value) {
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


}
