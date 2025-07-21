package com.example.android_notepad;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        createDirectoryForTxt();
        showTxtFiles();

        Button addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, noteActivity.class);
                startActivity(intent);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void createDirectoryForTxt(){
        File directory = new File(getFilesDir(), "txtFiles");
        if(!directory.exists()){
            boolean success = directory.mkdirs();
            if (!success) {
                Log.e("Directory", "failed to create directory");
            }
        }
    }
    private void showTxtFiles(){
        File directory = new File(getFilesDir(), "txtFiles");
        File[] files = directory.listFiles();
            LinearLayout linearLayout = findViewById(R.id.midLayout);
            for(int i=0; i<files.length; i++)
            {
                File oneFile = files[i];
                Button button = new Button(this);
                int height = (int) (40 * getResources().getDisplayMetrics().density);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        height
                );
                params.setMargins(0,20,0,0);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setColor(Color.GRAY);
                drawable.setStroke(1, Color.BLACK);
                drawable.setCornerRadius(16f);
                button.setBackground(drawable);
                button.setLayoutParams(params);
                button.setText(oneFile.getName());
                button.setId(View.generateViewId());
                button.setTextColor(Color.WHITE);
                button.setTextSize(10);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ReadNoteActivity.class);
                        intent.putExtra("fileName", oneFile.getName());
                        startActivity(intent);
                    }
                });

                linearLayout.addView(button);

                // TODO: 7/13/25 zrób odczytywanie plików i zrób ładny wygląd ikonek plików 
            }
        }

    }

