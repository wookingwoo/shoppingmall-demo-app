package com.wookingwoo.shoppingmalldemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ItemListAdopter extends BaseAdapter {


    ArrayList<listItem> itemDatas = new ArrayList<>();

    @Override
    public int getCount() {
        return itemDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return itemDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        Context c = viewGroup.getContext();
        if (view == null) {
            LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = li.inflate(R.layout.list_item, viewGroup, false);

        }

        TextView tv = view.findViewById(R.id.tv_itemTitle);
        ImageView iv = view.findViewById(R.id.iv_itemImg);

        listItem itemData = itemDatas.get(i);


        tv.setText(itemData.getItemTitle());
        iv.setImageDrawable(itemData.getD());





        ImageButton btn_delete = (ImageButton)view.findViewById(R.id.btn_delete);


        // 리스트 아이템 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDatas.remove(i);
                notifyDataSetChanged();
            }
        });







        return view;
    }

    public void addItem(String itemTitle, Drawable d) {


        listItem li = new listItem();

        li.setItemTitle(itemTitle);
        li.setD(d);

        itemDatas.add(li);
    }
}
