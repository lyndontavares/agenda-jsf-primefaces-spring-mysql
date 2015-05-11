package com.technos.teste;

import java.math.BigDecimal;

import com.technos.util.ValorExtenso;

public class TesteExtenso {
 
	      public static void main(String[] args) {
	        ValorExtenso e = new ValorExtenso();
	        String valorExtenso = e.write(BigDecimal.valueOf(1500));
	        System.out.println(valorExtenso);
	        
	      }
}
