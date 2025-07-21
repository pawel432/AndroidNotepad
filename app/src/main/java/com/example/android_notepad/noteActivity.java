package com.example.android_notepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class noteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);

        Button saveNoteBUtton = findViewById(R.id.saveNoteButton);
        saveNoteBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = findViewById(R.id.title);
                String title = titleEditText.getText().toString();
                EditText noteContentEditText = findViewById(R.id.noteContent);
                String content = noteContentEditText.getText().toString();
                File txtFiles = new File(getFilesDir(), "txtFiles");
                File file = new File(txtFiles, title);
                int fileNameIndex = 1;
                while(file.exists()){
                    file = new File(txtFiles, title + '(' + fileNameIndex + ')');
                    fileNameIndex++;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(content.getBytes());
                    fos.close();
                    Toast.makeText(noteActivity.this, "A note was saved.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(noteActivity.this, MainActivity.class);
                    startActivity(intent);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}