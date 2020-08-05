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

import java.util.ArrayList;

public class NongBu_ListFragment extends Fragment {

    ViewGroup viewGroup;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.nongbu_list_fragment, container, false);
        listView = getActivity().findViewById(R.id.list_listView);
        for (int i = 1; i <= 20; i++){
            list.add(i+"번 등록 물품");
        }
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        return viewGroup;
    }
}