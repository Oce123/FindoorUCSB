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

import com.google.android.material.textfield.TextInputEditText;
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
    private Spinner aisleFreeUpSpinner;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private ProgressBar progressBar;
    private Button OKBookButton;
    private EditText aisleIdEditText;

    private TextInputEditText textInputEditTextAisle, textInputEditTextRoomNumber, textInputEditTextStartTime, textInputEditTextEndTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaborative2);

        /*textInputEditTextAisle = findViewById(R.id.aisleId);
        textInputEditTextRoomNumber = findViewById(R.id.RoomNumber);
        textInputEditTextStartTime = findViewById(R.id.StartTime);
        textInputEditTextEndTime = findViewById(R.id.EndTime);
        progressBar = findViewById(R.id.progress);
        OKBookButton = findViewById(R.id.OKFreeUpButton);*/

        roomNumberEditText = findViewById(R.id.roomFreeUpTextNumber);
        startTimeEditText = findViewById(R.id.StartTimeFreeUpTextTime);
        endTimeEditText = findViewById(R.id.EndTimeFreeUpTextTime);
        progressBar = findViewById(R.id.progress);
        OKBookButton = findViewById(R.id.OKFreeUpButton);

        // Adapter for aisleFreeUp Spinner
        aisleFreeUpSpinner = findViewById(R.id.aisleFreeup);
        ArrayAdapter<CharSequence> adapterFreeUp = ArrayAdapter.createFromResource(this, R.array.room_numbers, android.R.layout.simple_spinner_item);
        adapterFreeUp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aisleFreeUpSpinner.setAdapter(adapterFreeUp);

        OKBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button", "onOKBookButtonClick: ");
                String aisleId = aisleFreeUpSpinner.getSelectedItem().toString();
                String roomNumber = roomNumberEditText.getText().toString();
                String startTime = startTimeEditText.getText().toString();
                String endTime = endTimeEditText.getText().toString();

                //String aisleId, roomNumber, startTime, endTime;
                //aisleId = String.valueOf(aisleFreeUpSpinner.getSelectedItem());
                /*aisleId = String.valueOf(textInputEditTextAisle.getText());
                Log.d("aisleID", aisleId);
                roomNumber = String.valueOf(textInputEditTextRoomNumber.getText());
                Log.d("roomNumber", roomNumber);
                startTime = String.valueOf(textInputEditTextStartTime.getText());
                Log.d("startTime", startTime);
                endTime = String.valueOf(textInputEditTextEndTime.getText());
                Log.d("endTime", endTime);*/

                if(!aisleId.equals("") && !roomNumber.equals("") && !startTime.equals("") && !endTime.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "aisle";
                            field[1] = "number";
                            field[2] = "starttime";
                            field[3] = "endtime";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = aisleId;
                            data[1] = roomNumber;
                            data[2] = startTime;
                            data[3] = endTime;
                            PutData putData = new PutData("http://192.168.5.69/FindoorDatabase/leaveReservation.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    Log.i("PutData", result);
                                    if(result.equals("Free Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void back(View view) {
        Intent bouton_back = new Intent(this, TableActivity.class);
        startActivity(bouton_back);
    }

    /*private class CheckRoomAvailabilityTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            int aisleId = Integer.parseInt(params[0]);
            int roomNumber = Integer.parseInt(params[1]);
            String startTime = params[2];
            String endTime = params[3];

            // Appel à la méthode isRoomAvailable
            return insertReservation(aisleId, roomNumber, startTime, endTime);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Ici, tu peux utiliser le résultat pour prendre des mesures, par exemple afficher un message à l'utilisateur.
            if (result) {
                String aisleId = aisleFreeUpSpinner.getSelectedItem().toString();
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

    }*/
}
