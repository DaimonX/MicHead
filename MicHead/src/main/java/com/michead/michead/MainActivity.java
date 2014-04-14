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
import android.widget.Button;

public class MainActivity extends Activity {

    final String TAG = "myLogs";
    boolean isRunning = false;
    int audioformat = MediaRecorder.AudioEncoder.AMR_NB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    void createAudioRecorder() {

        new Thread(new Runnable() {
            public void run() {
        final int rateInHz = 8000;
        int minBufferSize = AudioRecord.getMinBufferSize(rateInHz,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                rateInHz,
                AudioFormat.CHANNEL_IN_MONO,
                audioformat, minBufferSize);
        Log.d(TAG," audioRecord.getState()= "+audioRecord.getState());
        audioRecord.startRecording();
        final byte audiobuffer[] = new byte[minBufferSize];

        new Thread(new Runnable() {
            public void run() {
                int bufferSize = AudioTrack.getMinBufferSize(rateInHz,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);

                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                        rateInHz,
                        AudioFormat.CHANNEL_OUT_MONO,
                        audioformat,
                        bufferSize,
                        AudioTrack.MODE_STREAM
                );

                audioTrack.play();
                while (isRunning) {
                    audioTrack.write(audiobuffer, 0, audiobuffer.length);
                }
                audioTrack.release();
                return;
            }
        }).start();

        while (isRunning) {
            audioRecord.read(audiobuffer, 0, audiobuffer.length);
        }
        audioRecord.release();
        return;
    }}).start();}

    public void sameStart(View view) {
        Button btn = (Button)findViewById(R.id.startstop);
        if (!isRunning){
            createAudioRecorder();
            isRunning = true;

            btn.setText(getResources().getString(R.string.stop_text));
            return;
        }
        isRunning = false;
        btn.setText(getResources().getString(R.string.star_text));


    }

    private int freq =8000;
    private AudioRecord audioRecord = null;
    private AudioTrack audioTrack=null;
    private Thread Rthread = null;

    private AudioManager audioManager=null;
    byte[] buffer = new byte[freq];

    public void minDelay(View v){
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        final int bufferSize = AudioRecord.getMinBufferSize(freq,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);


        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, freq,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                MediaRecorder.AudioEncoder.AMR_NB, bufferSize);

        audioTrack = new AudioTrack(AudioManager.ROUTE_HEADSET, freq,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                MediaRecorder.AudioEncoder.AMR_NB, bufferSize,
                AudioTrack.MODE_STREAM);



        audioTrack.setPlaybackRate(freq);
        final byte[] buffer = new byte[bufferSize];
        audioRecord.startRecording();
        Log.i("minDelay", "Audio Recording started");
        audioTrack.play();
        Log.i("minDelay", "Audio Playing started");
        Rthread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        audioRecord.read(buffer, 0, bufferSize);
                        audioTrack.write(buffer, 0, buffer.length);

                    } catch (Throwable t) {
                        Log.e("Error", "Read write failed");
                        t.printStackTrace();
                    }
                }
            }
        });
        Rthread.start();


    }


}
