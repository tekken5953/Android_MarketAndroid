package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class Manager_ListFragment extends Fragment {
    ViewGroup viewGroup;
    RecyclerView mRecyclerView = null;
    ArrayList<RecyclerItem> mList = new ArrayList<>();
    RecyclerViewAdapter mAdapter;
    Drawable drawable_potato, drawable_apple;
    ArrayList<RecyclerItem> mData = null;
    RecyclerItem item;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.manager_list);
        drawable_potato = getResources().getDrawable(R.drawable.recycle_potato);
        drawable_apple = getResources().getDrawable(R.drawable.recycle_apple);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerViewAdapter(mList);
        mData = mList;
        mRecyclerView.setAdapter(mAdapter);
        addItem(drawable_potato, "감자", "농부1");
        addItem(drawable_apple, "사과", "농부2");
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                item = mData.get(position);
                builder.create();
                builder.setTitle("등록물품 상세정보");
                //TODO
                builder.setMessage("\n등록 유저  :  " + item.getDetailStr() + "\n\n등록시간  :  시간\n\n품 종  :  " + item.getNameStr() + "\n\n신청 중량  :   중량\n\n총 가격  :  가격");
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.manager_list_fragment, container, false);
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