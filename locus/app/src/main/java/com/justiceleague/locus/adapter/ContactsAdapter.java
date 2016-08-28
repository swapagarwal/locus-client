package com.justiceleague.locus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justiceleague.locus.R;
import com.justiceleague.locus.model.contact;

import java.util.List;

/**
 * Created by vishal.kumar1 on 28/08/16.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MovieViewHolder> {

    private List<contact> Contacts;
    private int rowLayout;
    private Context context;

    public static  class MovieViewHolder extends RecyclerView.ViewHolder{
        LinearLayout contactLayout;
        TextView contactName;
        TextView contactNumber;

        public MovieViewHolder(View v){
            super(v);
            contactLayout = (LinearLayout) v.findViewById(R.id.contact_layout);
            contactName = (TextView) v.findViewById(R.id.title);
            contactNumber = (TextView) v.findViewById(R.id.subtitle);
        }
    }

    public ContactsAdapter(List<contact> Contacts, int rowLayout, Context context) {
        this.Contacts = Contacts;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ContactsAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.MovieViewHolder holder, int position) {
        holder.contactName.setText(Contacts.get(position).getName());
        holder.contactNumber.setText(Contacts.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return Contacts.size();
    }
}
