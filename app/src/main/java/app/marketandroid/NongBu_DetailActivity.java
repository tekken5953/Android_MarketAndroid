package app.marketandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NongBu_DetailActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NongBu_DetailActivity.this,NongBu_MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nongbu_deatil_activity);

        findViewById(R.id.detail_back_press).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NongBu_DetailActivity.this,NongBu_MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}