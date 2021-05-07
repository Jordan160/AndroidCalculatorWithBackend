package com.jvetter2.androidcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvCalculator;
    private ArrayList<String> inputEntered = new ArrayList<>();
    private boolean clearScreen = false;
    private AlphaAnimation buttonClick = new AlphaAnimation(2F, 1F);
    private String mathEndpoint = "http://10.0.2.2:8080/test/rest/calculate/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCalculator = findViewById(R.id.tv_calculator);
    }

    public void onNumberClicked(View view) {
        Button button = (Button) view;
        view.startAnimation(buttonClick);

        if (clearScreen) {
            inputEntered.add(tvCalculator.getText().toString());
            tvCalculator.setText("0");
            clearScreen = false;
        }

        if (button.getText().toString().equalsIgnoreCase("0")) {
            if (!tvCalculator.getText().toString().equalsIgnoreCase("0")) {
                tvCalculator.setText(tvCalculator.getText() + "0");
            } else {
                tvCalculator.setText("0");
            }
        } else if (button.getText().toString().equalsIgnoreCase(".")) {
            if (!tvCalculator.getText().toString().equalsIgnoreCase("0")) {
                tvCalculator.setText(tvCalculator.getText() + ".");
            } else {
                tvCalculator.setText("0.");
            }
        } else {
            if (!tvCalculator.getText().toString().equalsIgnoreCase("0")) {
                tvCalculator.setText(tvCalculator.getText() + ((Button) view).getText().toString());
            } else {
                tvCalculator.setText(((Button) view).getText().toString());
            }
        }
    }

    public void onSymbolClicked(View view) {
        Button button = (Button) view;
        view.startAnimation(buttonClick);

        if (inputEntered.contains("+") || inputEntered.contains("+") || inputEntered.contains("x") || inputEntered.contains("÷")) {
            doMath();
        }

        if (button.getText().toString().equalsIgnoreCase("+") || button.getText().toString().equalsIgnoreCase("-")
                || button.getText().toString().equalsIgnoreCase("x") || button.getText().toString().equalsIgnoreCase("÷")) {
            inputEntered.add(tvCalculator.getText().toString());
            inputEntered.add(button.getText().toString());
            clearScreen = true;
        }
    }



    public void onAcClicked(View view) {
        view.startAnimation(buttonClick);
        tvCalculator.setText("0");
        inputEntered.clear();
    }

    public void onEqualsClicked(View view) {
        view.startAnimation(buttonClick);
        doMath();
    }

    public void doMath() {
        if (inputEntered.contains("+")) {
            String urlString = "add?a=x&b=y".replace("x", inputEntered.get(0)).replace("y", tvCalculator.getText().toString());
            tvCalculator.setText(String.valueOf(MathTask.getMathCall(mathEndpoint + urlString)));
            inputEntered.clear();
        }

        if (inputEntered.contains("-")) {
            String urlString = "subtract?a=x&b=y".replace("x", inputEntered.get(0)).replace("y", tvCalculator.getText().toString());
            tvCalculator.setText(String.valueOf(MathTask.getMathCall(mathEndpoint + urlString)));
            inputEntered.clear();
        }

        if (inputEntered.contains("x")) {
            String urlString = "multiply?" + "a=x&b=y".replace("x", inputEntered.get(0)).replace("y", tvCalculator.getText().toString());
            tvCalculator.setText(String.valueOf(MathTask.getMathCall(mathEndpoint + urlString)));
            inputEntered.clear();
        }

        if (inputEntered.contains("÷")) {
            String urlString = "divide?a=x&b=y".replace("x", inputEntered.get(0)).replace("y", tvCalculator.getText().toString());
            tvCalculator.setText(String.valueOf(MathTask.getMathCall(mathEndpoint + urlString)));
            inputEntered.clear();
        }
    }
}