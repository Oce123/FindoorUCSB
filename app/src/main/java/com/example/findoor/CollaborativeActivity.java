package com.example.findoor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CollaborativeActivity extends AppCompatActivity {

    private EditText roomNumberEditText;
    private Spinner aisleBookSpinner;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private ProgressBar progressBar;
    private Button OKBookButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaborative);

        roomNumberEditText = findViewById(R.id.RoomBookedTextNumber);
        startTimeEditText = findViewById(R.id.StartTimeBookedTextTime);
        endTimeEditText = findViewById(R.id.EndTimeBookedTextTime);
        progressBar = findViewById(R.id.progress);
        OKBookButton = findViewById(R.id.OKBookButton);

        // Adapter for aisleBook Spinner
        aisleBookSpinner = findViewById(R.id.aisleBook);
        ArrayAdapter<CharSequence> adapterBooked = ArrayAdapter.createFromResource(this, R.array.room_numbers, android.R.layout.simple_spinner_item);
        adapterBooked.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aisleBookSpinner.setAdapter(adapterBooked);

        // Adapter for aisleFreeUp Spinner
        Spinner aisleFreeUpSpinner = findViewById(R.id.aisleFreeup);
        ArrayAdapter<CharSequence> adapterFreeUp = ArrayAdapter.createFromResource(this, R.array.room_numbers, android.R.layout.simple_spinner_item);
        adapterFreeUp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aisleFreeUpSpinner.setAdapter(adapterFreeUp);

        OKBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button", "onOKBookButtonClick: ");
                String aisleId = aisleBookSpinner.getSelectedItem().toString();
                String roomNumber = roomNumberEditText.getText().toString();
                String startTime = startTimeEditText.getText().toString();
                String endTime = endTimeEditText.getText().toString();

                // Exécuter la tâche asynchrone pour vérifier la disponibilité de la salle
                new CheckRoomAvailabilityTask().execute(aisleId, roomNumber, startTime, endTime);
            }
        });
    }

    private boolean isRoomAvailable(int aisleId, int roomNumber, String startTime, String endTime) {
        try {
            // Créer un objet JSON pour les données à envoyer
            JSONObject postData = new JSONObject();
            postData.put("aisle", aisleId);
            postData.put("number", roomNumber);
            postData.put("starttime", startTime);
            postData.put("endtime", endTime);

            // Convertir l'objet JSON en chaîne
            String jsonString = postData.toString();

            // Spécifier l'URL du serveur PHP
            URL url = new URL("http://192.168.5.69/FindoorDatabase/verifier_disponibilite.php");

            // Créer la connexion HTTP
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);

            // Écrire les données JSON dans la sortie de la connexion
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Lire la réponse du serveur
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Vérifier la réponse du serveur
                return response.toString().equals("true");
            }

        } catch (Exception e) {
            Log.e("HTTP", "Error in HTTP request: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void insertReservation(int aisleId, int roomNumber, String startTime, String endTime) {
        // Démarrer la visibilité du ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Utiliser un Handler pour effectuer des opérations sur le thread principal
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                String strAisleId = String.valueOf(aisleId);
                String strRoomNumber = String.valueOf(roomNumber);
                // Créer un tableau pour les champs
                String[] field = new String[4];
                field[0] = "aisleId";
                field[1] = "roomNumber";
                field[2] = "startTime";
                field[3] = "endTime";

                // Créer un tableau pour les données
                String[] data = new String[4];
                data[0] = strAisleId;
                data[1] = strRoomNumber;
                data[2] = startTime;
                data[3] = endTime;

                // Créer une instance de la classe PutData
                PutData putData = new PutData("http://192.168.5.69/FindoorDatabase/insertReservation.php", "POST", field, data);

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);

                        // Récupérer le résultat de l'opération
                        String result = putData.getResult();

                        // Vérifier le résultat et afficher un message en conséquence
                        if (result.equals("Leave Reservation Success")) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            // Ajoutez ici toute logique supplémentaire après une réservation réussie
                        } else {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void back(View view) {
        Intent bouton_back = new Intent(this, TableActivity.class);
        startActivity(bouton_back);
    }

    private class CheckRoomAvailabilityTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            int aisleId = Integer.parseInt(params[0]);
            int roomNumber = Integer.parseInt(params[1]);
            String startTime = params[2];
            String endTime = params[3];

            // Appel à la méthode isRoomAvailable
            return isRoomAvailable(aisleId, roomNumber, startTime, endTime);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Ici, tu peux utiliser le résultat pour prendre des mesures, par exemple afficher un message à l'utilisateur.
            if (result) {
                String aisleId = aisleBookSpinner.getSelectedItem().toString();
                String roomNumber = roomNumberEditText.getText().toString();
                String startTime = startTimeEditText.getText().toString();
                String endTime = endTimeEditText.getText().toString();

                // Si la salle est disponible, insérer la réservation dans la base de données
                insertReservation(Integer.parseInt(aisleId), Integer.parseInt(roomNumber), startTime, endTime);

                // Informer l'utilisateur de la réussite de la réservation
                Toast.makeText(CollaborativeActivity.this, "Room booked successfully!", Toast.LENGTH_SHORT).show();
            } else {
                // Informer l'utilisateur que la salle demandée ne peut pas être réservée
                Toast.makeText(CollaborativeActivity.this, "The requested room is not available for booking.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
