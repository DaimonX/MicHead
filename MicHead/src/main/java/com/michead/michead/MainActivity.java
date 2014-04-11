package com.michead.michead;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    final String TAG = "myLogs";

    int mBufferSize, mBufferSize2;
    byte[] mBuffer;
    AudioRecord mRecorder;
    AudioTrack mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAudioRecorder();

    }

    void createAudioRecorder() {

        int i=AudioRecord.getMinBufferSize(16000,
                AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord a= new AudioRecord(MediaRecorder.AudioSource.MIC,16000,
                AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT,i);
        a.startRecording();
        final byte audiobuffer[]=new byte[1000];


        new Thread(new Runnable() {
            public void run() {
                AudioTrack aud= new AudioTrack(AudioManager.STREAM_MUSIC,16000,
                        AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT,AudioTrack.getMinBufferSize(16000,AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT),AudioTrack.MODE_STREAM);

                aud.play();
                while(true)
                {
                    aud.write(audiobuffer,0,audiobuffer.length);

                }
            }}).start();


        while(true)
        {
            a.read(audiobuffer,0,audiobuffer.length);
        }
    }

    public void sameStart(View view) {
        }


}
