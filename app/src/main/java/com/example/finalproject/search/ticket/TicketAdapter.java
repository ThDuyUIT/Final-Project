package com.example.finalproject.search.ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

//public class TicketAdapter extends BaseAdapter {
//    private Context context;
//    private int layout;
//    private List<Ticket> ticketList;
//
//    public TicketAdapter(Context context, int layout, List<Ticket> ticketList) {
//        this.context = context;
//        this.layout = layout;
//        this.ticketList = ticketList;
//    }
//
//
//    @Override
//    public int getCount() {
//        if(ticketList != null){
//            return ticketList.size();
//        }
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    private class ViewHolder{
//        RoundedImageView rivTicket;
//        TextView txtNameTicket, txtDepartureTime, txtPriceTicket;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder;
//        if(view == null){
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = layoutInflater.inflate(layout, null);
//            viewHolder = new ViewHolder();
//
//            viewHolder.rivTicket = (RoundedImageView) view.findViewById(R.id.imageTicket);
//            viewHolder.txtNameTicket = (TextView) view.findViewById(R.id.nameTicket);
//            viewHolder.txtDepartureTime = (TextView) view.findViewById(R.id.departureTime);
//            viewHolder.txtPriceTicket = (TextView) view.findViewById(R.id.priceTicket);
//
//            view.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        Ticket ticket = ticketList.get(i);
//        viewHolder.rivTicket.setBackgroundResource(ticket.getResourceID());
//        viewHolder.txtNameTicket.setText(ticket.getNameTicket());
//        viewHolder.txtDepartureTime.setText(ticket.getDepartureTime());
//        viewHolder.txtPriceTicket.setText(ticket.getPriceTicket());
//
//        return view;
//    }
//}

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ticketViewHolder>{
    private List<Ticket> ticketList;
    private IClickTicketListener iClickTicketListener;

    public TicketAdapter(List<Ticket> ticketList, IClickTicketListener listener) {
        this.ticketList = ticketList;
        this.iClickTicketListener = listener;
    }

    @NonNull
    @Override
    public ticketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_ticket, parent, false);
        return new ticketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ticketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        if (ticket == null) {
            return;
        }

        holder.rivTicket.setImageResource(ticket.getResourceID());
        holder.txtNameTicket.setText(ticket.getNameTicket());
        holder.txtDepartureTime.setText(ticket.getDepartureTime());
        holder.txtPriceTicket.setText(ticket.getPriceTicket());
        holder.layoutChooseTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickTicketListener.onClickTicketListener(ticket);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(ticketList !=null){
            return ticketList.size();
        }
        return 0;
    }

    public class ticketViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView rivTicket;
        private TextView txtNameTicket, txtDepartureTime, txtPriceTicket, txtBusNumber;
        private LinearLayout layoutChooseTicket;

        public ticketViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutChooseTicket = itemView.findViewById(R.id.linearTicket);
            rivTicket = itemView.findViewById(R.id.imageTicket);
            txtNameTicket = itemView.findViewById(R.id.nameTicket);
            txtBusNumber = itemView.findViewById(R.id.busNumber);
            txtDepartureTime = itemView.findViewById(R.id.departureTime);
            txtPriceTicket = itemView.findViewById(R.id.priceTicket);
        }
    }
}