package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import app.marketandroid.R;

public class NB_OptionFragment extends Fragment {
    ViewGroup viewGroup;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> list = new ArrayList<>();
    TextView main_title;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("설정");
        listView = getActivity().findViewById(R.id.option_listView);
        list.add(0,"계좌정보 등록");
        for (int i = 1; i <= 10; i++){
            list.add(i+"번 옵션 아이템");
        }
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        View v = LayoutInflater.from(getContext()).inflate(R.layout.account_add_dialog,null,false);
                        builder.setView(v);
                        final AlertDialog alertDialog = builder.create();
                        final Button ok = v.findViewById(R.id.account_ok_btn);
                        final Button cancel = v.findViewById(R.id.account_cancel_btn);
                        final TextView warning_msg = v.findViewById(R.id.account_warning_msg);

                        //경고 메시지 밑줄
                        SpannableString content = new SpannableString(warning_msg.getText().toString());
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        warning_msg.setText(content);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getContext(), "등록 완료", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();
                        break;
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.nb_option_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.clear();
    }
}