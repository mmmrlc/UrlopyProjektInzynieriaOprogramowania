/**
 * Klasa menadzera dziedziczaca klase osoby
 */
public class Menadzer extends Osoba {
    /**
     * ilosc urlopow odrzuconych przez menadzera
     */
    private int odrzuconeUrlopy;
    /**
     * ilosc urlopow zaakceptowanych przez menadzera
     */
    private int zaakceptowaneUrlopy;

    /**
     * Konstruktor klasy menadzera
     * @param login login nowego menadzera
     * @param haslo  haslo nowego menadzera
     * @param imie imie nowego menadzera
     * @param nazwisko  nazwisko nowego menadzera
     */
    public Menadzer(String login, String haslo, String imie, String nazwisko){

        super(login, haslo, imie, nazwisko);
        odrzuconeUrlopy = 0;
        zaakceptowaneUrlopy = 0;
    }

    /**
     *
     * @return ilosc urlopow, ktore odrzucil menadzer
     */
    public int getOdrzuconeUrlopy() {
        return odrzuconeUrlopy;
    }

    /**
     *
     * @return ilosc urlopow, ktore zaakceptowal menadzer
     */
    public int getZaakceptowaneUrlopy() {
        return zaakceptowaneUrlopy;
    }

    /**
     * Zmienia wartosc w polu odrzucone urlopy
     * @param odrzuconeUrlopy nowa ilosc odrzuconych urlopow
     */
    public void setOdrzuconeUrlopy(int odrzuconeUrlopy) {
        this.odrzuconeUrlopy = odrzuconeUrlopy;
    }

    /**
     * Zmienia wartosc w polu zaakceptowane urlopy
     * @param zaakceptowaneUrlopy nowa ilosc zaakceptowanych urlopow
     */
    public void setZaakceptowaneUrlopy(int zaakceptowaneUrlopy) {
        this.zaakceptowaneUrlopy = zaakceptowaneUrlopy;
    }

    /**
     * Inkrementuje ilosc odrzuconych urlopow
     */
    public void inkrementujOdrzucone(){
        odrzuconeUrlopy++;
    }

    /**
     * Inkrementuje ilosc zaakceptowanych urlopow
     */
    public void inkrementujZaakceptowane(){
        zaakceptowaneUrlopy++;
    }


}


