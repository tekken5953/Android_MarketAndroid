package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.marketandroid.R;

public class Manager_SettingFragment extends Fragment {
    ViewGroup viewGroup;
    ArrayList<myGroup> list = new ArrayList<>();
    ExpandableListView listView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 1; i <= 12; i++) {
            myGroup temp = new myGroup("품목" + i);
            temp.child.add("시세");
            temp.child.add("등록 제한");
            list.add(temp);
        }
        ExpandAdapter adapter = new ExpandAdapter(getContext().getApplicationContext(), R.layout.group_row, R.layout.child_row, list);
        listView = getActivity().findViewById(R.id.manager_setting_list);
        listView.setAdapter(adapter);
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