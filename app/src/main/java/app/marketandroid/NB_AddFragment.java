package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import app.marketandroid.Retrofit.PostItem;
import app.marketandroid.Retrofit.RetrofitData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NB_AddFragment extends Fragment {

    ViewGroup viewGroup;
    Button btn_potato, btn_apple, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    TextView main_title;
    Drawable alpha_potato, alpha_apple;
    RetrofitData retrofitData;
    Call<List<PostItem>> put_call;
    Call<PostItem> post_call;

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

        //Retrofit
//        retrofitData.initMyAPI();
//
//        retrofitData.getCall(put_call);
//        put_call.enqueue(new Callback<List<PostItem>>() {
//            @Override
//            public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
//                if( response.isSuccessful()){
//                    List<PostItem> mList = response.body();
//                    StringBuilder result = new StringBuilder();
//                    assert mList != null;
//                    for( PostItem item : mList){
//                        result.append("전화번호 : ").append(item.getTitle()).append(" 비밀번호: ").append(item.getText()).append("\n");
//                    }
//                    텍스트뷰.setText(result.toString());
//                }else {
//                    Log.d("retrofit","Status Code : " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<PostItem>> call, Throwable t) {
//                Log.d("retrofit","Fail msg : " + t.getMessage());
//            }
//        });
//
//        retrofitData.postCall(post_call);
//        post_call.enqueue(new Callback<PostItem>() {
//            @Override
//            public void onResponse(Call<PostItem> call, Response<PostItem> response) {
//                if(response.isSuccessful()){
//                    Log.d("retrofit","등록 완료");
//                }else {
//                    Log.d("retrofit","Status Code : " + response.code());
//                    Log.d("retrofit",response.errorBody().toString());
//                    Log.d("retrofit",call.request().body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostItem> call, Throwable t) {
//                Log.d("retrofit","Fail msg : " + t.getMessage());
//            }
//        });

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
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
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