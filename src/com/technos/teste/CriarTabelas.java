package com.technos.teste;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CriarTabelas {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("conexao");
		factory.close();

	}
}
