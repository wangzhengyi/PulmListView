package com.zhengyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhengyi.R;
import com.zhengyi.library.PulmBaseAdapter;

import java.util.List;

public class PulmImplAdapter extends PulmBaseAdapter<String> {
    private LayoutInflater inflater;

    public PulmImplAdapter(Context context, List<String> items) {
        super(items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_item_layout, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_img);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.id_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(items.get(position));
        viewHolder.imageView.setImageResource(R.drawable.huoying);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
