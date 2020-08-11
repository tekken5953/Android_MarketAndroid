package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.marketandroid.R;

public class Manager_SettingFragment extends Fragment {
    ViewGroup viewGroup;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Button edit_btn;
    EditText editText;
    RelativeLayout relativeLayout;
    RadioGroup rg1, rg2;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edit_btn = getActivity().findViewById(R.id.rd_btn);
        editText = getActivity().findViewById(R.id.rd_edit);
        relativeLayout = getActivity().findViewById(R.id.rd_relative);
        rg1 = getActivity().findViewById(R.id.rg_1);
        rg2 = getActivity().findViewById(R.id.rg_2);

        for (int i = 1; i <= 12; i++) {
            list.add("품목" + i);
        }
        listView = getActivity().findViewById(R.id.manager_setting_list);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "변경 완료", Toast.LENGTH_SHORT).show();
                rg1.clearCheck();
                rg2.clearCheck();
                editText.setText("");
                editText.clearFocus();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list.clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.manager_setting_fragment, container, false);
        return viewGroup;
    }
}