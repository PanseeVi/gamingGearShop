package vichungbach.com.example.shopgaminggear.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.adapter.banphimAdapter;
import vichungbach.com.example.shopgaminggear.model.productTD;
import vichungbach.com.example.shopgaminggear.retrofit.ApiGaminggearshop;
import vichungbach.com.example.shopgaminggear.retrofit.retrofitClient;
import vichungbach.com.example.shopgaminggear.utils.Utils;

public class banphimAct extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiGaminggearshop apiGaminggearshop;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;

    banphimAdapter banphimAdt;
    List<productTD> lstBanPhim;

    LinearLayoutManager linearLayoutManager;
    Handler handler =new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banphim);
        apiGaminggearshop = retrofitClient.getInstance(Utils.BASE_URL).create(ApiGaminggearshop.class);
        loai = getIntent().getIntExtra("loai",1);

        Anhxa();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition()==lstBanPhim.size()-1){
                        isLoading =true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                lstBanPhim.add(null);
                banphimAdt.notifyItemInserted(lstBanPhim.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lstBanPhim.remove(lstBanPhim.size()-1);
                banphimAdt.notifyItemRemoved(lstBanPhim.size());
                page = page+1;
                getData(page);
                banphimAdt.notifyDataSetChanged();
                isLoading = false;
            }
        },1000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiGaminggearshop.getSanPham(page,loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(


                        pdTDModel -> {
                            if (pdTDModel.isSuccess()){
                                if (banphimAdt == null){
                                    lstBanPhim = pdTDModel.getResult();
                                    banphimAdt = new banphimAdapter(getApplicationContext(),lstBanPhim);
                                    recyclerView.setAdapter(banphimAdt);

                                }else{
                                    int vitri = lstBanPhim.size()-1;
                                    int soluongadd = pdTDModel.getResult().size();
                                    for (int i = 0; i< soluongadd;i++){
                                        lstBanPhim.add(pdTDModel.getResult().get(i));
                                    }
                                    banphimAdt.notifyItemRangeInserted(vitri,soluongadd);

                                }

                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "không kết nối được với server", Toast.LENGTH_LONG).show();
                        }
                ));
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

    private void Anhxa() {
        toolbar = findViewById(R.id.toolBarBanPhim);
        recyclerView = findViewById(R.id.rcvBanPhim);
        linearLayoutManager =  new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        lstBanPhim = new ArrayList<>();
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}