package com.example.projetv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SwipeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> list;

    public SwipeAdapter(Context context, ArrayList<Integer> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 20;
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
    public View getView(int i, View convertview, ViewGroup parent) {
        View view;
        if(convertview == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_koloda, parent, false);
        }
        else{
            view = convertview;
        }
        return view;
    }
}
