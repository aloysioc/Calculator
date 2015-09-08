package com.lailkiu.calculatorv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String txDisplay = "";
    public static String txMemory = "";
    public static String txOperator = "";

    public static boolean okReset = true;
    public static boolean okZeroLeft = true;
    public static boolean okDecimal = true;

    public static double nCalc00 = 0.0;
    public static double nCalc01 = 0.0;
    public static double nCalcRs = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView vDisplay = (TextView)findViewById(R.id.vDisplay);
        TextView vMemory = (TextView)findViewById(R.id.vMemory);
        TextView vOperator = (TextView)findViewById(R.id.vOperator);

        vDisplay.setText(txDisplay);
        vMemory.setText(txMemory);
        vOperator.setText(txOperator);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void buttonClick(View view) {

        TextView vDisplay = (TextView)findViewById(R.id.vDisplay);
        TextView vMemory = (TextView)findViewById(R.id.vMemory);
        TextView vOperator = (TextView)findViewById(R.id.vOperator);

        String strClick = view.toString();

        if (strClick.contains("id/bt00")) { addNumber("0"); }
        else if (strClick.contains("id/bt01")) { addNumber("1"); }
        else if (strClick.contains("id/bt02")) { addNumber("2"); }
        else if (strClick.contains("id/bt03")) { addNumber("3"); }
        else if (strClick.contains("id/bt04")) { addNumber("4"); }
        else if (strClick.contains("id/bt05")) { addNumber("5"); }
        else if (strClick.contains("id/bt06")) { addNumber("6"); }
        else if (strClick.contains("id/bt07")) { addNumber("7"); }
        else if (strClick.contains("id/bt08")) { addNumber("8"); }
        else if (strClick.contains("id/bt09")) { addNumber("9"); }
        else if (strClick.contains("id/btDec")) { addNumber("."); }
        else if (strClick.contains("id/btRes")) { addOperator("="); }
        else if (strClick.contains("id/btSum")) { addOperator("+"); }
        else if (strClick.contains("id/btSub")) { addOperator("-"); }
        else if (strClick.contains("id/btMul")) { addOperator("*"); }
        else if (strClick.contains("id/btDiv")) { addOperator("/"); }
        else if (strClick.contains("id/btUnd")) {

            if (txDisplay.length() > 0) {
                if (txDisplay.substring(txDisplay.length()-1,txDisplay.length()).equals(".")) okDecimal = true;
                if (txDisplay.length() == 1) okZeroLeft = true;
                txDisplay = txDisplay.substring(0,txDisplay.length()-1);
            }

        }
        else if (strClick.contains("id/btClr")) {

            okReset = true;
            txDisplay = "";
            txMemory = "";
            txOperator = "";
            nCalc00 = 0.0;
            nCalc01 = 0.0;
            nCalcRs = 0.0;

        }

        vDisplay.setText(txDisplay);
        vMemory.setText(txMemory);
        vOperator.setText(txOperator);

    }


    public void addNumber(String txAdd) {

        if (okReset) {

            txDisplay = "";
            okReset = false;
            okZeroLeft = true;
            okDecimal = true;

        }


        switch (txAdd) {
            case "0":

                if (okZeroLeft == false) txDisplay += "0";
                break;

            case ".":

                if (okDecimal) { txDisplay+= "."; okZeroLeft = false; okDecimal = false; }
                break;

            default:

                txDisplay+= txAdd;
                okZeroLeft = false;
                break;

        }

    }


    public void addOperator(String refOp) {

        if (okReset == false) {

            if (txDisplay.length() > 0) {

                if (txOperator.equals("=") || txOperator.equals("")){
                    nCalcRs = Double.parseDouble("0" + txDisplay);
                    nCalc00 = nCalcRs;
                } else {
                    nCalc01 = Double.parseDouble("0" + txDisplay);
                }

                nCalc00 = nCalcRs;

                switch (txOperator) {
                    case "+":

                        nCalcRs = nCalc00 + nCalc01;
                        txMemory= nCalc00 + " + " + nCalc01 + " = " + nCalcRs + "\n" + txMemory;
                        break;

                    case "-":

                        nCalcRs = nCalc00 - nCalc01;
                        txMemory= nCalc00 + " - " + nCalc01 + " = " + nCalcRs + "\n" + txMemory;
                        break;

                    case "*":

                        nCalcRs = nCalc00 * nCalc01;
                        txMemory= nCalc00 + " * " + nCalc01 + " = " + nCalcRs + "\n" + txMemory;
                        break;

                    case "/":

                        if (nCalc01 != 0) {
                            nCalcRs = nCalc00 / nCalc01;
                        } else {
                            nCalcRs = 999999999999.;
                        }
                        txMemory= nCalc00 + " / " + nCalc01 + " = " + nCalcRs + "\n" + txMemory;
                        break;
                }

            }

        }

        txDisplay = String.valueOf(nCalcRs);

        okReset = true;
        txOperator = refOp;

    }


}
