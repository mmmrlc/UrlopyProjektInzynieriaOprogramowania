/**
 * Klasa urlopu
 */
public class Urlop {
    /**
     * pracownik aplikujacy o urlop
     */
    private Pracownik aplikujacyPracownik;
    /**
     * data rozpoczecia urlopu
     */
    private String dataRozpoczecia;
    /**
     * dlugosc urlopu
     */
    private int dlugosc;
    /**
     *  wartosc boolean pokazujaca, czy aplikacja o urlop zostala zaakceptowana
     */
    private boolean zaakceptowanie;
    /**
     * wartosc boolean pokazujaca, czy aplikacja o urlop zostala rozpatrzona
     */
    private boolean rozpatrzenie;

    /**
     * Konstruktor klasy urlopu
     * @param p pracownik, ktory aplikuje o urlop
     * @param data data rozpoczecia urlopu
     * @param dni dlugosc urlopu
     */
    public Urlop(Pracownik p, String data, int dni){
        aplikujacyPracownik = p;
        dataRozpoczecia = data;
        dlugosc = dni;
        zaakceptowanie = false;
        rozpatrzenie = false;
    }

    /**
     *
     * @return zwraca pracownika, ktory aplikuje o urlop
     */
    public Pracownik getAplikujacyPracownik() {
        return aplikujacyPracownik;
    }

    /**
     *
     * @return zwraca date rozpoczecia urlopu
     */
    public String getDataRozpoczecia() {
        return dataRozpoczecia;
    }

    /**
     *
     * @return zwraca dlugosc urlopu
     */
    public int getDlugosc() {
        return dlugosc;
    }

    /**
     *
     * @return zwraca status zaakceptowania urlopu
     */
    public boolean jestZaakceptowany() {
        return zaakceptowanie;
    }

    /**
     *
     * @return zwraca status rozpatrzenia urlopu
     */
    public boolean jestRozpatrzony() {return rozpatrzenie;}

    /**
     * Ustawia wartosc pola zaakceptowanie, pozwalajac na odrzucenie lub zaakceptowanie urlopu
     * @param decyzja decyzja o tym, czy urlop zostal zaakceptowany czy odrzucony
     */
    public void setZaakceptowanie(boolean decyzja){
        zaakceptowanie = decyzja;
    }

    /**
     * Ustawia wartosc pola odpowiadajacego za status rozpatrzenia urlopu
     * @param decyzja decyzja o tym, czy urlop jest rozpatrzony czy nierozpatrzony
     */
    public void setRozpatrzenie(boolean decyzja) { rozpatrzenie = decyzja; }

    /**
     *
     * @return String zawierajacy date rozpoczecia i dlugosc urlopu
     */
    public String toString(){
        return getDataRozpoczecia()+" - "+getDlugosc()+" dni";
    }
}

