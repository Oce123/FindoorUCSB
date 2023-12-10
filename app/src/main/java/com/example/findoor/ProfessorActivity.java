package com.example.findoor;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Arrays;

public class ProfessorActivity extends AppCompatActivity {

    Button maths, phys, elec, info, english, selfdev;
    ListView profroom;
    String subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        maths = findViewById(R.id.mathsButton);
        phys = findViewById(R.id.physicsButton);
        elec = findViewById(R.id.elecButton);
        info = findViewById(R.id.infoButton);
        english = findViewById(R.id.englishButton);
        selfdev = findViewById(R.id.selfDevButton);
        profroom = findViewById(R.id.ProfessorListView);

        maths.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subjects = "1";
                fetchProfessorRoomForSubjects(subjects, profroom);
            }
        });

        phys.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subjects = "2";
                fetchProfessorRoomForSubjects(subjects, profroom);
            }
        });

        elec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subjects = "3";
                fetchProfessorRoomForSubjects(subjects, profroom);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subjects = "4";
                fetchProfessorRoomForSubjects(subjects, profroom);
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subjects = "5";
                fetchProfessorRoomForSubjects(subjects, profroom);
            }
        });

        selfdev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subjects = "6";
                fetchProfessorRoomForSubjects(subjects, profroom);
            }
        });
    }

    private void fetchProfessorRoomForSubjects(String aisleId, ListView listView) {
        // Construisez et exécutez la requête vers le service PHP
        String[] field = new String[]{"subjects"};
        String[] data = new String[]{subjects};
        PutData putData = new PutData("http://192.168.5.69/FindoorDatabase/findprofessor.php", "POST", field, data);

        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                result = result.replace("[", "").replace("]", "");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfessorActivity.this, android.R.layout.simple_list_item_1, Arrays.asList(result.split(",")));
                listView.setAdapter(adapter);
            }
        }
    }
}
