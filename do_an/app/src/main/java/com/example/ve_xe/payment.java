package com.example.ve_xe;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class payment extends AppCompatActivity {
    private EditText editTextName, editTextCard, editTextCVV;
    private Spinner spinnerExpMonth, spinnerExpYear;
    private CheckBox checkBoxSave;
    private Button buttonPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextCard = (EditText) findViewById(R.id.editTextCard);
        editTextCVV = (EditText) findViewById(R.id.editTextCVV);

        spinnerExpMonth = (Spinner) findViewById(R.id.spinnerExpMonth);
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpMonth.setAdapter(adapterMonth);

        spinnerExpYear = (Spinner) findViewById(R.id.spinnerExpYear);
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpYear.setAdapter(adapterYear);

        checkBoxSave = (CheckBox) findViewById(R.id.checkBoxSave);

        buttonPay = (Button) findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle payment processing here
                // ...

                // If save card checkbox is checked, save payment details to database
                if (checkBoxSave.isChecked()) {
                    // Save payment details to database
                    // ...
                }
            }
        });
    }

}
