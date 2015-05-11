package com.technos.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValorExtenso {

    private static final BigInteger THOUSAND = new BigInteger("1000");
    private static final BigInteger HUNDRED = new BigInteger("100");
    private static final String CENTO = "CENTO";
    private static final String CEM = "CEM";
    
    private final Map<Integer, String> grandezasPlural = new HashMap<Integer, String>();
    private final Map<Integer, String> grandezasSingular = new HashMap<Integer, String>();
    
    /** Nomes dos números. */
    private final Map<Integer, String> nomes = new HashMap<Integer, String>();
    
    private static final String MOEDA_SINGULAR = "REAL";
    private static final String MOEDA_PLURAL = "REAIS";
    
    private static final String FRACAO_SINGULAR = "CENTAVO";
    private static final String FRACAO_PLURAL = "CENTAVOS";
    
    private static final String PARTICULA_ADITIVA = "E";
    private static final String PARTICULA_DESCRITIVA = "DE";
    
    /**
     * O conversor reconhece números até a ordem dos setilhões, portanto, o
     * maior valor suportado atualmente é o representado abaixo.
     */
    private static final BigDecimal MAX_SUPPORTED_VALUE = new BigDecimal("999999999999999999999999999.99");
    
    
    public ValorExtenso() {
        preencherGrandezasPlural();
        preencherGrandezasSingular();
        preencherNomes();
    }

    public String write(final BigDecimal amount) {
        if (null == amount) {throw new IllegalArgumentException();}
        
        /*
         * TODO substituir o método setScale, abaixo, pela versão cujo
         * parâmetro de arredondamento é um enum
         */
        BigDecimal value = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        
        if (value.compareTo(BigDecimal.ZERO) <= 0) {return "";}
        
        if (MAX_SUPPORTED_VALUE.compareTo(value) < 0) {
            throw new IllegalArgumentException("Valor acima do limite suportado.");
        }

        Stack<Integer> decomposed = decompose(value);

        /* Se o número estiver, digamos, na casa dos milhões, a pilha 
         * deverá conter 4 elementos sendo os dois últimos os das 
         * centenas e dos centavos, respectivamente. Assim, o expoente de
         * dez que representa a grandeza no topo da pilha é o número de 
         * (elementos - 2) * 3 */
        int expoente = 3 * (decomposed.size() - 2); // TODO usar um índice de grupos ao invés do expoente 
        
        StringBuffer sb = new StringBuffer();
        int lastNonZeroExponent = -1;
        
        while (!decomposed.empty()) {
            int valor = decomposed.pop();
            
            if (valor > 0) {
                sb.append(" ").append(PARTICULA_ADITIVA).append(" ");
                sb.append(comporNomeGrupos(valor));
                String nomeGrandeza = obterNomeGrandeza(expoente, valor);
                if (nomeGrandeza.length() > 0) {
                    sb.append(" ");    
                }
                sb.append(nomeGrandeza);
                
                lastNonZeroExponent = expoente;
            }
            
            switch (expoente) { // TODO ao invés desses switches e ifs, partir para a idéia das "Pendências"; talvez implementá-las com enum
            case 0:
                BigInteger parteInteira = value.toBigInteger();

                if (BigInteger.ONE.equals(parteInteira)) {
                    sb.append(" ").append(MOEDA_SINGULAR);
                } else if (parteInteira.compareTo(BigInteger.ZERO) > 0) {
                    if (lastNonZeroExponent >= 6) {
                        sb.append(" ").append(PARTICULA_DESCRITIVA);
                    }
                    sb.append(" ").append(MOEDA_PLURAL);
                }
                break;
                
            case -3:
                if (1 == valor) {
                    sb.append(" ").append(FRACAO_SINGULAR);
                } else if (valor > 1) {
                    sb.append(" ").append(FRACAO_PLURAL);
                }
                break;
            }
            
            expoente -= 3;
        }
        
        return sb.substring(3);
    }
    
    private StringBuffer comporNomeGrupos(int valor) {
        StringBuffer nome = new StringBuffer();

        int centenas = valor - (valor % 100);
        int unidades = valor % 10;
        int dezenas = (valor - centenas) - unidades;
        int duasCasas = dezenas + unidades;
        
        if (centenas > 0) {
            nome.append(" ").append(PARTICULA_ADITIVA).append(" ");
            
            if (100 == centenas) {
                if (duasCasas > 0) {
                    nome.append(CENTO);
                } else {
                    nome.append(CEM);
                }
            } else {
                nome.append(nomes.get(centenas));
            }
        }
        
        if (duasCasas > 0) {
            nome.append(" ").append(PARTICULA_ADITIVA).append(" ");
            if (duasCasas < 20) {
                nome.append(nomes.get(duasCasas));
            } else {
                if (dezenas > 0) {
                    nome.append(nomes.get(dezenas));
                }
                
                if (unidades > 0) {
                    nome.append(" ").append(PARTICULA_ADITIVA).append(" ");
                    nome.append(nomes.get(unidades));
                }
            }
        }
        
        return nome.delete(0, 3);
    }

    private String obterNomeGrandeza(int exponent, int value) {
        if (exponent < 3) {return "";}
        
        if (1 == value) {
            return grandezasSingular.get(exponent);
        } else {
            return grandezasPlural.get(exponent);
        }
    }

    private Stack<Integer> decompose(BigDecimal value) {
        BigInteger intermediate = value.multiply(new BigDecimal(100)).toBigInteger();
        Stack<Integer> decomposed = new Stack<Integer>();
        
        BigInteger[] result = intermediate.divideAndRemainder(HUNDRED);
        intermediate = result[0];
        decomposed.add(result[1].intValue());
        
        while (intermediate.compareTo(BigInteger.ZERO) > 0) {

                    result = intermediate.divideAndRemainder(THOUSAND);
            intermediate = result[0];
            decomposed.add(result[1].intValue());
        }
        
        /*
         * Se o valor for apenas em centavos, adicionar zero para a casa dos
         * reais inteiros
         */
        if (decomposed.size() == 1) {
            decomposed.add(0);
        }
        
        return decomposed;
    }

    private void preencherGrandezasPlural() {
        grandezasPlural.put(3, "MIL");
        grandezasPlural.put(6, "MILHÕES");
        grandezasPlural.put(9, "BILHÕES");
        grandezasPlural.put(12, "TRILHÕES");
        grandezasPlural.put(15, "QUATRILHÕES");
        grandezasPlural.put(18, "QUINTILHÕES");
        grandezasPlural.put(21, "SEXTILHÕES");
        grandezasPlural.put(24, "SETILHÕES");
    }

    private void preencherGrandezasSingular() {
        grandezasSingular.put(3, "MIL");
        grandezasSingular.put(6, "MILHÃO");
        grandezasSingular.put(9, "BILHÃO");
        grandezasSingular.put(12, "TRILHÃO");
        grandezasSingular.put(15, "QUATRILHÃO");
        grandezasSingular.put(18, "QUINTILHÃO");
        grandezasSingular.put(21, "SEXTILHÃO");
        grandezasSingular.put(24, "SETILHÃO");
    }

    private void preencherNomes() {
        nomes.put(1, "UM");
        nomes.put(2, "DOIS");
        nomes.put(3, "TRÊS");
        nomes.put(4, "QUATRO");
        nomes.put(5, "CINCO");
        nomes.put(6, "SEIS");
        nomes.put(7, "SETE");
        nomes.put(8, "OITO");
        nomes.put(9, "NOVE");
        nomes.put(10, "DEZ");
        nomes.put(11, "ONZE");
        nomes.put(12, "DOZE");
        nomes.put(13, "TREZE");
        nomes.put(14, "QUATORZE");
        nomes.put(15, "QUINZE");
        nomes.put(16, "DEZESSEIS");
        nomes.put(17, "DEZESSETE");
        nomes.put(18, "DEZOITO");
        nomes.put(19, "DEZENOVE");
        nomes.put(20, "VINTE");
        nomes.put(30, "TRINTA");
        nomes.put(40, "QUARENTA");
        nomes.put(50, "CINQUENTA");
        nomes.put(60, "SESSENTA");
        nomes.put(70, "SETENTA");
        nomes.put(80, "OITENTA");
        nomes.put(90, "NOVENTA");
        nomes.put(200, "DUZENTOS");
        nomes.put(300, "TREZENTOS");
        nomes.put(400, "QUATROCENTOS");
        nomes.put(500, "QUINHENTOS");
        nomes.put(600, "SEISCENTOS");
        nomes.put(700, "SETECENTOS");
        nomes.put(800, "OITOCENTOS");
        nomes.put(900, "NOVECENTOS");
    }
}


