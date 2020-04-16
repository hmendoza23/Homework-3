package com.example.maptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/** This class is needed for the implementation of the list of cars in the home fragment
 **/
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> addressList = new ArrayList<>();
    private Context context;
    private MapsActivity.RecyclerViewClickListener clickListener;
    /* Provide a reference to the views for each data item
       Complex data items may need more than one view per item, and
       you provide access to all the views for a data item in a view holder  */
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView address;
        private TextView index;
        private int position;
        private MapsActivity.RecyclerViewClickListener mListener;


        public MyViewHolder(View v, MapsActivity.RecyclerViewClickListener listener) {
            super(v);

            address = v.findViewById(R.id.RV_address);
            index = v.findViewById(R.id.index);
            mListener = listener;
            address.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            mListener.onClick(view, getAdapterPosition());
        }

        public void setAddressName(String address) {
            this.address.setText(address);
        }
        public void setPosition(int position){this.position = position;}
        public void setIndex(int position){
            int temp = position + 1;
            String temp2 = String.valueOf(temp);
            this.index.setText(temp2);
        }

    }

    /* Provide a suitable constructor (depends on the kind of dataset)*/
    public MyAdapter(Context context, ArrayList<String> addressList, MapsActivity.RecyclerViewClickListener listener) {
        this.context = context;
        this.addressList = addressList;
        clickListener = listener;
    }

    /* Create new views (invoked by the layout manager)*/
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        /* create a new view*/
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext()) ;
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(view, clickListener);
        return vh;
    }

    /* Replace the contents of a view (invoked by the layout manager) */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String address = addressList.get(position);

        holder.setAddressName(address);
        holder.setIndex(position);
        holder.setPosition(position);

    }

    /* Return the size of your dataset (invoked by the layout manager) */
    @Override
    public int getItemCount() {
        return addressList.size();
    }
}

