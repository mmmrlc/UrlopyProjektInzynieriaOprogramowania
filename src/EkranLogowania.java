import javax.swing.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Klasa odpowiadajaca za interfejs uzytkownika
 */
public class EkranLogowania extends JFrame {
    /**
     * baza danych uzywana przez aplikacje
     */
    private Dane baza;
    /**
     * glowny panel aplikacji, w ktorym znajduja sie panele logowania, pracownika, oraz menadzera
     */
    private JPanel panelGlowny;
    /**
     * panel, w ktorym znajduje sie ekran logowania
     */
    private JPanel loginPanel;
    /**
     * panel, w ktorym znajduje sie ekran pracownika
     */
    private JPanel pracownikPanel;
    /**
     * panel, w ktorym znajduje sie ekran menadzera
     */
    private JPanel menedzerPanel;
    /**
     * przycisk odpowiedzialny za logowanie sie do aplikacji
     */
    private JButton zalogujButton;
    /**
     * pole tekstowe, do ktorego wpisywany jest login uzytkownika
     */
    private JTextField loginTekst;
    /**
     * pole tekstowe, do ktorego wpisywane jest haslo uzytkownika
     */
    private JPasswordField hasloTekst;
    /**
     * pole tekstowe, w ktorym wyswietlane sa poprzednie urlopy pracownika
     */
    private JTextArea ubiegleUrlopy;
    /**
     * pole tekstowe, gdzie wyswietlany jest ostatni rozpatrzony urlop, o ktory aplikowal pracownik
     */
    private JTextField najblizszyUrlop;
    /**
     * pole tekstowe, gdzie wyswietlana jest ilosc pozostalych dni urlopowych pracownika
     */
    private JTextField pozostaleDniUrlopowe;
    /**
     * przycisk odpowiedzialny za aplikowanie o urlop przez pracownika
     */
    private JButton aplikujButton;
    /**
     * pole tekstowe, gdzie wpisywana jest data rozpoczecia urlopu podczas aplikowania o niego
     */
    private JTextField dataRozpoczecia;
    /**
     * pole tekstowe, gdzie wpisywana jest dlugosc urlopu podczas aplikowania o niego
     */
    private JTextField dlugoscUrlopu;
    /**
     * combo box, gdzie menadzer ma dostep do wszystkich nierozpatrzonych aplikacji o urlop i moze je rozpatrzac poprzez nacisniecie na jeden z nich
     */
    private JComboBox nierozpatrzoneUrlopy;
    /**
     * combo box, gdzie menadzer ma dostep do wszystkich pracownikow i moze przejrzec ich wszystkie urlopy poprzez nacisniecie na jednego z nich
     */
    private JComboBox pracownicy;
    /**
     * pole tekstowe, gdzie wyswietlane sa wszystkie urlopy (rozpatrzone oraz nierozpatrzone) pracownika, na ktorego nacisnal menadzer
     */
    private JTextArea urlopyWybranegoPracownika;
    /**
     * etykieta wyswietlajaca imie i nazwisko zalogowanego menadzera
     */
    private JLabel imieNazwiskoMenadzera;
    /**
     * etykieta wyswietlajaca imie i nazwisko zalogowanego pracownika
     */
    private JLabel imieNazwiskoPracownika;
    /**
     * etykieta wyswietlajaca ilosc urlopow zaakceptowanych przez menadzera
     */
    private JLabel iloscZaakceptowanychUrlopow;
    /**
     * etykieta wyswietlajaca ilosc urlopow odrzuconych przez menadzera
     */
    private JLabel iloscOdrzuconychUrlopow;
    /**
     * pracownik obecnie zalogowany do aplikacji
     */
    private Pracownik zalogowanyPracownik;
    /**
     * menadzer obecnie zalogowany do aplikacji
     */
    private Menadzer zalogowanyMenadzer;
    /**
     * wartosc boolean sprawdzajaca, czy ekran menadzera jest ladowany po raz pierwszy od wlaczenia aplikacji czy byl juz wczesniej ladowany
     */
    private boolean pierwszeLadowanie;

