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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class NB_ListFragment extends Fragment {

    ViewGroup viewGroup;
    RecyclerView mRecyclerView = null;
    ArrayList<RecyclerItem> mList = new ArrayList<>();
    RecyclerViewAdapter mAdapter;
    Drawable drawable_potato, drawable_apple;
    TextView main_title;
    ArrayList<RecyclerItem> mData = null;
    RecyclerItem item;

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
                item = mData.get(position);
                final BaseDialog baseDialog = new BaseDialog(getContext(), R.layout.nb_list_dialog);
                final TextView kinds = baseDialog.findViewById(R.id.nb_list_dialog_kinds);
                final TextView weight = baseDialog.findViewById(R.id.nb_list_dialog_weight);
                final ImageView btn = baseDialog.findViewById(R.id.nb_list_dialog_btn);
                kinds.setText(item.getNameStr());
                weight.setText(item.getDetailStr());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baseDialog.dismiss();
                    }
                });
                baseDialog.show();
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