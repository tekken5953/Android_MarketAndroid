package app.marketandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.marketandroid.Farmer.NB_MainActivity;
import app.marketandroid.Manager.Manager_MainActivity;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.LoginItem;
import app.marketandroid.Retrofit.SignUpItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    MyAPI mMyAPI;
    EditText edit_id, edit_pwd;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edit_id = findViewById(R.id.login_edit_id);
        edit_pwd = findViewById(R.id.login_edit_pwd);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_id.getText().toString().equals("")) {
                    toastMsg("핸드폰 번호를 입력 해 주세요.");
                    keyboardUp(edit_id);
                } else if (edit_pwd.getText().toString().equals("")) {
                    toastMsg("비밀번호를 입력 해 주세요.");
                    keyboardUp(edit_pwd);
                } else {
                    initMyAPI();
                    final LoginItem item = new LoginItem();
                    item.setPhone(edit_id.getText().toString());
                    item.setPassword(edit_pwd.getText().toString());
                    Call<LoginItem> post_call = mMyAPI.post_users(item);
                    post_call.enqueue(new Callback<LoginItem>() { //아이디, 비밀번호 입력 후 서버에 사용자인지 아닌지 확인
                        @Override
                        public void onResponse(Call<LoginItem> call, Response<LoginItem> response) {
                            if (response.isSuccessful()) {
                                Log.d("retrofit", "가입된 사용자");
                                if (response.code() == 200) {
                                    Call<LoginItem> get_call = mMyAPI.post_users(item);
                                    item.setUser_id(edit_id.getText().toString());
                                    item.setPassword(edit_pwd.getText().toString());
                                    get_call.enqueue(new Callback<LoginItem>() { //사용자인 경우 토큰 값 받아오기
                                        @Override
                                        public void onResponse(Call<LoginItem> call, Response<LoginItem> response) {
                                            StringBuilder result = new StringBuilder();
                                            assert response.body() != null;
                                            result.append("token : ").append(response.body().getToken());
                                            SharedPreferenceManager.setString(LoginActivity.this,
                                                    "token", "JWT " + response.body().getToken());
                                            Log.d("retrofit", result.toString());
                                            Call<LoginItem> post_token = mMyAPI.get_my_info("JWT " + response.body().getToken());
                                            post_token.enqueue(new Callback<LoginItem>() { //토큰 값 입력 후 사용자 정보 받아오기
                                                @Override
                                                public void onResponse(Call<LoginItem> call, Response<LoginItem> response) {
                                                    Log.d("retrofit", response.toString());
                                                    assert response.body() != null;
                                                    Log.d("retrofit", response.body().getPhone());
                                                    Log.d("retrofit", response.body().getName());
                                                    Log.d("retrofit", response.body().getIs_active());
                                                    Log.d("retrofit", response.body().getIs_admin());
                                                    if (response.body().getIs_admin().equals("true")) {
                                                        toastMsg(response.body().getName() + "님\n관리자로 로그인 하셨습니다.");
                                                        Intent intent = new Intent(LoginActivity.this, Manager_MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else if (response.body().getIs_admin().equals("false")) {
                                                        toastMsg(response.body().getName() + "님\n유저로 로그인 하셨습니다.");
                                                        Intent intent = new Intent(LoginActivity.this, NB_MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        toastMsg("아이디 혹은 비밀번호가 올바르지 않습니다.");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<LoginItem> call, Throwable t) {
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<LoginItem> call, Throwable t) {
                                        }
                                    });
                                } else {
                                    toastMsg("아이디 혹은 비밀번호가 올바르지 않습니다.");
                                    Log.d("retrofit", "Status Code : " + response.code());
                                }
                            } else {
                                toastMsg("아이디 혹은 비밀번호가 올바르지 않습니다.");
                                Log.d("retrofit", "Status Code : " + response.code());
                                Log.d("retrofit", response.errorBody().toString());
                                Log.d("retrofit", call.request().body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginItem> call, Throwable t) {
                            Log.d("retrofit", "Fail msg : " + t.getMessage());
                        }
                    });
                }

            }
        });

        findViewById(R.id.login_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View v = LayoutInflater.from(LoginActivity.this).inflate(R.layout.sign_up_dialog, null, false);
                builder.setView(v);
                final AlertDialog alertDialog = builder.create();
                final Button ok = v.findViewById(R.id.sign_up_ok);
                final Button cancel = v.findViewById(R.id.sign_up_cancel);
                final EditText hp = v.findViewById(R.id.sign_up_id);
                final EditText name = v.findViewById(R.id.sign_up_name);
                final EditText pwd = v.findViewById(R.id.sign_up_pwd);
                final EditText repwd = v.findViewById(R.id.sign_up_repwd);
                alertDialog.setCanceledOnTouchOutside(true);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hp.getText().toString().equals("")) {
                            toastMsg("핸드폰 번호를 입력 해 주세요.");
                            keyboardUp(hp);
                        } else if (name.getText().toString().equals("")) {
                            toastMsg("이름을 입력 해 주세요.");
                            keyboardUp(name);
                        } else if (pwd.getText().toString().equals("")) {
                            toastMsg("비밀번호를 입력 해 주세요.");
                            keyboardUp(pwd);
                        } else if (repwd.getText().toString().equals("")) {
                            toastMsg("비밀번호 확인을 입력 해 주세요.");
                            keyboardUp(repwd);
                        } else if (!hp.getText().toString().equals("") && !hp.getText().toString().startsWith("010")) {
                            toastMsg("핸드폰 번호는 010으로 시작해야 합니다.");
                            hp.setText("");
                            keyboardUp(hp);
                        } else if (!hp.getText().toString().equals("") && !(hp.getText().toString().length() == 11)) {
                            toastMsg("핸드폰 번호를 다시 확인해주세요.");
                            keyboardUp(hp);
                        } else if (!pwd.getText().toString().equals(repwd.getText().toString())) {
                            toastMsg("비밀번호가 일치하지 않습니다.");
                            keyboardUp(pwd);
                        } else {
                            toastMsg("가입이 신청되었습니다.\n관리자 승인 후 로그인이 가능합니다.");
                            initMyAPI();

                            final SignUpItem item = new SignUpItem();
                            item.setPhone(hp.getText().toString());
                            item.setName(name.getText().toString());
                            item.setPassword1(pwd.getText().toString());
                            item.setPassword2(repwd.getText().toString());
                            Call<SignUpItem> post_call = mMyAPI.post_signup_info(item);
                            post_call.enqueue(new Callback<SignUpItem>() {
                                @Override
                                public void onResponse(Call<SignUpItem> call, Response<SignUpItem> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("retrofit", response.body().toString());
                                        Log.d("retrofit", "Status Code : " + response.code());
                                    } else {
                                        Log.d("retrofit", "Status Code : " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<SignUpItem> call, Throwable t) {
                                    Log.d("retrofit", "Fail msg : " + t.getMessage());
                                }
                            });
                            alertDialog.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void keyboardUp(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //focus 후 키보드 올리기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void initMyAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.84.206/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }

    public void toastMsg(String s) {
        final LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        final TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(LoginActivity.this);
        text.setTextSize(13);
        text.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 200);
        toast.setView(layout);
        text.setText(s);
        toast.show();
    }
}