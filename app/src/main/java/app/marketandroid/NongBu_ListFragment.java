package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NongBu_ListFragment extends Fragment {

    ViewGroup viewGroup;
    RecyclerView mRecyclerView = null;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();
    RecyclerImageTextAdapter mAdapter;
    Drawable drawable_potato,drawable_apple;
    TextView main_title;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.recyclerView);
        drawable_potato = getResources().getDrawable(R.drawable.recycle_potato);
        drawable_apple = getResources().getDrawable(R.drawable.recycle_apple);
        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("등록 현황");
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerImageTextAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        addItem(drawable_potato,"감자","30kg, 2개");
        addItem(drawable_apple,"사과", "20kg, 3개");
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnItemClickListener(new RecyclerImageTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(),NongBu_DetailActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.nongbu_list_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mList.clear();
    }

    public void addItem(Drawable icon, String title, String desc) {
        RecyclerItem item = new RecyclerItem();
        item.setIconDrawable(icon);
        item.setNameStr(title);
        item.setDetailStr(desc);
        mList.add(item);
    }
}