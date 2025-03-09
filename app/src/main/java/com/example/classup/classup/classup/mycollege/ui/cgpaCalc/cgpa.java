package com.example.classup.classup.classup.mycollege.ui.cgpaCalc;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.classup.classup.classup.mycollege.R;

public class cgpa extends AppCompatActivity {

    private Spinner[] gradeSpinners;
    private Spinner[] creditSpinners;
    private Button calculateButton;
    private TextView sgpaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa);

        // Initialize spinners and other views
        initializeViews();

        // Set up onClickListener for the Calculate button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSGPA();
            }
        });
    }

    private void initializeViews() {
        gradeSpinners = new Spinner[] {
                findViewById(R.id.subject1_grade_spinner),
                findViewById(R.id.subject2_grade_spinner),
                findViewById(R.id.subject3_grade_spinner),
                findViewById(R.id.subject4_grade_spinner),
                findViewById(R.id.subject5_grade_spinner),
                findViewById(R.id.subject6_grade_spinner),
                findViewById(R.id.subject7_grade_spinner),
                findViewById(R.id.subject8_grade_spinner)
        };

        creditSpinners = new Spinner[] {
                findViewById(R.id.subject1_credit_spinner),
                findViewById(R.id.subject2_credit_spinner),
                findViewById(R.id.subject3_credit_spinner),
                findViewById(R.id.subject4_credit_spinner),
                findViewById(R.id.subject5_credit_spinner),
                findViewById(R.id.subject6_credit_spinner),
                findViewById(R.id.subject7_credit_spinner),
                findViewById(R.id.subject8_credit_spinner)
        };

        calculateButton = findViewById(R.id.calculate_button);
        sgpaTextView = findViewById(R.id.sgpa_text_view);
    }

    private void calculateSGPA() {
        int totalCredits = 0;
        float totalGradePoints = 0;

        for (int i = 0; i < gradeSpinners.length; i++) {
            String selectedGrade = gradeSpinners[i].getSelectedItem().toString();
            int credit = Integer.parseInt(creditSpinners[i].getSelectedItem().toString());

            totalCredits += credit;
            totalGradePoints += getGradePoint(selectedGrade) * credit;
        }

        float sgpa = totalGradePoints / totalCredits;
        sgpaTextView.setText("SGPA: " + sgpa);
    }

    private float getGradePoint(String grade) {
        switch (grade) {
            case "EX":
                return 10;
            case "A":
                return 9;
            case "B":
                return 8;
            case "C":
                return 7;
            case "D":
                return 6;
            case "P":
                return 5;
            case "F":
                return 2;
            default:
                return 0; // Default to 0 if grade not found
        }
    }
}
