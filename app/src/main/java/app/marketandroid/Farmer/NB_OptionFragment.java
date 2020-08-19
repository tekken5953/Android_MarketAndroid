package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.marketandroid.R;
import app.marketandroid.Retrofit.AccountItem;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.SharedPreferenceManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NB_OptionFragment extends Fragment {
    ViewGroup viewGroup;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> list = new ArrayList<>();
    TextView main_title,option_username,option_account;
    MyAPI mMyAPI;
    RelativeLayout relativeLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("설정");
        option_username = getActivity().findViewById(R.id.option_username);
        relativeLayout = getActivity().findViewById(R.id.option_relative);
        option_account = getActivity().findViewById(R.id.option_account);
        listView = getActivity().findViewById(R.id.option_listView);
        list.add(0,"등록계좌 정보 수정");
        for (int i = 1; i <= 10; i++){
            list.add(i+"번 옵션 아이템");
        }

        initMyAPI();

        final Call<List<AccountItem>> get_account = mMyAPI.get_account(SharedPreferenceManager.getString(getContext(),"token"));
        get_account.enqueue(new Callback<List<AccountItem>>() {
            @Override
            public void onResponse(Call<List<AccountItem>> call, Response<List<AccountItem>> response) {
                if (response.isSuccessful()){
                    option_username.setText(SharedPreferenceManager.getString(getContext(),"user_name")+"님");
                    List<AccountItem> mList = response.body();
                    assert mList != null;
                    for (AccountItem item : mList){
                        if (item.getUser() == SharedPreferenceManager.getInt(getContext(),"user_id")){
                            option_account.setText(item.getBank() + "  " + item.getAccount() + "  " + item.getAccount_name());
                        }
                    }
                }else{
                    TextView error = new TextView(getContext());
                    error.setText("세션이 만료되었습니다.\n다시 접속해주세요.");
                    error.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    error.setTextColor(Color.parseColor("#000000"));
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.addView(error);
                    relativeLayout.removeAllViews();
                    relativeLayout.addView(linearLayout);
                }
                }


            @Override
            public void onFailure(Call<List<AccountItem>> call, Throwable t) {

            }
        });

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
                        final EditText account = v.findViewById(R.id.account_account_edit);
                        final EditText bank = v.findViewById(R.id.account_bank_edit);
                        final EditText name = v.findViewById(R.id.account_name_edit);

                        //경고 메시지 밑줄
                        SpannableString content = new SpannableString(warning_msg.getText().toString());
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        warning_msg.setText(content);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final AccountItem item = new AccountItem();
                                Call<AccountItem> put_account = mMyAPI.put_account(SharedPreferenceManager.getInt(getContext(),"user_id"),
                                SharedPreferenceManager.getString(getContext(),"token"),item);
                                item.setUser(SharedPreferenceManager.getInt(getContext(),"user_id"));
                                item.setBank(bank.getText().toString());
                                item.setAccount(account.getText().toString());
                                item.setAccount_name(name.getText().toString());
                                put_account.enqueue(new Callback<AccountItem>() {
                                    @Override
                                    public void onResponse(Call<AccountItem> call, Response<AccountItem> response) {
                                        Toast.makeText(getContext(), "등록 계좌를 수정하였습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<AccountItem> call, Throwable t) {

                                    }
                                });

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

    private void initMyAPI() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.84.206/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }
}