package vichungbach.com.example.shopgaminggear.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.model.gioHang;
import vichungbach.com.example.shopgaminggear.model.productTD;
import vichungbach.com.example.shopgaminggear.utils.Utils;

public class chitietPDAct extends AppCompatActivity {
    TextView tensp, giasp, motasp;
    ImageView imgsp;
    Button btnaddCart;
    Spinner spnctsp;
    Toolbar toolbar;

    productTD productTD;

    NotificationBadge notificationBadge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_pdact);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnaddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        if (Utils.lstGiohang.size()>0){
            boolean flag = false;

            int soluong = Integer.parseInt(spnctsp.getSelectedItem().toString());
            for (int i = 0; i < Utils.lstGiohang.size();i++){
                if (Utils.lstGiohang.get(i).getIdsp()== productTD.getId()){
                    Utils.lstGiohang.get(i).setSoluong(soluong+Utils.lstGiohang.get(i).getSoluong());
                    Long gia = Long.parseLong(productTD.getGiaPD());//*Utils.lstGiohang.get(i).getSoluong();
                    Utils.lstGiohang.get(i).setGiasp(gia);
                    flag = true;

                }

            }
            if (flag == false){
                long gia = Long.parseLong(productTD.getGiaPD())*soluong;
                gioHang giohang = new gioHang();
                giohang.setGiasp(gia);
                giohang.setSoluong(soluong);
                giohang.setIdsp(productTD.getId());
                giohang.setTensp(productTD.getTenPD());
                giohang.setHinhsp(productTD.getHinhPD());
                Utils.lstGiohang.add(giohang);

            }

        }else {
            int soluong = Integer.parseInt(spnctsp.getSelectedItem().toString());
           long gia = Long.parseLong(productTD.getGiaPD())*soluong;
            gioHang giohang = new gioHang();
            giohang.setGiasp(gia);
            giohang.setSoluong(soluong);
            giohang.setIdsp(productTD.getId());
            giohang.setTensp(productTD.getTenPD());
            giohang.setHinhsp(productTD.getHinhPD());
            Utils.lstGiohang.add(giohang);
        }
        int totalItem = 0;
        for(int i=0;i < Utils.lstGiohang.size();i++){
            totalItem = totalItem+Utils.lstGiohang.get(i).getSoluong();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        productTD = (productTD) getIntent().getSerializableExtra("chitiet");
        tensp.setText((productTD.getTenPD()));
        motasp.setText(productTD.getMotaPD());
        Glide.with(getApplicationContext()).load(productTD.getHinhPD()).into(imgsp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(productTD.getGiaPD())) + " VNĐ");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);

        spnctsp.setAdapter(adapter);


    }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initView() {
        tensp = findViewById(R.id.tenCTSP);
        giasp = findViewById(R.id.giaCTSP);
        motasp = findViewById(R.id.motaCTSP);
        btnaddCart = findViewById(R.id.btnThemGH);
        spnctsp = findViewById(R.id.spnCTSP);
        imgsp = findViewById(R.id.imgChitiet);
        toolbar = findViewById(R.id.toolBarChiTiet);
        notificationBadge = findViewById(R.id.menu_sl);


        FrameLayout frameLayoutCart = findViewById(R.id.item_tocart);
        frameLayoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang = new Intent(getApplicationContext(), gioHangAct.class);
                startActivity(giohang);

            }
        });

        if (Utils.lstGiohang !=null){

            int totalItem = 0;
            for(int i=0;i < Utils.lstGiohang.size();i++){
                totalItem = totalItem+Utils.lstGiohang.get(i).getSoluong();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.lstGiohang !=null){

            int totalItem = 0;
            for(int i=0;i < Utils.lstGiohang.size();i++){
                totalItem = totalItem+Utils.lstGiohang.get(i).getSoluong();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }
}