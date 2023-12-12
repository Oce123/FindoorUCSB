package com.example.findoor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfessorActivity extends AppCompatActivity {

    Button maths, phys, elec, info, english, selfdev;
    ListView profroom;
    String subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        maths = findViewById(R.id.mathsButton);
        phys = findViewById(R.id.physicsButton);
        elec = findViewById(R.id.elecButton);
        info = findViewById(R.id.infoButton);
        english = findViewById(R.id.englishButton);
        selfdev = findViewById(R.id.selfDevButton);
        profroom = findViewById(R.id.ProfessorListView);

        maths.setOnClickListener(getSubjectButtonClickListener("1"));
        phys.setOnClickListener(getSubjectButtonClickListener("2"));
        elec.setOnClickListener(getSubjectButtonClickListener("3"));
        info.setOnClickListener(getSubjectButtonClickListener("4"));
        english.setOnClickListener(getSubjectButtonClickListener("5"));
        selfdev.setOnClickListener(getSubjectButtonClickListener("6"));

    }

    // Méthode pour créer des listeners pour les boutons de sujet
    private View.OnClickListener getSubjectButtonClickListener(final String subject) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjects = subject;
                fetchProfessorRoomForSubjects(subjects, profroom);
            }
        };
    }

    private void fetchProfessorRoomForSubjects(String subjects, ListView listView) {
        String[] field = new String[]{"subjects"};
        String[] data = new String[]{subjects};
        PutData putData = new PutData("http://192.168.5.69/FindoorDatabase/findprofessor.php", "POST", field, data);

        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();

                // Parse JSON array
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    // Create a list to display in the ListView
                    List<String> professorDetailsList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String room = jsonObject.getString("room");
                        professorDetailsList.add(name + " - Room " + room);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfessorActivity.this, android.R.layout.simple_list_item_1, professorDetailsList);
                    listView.setAdapter(adapter);

                    // Add click listener to ListView items
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Get the selected item (professor name)
                            String selectedItem = (String) parent.getItemAtPosition(position);
                            String professorName = selectedItem.split(" - ")[0];

                            // Fetch and display office hours for the selected professor
                            fetchAndDisplayOfficeHours(professorName);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fetchAndDisplayOfficeHours(String professorName) {
        // Perform a new request to get office hours for the selected professor
        String[] field = new String[]{"name"};
        String[] data = new String[]{professorName};
        PutData putData = new PutData("http://192.168.5.69/FindoorDatabase/getofficehours.php", "POST", field, data);

        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();

                // Parse JSON array for office hours
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    // Create a list to display office hours
                    List<String> officeHoursList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                        String officeHours = jsonArray.getString(i);
                        officeHoursList.add(officeHours);
                    }

                    // Display office hours in a dialog
                    showOfficeHoursDialog(officeHoursList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showOfficeHoursDialog(List<String> officeHoursList) {
        // Create a dialog to display office hours
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Office Hours");

        // Convert office hours list to a single string for display
        StringBuilder officeHoursStringBuilder = new StringBuilder();
        for (String officeHours : officeHoursList) {
            officeHoursStringBuilder.append(officeHours).append("\n");
        }

        builder.setMessage(officeHoursStringBuilder.toString());

        // Add a button to close the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Close the dialog
                dialog.dismiss();
            }
        });

        // Display the dialog
        builder.create().show();
    }


}
