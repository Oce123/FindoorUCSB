package com.example.findoor;

//Classe permettant de définir les paramètres des heures qui seront utilisées dans les spinners
public class Heure {

    private int Heure;
    private int Minute;


    public Heure(int Heure, int Minute) {
        this.Heure = Heure;
        this.Minute = Minute;
    }

    //getter de la variable Heure, accède à la variable Heure
    public int getHeure() {
        return Heure;
    }

    //setter de la variable Heure, nous permet de la modifier
    public void setHeure(int Heure) {
        this.Heure = Heure;
    }

    //getter de la variable Minute, accède à la variable Minute
    public int getMinute() {
        return Minute;
    }

    //setter de la variable Minute, nous permet de la modifier
    public void setMinute(int Minute) {
        this.Minute = Minute;
    }

    //Méthode permettant d'afficher l'heure sous format hh:mm
    public String getFullHeure()  {
        return this.Heure + ":" + this.Minute;
    }

    //Renvoie une chaine de caractères qui décrit le texte qui sera visible dans le spinner
    @Override
    public String toString()  {
        return this.getFullHeure();
    }


}

