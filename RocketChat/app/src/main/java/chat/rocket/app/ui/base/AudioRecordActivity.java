package chat.rocket.app.ui.base;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import chat.rocket.app.R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class AudioRecordActivity extends AppCompatActivity {
    //TODO: Support for Android 6 permissions!!
    private static final String LOG_TAG = "AudioRecordActivity";
    private static final int MAX_DURATION = 10000;
    public static final String FILE_PATH = "file_path";
    private final String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private SeekBar mSeekBar;
    private Subscription mTimerSubscription;
    private Date mStartTime;
    private TextView mProgressTextView;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mPlayButtonListener.onClick(null);
        }
    };
    private View mSendButton;

    private void onRecord(boolean start) {
        mPlayButton.setEnabled(!start);
        mSendButton.setEnabled(!start);
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        mRecordButton.setEnabled(!start);
        mSendButton.setEnabled(!start);
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setOnCompletionListener(mCompletionListener);
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            startPlayProgress();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        unsubscribeTimer();
        setTint(mSeekBar, getResources().getColor(R.color.accentColor));
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setMaxDuration(MAX_DURATION);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        startRecordProgress();
    }

    private void startPlayProgress() {
        mStartTime = new Date();
        setTint(mSeekBar, getResources().getColor(R.color.primaryColor));
        mTimerSubscription = Observable.interval(50, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    int time = (int) (System.currentTimeMillis() - mStartTime.getTime());
                    // evey 100 milliseconds this method is called
                    mSeekBar.setProgress(time);
                    mProgressTextView.setText((time / 1000) + "/10s");
                    if (time > MAX_DURATION) {
                        unsubscribeTimer();
                    }
                });
    }

    public static void setTint(SeekBar seekBar, int color) {
        ColorStateList s1 = ColorStateList.valueOf(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setThumbTintList(s1);
            seekBar.setProgressTintList(s1);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            Drawable progressDrawable = DrawableCompat.wrap(seekBar.getProgressDrawable());
            seekBar.setProgressDrawable(progressDrawable);
            DrawableCompat.setTintList(progressDrawable, s1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Drawable thumbDrawable = DrawableCompat.wrap(seekBar.getThumb());
                DrawableCompat.setTintList(thumbDrawable, s1);
                seekBar.setThumb(thumbDrawable);
            }
        } else {
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (seekBar.getIndeterminateDrawable() != null)
                seekBar.getIndeterminateDrawable().setColorFilter(color, mode);
            if (seekBar.getProgressDrawable() != null)
                seekBar.getProgressDrawable().setColorFilter(color, mode);
        }
    }

    private void startRecordProgress() {
        mStartTime = new Date();
        setTint(mSeekBar, getResources().getColor(R.color.accentColor));
        mTimerSubscription = Observable.interval(50, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    int time = (int) (System.currentTimeMillis() - mStartTime.getTime());
                    // evey 100 milliseconds this method is called
                    mSeekBar.setProgress(time);
                    mProgressTextView.setText((time / 1000) + "/10s");
                    if (time > MAX_DURATION) {
                        mRecordButtonListener.onClick(null);
                    }
                });
    }

    private void unsubscribeTimer() {
        if (mTimerSubscription != null) {
            mTimerSubscription.unsubscribe();
            mTimerSubscription = null;
        }
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        unsubscribeTimer();

    }

    private Button mRecordButton;
    private boolean mStartRecording = true;

    private View.OnClickListener mRecordButtonListener = new View.OnClickListener() {

        public void onClick(View v) {
            onRecord(mStartRecording);
            if (mStartRecording) {
                mRecordButton.setText(R.string.stop_recording);
            } else {
                mRecordButton.setText(R.string.start_recording);
            }
            mStartRecording = !mStartRecording;
        }
    };

    private Button mPlayButton;
    private boolean mStartPlaying = true;
    private View.OnClickListener mPlayButtonListener = new View.OnClickListener() {

        public void onClick(View v) {
            onPlay(mStartPlaying);
            if (mStartPlaying) {
                mPlayButton.setText(R.string.stop_playing);
            } else {
                mPlayButton.setText(R.string.start_playing);
            }
            mStartPlaying = !mStartPlaying;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        mPlayButton = (Button) findViewById(R.id.PlayButton);
        mRecordButton = (Button) findViewById(R.id.RecordButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPlayButton.setOnClickListener(mPlayButtonListener);
        mRecordButton.setOnClickListener(mRecordButtonListener);
        mSendButton = findViewById(R.id.SendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(FILE_PATH, mFileName);
                setResult(RESULT_OK, intent);

            }
        });

        mProgressTextView = (TextView) findViewById(R.id.ProgressTextView);

        mSeekBar = (SeekBar) findViewById(R.id.SeekBar);
        mSeekBar.setMax(MAX_DURATION);
        mSeekBar.setOnTouchListener((v, event) -> true);
        mPlayButton.setEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        unsubscribeTimer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}