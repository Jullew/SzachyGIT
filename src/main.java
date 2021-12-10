import java.util.Scanner;

public class main {

    enum Gracz {
        BIALY,
        CZARNY
    }

    static boolean mat = false;

    static Gracz aktualnyGracz = Gracz.BIALY;
    static boolean zlyRuch = false;
    static int[][] szachownica = {
            {102, 103, 104, 105, 106, 104, 103, 102},   //BIAŁE 50 KRÓL
            {101, 101, 101, 101, 101, 101, 101, 101},
            {101, 888, 111, 888, 888, 888, 888, 888},
            {888, 888, 101, 888, 888, 888, 888, 888},
            {888, 888, 888, 888, 888, 888, 888, 888},
            {888, 888, 888, 888, 888, 888, 888, 888},
            {111, 111, 111, 111, 111, 111, 111, 111},
            {112, 113, 114, 115, 116, 114, 113, 112}}; // CZARNE

    public static void main(String[] args) {

        int kolejka = 1;
        wyswietlSzachownice();
        while (true) {
            do {
//                    if (kolejka > 1) {
//                        if (zlyRuch == true) {
//                            System.out.println("Niepoprawny ruch! Powtórz..");
//                        } else
//                        sprawdzBicie();
//                        if (sprawdzBicie() == true) {
//                            System.out.println("Nie możesz zrobić tego ruchu, masz bicie!");
//                            System.out.println("Bicia pionkami:");
//                            for (int i = 0; i < bicia.length; i++) {
//                                if (bicia[i] != null) {
//                                    System.out.println(bicia[i]);
//                                }
//                            }
//                        }
//                    }


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

            } while (zlyRuch == true);
            wyswietlSzachownice();
            zmienGracza();

        }
    }

    public static int sprawdzPionekX1Y1(int x1, int y1) {


        int pionek = szachownica[x1][y1];


        return pionek;
    }


    public static boolean sprawdzRuch(int x1, int y1, int x2, int y2) {
        int pionek = sprawdzPionekX1Y1(x1, y1);

        int roznicaX = Math.abs(x1 - x2);
        int roznicaY = Math.abs(y1 - y2);


        if ((aktualnyGracz == Gracz.CZARNY && pionek > 110) || (aktualnyGracz == Gracz.BIALY && pionek < 110)) {
            if (pionek != 888) { // sprawdź czy pionek jest na pozycji początkowej
                if ((pionek < 104) || (pionek < 114 && pionek > 110)) { // sortowanie
                    if (pionek == 101 || pionek == 111) { // PION [BIAŁY / CZARNY]
                        if (x2 > x1 && ((((((x1 == 1 || x1 == 6) && roznicaX < 3) && (y1 == y2)) ||
                                (x1 > 1 && x1 < 7 && x2 == x1 + 1 && y1 == y2 && roznicaX == 1 && szachownica[x2][y2] == 888)))) ||
                                (szachownica[x2][y2] != 888 && ((x2 == x1 + 1) && (y2 == y1 + 1) || y2 == y1 - 1))) {

                            szachownica[x1][y1] = 888;
                            szachownica[x2][y2] = pionek;
                            return false;
                        } else {
                            return true;

                        }
                    } else if (pionek == 102 || pionek == 112) { // WIEŻA
                        //if (x1 == x2 || y1 == y2) {
                        if (((x1 == x2) && (y1 != y2)) || ((y1 == y2) && (x1 != x2))) {
                            for (int i = 0; i < x1 + roznicaX; i++) {
                                for (int j = 0; j < y1 + roznicaY; j++) {
                                    if (szachownica[i][j] != 888) {
                                        return true;
                                    }
                                }
                            }
                        }
                        szachownica[x1][y1] = 888;
                        szachownica[x2][y2] = pionek;
                        return false;

                    } else if (pionek == 103 || pionek == 113) { // KOŃ
                        if ((roznicaX == 2 && roznicaY == 1) || (roznicaY == 2 && roznicaX == 1)) {
                            szachownica[x1][y1] = 888;
                            szachownica[x2][y2] = pionek;
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    if (pionek == 104 || pionek == 114) {

                    }
                }
            } else {
                System.out.println("Nie ma pionka na tej pozycji!");
            }
        } else {
            System.out.println("To nie jest Twój pionek!");
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


        System.out.println("Teraz kolej na ruch gracza " + aktualnyGracz);
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

