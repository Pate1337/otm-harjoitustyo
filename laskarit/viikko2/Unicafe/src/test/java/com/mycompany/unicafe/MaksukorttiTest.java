package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        assertEquals("saldo: 0.20", kortti.toString());
    }
    
    @Test
    public void rahanOttaminenVahentaaSaldoaJosRahaaTarpeeksi() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.05", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(15);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void josRahaaEiTarpeeksiOtaRahaaPalauttaaFalse() {
        assertTrue(!kortti.otaRahaa(15));
    }
    
    @Test
    public void josRahaaTarpeeksiOtaRahaaPaluttaaTrue() {
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test
    public void maksukorttiPalauttaaOikeanSaldon() {
        assertEquals(10, kortti.saldo());
    }
}
