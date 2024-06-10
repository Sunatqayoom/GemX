package com.gemx.gemx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass);

        ImageView backBtn = findViewById(R.id.back);
        TextView signinBtn = findViewById(R.id.signin);

        backBtn.setOnClickListener(v->finish());
        signinBtn.setOnClickListener(v-> {
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finishAffinity();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
