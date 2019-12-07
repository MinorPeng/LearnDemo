package com.phs1024.studydemo.report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.util.CalculateUtil;

/**
 * @author PHS1024
 */
public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final char[] LEGAL_NUM = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ')'};

    private EditText mEtExpression;
    private Button mBtnZero;
    private Button mBtnPoint, mBtnDel, mBtnClear, mBtnEquals;
    private Button mBtnAdd, mBtnSubtract, mBtnMultiply, mBtnDivide, mBtnLeftParenthesis,
            mBtnRightParenthesis, mBtnPow, mBtnRemainder;

    private StringBuilder mExpression = new StringBuilder(LEGAL_NUM[0]);
    private boolean mCalculated = false;
    private int mParenthesises = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcultor);
        initView();
    }

    private void initView() {
        mEtExpression = findViewById(R.id.et_expression);

        Button btnOne = findViewById(R.id.btn_one);
        Button btnTwo = findViewById(R.id.btn_two);
        Button btnThree = findViewById(R.id.btn_three);
        Button btnFour = findViewById(R.id.btn_four);
        Button btnFive = findViewById(R.id.btn_five);
        Button btnSix = findViewById(R.id.btn_six);
        Button btnSeven = findViewById(R.id.btn_seven);
        Button btnEight = findViewById(R.id.btn_eight);
        Button btnNine = findViewById(R.id.btn_nine);
        mBtnZero = findViewById(R.id.btn_zero);

        mBtnPoint = findViewById(R.id.btn_point);
        mBtnDel = findViewById(R.id.btn_del);
        mBtnClear = findViewById(R.id.btn_clear);
        mBtnEquals = findViewById(R.id.btn_equals);

        mBtnAdd = findViewById(R.id.btn_add);
        mBtnSubtract = findViewById(R.id.btn_subtract);
        mBtnMultiply = findViewById(R.id.btn_multiply);
        mBtnDivide = findViewById(R.id.btn_divide);
        mBtnLeftParenthesis = findViewById(R.id.btn_left_parenthesis);
        mBtnRightParenthesis = findViewById(R.id.btn_right_parenthesis);
        mBtnPow = findViewById(R.id.btn_pow);
        mBtnRemainder = findViewById(R.id.btn_remainder);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        mBtnZero.setOnClickListener(this);

        mBtnPoint.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
        mBtnEquals.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnSubtract.setOnClickListener(this);
        mBtnMultiply.setOnClickListener(this);
        mBtnDivide.setOnClickListener(this);
        mBtnLeftParenthesis.setOnClickListener(this);
        mBtnRightParenthesis.setOnClickListener(this);
        mBtnPow.setOnClickListener(this);
        mBtnRemainder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mCalculated) {
            mExpression.delete(0, mExpression.length());
            mCalculated = false;
        }
        switch (v.getId()) {
            case R.id.btn_one:
            case R.id.btn_two:
            case R.id.btn_three:
            case R.id.btn_four:
            case R.id.btn_five:
            case R.id.btn_six:
            case R.id.btn_seven:
            case R.id.btn_eight:
            case R.id.btn_nine:
                mExpression.append(((Button) v).getText());
                break;
            case R.id.btn_zero:
                clickZero();
                break;
            case R.id.btn_point:
                clickPoint();
                break;
            case R.id.btn_equals:
                Log.e(this.getLocalClassName(), "" + mParenthesises);
                //等于直接计算结果 保证算术表达式合法性
                if (legal() && mParenthesises == 0) {
                    clickEqual();
                } else {
                    Toast.makeText(this, "非法表达式!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_del:
                //删除一个字符
                if (mExpression.charAt(mExpression.length() - 1) == '(') {
                    mParenthesises--;
                }
                if (mExpression.charAt(mExpression.length() - 1) == ')') {
                    mParenthesises++;
                }
                clickDel();
                break;
            case R.id.btn_clear:
                //清空表达式
                mParenthesises = 0;
                mExpression.delete(0, mExpression.length());
                break;
            case R.id.btn_add:
                clickAdd();
                break;
            case R.id.btn_subtract:
                clickSubtract();
                break;
            case R.id.btn_multiply:
                clickMultiply();
                break;
            case R.id.btn_divide:
                clickDivide();
                break;
            case R.id.btn_left_parenthesis:
                if (mExpression.length() == 0 || mExpression.charAt(mExpression.length() - 1) == '('
                        || (!legal() && mExpression.charAt(mExpression.length() - 1) != '.')) {
                    mExpression.append(mBtnLeftParenthesis.getText());
                    mParenthesises++;
                }
                break;
            case R.id.btn_right_parenthesis:
                if (legal() && mParenthesises > 0) {
                    mExpression.append(mBtnRightParenthesis.getText());
                    mParenthesises--;
                }
                break;
            case R.id.btn_pow:
                clickPow();
                break;
            case R.id.btn_remainder:
                clickRemainder();
                break;
            default:
                break;
        }
        mEtExpression.setText(mExpression);
    }

    private void clickZero() {
        //当第一位为0的时候 不允许连续输入0 如0000
        if (mExpression.length() == 1 && mExpression.toString().contentEquals(mBtnZero.getText())) {
            return;
        }
        mExpression.append(mBtnZero.getText());
    }

    private void clickPoint() {
        //允许第一位为小数点 自动在其前面加0
        if (mExpression.toString().isEmpty()) {
            mExpression.append(mBtnZero.getText()).append(mBtnPoint.getText());
        }
        //保证小数点前面是数字
        if (Character.isDigit(mExpression.charAt(mExpression.length() - 1))) {
            mExpression.append(mBtnPoint.getText());
        }
    }

    private void clickAdd() {
        //+前面只能为数字或者')'等操作符
        if (!legal()) {
            return;
        }
        mExpression.append(mBtnAdd.getText());
    }

    private void clickSubtract() {
        //-前面只能为数字或者')'等操作符
        if (!legal()) {
            if (mExpression.length() == 0) {
                mExpression.append(LEGAL_NUM[0]).append(mBtnSubtract.getText());
            }
            return;
        }
        mExpression.append(mBtnSubtract.getText());
    }

    private void clickMultiply() {
        //*前面只能为数字或者')'等操作符
        if (!legal()) {
            return;
        }
        mExpression.append(mBtnMultiply.getText());
    }

    private void clickDivide() {
        //前面只能为数字或者')'等操作符
        if (!legal()) {
            return;
        }
        mExpression.append(mBtnDivide.getText());
    }

    private void clickPow() {
        //^前面只能为数字或者')'等操作符
        if (!legal()) {
            return;
        }
        mExpression.append(mBtnPow.getText());
    }

    private void clickRemainder() {
        //%前面只能为数字或者')'等操作符
        if (!legal()) {
            return;
        }
        mExpression.append(mBtnRemainder.getText());
    }

    private void clickDel() {
        if (mExpression.length() == 0) {
            return;
        }
        mExpression.deleteCharAt(mExpression.length() - 1);
    }

    private void clickEqual() {
        if (mExpression.length() == 0) {
            return;
        }
        String result = CalculateUtil.calculate(mExpression.toString());
        mExpression.append("=\n").append(result);
        mCalculated = true;
    }

    /**
     * 判断最后一位字符，是否合法输入操作符
     * @return boolean true legal, false illegal
     */
    private boolean legal() {
        if (mExpression.length() == 0) {
            return false;
        }
        char pre = mExpression.charAt(mExpression.length() - 1);
        for (char c : LEGAL_NUM) {
            if (pre == c) {
                return true;
            }
        }
        return false;
    }
}
