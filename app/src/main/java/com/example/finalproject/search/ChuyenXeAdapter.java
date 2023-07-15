package com.example.finalproject.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.ChooseTicketActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChuyenXeAdapter extends RecyclerView.Adapter<ChuyenXeAdapter.chuyenxeViewholder>{
    private List<ChuyenXe> chuyenXeList;
    private Context context;

    public ChuyenXeAdapter(List<ChuyenXe> chuyenXeList, Context context) {
        this.chuyenXeList = chuyenXeList;
        this.context = context;
    }

    public void setChuyenXeList(List<ChuyenXe> list) {
        this.chuyenXeList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public chuyenxeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chuyenxe, parent, false);
        return new chuyenxeViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chuyenxeViewholder holder, int position) {
        ChuyenXe chuyenXe = chuyenXeList.get(position);
        if(chuyenXe == null){
            return;
        }
        String idCity = chuyenXe.getTitle();
        String[] parts = idCity.split(" - ");
        String idStart = parts[0];
        String idEnd = parts[1];



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("DIADIEM");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String tranferedTitle = "";
                String nameStart = "";
                String nameEnd = "";
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String keyDD = dataSnapshot.getKey();
                    String nameDD = dataSnapshot.child("nameCity").getValue(String.class);
                    String imgDD = dataSnapshot.child("anhdaidienDD").getValue(String.class);
                    if (keyDD.equals(idStart)){
                        nameStart = nameDD;
                    }

                    if(keyDD.equals(idEnd)){
                        nameEnd = nameDD;

                        Uri uri = Uri.parse(imgDD);
                        Picasso.get().load(uri).into(holder.imgChuyenXe);
                    }
                }

                tranferedTitle = nameStart + " - " + nameEnd;

                holder.txtChuyenXe.setText(tranferedTitle);
                holder.txtGia.setText(chuyenXe.getPrice());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


//        holder.imgChuyenXe.setImageResource(chuyenXe.getResourceImg());
//        holder.txtChuyenXe.setText(chuyenXe.getTitle());
//        holder.txtGia.setText(chuyenXe.getPrice());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TextView textView1 = (TextView) view.findViewById(R.id.textViewStartPoint);
//                textView1.setText(chuyenXe.getTitle());
                //Toast.makeText(context, chuyenXe.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, ChooseTicketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("City", holder.txtChuyenXe.getText().toString());
                bundle.putSerializable("Featured Route", chuyenXe);
                bundle.putBoolean("from ChuyenXeAdapter", true);
                intent.putExtra("searchTicketInfo", bundle);
                context.startActivity(intent);
            }
        });

    }

//    private void setSearchInfo(ChuyenXe chuyenXe){
//        Intent intent = new Intent(context, ChooseTicketActivity.class);
//        context.startActivity(intent);
//    }

    @Override
    public int getItemCount() {
        if(chuyenXeList != null){
            return chuyenXeList.size();
        }
        return 0;
    }

    public class chuyenxeViewholder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView imgChuyenXe;
        private TextView txtChuyenXe;
        private TextView txtGia;

        public chuyenxeViewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewItem);
            imgChuyenXe = itemView.findViewById(R.id.imageviewChuyenXe);
            txtChuyenXe = itemView.findViewById(R.id.textviewChuyenXe);
            txtGia = itemView.findViewById(R.id.textviewGiaChuyenXe);

        }


    }
}
