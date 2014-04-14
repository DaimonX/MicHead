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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAudioRecorder();

    }

    void createAudioRecorder() {

        final int rateInHz = 24000;
        int minBufferSize = AudioRecord.getMinBufferSize(rateInHz,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                rateInHz,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
        audioRecord.startRecording();
        final byte audiobuffer[] = new byte[1200];

        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            public void run() {
                int bufferSize = AudioTrack.getMinBufferSize(rateInHz,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);

                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                        rateInHz,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferSize,
                        AudioTrack.MODE_STREAM
                );
                audioTrack.play();
                while (true) {
                    audioTrack.write(audiobuffer, 0, audiobuffer.length);

                }
            }
        }).start();


        while (true) {
            audioRecord.read(audiobuffer, 0, audiobuffer.length);
        }
    }

    public void sameStart(View view) {
    }


}
