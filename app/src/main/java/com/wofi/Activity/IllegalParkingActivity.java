package com.wofi.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.wofi.R;
import com.wofi.constants.Constants;
import com.wofi.utils.HttpUtils;
import com.wofi.utils.PayPwdEditText;

import java.io.IOException;
import java.net.URLEncoder;


/**
 * Created by zxx on 2017/8/9.
 */

public class IllegalParkingActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private PayPwdEditText payPwdEditText;
    private Toolbar toolbar_illegal;
    private String BicycleId;
    private Button btn_rep;
    private String Title = "举报违停";
    private String Contenttext = "原因";
    private CheckBox[] checkBox = new CheckBox[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.illegal_parking_activity);
        toolbar_illegal = (Toolbar) findViewById(R.id.toolbar_illegal);
        btn_rep = (Button) findViewById(R.id.btn_rep);
        btn_rep.setOnClickListener(this);
        setSupportActionBar(toolbar_illegal);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        payPwdEditText = (PayPwdEditText) findViewById(R.id.ppe_pwd1);
        payPwdEditText.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                BicycleId= String.valueOf(Integer.parseInt(payPwdEditText.getPwdText()));
                Log.e("车辆id",BicycleId);

                Toast.makeText(IllegalParkingActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
        checkBox[0] = (CheckBox) findViewById(R.id.checkbox11);
        checkBox[1] = (CheckBox) findViewById(R.id.checkbox12);
        checkBox[2] = (CheckBox) findViewById(R.id.checkbox13);
        checkBox[3] = (CheckBox) findViewById(R.id.checkbox14);
        checkBox[4] = (CheckBox) findViewById(R.id.checkbox15);
        for (int i = 0; i < 5; i++) {
            checkBox[i].setOnCheckedChangeListener(this);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rep:
                initFeedback();
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                break;

        }

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.checkbox11:
                if (checkBox[0].isChecked()) {
                    Contenttext = Contenttext + "," + checkBox[0].getText().toString();
                } else {
                    Contenttext = Contenttext.replaceAll("," + checkBox[0].getText().toString(), "");
                }
                break;
            case R.id.checkbox12:
                if (checkBox[1].isChecked()) {
                    Contenttext = Contenttext + "," + checkBox[1].getText().toString();
                } else {
                    Contenttext = Contenttext.replaceAll("," + checkBox[1].getText().toString(), "");
                }
                break;
            case R.id.checkbox13:
                if (checkBox[2].isChecked()) {
                    Contenttext = Contenttext + "," + checkBox[2].getText().toString();
                } else {
                    Contenttext = Contenttext.replaceAll("," + checkBox[2].getText().toString(), "");

                }
                break;
            case R.id.checkbox14:
                if (checkBox[3].isChecked()) {
                    Contenttext = Contenttext + "," + checkBox[3].getText().toString();
                } else {
                    Contenttext = Contenttext.replaceAll("," + checkBox[3].getText().toString(), "");
                }
                break;
        }
    }

    private void initFeedback()
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                try {
                    String sTitle= URLEncoder.encode(Title,"utf-8");
                    String sContenttext=URLEncoder.encode(Contenttext,"utf-8");
                    HttpUtils.sendOkHttpRequest(Constants.USERFEEDBACK_URL+sTitle+"/"+sContenttext+"/"+BicycleId+"/"+sp.getString("Username",""));
                    Log.e("反馈",Constants.USERFEEDBACK_URL+sTitle+"/"+sContenttext+"/"+BicycleId+"/"+sp.getString("Username",""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

}
