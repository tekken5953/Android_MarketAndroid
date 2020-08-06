package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class NB_AddFragment extends Fragment {

    ViewGroup viewGroup;
    Button btn_potato, btn_apple, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    TextView main_title;
    Drawable alpha_potato, alpha_apple;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("출하물품 등록");
        btn_apple = getActivity().findViewById(R.id.button2);
        btn_potato = getActivity().findViewById(R.id.button);
        btn3 = getActivity().findViewById(R.id.button3);
        btn4 = getActivity().findViewById(R.id.button4);
        btn5 = getActivity().findViewById(R.id.button5);
        btn6 = getActivity().findViewById(R.id.button6);
        btn7 = getActivity().findViewById(R.id.button7);
        btn8 = getActivity().findViewById(R.id.button8);
        btn9 = getActivity().findViewById(R.id.button9);
        btn10 = getActivity().findViewById(R.id.button10);
        btn11 = getActivity().findViewById(R.id.button11);
        btn12 = getActivity().findViewById(R.id.button12);

        buttonClick(btn_potato);
        buttonClick(btn_apple);
        buttonClick(btn3);
        buttonClick(btn4);
        buttonClick(btn5);
        buttonClick(btn6);
        buttonClick(btn7);
        buttonClick(btn8);
        buttonClick(btn9);
        buttonClick(btn10);
        buttonClick(btn11);
        buttonClick(btn12);

        alpha_potato = btn_potato.getBackground();
        alpha_potato.setAlpha(100);
        alpha_apple = btn_apple.getBackground();
        alpha_apple.setAlpha(100);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.nb_add_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        alpha_potato.setAlpha(255);
        alpha_apple.setAlpha(255);
    }

    public void alertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.nb_add_dialog, null, false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        final Button add_btn = view.findViewById(R.id.add_dial_add_btn);
        final Button cancel_btn = view.findViewById(R.id.add_dial_cancel_btn);
        alertDialog.setCanceledOnTouchOutside(false);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "등록 완료", Toast.LENGTH_SHORT).show();
                //TODO
                alertDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void buttonClick(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }
}