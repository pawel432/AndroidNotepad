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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ReadNoteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read_note);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");
        EditText title = findViewById(R.id.title);
        File txtFiles = new File(getFilesDir(), "txtFiles");
        File file = new File(txtFiles, fileName);


        title.setText(file.getName());
        EditText editTextContent = findViewById(R.id.noteContent);
        String content;
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null){
                builder.append(line).append("\n");
                line = reader.readLine();
            }
            content = builder.toString();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editTextContent.setText(content);
        Button deleteButton = findViewById(R.id.deleteNoteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean deleted = file.delete();
                if(deleted){
                    Toast.makeText(ReadNoteActivity.this, "Note has been deleted.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReadNoteActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ReadNoteActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button saveButton = findViewById(R.id.saveNoteButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File editedFile = new File(txtFiles, String.valueOf(title.getText()));
                String fileTitle = String.valueOf(title.getText());
                int fileNameIndex = 1;
                while(editedFile.exists()){
                    editedFile = new File(txtFiles,  fileTitle + '(' + fileNameIndex + ')');
                    fileNameIndex++;
                }
                String content = editTextContent.getText().toString();
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(content.getBytes());
                    fos.close();
                    Toast.makeText(ReadNoteActivity.this, "A note was edited.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReadNoteActivity.this, MainActivity.class);
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