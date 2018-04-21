package com.husseinelkheshen.elgayza;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import com.google.firebase.storage.FirebaseStorage;

public class Video extends AppCompatActivity {

    String str;
    Uri uri;

    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        storage = FirebaseStorage.getInstance();

        str = "Firebase";

        VideoView videoview = findViewById(R.id.videoView);
        uri = Uri.parse(str);
        videoview.setVideoURI(uri);
        videoview.start();

        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
