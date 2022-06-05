import java.util.ArrayList;

/**
 * Klasa pracownika dziedziczaca klase osoby
 */
public class Pracownik extends Osoba {
    /**
     * ilosc dni urlopowych dostepnych dla pracownika
     */
    private int dniUrlopowe;
    /**
     * wszystkie rozpatrzone urlopy pracownika
     */
    private ArrayList<Urlop> urlopy;

    /**
     * Konstruktor klasy pracownika
     * @param login login nowego pracownika
     * @param haslo  haslo nowego pracownika
     * @param imie imie nowego pracownika
     * @param nazwisko  nazwisko nowego pracownika
     */
    public Pracownik(String login, String haslo, String imie, String nazwisko) {
        super(login, haslo, imie, nazwisko);
        dniUrlopowe = 25;
        urlopy = new ArrayList<>();


    }

    /**
     *
     * @return ilosc dni urlopowych posiadanych przez pracownika
     */
    public int getDniUrlopowe() {
        return dniUrlopowe;
    }

    /**
     *
     * @return wszystkie urlopy posiadane przez pracownika
     */
    public ArrayList<Urlop> getUrlopy() {
        return urlopy;
    }

    /**
     * Dodaje urlop do listy urlopow danego pracownika
     * @param u urlop, ktory bedzie dodany
     */
    public void dodajUrlop(Urlop u){
        urlopy.add(u);
    }

    /**
     * Ustawia ilosc dni urlopowych posiadanych przez pracownika
     * @param dniUrlopowe nowa ilosc dni urlopowych
     */
    public void setDniUrlopowe(int dniUrlopowe) {
        this.dniUrlopowe = dniUrlopowe;
    }
}
