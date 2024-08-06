package vichungbach.com.example.shopgaminggear.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import vichungbach.com.example.shopgaminggear.Interface.itemclickListener;
import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.activity.chitietPDAct;
import vichungbach.com.example.shopgaminggear.model.productTD;

public class sanphamAdapter extends RecyclerView.Adapter<sanphamAdapter.MyViewHolder> {
    Context context;
    List<productTD> array;

    public sanphamAdapter(Context context, List<productTD> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pd, parent ,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        productTD productTD = array.get(position);
        holder.txtTen.setText(productTD.getTenPD());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText("Giá: " + decimalFormat.format(Double.parseDouble(productTD.getGiaPD())) + " VNĐ");
        Glide.with(context).load(productTD.getHinhPD()).into(holder.imgHinhPD);
        holder.setItemclickListener(new itemclickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    Intent intent = new Intent(context, chitietPDAct.class);
                    intent.putExtra("chitiet",productTD);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTen, txtGia;
        ImageView imgHinhPD;
        private itemclickListener itemclickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.it_tv_tenPD);
            txtGia = itemView.findViewById(R.id.it_tv_giaPD);
            imgHinhPD = itemView.findViewById(R.id.item_hinhPD);
            itemView.setOnClickListener(this);
        }

        public void setItemclickListener(vichungbach.com.example.shopgaminggear.Interface.itemclickListener itemclickListener) {
            this.itemclickListener = itemclickListener;
        }

        @Override
        public void onClick(View v) {
            itemclickListener.onClick(v, getAdapterPosition(),false);
        }
    }
}
