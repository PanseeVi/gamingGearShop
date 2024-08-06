package vichungbach.com.example.shopgaminggear.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.adapter.loaiSPAdapter;
import vichungbach.com.example.shopgaminggear.adapter.sanphamAdapter;
import vichungbach.com.example.shopgaminggear.model.pdTDModel;
import vichungbach.com.example.shopgaminggear.model.productModel;
import vichungbach.com.example.shopgaminggear.model.productTD;
import vichungbach.com.example.shopgaminggear.model.products;
import vichungbach.com.example.shopgaminggear.retrofit.ApiGaminggearshop;
import vichungbach.com.example.shopgaminggear.retrofit.retrofitClient;
import vichungbach.com.example.shopgaminggear.utils.Utils;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHomeScreen;
    DrawerLayout drawerLayout;

    List<products> lstLoaiSanPham;
    List<productTD> lstProduct;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiGaminggearshop apiGaminggearshop;
    NotificationBadge notificationBadge;
    FrameLayout frameLayout;


//khai bao adt
    loaiSPAdapter adapterSP;

    sanphamAdapter adapterPD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiGaminggearshop = retrofitClient.getInstance(Utils.BASE_URL).create(ApiGaminggearshop.class);

        anhXa();
        actionBAr();

        if (isConnected(this)){
            
            actionViewFlipper();
            getLoaiSanPham();
            getProduct();
            getEventClick();
        }else {
            Toast.makeText(this, "Không có internet, vui lòng kiểm tra lại mạng!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEventClick() {

        listViewHomeScreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent Home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(Home);
                        break;


                    case 1:
                        Intent banphim = new Intent(getApplicationContext(), banphimAct.class);
                        startActivity(banphim);
                        banphim.putExtra("loai",1);
                        break;

                    case 2:
                        Intent tainghe = new Intent(getApplicationContext(), banphimAct.class);
                        tainghe.putExtra("loai",2);
                        startActivity(tainghe);
                        break;
                    case 3:
                        Intent chuot = new Intent(getApplicationContext(), banphimAct.class);
                        chuot.putExtra("loai",3);
                        startActivity(chuot);
                        break;
                }
            }
        });{

        }

    }

    private void getProduct() {
        compositeDisposable.add(apiGaminggearshop.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                   pdTDModel -> {
                       if (pdTDModel.isSuccess()){
                              lstProduct = pdTDModel.getResult();
                              adapterPD = new sanphamAdapter(getApplicationContext(),lstProduct);
                              recyclerViewHome.setAdapter(adapterPD);
                       }

                   },throwable -> {
                       Toast.makeText(getApplicationContext(),"Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiGaminggearshop.getloaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel ->{
                            if (productModel.isSuccess()){
                                lstLoaiSanPham = productModel.getResult();
                                adapterSP = new loaiSPAdapter(getApplicationContext(),lstLoaiSanPham);
                                listViewHomeScreen.setAdapter(adapterSP);
                            }
                        }
        ));
    }

    private void actionViewFlipper() {
        List<String> mangquangcao =new ArrayList<>();
        mangquangcao.add("https://cdn.techzones.vn/Data/Sites/1/media/content/blogs/gaming-gear-la-gi/techzones-tim-hieu-xem-gaming-gear-la-gi.jpg?w=1920");
        mangquangcao.add("https://cdn.tgdd.vn//GameApp/1370924//gaming-gear-la-gi-top-5-gaming-gear-ma-game-thu-nao-cung-nen-800x450-1.jpg");
        mangquangcao.add("https://www.everythingusb.com/media/hardcore-gaming.jpg");

        for (int i =0 ; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.silde_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.silde_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    // actionbar
    private void actionBAr() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    // anh xa
    private void anhXa() {

        toolbar = findViewById(R.id.toolBarHome);
        viewFlipper = findViewById(R.id.viewFlipper);

        //setting RCV
        recyclerViewHome = findViewById(R.id.rcvHotProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.setHasFixedSize(true);

        navigationView = findViewById(R.id.navHomeScreen);
        listViewHomeScreen = findViewById(R.id.lstViewHome);
        drawerLayout = findViewById(R.id.drawerLayout);
        notificationBadge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.item_tocart);


        //khoi tao list sp
        lstLoaiSanPham = new ArrayList<>();
        lstProduct = new ArrayList<>();

       if (Utils.lstGiohang == null ){
           Utils.lstGiohang = new ArrayList<>();
       }else {
           int totalItem = 0;
           for(int i=0;i < Utils.lstGiohang.size();i++){
               totalItem = totalItem+Utils.lstGiohang.get(i).getSoluong();
           }
           notificationBadge.setText(String.valueOf(totalItem));
       }
       frameLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent giohang = new Intent(getApplicationContext(), gioHangAct.class);
               startActivity(giohang);
           }
       });


    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for(int i=0;i < Utils.lstGiohang.size();i++){
            totalItem = totalItem+Utils.lstGiohang.get(i).getSoluong();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else {
            return false;
        }



    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}