    /**
     * Konstruktor interfejsu uzytkownika
     * @param bazaDanych baza danych uzywana przez aplikacje
     */
    public EkranLogowania(Dane bazaDanych) {
        pierwszeLadowanie = true;
        baza = bazaDanych;
        setContentPane(loginPanel);
        setSize(380, 500);
        setResizable(false);
        setTitle("Ekran Logowania");
        setVisible(true);
        getRootPane().setDefaultButton(zalogujButton); //pozwala na zalogowanie sie enterem
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter()
        {
            /**
             * Metoda przywolywana, kiedy okienko aplikacji zostanie zamkniete. Zapisuje ona wszystkie dane do plikow csv
             */
            public void windowClosing(WindowEvent we)
            {
                baza.zapiszPracownikow();
                baza.zapiszUrlopy();
                baza.zapiszMenadzerow();
        }});


        zalogujButton.addActionListener(new ActionListener() {
            /**
             * Metoda przywolywana, kiedy zostanie nacisniety przycisk logowania do aplikacji
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Pracownik p = baza.znajdzPracownika(loginTekst.getText());
                Menadzer m = baza.znajdzMenadzera(loginTekst.getText());
                String wpisaneHaslo = new String(hasloTekst.getPassword());

                //przypadek gdzie login nie istnieje
                if (p == null && m == null) {
                    JOptionPane.showMessageDialog(null, "Nieprawidlowy login.");
                }
                //przypadek gdzie login nalezy do pracownika
                else if (m == null) {
                    logujPracownika(p, wpisaneHaslo);

                }
                //przypadek gdzie login nalezy do menadzera
                else {
                    logujMenadzera(m, wpisaneHaslo);
                }
            }
        });


        aplikujButton.addActionListener(new ActionListener() {
            /**
             * Metoda przywolywana, kiedy zostanie nacisniety przycisk aplikowania o urlop
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String czasUrlopu = dlugoscUrlopu.getText();
                boolean prawidlowaData = sprawdzDate(dataRozpoczecia.getText());
                if(czasUrlopu.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Wpisz dlugosc urlopu");
                }
               else if (!prawidlowaData) {
                    JOptionPane.showMessageDialog(null, "Nieprawidlowa data rozpoczecia urlopu (dd.mm.yyyy)");
                } else if (Integer.parseInt(czasUrlopu)<=zalogowanyPracownik.getDniUrlopowe()) {
                    Urlop aplikacja = new Urlop(zalogowanyPracownik, dataRozpoczecia.getText(), Integer.parseInt(czasUrlopu));
                    baza.dodajUrlop(aplikacja);
                    zalogowanyPracownik.setDniUrlopowe(zalogowanyPracownik.getDniUrlopowe()-aplikacja.getDlugosc());
                    JOptionPane.showMessageDialog(null, "Aplikacja o urlop zostala wyslana");
                    ladujEkranPracownika();
                }
               else{
                   JOptionPane.showMessageDialog(null,"Nie masz tylu pozostalych dni urlopowych");
                }
            }


        });

        dlugoscUrlopu.addKeyListener(new KeyAdapter() {
            /**
             * Metoda sprawiajaca, ze do pola dlugoscUrlopu mozna wpisywac tylko liczby
             */
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });


