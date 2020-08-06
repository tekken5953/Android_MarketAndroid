package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NB_ListFragment extends Fragment {

    ViewGroup viewGroup;
    RecyclerView mRecyclerView = null;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();
    RecyclerViewAdapter mAdapter;
    Drawable drawable_potato, drawable_apple;
    TextView main_title;
    ArrayList<RecyclerItem> mData = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.recyclerView);
        drawable_potato = getResources().getDrawable(R.drawable.recycle_potato);
        drawable_apple = getResources().getDrawable(R.drawable.recycle_apple);
        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("금일 등록 현황");
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerViewAdapter(mList);
        mData = mList;
        mRecyclerView.setAdapter(mAdapter);
        addItem(drawable_potato, "감자", "20Kg");
        addItem(drawable_apple, "사과", "10Kg");
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                RecyclerItem item = mData.get(position);
                builder.create();
                builder.setTitle("등록물품 상세정보");
                //TODO
                builder.setMessage("\n등록 시간  :  시간\n\n품 종  :  " + item.getNameStr() + "\n\n중 량  :  " + item.getDetailStr() + "\n\n수 량  :  수량\n\n총 가격  :  가격");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.nb_list_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mList.clear();
    }

    public void addItem(Drawable icon, String title, String detail) {
        RecyclerItem item = new RecyclerItem();
        item.setIconDrawable(icon);
        item.setNameStr(title);
        item.setDetailStr(detail);
        mList.add(item);
    }
}