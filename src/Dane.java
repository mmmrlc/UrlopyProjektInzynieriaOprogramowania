
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Baza danych zawierajaca informacje o wszystkich istniejacych urlopach, pracownikach, oraz menadzerach
 */
public class Dane {
    /**
     * lista z danymi wszystkich menadzerow
     */
    private ArrayList<Menadzer> menadzerzy;
    /**
     * lista z danymi wszystkich pracownikow
     */
    private ArrayList<Pracownik> pracownicy;
    /**
     * lista ze wszystkimi urlopami
     */
    private ArrayList<Urlop> urlopy;
    /**
     * plik, do ktorego zapisywane sa dane menadzerow
     */
    private File plikMenadzerow;
    /**
     * plik, do ktorego zapisywane sa dane pracownikow
     */
    private File plikPracownikow;
    /**
     * plik, do ktorego zapisywane sa dane urlopow
     */
    private File plikUrlopow;

    /**
     *
     * @return wszyscy zarejestrowani pracownicy jako ArrayList
     */
    public ArrayList<Pracownik> getPracownicy() {
        return pracownicy;
    }

    /**
     *
     * @return wszyscy zarejestrowani menadzerzy jako ArrayList
     */
    public ArrayList<Menadzer> getMenadzerzy() {
        return menadzerzy;
    }

    /**
     *
     * @return wszystkie zarejestrowane urlopy jako ArrayList
     */
    public ArrayList<Urlop> getUrlopy() {
        return urlopy;
    }

    /**
     * Konstruktor bazy danych, nie wymaga parametrow i uzywa domyslnych nazw plikow csv
     */
    public Dane(){
        menadzerzy = new ArrayList<>();
        pracownicy = new ArrayList<>();
        urlopy = new ArrayList<>();
        plikMenadzerow = new File("menadzerzy.csv");
        plikPracownikow = new File("pracownicy.csv");
        plikUrlopow = new File("urlopy.csv");
        ladujPracownikow();
        ladujUrlopy();
        ladujMenadzerow();
        for(Pracownik p: pracownicy){
            for(Urlop u: urlopy){
                if(u.getAplikujacyPracownik().equals(p) && u.jestRozpatrzony())
                    p.dodajUrlop(u);
            }
        }
    }

    /**
     * Konstruktor bazy danych, nazwa kazdego z uzywanych plikow csv jest zdefiniowana w parametrze
     * @param nazwaPlikuMenadzerow nazwa pliku przechowujacego dane menadzerow
     * @param nazwaPlikuPracownikow nazwa pliku przechowujacego dane pracownikow
     * @param nazwaPlikuUrlopow nazwa pliku przechowujacego dane urlopow
     */
    public Dane(String nazwaPlikuMenadzerow, String nazwaPlikuPracownikow, String nazwaPlikuUrlopow){
        menadzerzy = new ArrayList<>();
        pracownicy = new ArrayList<>();
        urlopy = new ArrayList<>();
        plikMenadzerow = new File(nazwaPlikuMenadzerow);
        plikPracownikow = new File(nazwaPlikuPracownikow);
        plikUrlopow = new File(nazwaPlikuUrlopow);
        ladujPracownikow();
        ladujUrlopy();
        ladujMenadzerow();
        for(Pracownik p: pracownicy){
            for(Urlop u: urlopy){
                if(u.getAplikujacyPracownik().equals(p) && u.jestRozpatrzony())
                    p.dodajUrlop(u);
            }
        }
    }

