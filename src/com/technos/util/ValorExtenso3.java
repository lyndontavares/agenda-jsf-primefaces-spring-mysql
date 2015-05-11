package com.technos.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Esta classe traduz números de sua forma numeral para sua forma por extenso.
 * 
 * @author girino
 * 
 * Regras:
 * 1. numeros abaixo de 20 tem nome próprio.
 * 2. de 21 a 99 os numeros são formados por DEZENA 'e' UNIDADE (exemplo: "trinta e cinco")
 * 3. dezenas redondas não tem nada depois (20 -> "vinte", e não "vinte e zero").
 * 4. 100 tem nome próprio: cem
 * 5. números maiores que cem são compostos por CENTENA 'e' extenso(numero % 100), onde extenso() é a função que converte números menores que 100.
 *    (exemplos: cento e vinte e um, quinhentos e quarenta).
 * 6. acima de 1000 agrupa-se em blocos de 3 dígitos (potências de 1000) onde a representação 
 *    descrita em 5 é usada internametne, posfixados pelo descritor do grupo (mil, milhão, etc). Os grupos são concatenados por virgula (opcional)
 * 6a. A ultima concatenação é feita por "e".
 * 6a1. A ultima concatenação é omitida (Ou substituida por vírgula) caso o ultimo grupo seja maior que 100 e não seja múltiplo e 100.
 * 6b. o "um" em frente ao descritor de grupo "mil" é opcional e deve ser parametrizável.
 *     Exemplos:
 *               mil e um.
 *               duzentos milhões, vinte mil e dez.
 *               um milhão, dois mil duzentos e cinquenta.
 *               um milhão, dois mil e quarenta e cinco.
 * 7. Ao acrescentar a unidade (por exemplo, "reais") usa-se o prefixo "de" antes da undiade caso o ultimo seja de milhão ou maior. 
 *    Exemplos:
 *              mil reais.
 *              um milhão de reais.
 *              dois bilhões de reais.
 *              um milhão, cento e vinte mil duzentos e cinquenta reais.
 */

public class ValorExtenso3 {

        /* parâmetros */
        private boolean milSemUm = false;
        private TipoSeparador separadorGrupo = TipoSeparador.VIRGULA;
        /* este parÂmetro não deveria existir, já que segundo as gramáticas omite-se apenas depois de mil */
        private TipoSeparador separadorUltimoGrupo = TipoSeparador.VIRGULA;
        private TipoSeparador separadorDepoisDeMil = TipoSeparador.VIRGULA;


        /**
         * O construtor permite que se crie novas instÂncias paramterizadas de forma distinta do padrão.
         * Os parametros opcionais definem:
         * 1. Se o descritor de milhares será prefixado por 1 quando for o caso ("um mil" ao invés de apenas "mil");
         * 2. Se a vírgula depois dos descritores de potencias de dez (mil, milhão, bilhão, etc) 
         *    será omitda;
         * 3. Se haverá vírgula do descritor "mil" ("dez mil, duzentos e cinquenta" contra "dez mil duzentos e cinquenta").
         *  
         * @param milSemUm Caso seja "true", o "um" será omitido antes dos milhares.
         * @param virgulaEntreGrupos Caso seja true usa-se a vírgula, caso false, será omitida.
         * @param virgulaDepoisDeMil Caso seja true usa-se o que for determinado pelo 
         *                                                              parametro virgulaEntreGrupos, caso seja false, 
         *                                                              omite-se a virgula em todos os casos.
         */
        public ValorExtenso3(boolean milSemUm, boolean virgulaEntreGrupos, boolean virgulaDepoisDeMil) {
                this.milSemUm = milSemUm;
                separadorGrupo = virgulaEntreGrupos?TipoSeparador.VIRGULA:TipoSeparador.NENHUM;
                separadorUltimoGrupo = separadorGrupo;
                // virgula depois de mil só faz sentido se o separador for virgula
                separadorDepoisDeMil = virgulaDepoisDeMil?separadorGrupo:TipoSeparador.NENHUM;
        }
        
        private static ValorExtenso3 defaultInstance = new ValorExtenso3(false, true, true);
        /**
         * singleton
         * @return a instância default.
         */
        public static ValorExtenso3 getDefaultInstance() {
                return defaultInstance;
        }
        

        private enum TipoSeparador {
                NENHUM(" "),
                E(" e "),
                VIRGULA(", "),
                DE(" de ");

                String separadorStr;

                TipoSeparador(String separadorStr) {
                        this.separadorStr = separadorStr;
                } 

                public String getSeparador() {
                        return separadorStr;
                }
        }

        public static final String ZERO = "zero";
        public static final String CEM = "cem";
        public static final BigDecimal C_1000 = new BigDecimal(1000);
        public static final BigDecimal C_100 = new BigDecimal(100);
        public static final BigDecimal C_0 = new BigDecimal(0);

