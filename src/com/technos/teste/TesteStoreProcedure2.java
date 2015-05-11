package com.technos.teste;

import com.technos.daoImpl.ContatoDaoImpl;

public class TesteStoreProcedure2 {
	public static void main(String[] args) {

		String msg = "";
		ContatoDaoImpl dao = new ContatoDaoImpl();
		try {

			msg = dao.callStoreProcedure("contato_expurgar", "123", 1);
			System.out.println(msg);

		} catch (Exception e) {
			dao.getEntityManager().close();
			e.printStackTrace();

		}
	}
}
