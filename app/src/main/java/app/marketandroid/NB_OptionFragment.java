package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        for (int i = 1; i <= 10; i++){
            list.add(i+"번 옵션 아이템");
        }
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

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