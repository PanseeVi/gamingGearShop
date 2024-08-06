package vichungbach.com.example.shopgaminggear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import vichungbach.com.example.shopgaminggear.R;
import vichungbach.com.example.shopgaminggear.model.products;

public class loaiSPAdapter extends BaseAdapter {


    List<products> array;
    Context context;

    public loaiSPAdapter( Context context,List<products> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView texttensp;
        ImageView imghinhanh;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_sanpham,null);
            viewHolder.texttensp = convertView.findViewById(R.id.item_tenSP);
            viewHolder.imghinhanh = convertView.findViewById(R.id.item_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.texttensp.setText(array.get(position).getTenSP());
        Glide.with(context).load(array.get(position).getHinhanh()).into(viewHolder.imghinhanh);
        return convertView;
    }
}
