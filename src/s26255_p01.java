import java.util.Scanner;

//GITHUB - Jullew -> SzachyGIT
//Szachy v1
//Autor: Julian Lewandowski
//PJATK Podstawy programowania w Javie (Aktualizacja 12.12.2021)
//TODO Zliczanie zbitych pionków, sprawdzanie szach/mat oraz bicie wielu pionków w jednym ruchu

public class s26255_p01 {

    enum Gracz {
        BIALY,
        CZARNY
    }

    static boolean mat = false;

    static Gracz aktualnyGracz = Gracz.BIALY;
    static boolean zlyRuch = false;

    static int[][] szachownica = {
            {102, 103, 104, 105, 106, 104, 103, 102},   //BIAŁE 102 WIEŻA, 103 KOŃ, 104 GONIEC, 105 HETMAN, 106 KRÓL
            {101, 101, 101, 101, 101, 101, 101, 101},   //BIAŁE
            {888, 888, 888, 888, 888, 888, 888, 888},
            {888, 888, 888, 888, 888, 888, 888, 888},
            {888, 888, 888, 888, 888, 888, 888, 888},
            {888, 888, 888, 888, 888, 888, 888, 888},
            {111, 111, 111, 111, 111, 111, 111, 111}, // CZARNE
            {112, 113, 114, 115, 116, 114, 113, 112}}; // CZARNE 112 WIEŻA, 113 KOŃ, 114 GONIEC, 115 HETMAN, 116 KRÓL

    public static void main(String[] args) {


        wyswietlSzachownice();
        while (true) {
            do {
                if (zlyRuch == true) {
                    System.out.println("Niepoprawny ruch! Powtórz..");
                }

                String ruch = pobierzRuch();
                int x1 = getY1(ruch);
                int y1 = getX1(ruch);
                int x2 = getY2(ruch);
                int y2 = getX2(ruch);

//            mat = sprawdzMata();
           /* if(mat) {
                System.out.println("Zwyciezył " + aktualnyGracz.name());
                break;
            }*/

                sprawdzRuch(x1, y1, x2, y2);
            }
            while (zlyRuch == true);
            wyswietlSzachownice();
            zmienGracza();
        }
    }

    public static int sprawdzPionekX1Y1(int x1, int y1) {
        int pionek = szachownica[x1][y1];

        return pionek;
    }