    /**
     * Metoda pozwalajaca dodac nowa osobe do bazy danych
     * @param o osoba ktora ma zostac dodana, moze byc to pracownik albo menadzer
     */
    public void dodajOsobe(Osoba o){
        boolean istnieje = false;
        //kod dla menadzera
        if(o instanceof Menadzer){
            for(Menadzer m: menadzerzy) {
                if (m.getLogin().equals(o.getLogin())){
                    JOptionPane.showMessageDialog(null, "Taki menedzer juz istnieje.");
                    istnieje = true;
                    break;
                }
            }
            if(!istnieje)
                menadzerzy.add((Menadzer) o);
        }
        //kod dla pracownika
        else {
            for(Pracownik p: pracownicy) {
                if (p.getLogin().equals(o.getLogin())){
                    JOptionPane.showMessageDialog(null, "Taki pracownik juz istnieje.");
                    istnieje = true;
                    break;
                }
            }
            if(!istnieje)
                pracownicy.add((Pracownik) o);
        }

    }

    /**
     * Metoda szukajaca w bazie pracownika o danym loginie i zwracajaca jego informacje
     * @param login login pracownika, ktory jest wyszukiwany w bazie
     * @return pracownik o danym loginie
     */
    public Pracownik znajdzPracownika(String login){
        for(Pracownik p: pracownicy){
            if(p.getLogin().equals(login)){
                return p;
            }
        }
        return null;
    }

    /**
     * Metoda szukajaca w bazie menadzera o danym loginie i zwracajaca jego informacje
     * @param login login menadzera, ktory jest wyszukiwany w bazie
     * @return menadzer o danym loginie
     */
    public Menadzer znajdzMenadzera(String login){
        for(Menadzer m: menadzerzy) {
            if (m.getLogin().equals(login))
                return m;
        }
        return null;
    }

    /**
     * Metoda dodajaca urlop do bazy danych
     * @param u urlop, ktory ma zostac dodany
     */
    public void dodajUrlop(Urlop u){
        urlopy.add(u);
    }

    /**
     *
     * @return wszystkie aplikacje o urlop, w ktorych pole rozpatrzenia ma wartosc false (sa one nierozpatrzone)
     */
    public ArrayList<Urlop> getNierozpatrzoneUrlopy(){
        ArrayList<Urlop> urlopyDoRozpatrzenia = new ArrayList<>();
        for(Urlop u: urlopy){
            if(!u.jestRozpatrzony()){
                urlopyDoRozpatrzenia.add(u);
            }
        }
        return urlopyDoRozpatrzenia;
    }

