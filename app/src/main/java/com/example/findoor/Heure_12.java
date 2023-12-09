package com.example.findoor;

//Classe permettant de définir les paramètres des heures qui seront utilisés dans les spinners
public class Heure_12 {

    private int Heure_12;
    private int Minute_12;
    private String Format;


    public Heure_12(int Heure_12, int Minute_12, String Format) {
        this.Heure_12 = Heure_12;
        this.Minute_12 = Minute_12;
        this.Format = Format;
    }

    //getter de la variable Heure_12, accède à la variable Heure_12
    public int getHeure_12() {
        return Heure_12;
    }

    //setter de la variable Heure_12, nous permet de la modifier
    public void setHeure_12(int Heure_12) {
        this.Heure_12 = Heure_12;
    }

    //getter de la variable Minute_12, accède à la variable Minute_12
    public int getMinute_12() { return Minute_12; }

    //setter de la variable Minute_12, nous permet de la modifier
    public void setMinute_12(int Minute_12) {
        this.Minute_12 = Minute_12;
    }

    //nous retourne le format actuel de l'heure(AM/PM)
    public String Format() { return Format; }

    //setter de la variable Format, nous permet de la modifier
    public void Format(String Format) {
        this.Format = Format;
    }

    //Méthode permettant d'afficher l'heure sous format hh:mm (AM/PM)
    public String getFullHeure_12()  {
        return this.Heure_12 + ":" + this.Minute_12 + "    " + this.Format;
    }

    //Renvoie une chaine de caractères qui décrit le texte qui sera visible dans le spinner
    @Override
    public String toString()  {
        return this.getFullHeure_12();
    }

}