        public static String[] UNIDADES = {
                "", "um", "dois", "tr\u00EAs", "quatro", "cinco", "seis", "sete", "oito", "nove", 
            "dez", "onze", "doze", "treze", "quatorze","quinze","dezesseis","dezessete","dezoito","dezenove"
        };

        public static String[] DEZENAS = {
                "","", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"
        };

        public static String[] CENTENAS = {
                "", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"
        };

        public static String[] ordensSingular= {
                "", "mil", "milh\u00E3o", "bilh\u00E3o", "trilh\u00E3o", "quatrilh\u00E3o", "quintilh\u00E3o", "sextilh\u00E3o", "setilh\u00E3o", "octilh\u00E3o", "nonilh\u00E3o", 
           "decilh\u00E3o", "undecilh\u00E3o", "dodecilh\u00E3o", "tredecilh\u00E3o", "quatordecilh\u00E3o", "quindecilh\u00E3o", "sedecilh\u00E3o", "septendecilh\u00E3o"
        };

        public static String[] ordensPlural = {
                "", "mil", "milh\u00F5es", "bilh\u00F5es", "trilh\u00F5es", "quatrilh\u00F5es", "quintilh\u00F5es", "sextilh\u00F5es", "setilh\u00F5es", "octilh\u00F5es", 
                "decilh\u00F5es", "undecilh\u00F5es", "dodecilh\u00F5es", "tredecilh\u00F5es", "quatordecilh\u00F5es", "quindecilh\u00F5es", "sedecilh\u00F5es", "septendecilh\u00F5es" 
        };

        /**
         * converte valores menores que 20.
         */
        String unidades(int n) {
                if (n == 0) return ZERO;
                if (n < UNIDADES.length) return UNIDADES[n];
                throw new RuntimeException("valor: " + n);
        }

        /**
         * Converte valores menores que 100. Utiliza o método unidades() auxiliarmente.
         */
        String dezenas(int n) {
                if (n < UNIDADES.length) return unidades(n);
                int unidade = n % 10;
                n = n / 10;
                // ignora o 0 em dezenas redondas (20, 30, 40, etc)
                String unidadeStr = "";
                if (unidade != 0) {
                        unidadeStr = TipoSeparador.E.getSeparador() + unidades(unidade);
                }
                if (n < DEZENAS.length) return DEZENAS[n] + unidadeStr;
                throw new RuntimeException();
        }

        /**
         * Converte valores menores que 1000 usando dezenas() auxiliarmente.
         */
        String centenas(int n) {
                // caso especial "100", tratado a parte.
                if (n == 100) return CEM;
                if (n < 100) return dezenas(n);
                // separa centenas e dezenas
                int rem = n % 100;
                n = n/100;
                String dezenaStr = "";
                if (rem != 0) {
                        dezenaStr = TipoSeparador.E.getSeparador() + dezenas(rem);
                }
                if (n < CENTENAS.length) return CENTENAS[n] + dezenaStr;
                throw new RuntimeException();
        }

        /**
         * monta grupos de 3 dígitos junto com seu sufixo, identificando o 
         * uso de plural quando necessário.
         */
        String montaGrupo(int n, int grupo) {
                if (n == 0) return "";
                String nomeGrupo = "";
                if (n == 1) {
                        // mil é exceção, regra 6b.
                        if (grupo == 1 && milSemUm) {
                                return ordensSingular[grupo];
                        }
                        nomeGrupo = ordensSingular[grupo];
                } else {
                        nomeGrupo = ordensPlural[grupo];
                }
                if (grupo == 0) return centenas(n); // pra evitar "trailling space"
                return centenas(n) + TipoSeparador.NENHUM.getSeparador() + nomeGrupo;
        }

        private class TuplaGrupo {
                Integer valor;
                Integer posicao;
                String extenso;
                public TuplaGrupo(Integer valor, Integer posicao, String extenso) {
                        this.valor = valor;
                        this.posicao = posicao;
                        this.extenso = extenso;
                }
                public Integer getValor() {
                        return this.valor;
                }
                public Integer getPosicao() {
                        return this.posicao;
                }
                public String getExtenso() {
                        return this.extenso;
                }
        }

        protected List<TuplaGrupo> montaGrupos(BigDecimal n) {
                List<TuplaGrupo> grupos = new ArrayList<TuplaGrupo>();
                int i = 0;
                while (n.compareTo(C_0) > 0) {
                        BigDecimal[] tmp = n.divideAndRemainder(C_1000);
                        if (tmp[1].intValue() > 0) {
                                TuplaGrupo grupo = new TuplaGrupo(tmp[1].intValue(), i, montaGrupo(tmp[1].intValue(), i));
                                grupos.add(grupo);
                        }
                        n = tmp[0];
                        i++;
                }
                return grupos;
        }

