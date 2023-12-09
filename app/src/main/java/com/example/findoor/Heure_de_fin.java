package com.example.findoor;

//Classe permettant de définir les paramètres des heures qui seront utilisés dans les spinners
public class Heure_de_fin {

    private int Heure_de_fin;
    private int Minute_de_fin;

    public Heure_de_fin(int heure_de_fin, int minute_de_fin) {
        Heure_de_fin = heure_de_fin;
        Minute_de_fin = minute_de_fin;
    }

    //getter de la variable Heure_de_fin, accède à la variable Heure_de_fin
    public int getHeure_de_fin() {
        return Heure_de_fin;
    }

    //setter de la variable Heure_de_fin, nous permet de la modifier
    public void setHeure_de_fin(int Heure_de_fin) {
        this.Heure_de_fin = Heure_de_fin;
    }

    //getter de la variable Minute_de_fin, accède à la variable Minute_de_fin
    public int getMinute_de_fin() {
        return Minute_de_fin;
    }

    //setter de la variable Minute_de_fin, nous permet de la modifier
    public void setMinute_de_fin(int Minute_de_fin) {
        this.Minute_de_fin = Minute_de_fin;
    }

    //Méthode permettant d'afficher l'heure sous format hh:mm
    public String getFullHeure_de_fin()  {
        return this.Heure_de_fin + ":" + this.Minute_de_fin;
    }

    //Renvoie une chaine de caractères qui décrit le texte qui sera visible dans le spinner
    @Override
    public String toString()  {
        return this.getFullHeure_de_fin();
    }

}

