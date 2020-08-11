package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.marketandroid.R;

public class Manager_ListFragment extends Fragment {
    ViewGroup viewGroup;
    Drawable drawable_potato, drawable_apple;
    ArrayList<MGRecyclerItem> mData = null;
    RecyclerView mRecyclerView = null;
    ArrayList<MGRecyclerItem> mList = new ArrayList<>();
    MGRecyclerViewAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawable_potato = getResources().getDrawable(R.drawable.recycle_potato);
        drawable_apple = getResources().getDrawable(R.drawable.recycle_apple);

        mRecyclerView = getActivity().findViewById(R.id.mgrecyclerView);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new MGRecyclerViewAdapter(mList);
        mData = mList;
        mRecyclerView.setAdapter(mAdapter);
        addItem("농부1","감자","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부2","고구마","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부3","당근","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부4","사과","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부5","감자","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부6","복숭아","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부7","가지","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.manager_list_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void addItem(String user_name, String products, String weight, String count, String total_price, String personal_price) {
        MGRecyclerItem item = new MGRecyclerItem(user_name,products,weight,count,total_price,personal_price);
        item.setUser_name(user_name);
        item.setProductsStr(products);
        item.setWeightStr(weight);
        item.setCountStr(count);
        item.setTotal_priceStr(total_price);
        item.setPersonal_priceStr(personal_price);
        mList.add(item);
    }

}