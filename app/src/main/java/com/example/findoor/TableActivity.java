package com.example.findoor;

import static com.example.findoor.SettingsActivity.getVarHeure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.security.keystore.StrongBoxUnavailableException;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.Arrays;

//classe permettant d'afficher les salles disponibles sous format tableau
public class TableActivity extends AppCompatActivity {

    private Spinner spinnerHeure;
    private Spinner spinnerHeure_de_fin;
    Button epi1, epi2, epi3, epi4, epi5, epi6;
    ListView epi1_liste, epi2_liste, epi3_liste, epi4_liste, epi5_liste, epi6_liste;
    boolean FormatHeure;
    String aisleId;

    //appelé à la création de l'activité, sert d'initialisation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        FormatHeure = getVarHeure();
        epi1 = findViewById(R.id.epi1);
        epi1_liste = findViewById(R.id.epi1liste);
        epi2 = findViewById(R.id.epi2);
        epi2_liste = findViewById(R.id.epi2liste);
        epi3 = findViewById(R.id.epi3);
        epi3_liste = findViewById(R.id.epi3liste);
        epi4 = findViewById(R.id.epi4);
        epi4_liste = findViewById(R.id.epi4liste);
        epi5 = findViewById(R.id.epi5);
        epi5_liste = findViewById(R.id.epi5liste);
        epi6 = findViewById(R.id.epi6);
        epi6_liste = findViewById(R.id.epi6liste);

