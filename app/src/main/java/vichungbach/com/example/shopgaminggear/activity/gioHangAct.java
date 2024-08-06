package vichungbach.com.example.shopgaminggear.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.adapter.gioHangAdapter;
import vichungbach.com.example.shopgaminggear.model.EventBus.tinhtongEvent;
import vichungbach.com.example.shopgaminggear.model.gioHang;
import vichungbach.com.example.shopgaminggear.utils.Utils;

public class gioHangAct extends AppCompatActivity {

    TextView txtGioHangNull, txtTongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnMuahang;
    gioHangAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tongTien();
    }

    private void tongTien() {
        long tongtientra = 0;
        for (int i = 0;i<Utils.lstGiohang.size();i++){
            tongtientra = tongtientra + (Utils.lstGiohang.get(i).getGiasp()*Utils.lstGiohang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtientra)+" VNĐ");
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (Utils.lstGiohang.size() == 0){
            txtGioHangNull.setVisibility(View.VISIBLE);
        }else {
            adapter = new gioHangAdapter(getApplicationContext(),Utils.lstGiohang);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initView() {
        txtGioHangNull = findViewById(R.id.txtGioHangNull);
        toolbar = findViewById(R.id.toolbarCart);
        txtTongtien = findViewById(R.id.tv_TotalCart);
        recyclerView= findViewById(R.id.recCart);
        btnMuahang= findViewById(R.id.btnMuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTingTien(tinhtongEvent event){
        if (event!= null){
            tongTien();
        }

    }
}