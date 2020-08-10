package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Manager_ListFragment extends Fragment {
    ViewGroup viewGroup;
    Drawable drawable_potato, drawable_apple;
    EditText filtter;
    ListView listView;
    List<String> list = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawable_potato = getResources().getDrawable(R.drawable.recycle_potato);
        drawable_apple = getResources().getDrawable(R.drawable.recycle_apple);
        listView = getActivity().findViewById(R.id.manager_list);
        filtter = getActivity().findViewById(R.id.manager_list_edit);
        list.add("감자\n\n농부1"); list.add("사과\n\n농부2"); list.add("고구마\n\n농부1"); list.add("복숭아\n\n농부3"); list.add("감자\n\n농부3");
        list.add("당근\n\n농부4"); list.add("당근\n\n농부5"); list.add("고구마\n\n농부5"); list.add("딸기\n\n농부4"); list.add("당근\n\n농부1");
        list.add("호박\n\n농부8"); list.add("사과\n\n농부1");
        arrayList.addAll(list);
        adapter = new ArrayAdapter<>(getActivity(),R.layout.list_item, arrayList);
        listView.setAdapter(adapter);
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
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final BaseDialog baseDialog = new BaseDialog(getContext(), R.layout.manager_list_dialog);
                final TextView name = baseDialog.findViewById(R.id.manager_list_dialog_user);
                final TextView kinds = baseDialog.findViewById(R.id.manager_list_dialog_kinds);
                final ImageView back = baseDialog.findViewById(R.id.manager_list_dialog_btn);
                int space = list.get(i).indexOf("\n");
                name.setText(list.get(i).substring(space+2));
                kinds.setText(list.get(i).substring(0,space));
                back.setOnClickListener(new View.OnClickListener() {
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.manager_list_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        arrayList.clear();
    }


    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        arrayList.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            arrayList.addAll(list);
        }
        // 문자 입력을 할때..
        else {
            for (int i = 0; i < list.size(); i++){
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (list.get(i).toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    arrayList.add(list.get(i));
                }
            }

        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
}