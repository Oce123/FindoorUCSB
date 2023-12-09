package com.example.findoor;

//classe servant de répertoire pour l'ensemble des horaires utilisés dans les spinners
public class HeureDataUtils {

    //répertoire des horaires de début, sous forme de tableau
    public static Heure[] getHeure()  {
        Heure h1 = new Heure(8, 00);
        Heure h2 = new Heure(9, 00);
        Heure h3 = new Heure(10, 00);
        Heure h4 = new Heure(11, 00);
        Heure h5 = new Heure(12, 00);
        Heure h6 = new Heure(13, 00);
        Heure h7 = new Heure(14, 00);
        Heure h8 = new Heure(15, 00);
        Heure h9 = new Heure(16, 00);
        Heure h10 = new Heure(17, 00);
        Heure h11 = new Heure(18, 00);
        Heure h12 = new Heure(19, 00);

        return new Heure[] {h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12};
    }

    //répertoire des horaires de fin, sous forme de tableau
    public static Heure_de_fin[] getHeure_de_fin()  {
        Heure_de_fin h1 = new Heure_de_fin(8, 00);
        Heure_de_fin h2 = new Heure_de_fin(9, 00);
        Heure_de_fin h3 = new Heure_de_fin(10, 00);
        Heure_de_fin h4 = new Heure_de_fin(11, 00);
        Heure_de_fin h5 = new Heure_de_fin(12, 00);
        Heure_de_fin h6 = new Heure_de_fin(13, 00);
        Heure_de_fin h7 = new Heure_de_fin(14, 00);
        Heure_de_fin h8 = new Heure_de_fin(15, 00);
        Heure_de_fin h9 = new Heure_de_fin(16, 00);
        Heure_de_fin h10 = new Heure_de_fin(17, 00);
        Heure_de_fin h11 = new Heure_de_fin(18, 00);
        Heure_de_fin h12 = new Heure_de_fin(19, 00);

        return new Heure_de_fin[] {h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12};
    }

    //répertoire des horaires de début en format 12h, sous forme de tableau
    public static Heure_12[] getHeure_12()  {
        Heure_12 h1 = new Heure_12(8, 00, "AM");
        Heure_12 h2 = new Heure_12(9, 00, "AM");
        Heure_12 h3 = new Heure_12(10, 00, "AM");
        Heure_12 h4 = new Heure_12(11, 00, "AM");
        Heure_12 h5 = new Heure_12(12, 00, "AM");
        Heure_12 h6 = new Heure_12(1, 00,"PM");
        Heure_12 h7 = new Heure_12(2, 00,"PM");
        Heure_12 h8 = new Heure_12(3, 00,"PM");
        Heure_12 h9 = new Heure_12(4, 00,"PM");
        Heure_12 h10 = new Heure_12(5, 00,"PM");
        Heure_12 h11 = new Heure_12(6, 00,"PM");
        Heure_12 h12 = new Heure_12(7, 00, "PM");

        return new Heure_12[] {h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12};
    }

    //répertoire des horaires de fin en format 12h, sous forme de tableau
    public static Heure_de_fin_12[] getHeure_de_fin_12()  {
        Heure_de_fin_12 h1 = new Heure_de_fin_12(8, 00, "AM");
        Heure_de_fin_12 h2 = new Heure_de_fin_12(9, 00, "AM");
        Heure_de_fin_12 h3 = new Heure_de_fin_12(10, 00, "AM");
        Heure_de_fin_12 h4 = new Heure_de_fin_12(11, 00, "AM");
        Heure_de_fin_12 h5 = new Heure_de_fin_12(12, 00, "AM");
        Heure_de_fin_12 h6 = new Heure_de_fin_12(1, 00,"PM");
        Heure_de_fin_12 h7 = new Heure_de_fin_12(2, 00,"PM");
        Heure_de_fin_12 h8 = new Heure_de_fin_12(3, 00,"PM");
        Heure_de_fin_12 h9 = new Heure_de_fin_12(4, 00,"PM");
        Heure_de_fin_12 h10 = new Heure_de_fin_12(5, 00,"PM");
        Heure_de_fin_12 h11 = new Heure_de_fin_12(6, 00,"PM");
        Heure_de_fin_12 h12 = new Heure_de_fin_12(7, 00, "PM");

        return new Heure_de_fin_12[] {h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12};
    }
}

