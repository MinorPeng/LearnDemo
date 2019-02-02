package com.example.a14512.mvp_login.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a14512.mvp_login.R;
import com.example.a14512.mvp_login.presenter.Userpresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, IUserView {

    @Bind(R.id.id_edt)
    EditText idEdt;
    @Bind(R.id.first_name_edt)
    EditText firstNameEdt;
    @Bind(R.id.last_name_edt)
    EditText lastNameEdt;
    @Bind(R.id.saveButton)
    Button saveButton;
    @Bind(R.id.loadButton)
    Button loadButton;

    private Userpresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mUserPresenter = new Userpresenter(this);
        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                mUserPresenter.saveUser(getID(), getFirstName(), getLastName());
                break;
            case R.id.loadButton:
                mUserPresenter.loadUser(getID());
                break;
            default:
                break;
        }
    }

    @Override
    public int getID() {
        return Integer.parseInt(idEdt.getText().toString());
    }

    @Override
    public String getFirstName() {
        return firstNameEdt.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastNameEdt.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        firstNameEdt.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        lastNameEdt.setText(lastName);
    }
}
