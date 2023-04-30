package com.example.finalproject.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewholder>{
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setCategoryList(List<Category> list) {
        this.categoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public categoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new categoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewholder holder, int position) {
        Category category = categoryList.get(position);
        if(category == null){
            return;
        }

        holder.textViewCategory.setText(category.getNameCategory());

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcvChuyenXe.setLayoutManager(linearLayoutManager1);

        ChuyenXeAdapter chuyenXeAdapter = new ChuyenXeAdapter();
        chuyenXeAdapter.setChuyenXeList(category.getChuyenXeList());
        holder.rcvChuyenXe.setAdapter(chuyenXeAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcvKhuyenMai.setLayoutManager(linearLayoutManager2);

        KhuyenMaiAdapter khuyenMaiAdapter = new KhuyenMaiAdapter();
        khuyenMaiAdapter.setKhuyenMaiList(category.getKhuyenMaiList());
        holder.rcvKhuyenMai.setAdapter(khuyenMaiAdapter);
    }

    @Override
    public int getItemCount() {
        if(categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public class categoryViewholder extends RecyclerView.ViewHolder {

        private TextView textViewCategory;
        private RecyclerView rcvChuyenXe, rcvKhuyenMai;

        public categoryViewholder(@NonNull View itemView) {
            super(itemView);

            textViewCategory = itemView.findViewById(R.id.texviewNameCategory1);

            rcvChuyenXe = itemView.findViewById(R.id.recycleviewCategory1);
            rcvKhuyenMai = itemView.findViewById(R.id.recycleviewCategory2);
        }
    }
}
