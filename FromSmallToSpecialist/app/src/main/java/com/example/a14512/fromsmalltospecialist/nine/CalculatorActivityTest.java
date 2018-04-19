package com.example.a14512.fromsmalltospecialist.nine;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a14512.fromsmalltospecialist.R;
import com.example.a14512.fromsmalltospecialist.activity.CalculatorActivity;

/**
 * @author 14512 on 2018/4/10
 */

public class CalculatorActivityTest extends ActivityUnitTestCase<CalculatorActivity> {

    CalculatorActivity mListActivity;
    EditText mParam1Edt, mParam2Edt;
    Button mCalculate;
    TextView mResultTv;

    public CalculatorActivityTest() {
        super(CalculatorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Context context = getInstrumentation().getTargetContext();
        Intent intent = new Intent(context, CalculatorActivity.class);
        startActivity(intent, null, null);
        mListActivity = getActivity();
        checkWidgets();
    }

    private void checkWidgets() {
        mParam1Edt = mListActivity.findViewById(R.id.editText);
        assertNotNull(mParam1Edt);
        assertEquals("", mParam1Edt.getText().toString());

        mParam2Edt = mListActivity.findViewById(R.id.editText2);
        assertNotNull(mParam2Edt);
        assertEquals("", mParam2Edt.getText().toString());

        mCalculate = mListActivity.findViewById(R.id.btnCalculate);
        assertNotNull(mCalculate);
        mResultTv = mListActivity.findViewById(R.id.tvResult);
        assertNotNull(mResultTv);
    }

    public void testAddInActivity() {
        mParam1Edt.setText("2");
        mParam2Edt.setText("3");
        mCalculate.performClick();
        assertEquals(5, (int) Integer.valueOf(mResultTv.getText().toString()));
    }
}