    /**
     * Metoda zapisuje dane wszystkich pracownikow do pliku csv
     */
    public void zapiszPracownikow(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(plikPracownikow));
            bw.write("Login,Haslo,Imie,Nazwisko,Dni Urlopowe");
            bw.newLine();
            for(Pracownik p: pracownicy){
                bw.write(p.getLogin()+","+p.getHaslo()+","+p.getImie()+","+p.getNazwisko()+","
                        +p.getDniUrlopowe());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.err.println("Wystapil blad przy zapisywaniu danych pracownikow");
        }

    }
    /**
     * Metoda zapisuje dane wszystkich urlopow do pliku csv
     */
    public void zapiszUrlopy(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(plikUrlopow));
            bw.write("Aplikujacy Pracownik,Data Rozpoczecia,Dlugosc,Zaakceptowanie,Rozpatrzenie");
            bw.newLine();
            for(Urlop u: urlopy){
                bw.write(u.getAplikujacyPracownik().getLogin()+","+u.getDataRozpoczecia()+","+u.getDlugosc()+","+u.jestZaakceptowany()+","+u.jestRozpatrzony());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.err.println("Wystapil blad przy zapisywaniu danych urlopow");
        }

    }

    /**
     * Metoda zapisuje dane wszystkich menadzerow do pliku csv
     */
    public void zapiszMenadzerow(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(plikMenadzerow));
            bw.write("Login,Haslo,Imie,Nazwisko,Zaakceptowane Urlopy,Odrzucone Urlopy");
            bw.newLine();
            for(Menadzer m: menadzerzy){
                bw.write(m.getLogin()+","+m.getHaslo()+","+m.getImie()+","+m.getNazwisko()+","
                        +m.getZaakceptowaneUrlopy()+","+m.getOdrzuconeUrlopy());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.err.println("Wystapil blad przy zapisywaniu danych pracownikow");
        }

    }

    /**
     * Metoda laduje dane wszystkich pracownikow z pliku csv
     */
    public void ladujPracownikow(){
        if(plikPracownikow.canRead()){
        ArrayList<String[]> linijki = new ArrayList<>();
        //wczytuje wszystkie linijki pliku do nowej arraylisty
        try {
            BufferedReader br = new BufferedReader(new FileReader(plikPracownikow));
            String linijka = "";
            while ((linijka = br.readLine()) != null) {
                linijki.add(linijka.split(","));
            }
            //starts at 1 because line 0 are the headers in the file
            for (int i=1;i<linijki.size();i++){
                String[] obecnaLinijka = linijki.get(i);
                Pracownik obecnyPracownik = new Pracownik(obecnaLinijka[0], obecnaLinijka[1],
                        obecnaLinijka[2], obecnaLinijka[3]);
                obecnyPracownik.setDniUrlopowe(Integer.parseInt(obecnaLinijka[4]));
                pracownicy.add(obecnyPracownik);
            }
            br.close();
        }
        catch (IOException e) {
            System.err.println("Wystapil blad przy ladowaniu danych pracownikow.");
        }}
    }
    /**
     * Metoda laduje dane wszystkich urlopow z pliku csv
     */
    public void ladujUrlopy(){
        if(plikUrlopow.canRead()){
        ArrayList<String[]> linijki = new ArrayList<>();
        //wczytuje wszystkie linijki pliku do nowej arraylisty
        try {
            BufferedReader br = new BufferedReader(new FileReader(plikUrlopow));
            String linijka = "";
            while ((linijka = br.readLine()) != null) {
                linijki.add(linijka.split(","));
            }
            //starts at 1 because line 0 are the headers in the file
            for (int i=1;i<linijki.size();i++){
                String[] obecnaLinijka = linijki.get(i);
                Pracownik obecnyPracownik = znajdzPracownika(obecnaLinijka[0]);
                Urlop obecnyUrlop = new Urlop(obecnyPracownik,obecnaLinijka[1],Integer.parseInt(obecnaLinijka[2]));
                if(obecnaLinijka[3].equals("true"))
                    obecnyUrlop.setZaakceptowanie(true);
                if(obecnaLinijka[4].equals("true"))
                    obecnyUrlop.setRozpatrzenie(true);
                urlopy.add(obecnyUrlop);
            }
            br.close();
        }
        catch (IOException e) {
            System.err.println("Wystapil blad przy ladowaniu danych urlopow");
        }}
    }

    /**
     * Metoda laduje dane wszystkich menadzerow z pliku csv
     */
    public void ladujMenadzerow(){
        if(plikMenadzerow.canRead()){
        ArrayList<String[]> linijki = new ArrayList<>();
        //wczytuje wszystkie linijki pliku do nowej arraylisty
        try {
            BufferedReader br = new BufferedReader(new FileReader(plikMenadzerow));
            String linijka = "";
            while ((linijka = br.readLine()) != null) {
                linijki.add(linijka.split(","));
            }
            //starts at 1 because line 0 are the headers in the file
            for (int i=1;i<linijki.size();i++){
                String[] obecnaLinijka = linijki.get(i);
                Menadzer obecnyMenadzer = new Menadzer(obecnaLinijka[0], obecnaLinijka[1],
                        obecnaLinijka[2], obecnaLinijka[3]);
                obecnyMenadzer.setZaakceptowaneUrlopy(Integer.parseInt(obecnaLinijka[4]));
                obecnyMenadzer.setOdrzuconeUrlopy(Integer.parseInt(obecnaLinijka[5]));
                menadzerzy.add(obecnyMenadzer);
            }
            br.close();
        }
        catch (IOException e) {
            System.err.println("Wystapil blad przy ladowaniu danych pracownikow.");
        }}
    }






}


