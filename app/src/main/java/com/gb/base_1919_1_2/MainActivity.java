package com.gb.base_1919_1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Calculator calculator;

    private TextView resultField; // текстовое поле для вывода результата
    private EditText numberField;   // поле для ввода числа
    private TextView operationField;    // текстовое поле для вывода знака операции
    private Double operand = null;
    private String lastOperation = "="; // последняя операция

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApp.currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        ((RadioButton)findViewById(R.id.styleDef)).setOnClickListener(this);
        ((RadioButton)findViewById(R.id.styleOne)).setOnClickListener(this);
        ((RadioButton)findViewById(R.id.styleTwo)).setOnClickListener(this);
        ((RadioButton)findViewById(R.id.styleSecond)).setOnClickListener(this);


    }
void initView(){
    // получаем все поля по id из activity_main.xml
    resultField = findViewById(R.id.resultField);
    numberField = findViewById(R.id.numberField);
    operationField = findViewById(R.id.operationField);
}
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    // обработка нажатия на числовую кнопку
    public void onNumberClick(View view) {

        Button button = (Button) view;
        numberField.append(button.getText());


        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    // обработка нажатия на кнопку операции
    public void onOperationClick(View view) {

        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        // если введенно что-нибудь
        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.styleDef:{
                MyApp.currentTheme=R.style.ThemeDef;
                break;
            }
            case R.id.styleOne:{
                MyApp.currentTheme=R.style.myThemeRed;
                break;
            }
            case R.id.styleTwo:{
                MyApp.currentTheme=R.style.myThemeGreen;
                break;
            }
            case R.id.styleSecond:{
                MyApp.currentTheme=R.style.myThemeBlue;
                break;
            }
        }
        recreate();
    }
}
