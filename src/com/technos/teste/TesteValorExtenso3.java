package com.technos.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import com.technos.util.ValorExtenso3;

public class TesteValorExtenso3 {

    @Test
    public void testGetInstance() {
            ValorExtenso3 instance = ValorExtenso3.getDefaultInstance();
            assertNotNull(instance);
    }

    @Test
    public void testConverteInteiro() {
            ValorExtenso3 n = ValorExtenso3.getDefaultInstance();
            assertEquals("um mil", n.converteInteiro(1000));
            assertEquals("um mil e um", n.converteInteiro(1001));
            assertEquals("doze mil, trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(12345.67)));
            assertEquals("um milhão", n.converteInteiro(1000000L));
            assertEquals("dois milhões", n.converteInteiro(new BigInteger("2000000")));
            assertEquals("um", n.converteInteiro((short)1));
            assertEquals("um", n.converteInteiro(1.01));
            
            // testa outras possibilidades
            // mil sem um
            n = new ValorExtenso3(true, true, true);
            assertEquals("mil", n.converteInteiro(1000));
            assertEquals("mil e um", n.converteInteiro(1001));
            assertEquals("doze mil, trezentos e quarenta e cinco", n.converteInteiro(12345.67));
            assertEquals("um milhão", n.converteInteiro(1000000));

            // sem virgulas
            n = new ValorExtenso3(true, false, true);
            assertEquals("mil e um", n.converteInteiro(new BigDecimal(1001)));
            assertEquals("doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(12345.67)));
            assertEquals("um bilhão trinta e quatro milhões doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(1034012345.67)));
            // sem virgulas, outra opção
            n = new ValorExtenso3(true, false, false);
            assertEquals("mil e um", n.converteInteiro(new BigDecimal(1001)));
            assertEquals("doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(12345.67)));
            assertEquals("um bilhão trinta e quatro milhões doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(1034012345.67)));
            // sem virgulas, mil com um
            n = new ValorExtenso3(false, false, false);
            assertEquals("um mil e um", n.converteInteiro(new BigDecimal(1001)));
            assertEquals("doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(12345.67)));
            assertEquals("um bilhão trinta e quatro milhões doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(1034012345.67)));
            // com virgulas, mil sem virgula
            n = new ValorExtenso3(false, true, false);
            assertEquals("um mil e um", n.converteInteiro(new BigDecimal(1001)));
            assertEquals("doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(12345.67)));
            assertEquals("um bilhão, trinta e quatro milhões, doze mil trezentos e quarenta e cinco", n.converteInteiro(new BigDecimal(1034012345.67)));
    }

    @Test
    public void testConverteMoedaBigDecimalStringArrayStringArrayInt() {
            ValorExtenso3 n = ValorExtenso3.getDefaultInstance();
            assertEquals("um mil litros e duzentos e cinquenta mililitros", n.converteMoeda(new BigDecimal(1000.250), new String[] {"litro","litros"}, new String[] {"mililitro", "mililitros"}, 1000));
    }

    @Test
    public void testConverteMoedaBigDecimal() {
            ValorExtenso3 n = ValorExtenso3.getDefaultInstance();
            assertEquals("um mil reais", n.converteMoeda(1000));
            assertEquals("um mil e um reais", n.converteMoeda(new BigInteger("1001")));
            assertEquals("doze mil, trezentos e quarenta e cinco reais e sessenta e sete centavos", n.converteMoeda(12345.67));
            assertEquals("um milhão de reais", n.converteMoeda(new Long(1000000)));
            assertEquals("dois milhões de reais", n.converteMoeda(new BigInteger("2000000")));
            assertEquals("um real", n.converteMoeda(new Short((short) 1)));
            assertEquals("um real e um centavo", n.converteMoeda(new Float(1.01)));
            
            // really big number
            BigDecimal big = new BigDecimal(1000).pow(17).multiply(new BigDecimal(345));
            big = big.add(new BigDecimal(1000).pow(16).multiply(new BigDecimal(233)));
            big = big.add(new BigDecimal(1000).pow(15).multiply(new BigDecimal(950)));
            big = big.add(new BigDecimal(1000).pow(14).multiply(new BigDecimal(861)));
            big = big.add(new BigDecimal(1000).pow(13).multiply(new BigDecimal(772)));
            big = big.add(new BigDecimal(1000).pow(12).multiply(new BigDecimal(683)));
            big = big.add(new BigDecimal(1000).pow(11).multiply(new BigDecimal(594)));
            big = big.add(new BigDecimal(1000).pow(10).multiply(new BigDecimal(405)));
            big = big.add(new BigDecimal(1000).pow(9).multiply(new BigDecimal(306)));
            big = big.add(new BigDecimal(1000).pow(8).multiply(new BigDecimal(217)));
            big = big.add(new BigDecimal(1000).pow(7).multiply(new BigDecimal(128)));
            big = big.add(new BigDecimal(1000).pow(6).multiply(new BigDecimal(39)));
            big = big.add(new BigDecimal(1000).pow(5).multiply(new BigDecimal(940)));
            big = big.add(new BigDecimal(1000).pow(4).multiply(new BigDecimal(851)));
            big = big.add(new BigDecimal(1000).pow(3).multiply(new BigDecimal(762)));
            big = big.add(new BigDecimal(1000).pow(2).multiply(new BigDecimal(673)));
            big = big.add(new BigDecimal(1000).pow(1).multiply(new BigDecimal(23)));
            big = big.add(new BigDecimal(17));
            assertEquals("trezentos e quarenta e cinco septendecilhões, duzentos e trinta e três sedecilhões, novecentos e cinquenta quindecilhões, oitocentos e sessenta e um quatordecilhões, setecentos e setenta e dois tredecilhões, seiscentos e oitenta e três dodecilhões, quinhentos e noventa e quatro undecilhões, quatrocentos e cinco decilhões, trezentos e seis octilhões, duzentos e dezessete setilhões, cento e vinte e oito sextilhões, trinta e nove quintilhões, novecentos e quarenta quatrilhões, oitocentos e cinquenta e um trilhões, setecentos e sessenta e dois bilhões, seiscentos e setenta e três milhões, vinte e três mil e dezessete reais", n.converteMoeda(big));
    }

}
