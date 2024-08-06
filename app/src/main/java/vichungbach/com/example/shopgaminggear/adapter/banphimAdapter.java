package vichungbach.com.example.shopgaminggear.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;

import java.text.DecimalFormat;
import java.util.List;

import vichungbach.com.example.shopgaminggear.Interface.itemclickListener;
import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.activity.chitietPDAct;
import vichungbach.com.example.shopgaminggear.model.productTD;

public class banphimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<productTD> array;

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public banphimAdapter(Context context, List<productTD> array) {
        this.context = context;
        this.array = array;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banphim,parent,false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder =(MyViewHolder) holder;
            productTD productBanPhim = array.get(position);
            myViewHolder.tensp.setText(productBanPhim.getTenPD().trim());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(productBanPhim.getGiaPD())) + " VNĐ");
            myViewHolder.motasp.setText(productBanPhim.getMotaPD());
            Glide.with(context).load(productBanPhim.getHinhPD()).into(myViewHolder.imgbanphim);
            myViewHolder.setItemclickListener(new itemclickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick){
                        Intent intent = new Intent(context, chitietPDAct.class);
                        intent.putExtra("chitiet",productBanPhim);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });

        }else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }



    public class LoadingViewHolder extends  RecyclerView.ViewHolder{

        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tensp,giasp , motasp;
        ImageView imgbanphim;
        private itemclickListener itemclickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.tv_tenBanPhim);
            giasp = itemView.findViewById(R.id.tv_giaBanPhim);
            motasp = itemView.findViewById(R.id.tv_motaBanPhim);
            imgbanphim = itemView.findViewById(R.id.imgBanPhim);
            itemView.setOnClickListener(this);

        }

        public void setItemclickListener(vichungbach.com.example.shopgaminggear.Interface.itemclickListener itemclickListener) {
            this.itemclickListener = itemclickListener;
        }

        @Override
        public void onClick(View v) {
            itemclickListener.onClick(v,getAdapterPosition(),false);
        }
    }
}
