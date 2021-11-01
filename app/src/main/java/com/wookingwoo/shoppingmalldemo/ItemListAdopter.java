package com.wookingwoo.shoppingmalldemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemListAdopter extends BaseAdapter {

    String[] arrayDB = new String[500];


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


        arrayDB[i] = itemData.getItemTitle();


        tv.setText(itemData.getItemTitle());
        iv.setImageDrawable(itemData.getD());


        ImageButton btn_delete = (ImageButton) view.findViewById(R.id.btn_delete);


        // 리스트 아이템 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDatas.remove(i);
                notifyDataSetChanged();
                uploadDB(); // Firestore에 갱신
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


    public void uploadDB() {

// FirebaseFirestore 저장 시작
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


        Map<String, Object> cartItems = new HashMap<>();
        cartItems.put("items", Arrays.asList(arrayDB));


        db.collection("ShoppingCart").document(firebaseUser.getUid())
                .set(cartItems)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add-item-firesotre", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Add-item-firesotre", "Error writing document", e);
                    }
                });

    }
}
