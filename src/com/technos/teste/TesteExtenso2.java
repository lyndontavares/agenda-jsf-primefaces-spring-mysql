package com.technos.teste;

/* Classe Main de teste */
import java.util.Random;

import com.technos.util.ValorExtenso2;

public class TesteExtenso2 {

    public static void main(String[] args) {
        Random r = new Random();
        Integer i, n ;

        ValorExtenso2 e = new ValorExtenso2() ;

        for(i=0; i < 10; i++) {
            /* Gera um número aleatório positivo. */
            n = r.nextInt() ;
            if(n < 0) n=-1*n ;

            /* Escreve por extenso o numero dado. Veja que
             * precisamos convertê-lo para string.
             */
            System.out.println(n + " : " + e.getExtenso(n.toString())) ;
        }
    }
}