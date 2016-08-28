package com.justiceleague.locus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.justiceleague.locus.R;
import com.justiceleague.locus.adapter.ContactsAdapter;
import com.justiceleague.locus.model.contact;

import java.util.List;

/**
 * Created by vishal.kumar1 on 28/08/16.
 */
public class FragmentAllUser extends BaseFragment {

    List<contact> contacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_all_users,container,false);
        showALlContacts(view);
        return view;
    }

    public void showALlContacts(View view){
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contacts.add(new contact("ada","999"));
        contacts.add(new contact("ada","888"));
        recyclerView.setAdapter(new ContactsAdapter(contacts, R.layout.list_item_contacts, getActivity()));
    }
}
