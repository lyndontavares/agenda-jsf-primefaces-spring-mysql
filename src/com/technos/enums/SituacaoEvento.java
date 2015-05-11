package com.technos.enums;

public enum SituacaoEvento {

	AGUARDANDO("0"), CONFIRMADO("1"), REALIZADO("2"), CANCELADO("-1");

	private static SituacaoEvento[] allValues = values();

	public static SituacaoEvento fromOrdinal(int n) {
		return allValues[n];
	}

	private String value;

	private SituacaoEvento(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int toInt() {
		long ret = Long.valueOf(value.toString());
		return (int) ret;
	}

	public String toCss() {
		String ret = "";
		switch (this) {
		case AGUARDANDO:
			ret = "background-color:#D6EBFF;color:#000000";
			break;
		case CONFIRMADO:
			ret = "background-color:#40E0D0;color:#000000";
			break;
		case REALIZADO:
			ret = "background-color:#87CEEB;color:#000000";
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
