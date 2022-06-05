import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestyJednostkowe {
    private Pracownik p;
    private Menadzer m;
    private Urlop u1;
    private Urlop u2;
    private Dane baza;
    @Before
    public void setUp(){
        p = new Pracownik("janek","janek1","Jan","Kowalski");
        m = new Menadzer("admin","admin","Tomasz","Gil");
        u1 = new Urlop(p,"23.06.2022",3);
        u2 = new Urlop(p,"24.12.2021",7);
        baza = new Dane("testMenadzerzy.csv","testPracownicy.csv","testUrlopy.csv");
        baza.dodajUrlop(u1);
        baza.dodajUrlop(u2);
        baza.dodajOsobe(p);
        baza.dodajOsobe(m);
    }

    @Test
    public void testPracownikGettery(){
        assertEquals("janek",p.getLogin());
        assertEquals("janek1",p.getHaslo());
        assertEquals("Jan",p.getImie());
        assertEquals("Kowalski",p.getNazwisko());
        assertEquals(25,p.getDniUrlopowe());
    }

    @Test
    public void testSetDniUrlopowe(){
        p.setDniUrlopowe(13);
        assertEquals(13,p.getDniUrlopowe());
    }

    @Test
    public void testPracownikDodajUrlop(){
        p.dodajUrlop(u1);
        p.dodajUrlop(u2);
        ArrayList<Urlop> urlopy = new ArrayList<>();
        urlopy.add(u1);
        urlopy.add(u2);
        assertArrayEquals(urlopy.toArray(),p.getUrlopy().toArray());
    }

    @Test
    public void testUrlopGettery(){
        assertEquals(p,u1.getAplikujacyPracownik());
        assertEquals("23.06.2022",u1.getDataRozpoczecia());
        assertEquals(3,u1.getDlugosc());
        assertEquals(false,u1.jestRozpatrzony());
        assertEquals(false,u1.jestZaakceptowany());
    }

    @Test
    public void testUrlopSettery(){
        u1.setRozpatrzenie(true);
        u1.setZaakceptowanie(true);
        assertEquals(true,u1.jestRozpatrzony());
        assertEquals(true,u1.jestZaakceptowany());
    }

    @Test
    public void testUrlopToString(){
        assertEquals("23.06.2022 - 3 dni",u1.toString());
    }

    @Test
    public void testMenadzerGettery(){
        assertEquals("admin",m.getLogin());
        assertEquals("admin",m.getHaslo());
        assertEquals("Tomasz",m.getImie());
        assertEquals("Gil",m.getNazwisko());
        assertEquals(0,m.getOdrzuconeUrlopy());
        assertEquals(0,m.getZaakceptowaneUrlopy());
    }

    @Test
    public void testMenadzerSettery(){
        m.setOdrzuconeUrlopy(4);
        m.setZaakceptowaneUrlopy(7);
        assertEquals(4,m.getOdrzuconeUrlopy());
        assertEquals(7,m.getZaakceptowaneUrlopy());
    }

    @Test
    public void testDaneGettery(){
        Pracownik[] pracownicy = {p};
        Menadzer[] menadzerzy = {m};
        Urlop[] urlopy = {u1,u2};
        assertArrayEquals(pracownicy,baza.getPracownicy().toArray());
        assertArrayEquals(menadzerzy,baza.getMenadzerzy().toArray());
        assertArrayEquals(urlopy,baza.getUrlopy().toArray());
    }

    @Test
    public void testDaneDodajOsobe(){
        Pracownik nowyPracownik = new Pracownik("nowy","1234","Adam","Nowak");
        Menadzer nowyMenadzer = new Menadzer("szefwszystkichszefow","abcd","Witold","Szpak");
        baza.dodajOsobe(nowyPracownik);
        baza.dodajOsobe(nowyMenadzer);
        Pracownik[] pracownicy = {p,nowyPracownik};
        Menadzer[] menadzerzy = {m,nowyMenadzer};
        assertArrayEquals(pracownicy,baza.getPracownicy().toArray());
        assertArrayEquals(menadzerzy,baza.getMenadzerzy().toArray());
    }

    @Test
    public void testDaneDodajIstniejacaOsobe(){
        baza.dodajOsobe(p);
        Pracownik[] pracownicy = {p};
        assertArrayEquals(pracownicy,baza.getPracownicy().toArray());
    }

    @Test
    public void testDodajUrlop(){
        Urlop nowyUrlop = new Urlop(p,"24.09.2022",6);
        baza.dodajUrlop(nowyUrlop);
        Urlop[] urlopy = {u1,u2,nowyUrlop};
        assertArrayEquals(urlopy,baza.getUrlopy().toArray());
    }

    @Test
    public void testZnajdzPracownika(){
        assertEquals(p,baza.znajdzPracownika("janek"));
        assertNull(baza.znajdzPracownika("nieistniejacypracownik"));
    }

    @Test
    public void testZnajdzMenadzera(){
        assertEquals(m,baza.znajdzMenadzera("admin"));
        assertNull(baza.znajdzMenadzera("nieistniejacymenadzer"));
    }



}
