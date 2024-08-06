package vichungbach.com.example.shopgaminggear.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.retrofit.ApiGaminggearshop;
import vichungbach.com.example.shopgaminggear.retrofit.retrofitClient;
import vichungbach.com.example.shopgaminggear.utils.Utils;

public class logInAct extends AppCompatActivity {

    TextView txtDangKy;
    EditText email, pass;
    AppCompatButton btnDangNhap;
    ApiGaminggearshop apiGaminggearshop;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();
        initControl();
    }

    private void initControl() {
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), registerAct.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_pass)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Password", Toast.LENGTH_SHORT).show();
                } else {
                    compositeDisposable.add(apiGaminggearshop.dangnhap(str_email, str_pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if (userModel.isSuccess()) {
                                            Utils.userCR = userModel.getResult().get(0);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Đăng nhập không thành công. Vui lòng kiểm tra lại email và mật khẩu", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void initView() {
        apiGaminggearshop = retrofitClient.getInstance(Utils.BASE_URL).create(ApiGaminggearshop.class);
        txtDangKy = findViewById(R.id.tv_DKTK);
        email = findViewById(R.id.edt_dnmail);
        pass = findViewById(R.id.edt_dnpass);
        btnDangNhap = findViewById(R.id.btnDN);
        Paper.init(this);

        if (Paper.book().read("email") != null && Paper.book().read("pass") != null) {
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.userCR.getEmail() != null && Utils.userCR.getPass() != null) {
            email.setText(Utils.userCR.getEmail());
            pass.setText(Utils.userCR.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
