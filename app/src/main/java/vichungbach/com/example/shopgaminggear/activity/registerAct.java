package vichungbach.com.example.shopgaminggear.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.retrofit.ApiGaminggearshop;
import vichungbach.com.example.shopgaminggear.retrofit.retrofitClient;
import vichungbach.com.example.shopgaminggear.utils.Utils;

public class registerAct extends AppCompatActivity {

    EditText email, pass,repass,mobile,username;
    AppCompatButton btnDangKy;
    ApiGaminggearshop apiGaminggearshop;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }

    private void initControl() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangky();
            }
        });
    }

    private void dangky() {
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_mobile = mobile.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập lại Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_mobile)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập mobile", Toast.LENGTH_SHORT).show();
        }  else if (TextUtils.isEmpty(str_username)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập username", Toast.LENGTH_SHORT).show();
        } else {
            if (str_pass.equals(str_repass)){
                compositeDisposable.add(apiGaminggearshop.dangky(str_email,str_pass,str_username,str_mobile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                        Utils.userCR.setEmail(str_email);
                                        Utils.userCR.setPass(str_pass);
                                        Intent intent = new Intent(getApplicationContext(), logInAct.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }else{
                Toast.makeText(getApplicationContext(),"Pass chưa khớp", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void initView() {
        apiGaminggearshop = retrofitClient.getInstance(Utils.BASE_URL).create( ApiGaminggearshop.class);
        email = findViewById(R.id.edt_dkmail);
        pass = findViewById(R.id.edt_dkpass);
        repass = findViewById(R.id.edt_dkrepass);
        btnDangKy = findViewById(R.id.btnDK);
        mobile = findViewById(R.id.edt_dkmobile);
        username = findViewById(R.id.edt_dkusername);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}