        //interaction lors de l'appui sur le bouton epi1 pour afficher les salles disponibles
        epi1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aisleId = "1";
                fetchRoomNumbersForAisle(aisleId, epi1_liste);
            }
        });

        epi2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aisleId = "2";
                fetchRoomNumbersForAisle(aisleId, epi2_liste);
            }
        });

        epi3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aisleId = "3";
                fetchRoomNumbersForAisle(aisleId, epi3_liste);
            }
        });

        epi4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aisleId = "4";
                fetchRoomNumbersForAisle(aisleId, epi4_liste);
            }
        });

        epi5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aisleId = "5";
                fetchRoomNumbersForAisle(aisleId, epi5_liste);
            }
        });

        epi6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aisleId = "6";
                fetchRoomNumbersForAisle(aisleId, epi6_liste);
            }
        });

        if(FormatHeure == true) // Test pour savoir s'il faut afficher en 12 ou 24h
        {
            //Utilisation du tableaux avec les heures en 24h de début + utilisation dans le spinner
            this.spinnerHeure = (Spinner) findViewById(R.id.heure_de_debut);
            Heure[] heure = HeureDataUtils.getHeure();
            ArrayAdapter<Heure> adapter = new ArrayAdapter<Heure>(this,android.R.layout.simple_spinner_item,heure);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerHeure.setAdapter(adapter);
            this.spinnerHeure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onItemSelectedHandler(parent, view, position, id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //Utilisation du tableaux avec les heures en 24h de fin + utilisation dans le spinner
            this.spinnerHeure_de_fin = (Spinner) findViewById(R.id.heure_de_fin);
            Heure_de_fin[] heure_de_fin = HeureDataUtils.getHeure_de_fin();
            ArrayAdapter<Heure_de_fin> adapter2 = new ArrayAdapter<Heure_de_fin>(this, android.R.layout.simple_spinner_item, heure_de_fin);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerHeure_de_fin.setAdapter(adapter2);
            this.spinnerHeure_de_fin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //onItemSelectedHandler2(parent, view, position, id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        // Utilisation de la même structure précédente mais pour les heures en 12h
        else {
            this.spinnerHeure = (Spinner) findViewById(R.id.heure_de_debut);
            Heure_12[] Heure12 = HeureDataUtils.getHeure_12();
            ArrayAdapter<Heure_12> adapter = new ArrayAdapter<Heure_12>(this,android.R.layout.simple_spinner_item,Heure12);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerHeure.setAdapter(adapter);
            this.spinnerHeure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onItemSelectedHandler12h(parent, view, position, id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            this.spinnerHeure_de_fin = (Spinner) findViewById(R.id.heure_de_fin);
            Heure_de_fin_12[] Heure_de_fin_12 = HeureDataUtils.getHeure_de_fin_12();
            ArrayAdapter<Heure_de_fin_12> adapter2 = new ArrayAdapter<Heure_de_fin_12>(this,android.R.layout.simple_spinner_item,Heure_de_fin_12);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinnerHeure_de_fin.setAdapter(adapter2);
            this.spinnerHeure_de_fin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //onItemSelectedHandler2(parent, view, position, id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }}

    private void fetchRoomNumbersForAisle(String aisleId, ListView listView) {
        // Construisez et exécutez la requête vers le service PHP
        String[] field = new String[]{"aisle"};
        String[] data = new String[]{aisleId};
        PutData putData = new PutData("http://192.168.5.69/FindoorDatabase/searchclass.php", "POST", field, data);

        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                result = result.replace("[", "").replace("]", "");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(TableActivity.this, android.R.layout.simple_list_item_1, Arrays.asList(result.split(",")));
                listView.setAdapter(adapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomNumberString = (String) parent.getItemAtPosition(position);

                // Assurez-vous que la chaîne est correctement formatée en tant qu'entier
                try {
                    int roomNumber = Integer.parseInt(roomNumberString.trim()); // Utilisez trim() pour supprimer les espaces éventuels
                    showConfirmationDialog(aisleId, roomNumber);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Gérez l'erreur de conversion ici, par exemple, affichez un message d'erreur à l'utilisateur
                    Toast.makeText(TableActivity.this, "Erreur de conversion du numéro de salle", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showConfirmationDialog(String aisleId, int roomNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to book the room " + roomNumber + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // L'utilisateur a cliqué sur "Oui", retirez la salle de la base de données
                        deleteReservationFromDatabase(aisleId, roomNumber);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // L'utilisateur a cliqué sur "Non", fermez la fenêtre modale
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void deleteReservationFromDatabase(String aisleId, int roomNumber) {
        // Convertir les valeurs en entiers
        int aisle = Integer.parseInt(aisleId);
        // Construisez et exécutez la requête vers le service PHP pour supprimer la réservation
        String[] field = new String[]{"aisle", "number"};
        String[] data = new String[]{String.valueOf(aisle), String.valueOf(roomNumber)};
        PutData putData = new PutData("http://192.168.5.69/FindoorDatabase/deletereservation.php", "POST", field, data);

        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                // Affichez un message ou effectuez d'autres actions en fonction du résultat de la suppression
                Toast.makeText(TableActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Message toast pour indiqué les paramètres utilisés
    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        Heure heure = (Heure) adapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Heure de début selectionnée: " + heure.getFullHeure() ,Toast.LENGTH_SHORT).show();
    }

    private void onItemSelectedHandler12h(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        Heure_12 heure_12 = (Heure_12) adapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Heure de début selectionnée: " + heure_12.getFullHeure_12() ,Toast.LENGTH_SHORT).show();
    }

    private void onItemSelectedHandler2(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        Heure_de_fin heure_de_fin = (Heure_de_fin) adapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Heure de fin selectionnée: " + heure_de_fin.getFullHeure_de_fin() ,Toast.LENGTH_SHORT).show();
    }

    private void onItemSelectedHandler2_12h(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        Heure_de_fin_12 heure_de_fin_12 = (Heure_de_fin_12) adapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Heure de fin selectionnée: " + heure_de_fin_12.getFullHeure_de_fin_12() ,Toast.LENGTH_SHORT).show();
    }

    //redirection du bouton
    public void parametres(View view){
        Intent bouton_parametres = new Intent(this, SettingsActivity.class);
        startActivity(bouton_parametres);
    }

    //redirection du bouton
    public void back(View view){
        Intent bouton_back = new Intent(this, WelcomeActivity.class);
        startActivity(bouton_back);
    }

    public void find(View view){
        Intent bouton_find = new Intent(this, ProfessorActivity.class);
        startActivity(bouton_find);
    }

    public void collaborative(View view){
        Intent bouton_collaborative = new Intent(this, CollaborativeActivity.class);
        startActivity(bouton_collaborative);
    }

}

