package com.michead.michead;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    AudioLine al;
    boolean isRunning2 = false;
    LinearLayout v;
    GraphicsView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GraphicsView gv = new GraphicsView(this);
        setContentView(R.layout.activity_main);
        al = new AudioLine();
        v = (LinearLayout) findViewById(R.id.linearLayout);
        myView = new GraphicsView(this);
        myView.setId(3);
        v.addView(myView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    public void sameStart(View view) {
        Button btn = (Button) findViewById(R.id.startstop);

        switch (al.getStatus()) {
            case FINISHED:
                al = new AudioLine();
                al.execute();
                btn.setText(getResources().getString(R.string.stop_text));
                Log.i("", "FINISHED");
                break;
            case PENDING:
                al.execute();
                btn.setText(getResources().getString(R.string.stop_text));
                Log.i("", "PENDING");
                break;
            case RUNNING:
                al.cancel(true);
                al.aTrack.cancel(true);
                btn.setText(getResources().getString(R.string.start_text));
                Log.i("", "RUNNING");

                break;
        }
    }
}
