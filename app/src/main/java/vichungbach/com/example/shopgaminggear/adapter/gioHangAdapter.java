package vichungbach.com.example.shopgaminggear.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import vichungbach.com.example.shopgaminggear.Interface.imageClicklistener;
import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.model.EventBus.tinhtongEvent;
import vichungbach.com.example.shopgaminggear.model.gioHang;
import vichungbach.com.example.shopgaminggear.utils.Utils;

public class gioHangAdapter extends RecyclerView.Adapter<gioHangAdapter.MyViewHolder> {

    Context context;
    List<gioHang> gioHangList;

    public gioHangAdapter(Context context, List<gioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        gioHang gioHang = gioHangList.get(position);
        holder.item_cartTensp.setText(gioHang.getTensp());
        holder.tv_cartSoluong.setText(gioHang.getSoluong()+"");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_cartImg);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_cartGiasp.setText("Giá: " + decimalFormat.format((gioHang.getGiasp())) + " VNĐ");
        Long gia = gioHang.getSoluong()* gioHang.getGiasp();
        holder.item_cartGiaspTT.setText(decimalFormat.format(gia) + " VNĐ");

        holder.setImageClicklistener(new imageClicklistener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1){
                    if (gioHangList.get(pos).getSoluong()>1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.tv_cartSoluong.setText(gioHangList.get(pos).getSoluong()+"");
                        Long gia = gioHangList.get(pos).getSoluong()* gioHangList.get(pos).getGiasp();
                        holder.item_cartGiaspTT.setText(decimalFormat.format(gia) + " VNĐ");
                        EventBus.getDefault().postSticky(new tinhtongEvent());

                    } else if (gioHangList.get(pos).getSoluong()==1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.lstGiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new tinhtongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.show();
                    }
                } else if (giatri==2) {
                    if (gioHangList.get(pos).getSoluong()<11){
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.tv_cartSoluong.setText(gioHangList.get(pos).getSoluong()+"");
                    Long gia = gioHangList.get(pos).getSoluong()* gioHangList.get(pos).getGiasp();
                    holder.item_cartGiaspTT.setText(decimalFormat.format(gia) + " VNĐ");

                    EventBus.getDefault().postSticky(new tinhtongEvent());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_cartImg, giohangtru ,giohangcong;
        TextView item_cartTensp,item_cartGiasp,item_cartGiaspTT,tv_cartSoluong;

        imageClicklistener imageClicklistener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cartImg = itemView.findViewById(R.id.item_cartImg);
            item_cartTensp = itemView.findViewById(R.id.item_cartTensp);
            item_cartGiasp = itemView.findViewById(R.id.item_cartGiasp);
            tv_cartSoluong = itemView.findViewById(R.id.tv_cartSoluong);
            item_cartGiaspTT = itemView.findViewById(R.id.item_cartGiaspTT);
            giohangtru = itemView.findViewById(R.id.im_cartTrusp);
            giohangcong = itemView.findViewById(R.id.im_cartCongsp);


            giohangcong.setOnClickListener(this);
            giohangtru.setOnClickListener(this);

        }

        public void setImageClicklistener(vichungbach.com.example.shopgaminggear.Interface.imageClicklistener imageClicklistener) {
            this.imageClicklistener = imageClicklistener;
        }

        @Override
        public void onClick(View v) {
            if (v == giohangtru){
                imageClicklistener.onImageClick(v,getAdapterPosition(),1);
            }else if (v == giohangcong){
                imageClicklistener.onImageClick(v,getAdapterPosition(),2);
            }

        }
    }
}
