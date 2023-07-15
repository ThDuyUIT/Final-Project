package com.example.finalproject.admin.managefunction.XE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.List;

public class XeAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Xe> xeList;

    public XeAdapter(Context context, int layout, List<Xe> xeList) {
        this.context = context;
        this.layout = layout;
        this.xeList = xeList;
    }

    private class ViewHolder{
        TextView txtNumberXe;
    }

    @Override
    public int getCount() {
        if(xeList != null){
            return xeList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            viewholder = new ViewHolder();

            viewholder.txtNumberXe = (TextView) view.findViewById(R.id.textviewNumberXe);
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }

        Xe xe = xeList.get(i);
        viewholder.txtNumberXe.setText(xe.getIdNumber());

        return view;
    }
}
