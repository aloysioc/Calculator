package com.lailkiu.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String m_Display = "";
    private String m_Memory = "";
    private String m_Operator = "";

    private boolean okReset = true;
    private boolean okZeroLeft = true;
    private boolean okDecimal = true;

    private double nCalc00 = 0.0;
    private double nCalc01 = 0.0;
    private double nCalcRs = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setScreen() {
        TextView vDisplay = (TextView)findViewById(R.id.Display);
        TextView vMemory = (TextView)findViewById(R.id.Memory);
        TextView vOperator = (TextView)findViewById(R.id.Operator);

        vDisplay.setText(m_Display);
        vMemory.setText(m_Memory);
        vOperator.setText(m_Operator);
    }

    public void buttonClick(View view) {
        int click = view.getId();

        switch (click) {
            case R.id.bt00: addNumber("0"); break;
            case R.id.bt01: addNumber("1"); break;
            case R.id.bt02: addNumber("2"); break;
            case R.id.bt03: addNumber("3"); break;
            case R.id.bt04: addNumber("4"); break;
            case R.id.bt05: addNumber("5"); break;
            case R.id.bt06: addNumber("6"); break;
            case R.id.bt07: addNumber("7"); break;
            case R.id.bt08: addNumber("8"); break;
            case R.id.bt09: addNumber("9"); break;
            case R.id.btDec: addNumber("."); break;
            case R.id.btSum: addOperator("+"); break;
            case R.id.btSub: addOperator("-"); break;
            case R.id.btMul: addOperator("*"); break;
            case R.id.btDiv: addOperator("/"); break;
            case R.id.btResult: addOperator("="); break;
            case R.id.btBack:
                if (m_Display.length() > 0) {
                    if (m_Display.substring(m_Display.length()-1, m_Display.length()).equals(".")) okDecimal = true;
                    if (m_Display.length() == 1) okZeroLeft = true;
                    m_Display = m_Display.substring(0, m_Display.length()-1);
                }
                break;
            case R.id.btReset:
                okReset = true;
                m_Display = "";
                m_Memory = "";
                m_Operator = "";
                nCalc00 = 0.0;
                nCalc01 = 0.0;
                nCalcRs = 0.0;
                break;
        }
        setScreen();
    }


    public void addNumber(String txAdd) {

        if (okReset) {
            m_Display = "";
            okReset = false;
            okZeroLeft = true;
            okDecimal = true;
        }

        switch (txAdd) {
            case "0":
                if (okZeroLeft == false) m_Display += "0";
                break;
            case ".":
                if (okDecimal) {
                    if (m_Display.length() == 0){ m_Display += "0."; }
                    else { m_Display += "."; }
                    okZeroLeft = false;
                    okDecimal = false;
                }
                break;
            default:
                m_Display += txAdd;
                okZeroLeft = false;
                break;
        }
    }


    public void addOperator(String refOp) {

        if (okReset == false) {

            if (m_Display.length() > 0) {

                if (m_Operator.equals("=") || m_Operator.equals("")){
                    nCalcRs = Double.parseDouble("0" + m_Display);
                    nCalc00 = nCalcRs;
                } else {
                    nCalc01 = Double.parseDouble("0" + m_Display);
                }

                nCalc00 = nCalcRs;

                switch (m_Operator) {
                    case "+":
                        nCalcRs = nCalc00 + nCalc01;
                        m_Memory = nCalc00 + " + " + nCalc01 + " = " + nCalcRs + "\n" + m_Memory;
                        break;
                    case "-":
                        nCalcRs = nCalc00 - nCalc01;
                        m_Memory = nCalc00 + " - " + nCalc01 + " = " + nCalcRs + "\n" + m_Memory;
                        break;
                    case "*":
                        nCalcRs = nCalc00 * nCalc01;
                        m_Memory = nCalc00 + " * " + nCalc01 + " = " + nCalcRs + "\n" + m_Memory;
                        break;
                    case "/":
                        if (nCalc01 != 0) {
                            nCalcRs = nCalc00 / nCalc01;
                        } else {
                            nCalcRs = 999999999999.;
                        }
                        m_Memory = nCalc00 + " / " + nCalc01 + " = " + nCalcRs + "\n" + m_Memory;
                        break;
                }
            }
        }
        m_Display = String.valueOf(nCalcRs);
        okReset = true;
        m_Operator = refOp;
    }
}