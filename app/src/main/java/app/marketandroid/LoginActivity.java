package app.marketandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NB_MainActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        findViewById(R.id.login_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Manager_MainActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        findViewById(R.id.login_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View v = LayoutInflater.from(LoginActivity.this).inflate(R.layout.sign_up_dialog,null,false);
                builder.setView(v);
                final AlertDialog alertDialog = builder.create();
                final Button ok = v.findViewById(R.id.sign_up_ok);
                final Button cancel = v.findViewById(R.id.sign_up_cancel);
                final EditText ph = v.findViewById(R.id.sign_up_id);
                final EditText name = v.findViewById(R.id.sign_up_name);
                final EditText pwd = v.findViewById(R.id.sign_up_pwd);
                final EditText repwd = v.findViewById(R.id.sign_up_repwd);
                alertDialog.setCanceledOnTouchOutside(true);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ph.getText().toString().equals("")){
                            Toast.makeText(LoginActivity.this, "핸드폰 번호를 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                            keyboardUp(ph);
                        } else if (name.getText().toString().equals("")) {
                            Toast.makeText(LoginActivity.this, "이름을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                            keyboardUp(name);
                        }else if (pwd.getText().toString().equals("")){
                            Toast.makeText(LoginActivity.this, "비밀번호를 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                            keyboardUp(pwd);
                        }else if (repwd.getText().toString().equals("")){
                            Toast.makeText(LoginActivity.this, "비밀번호 확인을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                            keyboardUp(repwd);
                        } else if(!ph.getText().toString().equals("") && !ph.getText().toString().startsWith("010")){
                            Toast.makeText(LoginActivity.this, "핸드폰 번호는 010으로 시작해야 합니다.", Toast.LENGTH_SHORT).show();
                            ph.setText("");
                            keyboardUp(ph);
                        }else if(!ph.getText().toString().equals("") && !(ph.getText().toString().length() == 11)){
                            Toast.makeText(LoginActivity.this, "핸드폰 번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                            keyboardUp(ph);
                        } else if(!pwd.getText().toString().equals(repwd.getText().toString())){
                            Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            keyboardUp(pwd);
                        } else{
                            Toast.makeText(LoginActivity.this, "\t\t\t\t가입이 완료되었습니다.\n해당 정보로 로그인이 가능합니다.", Toast.LENGTH_SHORT).show();
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
}