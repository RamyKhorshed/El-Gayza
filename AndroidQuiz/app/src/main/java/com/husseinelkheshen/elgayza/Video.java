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

        str = "https://firebasestorage.googleapis.com/v0/b/el-gayza.appspot.com/o/Sample.mp4?alt=media&token=f4d383a4-174a-45f7-a12b-5b7ad57b4883";

        VideoView video = findViewById(R.id.videoView);
        uri = Uri.parse(str);
        video.setVideoURI(uri);
        video.start();


    }

    @Override
    public void onBackPressed() {
        return;
    }
}