        protected String reagrupa(List<TuplaGrupo> grupos) {
                // avalia regra 6a1: ultimo grupo é > 100 e não é multiplo de 100
                boolean omiteUltimoSeparador = false;
                if (grupos.get(0).getValor() > 100 && grupos.get(0).getValor() % 100 != 0) {
                        omiteUltimoSeparador = true;
                }
                Iterator<TuplaGrupo> it = grupos.iterator();
                String ret = it.next().getExtenso();
                if (!omiteUltimoSeparador && it.hasNext()) {
                        ret = it.next().getExtenso() + TipoSeparador.E.getSeparador() + ret;
                } else if (it.hasNext()) {
                        TuplaGrupo grupo = it.next();
                        TipoSeparador separador = grupo.getPosicao().equals(1)?separadorDepoisDeMil:separadorUltimoGrupo;
                        ret = grupo.getExtenso() + separador.getSeparador() + ret;
                }
                while (it.hasNext()) {
                        ret = it.next().getExtenso() + separadorGrupo.getSeparador() + ret;
                }
                return ret;
        }

        /**
         * Implementa a regra 6:
         * Separa em grupos de 3 dígitos (potencias de 1000), processa os grupos separadamente
     * e depois reagrupa, separando por virgula exceto no ultimo separador, que pode ser
         * "e" ou entao ser omitido (regra 6a e 6a1).
         **/
        public  <T extends Number> String converteInteiro(T number) {
                BigDecimal n = new BigDecimal(number.toString());
                // na verdade a penas o 0 precisa ser exceção, mas isso poupa esforço.
                if (n.compareTo(C_1000) < 0) return centenas(n.intValue());

                List<TuplaGrupo> grupos = montaGrupos(n);
                return reagrupa(grupos);
        }

        String principal(BigDecimal n, String[] nomesMoeda) {
                String nomeMoeda = nomesMoeda[1];
                if (n.compareTo(BigDecimal.ONE) == 0) {
                        nomeMoeda = nomesMoeda[0];
                }
                if (n.compareTo(C_1000) < 0) return centenas(n.intValue()) + TipoSeparador.NENHUM.getSeparador() + nomeMoeda;
                List<TuplaGrupo> grupos = montaGrupos(n);
                TipoSeparador separador = TipoSeparador.NENHUM;
                // se for maior que milhão, usa "de" (regra 7).
                if (grupos.get(0).getPosicao() >= 2) {
                        separador = TipoSeparador.DE;
                }
                return reagrupa(grupos) + separador.getSeparador() + nomeMoeda;
        }

        String subdivisao(BigDecimal n, String[] nomesSubdivisao, int escalaSubdivisao) {
                // basicamente é executar o principal da subdivisao * escala.
                return principal(n.multiply(new BigDecimal(escalaSubdivisao)).divideToIntegralValue(BigDecimal.ONE), nomesSubdivisao);
        }

        /**
         * Similar ao converteInteiro(), mas usa o sufixo da moeda fornecido (e.g. real, dollar, etc) 
         * e computa as casas decimais (centavos) de acordo com a escala fornecida e 
         * usando os nomes fornecidos para a subdivisão da moeda.
         * 
         * É necessário os nomes no singular e no plural para a moeda, bem como para a subdivisão. 
         * A escala deve ser preferencialmente um múltiplo de 10, já que a notação original é decimal.
         * O funcionamento para escalas diferentes desse formato não é garantido.
         * 
         * @param n Número a ser convertido.
         * @param nomesMoeda vetor contendo o nome no singular e no plural da moeda.
         * @param nomesSubdivisao vetor contendo o nome no singular e no plural da subdivisão da moeda.
         * @param escalaSubdivisao escala usada na subdivisão (100 para centavos).
         * @return O valor escrito por extenso.
         */
        public  <T extends Number> String converteMoeda(T number, String[] nomesMoeda, String[] nomesSubdivisao, int escalaSubdivisao) {
                BigDecimal n = new BigDecimal(number.toString());
                BigDecimal[] split = n.divideAndRemainder(BigDecimal.ONE);

                // se não houver centavos, só retorna o principal
                if (split[1].compareTo(BigDecimal.ZERO) == 0) {
                        return principal(split[0], nomesMoeda);
                }
                // se não houver principal, só retorna os centavos
                if (split[0].compareTo(BigDecimal.ZERO) == 0) {
                        return subdivisao(split[1], nomesSubdivisao, escalaSubdivisao);
                }
                return principal(split[0], nomesMoeda) + TipoSeparador.E.getSeparador() + subdivisao(split[1], nomesSubdivisao, escalaSubdivisao);
        }
        /**
         * Este método é usado quando a moeda é o real (BRL), subdividido em centavos.
         * 
         * @param n Número a ser convertido.
         * @return O valor escrito por extenso.
         */
        public <T extends Number> String converteMoeda(T numero) {
                return converteMoeda(numero, new String[] {"real", "reais"}, new String[] {"centavo", "centavos"}, 100);
        }       
}