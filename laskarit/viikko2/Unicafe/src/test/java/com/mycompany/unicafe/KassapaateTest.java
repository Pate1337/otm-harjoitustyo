/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paavo
 */
public class KassapaateTest {
    Kassapaate paate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @Test
    public void luomishetkellaKassapaatteessaOikeaMaaraRahaa() {
        assertTrue(paate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void luomishetkellaEdullisiaLounaitaEiMyyty() {
        assertTrue(paate.edullisiaLounaitaMyyty() == 0);
    }
    
    @Test
    public void luomishetkellaMaukkaitaLounaitaEiMyyty() {
        assertTrue(paate.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void paatteenKassaKasvaaKunOstetaanEdullinenJaMaksuTasaraha() {
        paate.syoEdullisesti(240);
        assertEquals(paate.kassassaRahaa(), 100240);
    }
    @Test
    public void paatteenKassaKasvaaKunOstetaanMaukasJaMaksuTasaraha() {
        paate.syoMaukkaasti(400);
        assertEquals(paate.kassassaRahaa(), 100400);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaOikeanMaaranRahaa() {
        assertEquals(paate.syoMaukkaasti(500), 100);
    }
    
    @Test
    public void syoEdullisestiPalauttaaOikeanMaaranRahaa() {
        assertEquals(paate.syoEdullisesti(300), 60);
    }
    
    @Test
    public void syoEdullisestiPalauttaaMaksunJosEiRiittava() {
        assertEquals(paate.syoEdullisesti(100), 100);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaMaksunJosEiRiittava() {
        assertEquals(paate.syoMaukkaasti(100), 100);
    }
    
    @Test
    public void syoEdullisestiVahentaaKortinSaldoaKunSaldoaTarpeeksi() {
        kortti = new Maksukortti(300);
        paate.syoEdullisesti(kortti);
        assertEquals(kortti.saldo(), 60);
    }
    
    @Test
    public void syoMaukkaastiVahentaaKortinSaldoaKunSaldoaTarpeeksi() {
        kortti = new Maksukortti(500);
        paate.syoMaukkaasti(kortti);
        assertEquals(kortti.saldo(), 100);
    }
    
    @Test
    public void syoEdullisestiPalauttaaFalseJosSaldoRiittamaton() {
        kortti = new Maksukortti(100);
        assertTrue(!paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoEdullisestiPalauttaaTrueJosSaldoRiittava() {
        kortti = new Maksukortti(300);
        assertTrue(paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoMaukkaastiPalauttaaFalseJosSaldoRiittamaton() {
        kortti = new Maksukortti(100);
        assertTrue(!paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoMaukkaastiPalauttaaTrueJosSaldoRiittava() {
        kortti = new Maksukortti(500);
        assertTrue(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void lataaRahaaKortilleVahentaaKassanSaldoaKunSummaPositiivinen() {
        kortti = new Maksukortti(100);
        paate.lataaRahaaKortille(kortti, 50);
        assertEquals(kortti.saldo(), 150);
        assertEquals(paate.kassassaRahaa(), 99950);
    }
    
    @Test
    public void lataaRahaaKortilleEiVahennaKassaSaldoaKunSummaEiPositiivinen() {
        kortti = new Maksukortti(100);
        paate.lataaRahaaKortille(kortti, -50);
        assertEquals(kortti.saldo(), 100);
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void kassaPalauttaaOikeanMaaranMyytyjaEdullisiaLounaita() {
        paate.syoEdullisesti(500);
        paate.syoEdullisesti(500);
        assertEquals(paate.edullisiaLounaitaMyyty(), 2);
    }
    
    @Test
    public void kassaPalauttaaOikeanMaaranMyytyjaMaukkaitaLounaita() {
        paate.syoMaukkaasti(500);
        paate.syoMaukkaasti(500);
        assertEquals(paate.maukkaitaLounaitaMyyty(), 2);
    }
}
