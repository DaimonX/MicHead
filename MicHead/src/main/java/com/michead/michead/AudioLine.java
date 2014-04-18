package com.michead.michead;

import android.annotation.TargetApi;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;


/**
 * Created by Администратор on 18.04.2014.
 */
public class AudioLine extends AsyncTask<Void, Void, Void> {

    boolean isRunning = true;
    final String TAG = "myLogs";
    final int rateInHz = 8000;
    int audioformat = MediaRecorder.AudioEncoder.AMR_NB;
    AsyncTask<Void, Void, Void> aTrack;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected Void doInBackground(Void... voids) {

        int minBufferSize = AudioRecord.getMinBufferSize(rateInHz,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                rateInHz,
                AudioFormat.CHANNEL_IN_MONO,
                audioformat, minBufferSize);
        Log.d(TAG, " audioRecord.getState()= " + audioRecord.getState());
        audioRecord.startRecording();
        final byte audiobuffer[] = new byte[minBufferSize];

        aTrack = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                int bufferSize = AudioTrack.getMinBufferSize(rateInHz,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);
                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, rateInHz, AudioFormat.CHANNEL_OUT_MONO, audioformat, bufferSize, AudioTrack.MODE_STREAM);
                Log.d(TAG, " audioTrack.getState()= " + audioTrack.getState());
                audioTrack.play();

                while (!isCancelled()) {
                    audioTrack.write(audiobuffer, 0, audiobuffer.length);
                    //Log.i("write", Arrays.toString(audiobuffer));
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
               
            }
        };
        aTrack.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        while (!isCancelled()) {
            audioRecord.read(audiobuffer, 0, audiobuffer.length);
        }

        audioRecord.release();
        return null;
    }

}