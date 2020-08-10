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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    EditText filtter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.manager_list);
        drawable_potato = getResources().getDrawable(R.drawable.recycle_potato);
        drawable_apple = getResources().getDrawable(R.drawable.recycle_apple);
        filtter = getActivity().findViewById(R.id.manager_list_edit);
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
                item = mData.get(position);
                final BaseDialog baseDialog = new BaseDialog(getContext(), R.layout.manager_list_dialog);
                final TextView kinds = baseDialog.findViewById(R.id.manager_list_dialog_kinds);
                final TextView user = baseDialog.findViewById(R.id.manager_list_dialog_user);
                final ImageView btn = baseDialog.findViewById(R.id.manager_list_dialog_btn);
                kinds.setText(item.getNameStr());
                user.setText(item.getDetailStr());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baseDialog.dismiss();
                    }
                });
                baseDialog.show();
            }
        });

        filtter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = filtter.getText().toString();
                search(text);
            }
        });
        mAdapter.notifyDataSetChanged();
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

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        mList.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            addItem(drawable_potato, "감자", "농부1");
            addItem(drawable_apple, "사과", "농부2");
        }
        // 문자 입력을 할때..
        else {
            // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
            if (charText.toLowerCase().contains("감자")) {
                // 검색된 데이터를 리스트에 추가한다.
                addItem(drawable_potato, "감자", "농부1");
            } else if (charText.toLowerCase().contains("사과")) {
                addItem(drawable_apple, "사과", "농부2");
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        mAdapter.notifyDataSetChanged();
    }
}