    // TODO warto poniższe zoptymalizować
    public static void sprawdzRuch(int x1, int y1, int x2, int y2) {
        int pionek = sprawdzPionekX1Y1(x1, y1);
        int powtorz = 0;
        int roznicaX = Math.abs(x1 - x2);
        int roznicaY = Math.abs(y1 - y2);

        if (x1 < 8 && x2 < 8 && y1 < 8 && y2 < 8) {
            if ((aktualnyGracz == Gracz.CZARNY && pionek > 110) || (aktualnyGracz == Gracz.BIALY && pionek < 110)) {
                if ((aktualnyGracz == Gracz.BIALY && szachownica[x2][y2] > 110) ||
                        (aktualnyGracz == Gracz.CZARNY && (szachownica[x2][y2] < 110 || szachownica[x2][y2] == 888))) { // spr. ataku swojego pionka
                    if (pionek != 888) { // sprawdź czy pionek jest na pozycji początkowej
                        if ((pionek < 104) || (pionek < 114 && pionek > 110)) { // sortowanie
                            if (pionek == 101 || pionek == 111) { // PION [BIAŁY / CZARNY]
                                if ((x2 > x1 && ((((((x1 == 1 || x1 == 6) && roznicaX < 3) && (y1 == y2)) || //BIAŁY
                                        (x1 > 1 && x1 < 7 && x2 == x1 + 1 && y1 == y2 && roznicaX == 1 && szachownica[x2][y2] == 888)))) || //BIAŁY
                                        (szachownica[x2][y2] != 888 && ((x2 == x1 + 1) && (y2 == y1 + 1) || y2 == y1 - 1))) || // BIAŁY
                                        ((x1 > x2 && ((((((x1 == 1 || x1 == 6) && roznicaX < 3) && (y1 == y2)) || //CZARNY
                                                (x1 > 1 && x1 < 7 && x2 == x1 - 1 && y1 == y2 && roznicaX == 1 && szachownica[x2][y2] == 888)))) || //CZARNY
                                                (szachownica[x2][y2] != 888 && ((x2 == x1 - 1) && (y2 == y1 + 1) || y2 == y1 - 1))))) {// CZARNY

                                    szachownica[x1][y1] = 888;
                                    szachownica[x2][y2] = pionek;
                                    zlyRuch = false;
                                } else {
                                    zlyRuch = true;
                                }
                            } else if (pionek == 102 || pionek == 112) { // WIEŻA
                                if (x1 == x2 && y1 > y2) {
                                    for (int i = y1 - 1; i != y2; i--) {
                                        if (szachownica[x1][i] != 888) {
                                            powtorz = 1;
                                            break;
                                        }
                                    }
                                    if (powtorz == 1) {
                                        zlyRuch = true;
                                    } else {
                                        szachownica[x1][y1] = 888;
                                        szachownica[x2][y2] = pionek;
                                        zlyRuch = false;
                                    }
                                } else if (x1 == x2 && y2 > y1) {
                                    for (int i = y1 + 1; i != y2; i++) {
                                        if (szachownica[x1][i] != 888) {
                                            powtorz = 1;
                                            break;
                                        }
                                    }
                                    if (powtorz == 1) {
                                        zlyRuch = true;
                                    } else {
                                        szachownica[x1][y1] = 888;
                                        szachownica[x2][y2] = pionek;
                                        zlyRuch = false;
                                    }
                                    zlyRuch = true;
                                } else if (y1 == y2 && x1 > x2) {
                                    for (int i = x1 - 1; i != x2; i--) {
                                        if (szachownica[i][y1] != 888) {
                                            powtorz = 1;
                                            break;
                                        }
                                    }
                                    if (powtorz == 1) {
                                        zlyRuch = true;
                                    } else {
                                        szachownica[x1][y1] = 888;
                                        szachownica[x2][y2] = pionek;
                                        zlyRuch = false;
                                    }
                                } else if (y1 == y2 && x2 > x1) {
                                    for (int i = x1 + 1; i != x2; i++) {
                                        if (szachownica[i][y1] != 888) {
                                            powtorz = 1;
                                            break;
                                        }
                                    }
                                    if (powtorz == 1) {
                                        zlyRuch = true;
                                    } else {
                                        szachownica[x1][y1] = 888;
                                        szachownica[x2][y2] = pionek;
                                        zlyRuch = false;
                                    }
                                } else {
                                    zlyRuch = true;
                                }
                            } else if (pionek == 103 || pionek == 113) { // KOŃ
                                if ((roznicaX == 2 && roznicaY == 1) || (roznicaY == 2 && roznicaX == 1)) {
                                    szachownica[x1][y1] = 888;
                                    szachownica[x2][y2] = pionek;
                                    zlyRuch = false;
                                } else {
                                    zlyRuch = true;
                                }
                            }
                        } else {
                            if (pionek == 104 || pionek == 114) { // GONIEC
                                if (roznicaX == roznicaY) {
                                    if (x2 > x1 && y2 > y1) {
                                        for (int i = x1 + 1; i != x2; i++) {
                                            for (int j = y1 + 1; j != y2; j++) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (x2 > x1 && y2 < y1) {
                                        for (int i = x1 + 1; i != x2; i++) {
                                            for (int j = y1 - 1; j != y2; j--) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (x2 < x1 && y2 > y1) {
                                        for (int i = x1 - 1; i != x2; i--) {
                                            for (int j = y1 + 1; j != y2; j++) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (x2 < x1 && y2 < y1) {
                                        for (int i = x1 - 1; i != x2; i--) {
                                            for (int j = y1 - 1; j != y2; j--) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else {
                                        zlyRuch = true;
                                    }

                                } else {
                                    zlyRuch = true;
                                }
                            } else if (pionek == 105 || pionek == 115) { // HETMAN
                                if (roznicaX == roznicaY) {
                                    if (x2 > x1 && y2 > y1) {
                                        for (int i = x1 + 1; i != x2; i++) {
                                            for (int j = y1 + 1; j != y2; j++) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (x2 > x1 && y2 < y1) {
                                        for (int i = x1 + 1; i != x2; i++) {
                                            for (int j = y1 - 1; j != y2; j--) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (x2 < x1 && y2 > y1) {
                                        for (int i = x1 - 1; i != x2; i--) {
                                            for (int j = y1 + 1; j != y2; j++) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (x2 < x1 && y2 < y1) {
                                        for (int i = x1 - 1; i != x2; i--) {
                                            for (int j = y1 - 1; j != y2; j--) {
                                                if (szachownica[i][j] != 888) {
                                                    powtorz = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else {
                                        zlyRuch = true;
                                    }

                                } else {
                                    if (x1 == x2 && y1 > y2) {
                                        for (int i = y1 - 1; i != y2; i--) {
                                            if (szachownica[x1][i] != 888) {
                                                powtorz = 1;
                                                break;
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (x1 == x2 && y2 > y1) {
                                        for (int i = y1 + 1; i != y2; i++) {
                                            if (szachownica[x1][i] != 888) {
                                                powtorz = 1;
                                                break;
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                        zlyRuch = true;
                                    } else if (y1 == y2 && x1 > x2) {
                                        for (int i = x1 - 1; i != x2; i--) {
                                            if (szachownica[i][y1] != 888) {
                                                powtorz = 1;
                                                break;
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else if (y1 == y2 && x2 > x1) {
                                        for (int i = x1 + 1; i != x2; i++) {
                                            if (szachownica[i][y1] != 888) {
                                                powtorz = 1;
                                                break;
                                            }
                                        }
                                        if (powtorz == 1) {
                                            zlyRuch = true;
                                        } else {
                                            szachownica[x1][y1] = 888;
                                            szachownica[x2][y2] = pionek;
                                            zlyRuch = false;
                                        }
                                    } else {
                                        zlyRuch = true;
                                    }
                                }
                            } else if (pionek == 106 || pionek == 116) {

                            }
                        }
                    } else {
                        System.out.println("Nie ma pionka na tej pozycji!");
                        zlyRuch = true;
                    }
                } else {
                    System.out.println("Nie możesz zbić swojego pionka!");
                    zlyRuch = true;
                }
            } else {
                System.out.println("To nie jest Twój pionek!");
                zlyRuch = true;
            }
        } else {
            System.out.println("Wprowadź poprawny ruch!");
            zlyRuch = true;
        }
    }

    public static int[] sprawdzKrola() { //tablica krole[] przechowuje pozycje królów - kolejno biały x1, y1, czarny: x2, y2
        int[] krole = {0, 0, 0, 0};

        for (int i = 0; i < szachownica.length; i++) {
            for (int j = 0; j < szachownica.length; j++) {
                if (szachownica[i][j] == 106) {
                    krole[0] = i;
                    krole[1] = j;
                } else if (szachownica[i][j] == 116) {
                    krole[2] = i;
                    krole[3] = j;
                }
            }
        }
        return krole;
    }

    public static boolean sprawdzSzach(int[] krole) { //sprawdz, czy są pionki atakujące pozycje króla

        int XkB = krole[0];
        int YkB = krole[1];
        int XkCZ = krole[2];
        int YkCZ = krole[3];

        if (szachownica[XkB + 1][YkB + 1] == 111 ||
                szachownica[XkB + 1][YkB - 1] == 111 ||
                szachownica[XkB + 2][YkB + 1] == 113 ||
                szachownica[XkB + 2][YkB - 1] == 113 ||
                szachownica[XkB - 2][YkB + 1] == 113 ||
                szachownica[XkB - 2][YkB - 1] == 113 ||
                szachownica[XkB + 1][YkB + 2] == 113 ||
                szachownica[XkB + 1][YkB - 2] == 113 ||
                szachownica[XkB - 1][YkB + 2] == 113 ||
                szachownica[XkB - 1][YkB - 2] == 113) {
            return true;
        }
        return false;
    }

    private static void zmienGracza() {
        if (aktualnyGracz == Gracz.BIALY)
            aktualnyGracz = Gracz.CZARNY;
        else
            aktualnyGracz = Gracz.BIALY;
    }

    public static String pobierzRuch() {

        System.out.println("Teraz kolej na ruch gracza " + aktualnyGracz + ". Przykład: A2 A4");
        Scanner scan = new Scanner(System.in);
        String pozycjaPocz = scan.nextLine();

        System.out.println(pozycjaPocz);
        return (pozycjaPocz);
    }

    public static int getX1(String ruch) {

        char litera1 = ruch.charAt(0);
        int x1;
        if (Character.isUpperCase(litera1))
            x1 = litera1 - 'A';
        else
            x1 = litera1 - 'a';
        return x1;
    }

    public static int getY1(String ruch) {
        int y1 = ruch.charAt(1) - '1';
        return y1;
    }

    public static int getX2(String ruch) {
        char litera2 = ruch.charAt(3);
        int x2;
        if (Character.isUpperCase(litera2))
            x2 = litera2 - 'A';
        else
            x2 = litera2 - 'a';
        return x2;
    }

    public static int getY2(String ruch) {
        int y2 = ruch.charAt(4) - '1';
        return y2;

    }

    public static void wyswietlSzachownice() {
        int i;
        int j;
        char[] indeksy = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

        System.out.print("      ");
        for (i = 0; i < 8; i++) {
            System.out.print(indeksy[i] + "       ");
        }

        System.out.println("");
        System.out.println("");
        for (i = 1; i < szachownica.length + 1; i++) {
            if (i > 5) {
                System.out.print(i + "   | ");
            } else {
                System.out.print(i + "   | ");
            }
            for (j = 0; j < 8; j++) {
                System.out.print(szachownica[i - 1][j] + "  |  ");
            }
            System.out.println("");
        }
    }
}