/**
 * Ogolna klasa osoby
 */
public class Osoba {
    /**
     * login uzytkownika
     */
    private String login;
    /**
     * haslo uzytkownika
     */
    private String haslo;
    /**
     * imie uzytkownika
     */
    private String imie;
    /**
     * nazwisko uzytkownika
     */
    private String nazwisko;

    /**
     * Konstruktor klasy osoby
     * @param login login nowej osoby
     * @param haslo  haslo nowej osoby
     * @param imie imie nowej osoby
     * @param nazwisko  nazwisko nowej osoby
     */
    public Osoba(String login, String haslo, String imie, String nazwisko) {
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    /**
     *
     * @return zwraca login danej osoby
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @return zwraca haslo danej osoby
     */
    public String getHaslo() {
        return haslo;
    }

    /**
     *
     * @return zwraca imie danej osoby
     */
    public String getImie() {
        return imie;
    }

    /**
     *
     * @return zwraca nazwisko danej osoby
     */
    public String getNazwisko() {
        return nazwisko;
    }


}
