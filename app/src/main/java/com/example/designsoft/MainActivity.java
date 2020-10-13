package com.example.designsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.designsoft.build.ComputerDesign;
import com.example.designsoft.view.CustomSwitchButton;

public class MainActivity extends AppCompatActivity {

    private CustomSwitchButton mCustomButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomButton = findViewById(R.id.csb);
        mCustomButton.setOnTurnStatusClickListener(new CustomSwitchButton.onTurnStatusClickListener() {
            @Override
            public void onTurnOnClick() {
                mCustomButton.startOnAnimal();
            }

            @Override
            public void onTurnOffClick() {
                mCustomButton.startOffAnimal();
            }
        });
    }
}
