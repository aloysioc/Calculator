package com.lailkiu.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String mDisplay = "";
    private String mMemory = "";
    private String mOperator = "";
    private boolean mOkReset = true;
    private boolean mOkZeroLeft = true;
    private boolean mOkDecimal = true;
    private double mCalc00 = 0.0;
    private double mCalc01 = 0.0;
    private double mCalcRs = 0.0;

    static final String STATE_DISPLAY = "DISPLAY";
    static final String STATE_MEMORY = "MEMORY";
    static final String STATE_OPERATOR = "+";
    static final String STATE_RESET = "true";
    static final String STATE_ZERO_LEFT = "true";
    static final String STATE_DECIMAL = "true";
    static final String STATE_CALC00 = "0.0";
    static final String STATE_CALC01 = "0.0";
    static final String STATE_CALCRS = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mDisplay = savedInstanceState.getString(STATE_DISPLAY);
            mMemory = savedInstanceState.getString(STATE_MEMORY);
            mOperator = savedInstanceState.getString(STATE_OPERATOR);
            mOkReset = savedInstanceState.getBoolean(STATE_RESET);
            mOkZeroLeft = savedInstanceState.getBoolean(STATE_ZERO_LEFT);
            mOkDecimal = savedInstanceState.getBoolean(STATE_DECIMAL);
            mCalc00 = savedInstanceState.getDouble(STATE_CALC00);
            mCalc01 = savedInstanceState.getDouble(STATE_CALC01);
            mCalcRs = savedInstanceState.getDouble(STATE_CALCRS);
        }

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

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Saving user's current state
        savedInstanceState.putString(STATE_DISPLAY, mDisplay);
        savedInstanceState.putString(STATE_MEMORY, mMemory);
        savedInstanceState.putString(STATE_OPERATOR, mOperator);
        savedInstanceState.putBoolean(STATE_RESET, mOkReset);
        savedInstanceState.putBoolean(STATE_ZERO_LEFT, mOkZeroLeft);
        savedInstanceState.putBoolean(STATE_DECIMAL, mOkDecimal);
        savedInstanceState.putDouble(STATE_CALC00, mCalc00);
        savedInstanceState.putDouble(STATE_CALC01, mCalc01);
        savedInstanceState.putDouble(STATE_CALCRS, mCalcRs);
        //Saving view's current hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setScreen() {
        TextView vDisplay = (TextView)findViewById(R.id.Display);
        TextView vMemory = (TextView)findViewById(R.id.Memory);
        TextView vOperator = (TextView)findViewById(R.id.Operator);

        vDisplay.setText(mDisplay);
        vMemory.setText(mMemory);
        vOperator.setText(mOperator);
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
                if (mDisplay.length() > 0) {
                    if (mDisplay.substring(mDisplay.length()-1, mDisplay.length()).equals(".")) mOkDecimal = true;
                    if (mDisplay.length() == 1) mOkZeroLeft = true;
                    mDisplay = mDisplay.substring(0, mDisplay.length()-1);
                }
                break;
            case R.id.btReset:
                mOkReset = true;
                mDisplay = "";
                mMemory = "";
                mOperator = "";
                mCalc00 = 0.0;
                mCalc01 = 0.0;
                mCalcRs = 0.0;
                break;
        }
        setScreen();
    }


    public void addNumber(String txAdd) {

        if (mOkReset) {
            mDisplay = "";
            mOkReset = false;
            mOkZeroLeft = true;
            mOkDecimal = true;
        }

        switch (txAdd) {
            case "0":
                if (mOkZeroLeft == false) mDisplay += "0";
                break;
            case ".":
                if (mOkDecimal) {
                    if (mDisplay.length() == 0){ mDisplay += "0."; }
                    else { mDisplay += "."; }
                    mOkZeroLeft = false;
                    mOkDecimal = false;
                }
                break;
            default:
                mDisplay += txAdd;
                mOkZeroLeft = false;
                break;
        }
    }


    public void addOperator(String refOp) {

        if (mOkReset == false) {

            if (mDisplay.length() > 0) {

                if (mOperator.equals("=") || mOperator.equals("")){
                    mCalcRs = Double.parseDouble("0" + mDisplay);
                    mCalc00 = mCalcRs;
                } else {
                    mCalc01 = Double.parseDouble("0" + mDisplay);
                }

                mCalc00 = mCalcRs;

                switch (mOperator) {
                    case "+":
                        mCalcRs = mCalc00 + mCalc01;
                        mMemory = mCalc00 + " + " + mCalc01 + " = " + mCalcRs + "\n" + mMemory;
                        break;
                    case "-":
                        mCalcRs = mCalc00 - mCalc01;
                        mMemory = mCalc00 + " - " + mCalc01 + " = " + mCalcRs + "\n" + mMemory;
                        break;
                    case "*":
                        mCalcRs = mCalc00 * mCalc01;
                        mMemory = mCalc00 + " * " + mCalc01 + " = " + mCalcRs + "\n" + mMemory;
                        break;
                    case "/":
                        if (mCalc01 != 0) {
                            mCalcRs = mCalc00 / mCalc01;
                        } else {
                            mCalcRs = 999999999999.;
                        }
                        mMemory = mCalc00 + " / " + mCalc01 + " = " + mCalcRs + "\n" + mMemory;
                        break;
                }
            }
        }
        mDisplay = String.valueOf(mCalcRs);
        mOkReset = true;
        mOperator = refOp;
    }
}