        nierozpatrzoneUrlopy.addActionListener(new ActionListener() {
            /**
             * Metoda odpowiedzialna za funkcjonalosc akceptowania lub odrzucania nieodrzuconych urlopow przez menadzera
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nierozpatrzoneUrlopy.getItemCount()>0 && !nierozpatrzoneUrlopy.getItemAt(0).equals("Brak urlopow do rozpatrzenia") && !pierwszeLadowanie){
                int wybranyIndex = nierozpatrzoneUrlopy.getSelectedIndex();
                Urlop wybranyUrlop = baza.getNierozpatrzoneUrlopy().get(wybranyIndex); //zwraca urlop na ktory menedzer kliknal w combo boxie
                Pracownik aplikant = wybranyUrlop.getAplikujacyPracownik();
                UIManager.put("OptionPane.yesButtonText", "Tak");
                UIManager.put("OptionPane.noButtonText", "Nie");
                int result = JOptionPane.showConfirmDialog(null,"Czy chcesz zaakceptowac ten urlop?\n"+ nierozpatrzoneUrlopy.getSelectedItem(), "Rozpatrzenie Aplikacji o Urlop",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result==JOptionPane.YES_OPTION){ // kod dla urlopu zaakceptowanego
                    zalogowanyMenadzer.inkrementujZaakceptowane();
                    wybranyUrlop.setRozpatrzenie(true);
                    wybranyUrlop.setZaakceptowanie(true);
                    aplikant.dodajUrlop(wybranyUrlop);
                    JOptionPane.showMessageDialog(null,"Pomyslnie zaakceptowano aplikacje o urlop");
                }
                else if (result == JOptionPane.NO_OPTION){ // kod dla urlopu odrzuconego
                    zalogowanyMenadzer.inkrementujOdrzucone();
                    wybranyUrlop.setRozpatrzenie(true);
                    wybranyUrlop.setZaakceptowanie(false);
                    aplikant.dodajUrlop(wybranyUrlop);
                    aplikant.setDniUrlopowe(aplikant.getDniUrlopowe()+wybranyUrlop.getDlugosc());
                    JOptionPane.showMessageDialog(null,"Pomyslnie odrzucono aplikacje o urlop");
                }
                pracownicy.removeAllItems();
                ladujEkranMenadzera();
                urlopyWybranegoPracownika.setText("");

            }}
        });


        pracownicy.addActionListener(new ActionListener() {
            /**
             * Metoda odpowiedzialna za wyswietlanie urlopow poszczegolnego pracownika wybranego przez menadzera
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pracownicy.getItemCount()>0){
                urlopyWybranegoPracownika.setText("");
                Pracownik p = baza.getPracownicy().get(pracownicy.getSelectedIndex());
                for(Urlop u: baza.getUrlopy()){
                    if(u.getAplikujacyPracownik().equals(p)){
                        urlopyWybranegoPracownika.append(u.toString());
                        if(u.jestRozpatrzony()) {
                            urlopyWybranegoPracownika.append(" - rozpatrzony");
                            if(u.jestZaakceptowany()){
                                urlopyWybranegoPracownika.append(" - zaakceptowany");
                            }
                            else{
                                urlopyWybranegoPracownika.append(" - odrzucony");
                            }
                        }
                        else
                            urlopyWybranegoPracownika.append(" - nierozpatrzony");
                        urlopyWybranegoPracownika.append("\n");
                    }


                }}
            }
        });
    }

    /**
     * Metoda odpowiedzialna za logowanie do ekranu pracownika. Sprawdza ona czy wpisane haslo jest poprawne
     * @param p logujacy sie pracownik
     * @param haslo haslo wpisane do pola tekstowego
     */
    private void logujPracownika(Pracownik p,String haslo){
        if(haslo.equals(p.getHaslo())){
            setContentPane(pracownikPanel);
            setVisible(true);
            setTitle("Ekran Pracownika");
            JOptionPane.showMessageDialog(null, "Poprawnie zalogowano do ekranu pracownika");
            zalogowanyPracownik = p;
            ladujEkranPracownika();
        }
        else{
            JOptionPane.showMessageDialog(null, "Niepoprawne haslo");
        }
    }

    /**
     * Metoda odpowiedzialna za logowanie do ekranu menadzera. Sprawdza ona czy wpisane haslo jest poprawne
     * @param m logujacy sie menadzer
     * @param haslo haslo wpisane do pola tekstowego
     */
    private void logujMenadzera(Menadzer m, String haslo){
        if(haslo.equals(m.getHaslo())){
            setContentPane(menedzerPanel);
            setVisible(true);
            setTitle("Ekran Menadzera");
            JOptionPane.showMessageDialog(null, "Poprawnie zalogowano do ekranu menadzera");
            zalogowanyMenadzer = m;
            ladujEkranMenadzera();
        }
        else{
            JOptionPane.showMessageDialog(null, "Niepoprawne haslo.");
        }
    }

    /**
     * Metoda odpowiedzialna za ladowanie ekranu pracownika. Umieszcza ona dane o urlopach logujacego sie pracownika oraz jego dostepnych dniach urlopowych
     */
    private void ladujEkranPracownika(){
        imieNazwiskoPracownika.setText("Witaj, "+zalogowanyPracownik.getImie()+" "+zalogowanyPracownik.getNazwisko());
        ubiegleUrlopy.setText("");
        ArrayList<Urlop> wzieteUrlopy = zalogowanyPracownik.getUrlopy();
        pozostaleDniUrlopowe.setText(Integer.toString(zalogowanyPracownik.getDniUrlopowe()));
        if(wzieteUrlopy.isEmpty()){
            najblizszyUrlop.setText("Brak zaplanowanego urlopu");
            ubiegleUrlopy.setText("Brak urlopow do wyswietlenia");
        }
        else{
            Urlop najblizszy = znajdzNajblizszyUrlop(wzieteUrlopy);
            if(najblizszy == null){
                najblizszyUrlop.setText("Brak zaplanowanego urlopu");
            }
            else{
                najblizszyUrlop.setText(najblizszy.getDataRozpoczecia()+" - "+najblizszy.getDlugosc()+" dni");
            }
            for(Urlop u: wzieteUrlopy){
                if(!u.equals(najblizszy)){
                ubiegleUrlopy.append(u.toString());
                if(u.jestZaakceptowany()){
                    ubiegleUrlopy.append(" - zaakceptowany");
                }
                else{
                    ubiegleUrlopy.append(" - odrzucony");
                }
                ubiegleUrlopy.append("\n");
            }}
        }
    }

    /**
     * Metoda odpowiedzialna za ladowanie ekranu menadzera. Pokazuje ona ilosc urlopow ktore zaakceptowal i odrzucil, wszystkich pracownikaw, oraz nierozpatrzone urlopy
     * @return Wszystkie nierozpatrzone urlopy jako ArrayList
     */
    private ArrayList<Urlop> ladujEkranMenadzera(){
        imieNazwiskoMenadzera.setText("Witaj, "+zalogowanyMenadzer.getImie()+" "+zalogowanyMenadzer.getNazwisko());
        iloscZaakceptowanychUrlopow.setText("Zaakceptowane Urlopy: "+zalogowanyMenadzer.getZaakceptowaneUrlopy());
        iloscOdrzuconychUrlopow.setText("Odrzucone Urlopy: "+zalogowanyMenadzer.getOdrzuconeUrlopy());
        nierozpatrzoneUrlopy.removeAllItems();
        urlopyWybranegoPracownika.setText("");
        ArrayList<Urlop> urlopyDoRozpatrzenia = baza.getNierozpatrzoneUrlopy();
        for(Urlop u: urlopyDoRozpatrzenia){
            Pracownik aplikujacy = u.getAplikujacyPracownik();
            nierozpatrzoneUrlopy.addItem(aplikujacy.getImie()+" "+aplikujacy.getNazwisko()+" - data rozpoczecia - "+u.toString());
        }
        if(urlopyDoRozpatrzenia.isEmpty()){
            nierozpatrzoneUrlopy.addItem("Brak urlopow do rozpatrzenia");
        }
        for(Pracownik p: baza.getPracownicy()){
            pracownicy.addItem(p.getImie()+" "+p.getNazwisko());
        }
        pierwszeLadowanie = false;
        return urlopyDoRozpatrzenia;
    }

    /**
     *
     * @param wszystkieUrlopy wszystkie rozpatrzone urlopy danego pracownika
     * @return ostatni urlop, o ktory zaaplikowal pracownik
     */
    private Urlop znajdzNajblizszyUrlop(ArrayList<Urlop> wszystkieUrlopy){
        boolean najblizszyUrlopZnaleziony = false;
        Urlop ostatniUrlop = null;
        int i = wszystkieUrlopy.size()-1;
        while(!najblizszyUrlopZnaleziony&&i>=0){
            ostatniUrlop = wszystkieUrlopy.get(i);
            if(ostatniUrlop.jestZaakceptowany()){
                najblizszyUrlopZnaleziony = true;
            }
            i--;
        }
        return ostatniUrlop;
}

    /**
     * Metoda sprawdza, czy wpisana data odpowiada istniejacemu dniowi w kalendarzu, jest zapisana w poprawnym formacie (dd.mm.yy), oraz znajduje sie w przyszlosci
     * @param data data wpisana do pola tekstowego
     * @return wartosc true albo false, true w przypadku prawidlowej daty oraz false w przypadku nieprawidlowej daty
     */
    private boolean sprawdzDate(String data){
        try{
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            format.setLenient(false);
            Date dzien = format.parse(data);
            if(dzien.after(new Date())){
                return true;}
            else{
                return false;
            }

        } catch(ParseException e){
            return false;
        }
    }

    /**
     * Metoda main odpowiadajaca za uruchomenie aplikacji
     */
    public static void main(String[] args) {
        Dane baza = new Dane();
        EkranLogowania login = new EkranLogowania(baza);
    }




}
