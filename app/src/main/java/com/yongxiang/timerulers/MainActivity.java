package com.yongxiang.timerulers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import timerulers.yongxiang.com.timerulerslib.views.RecordDataExistTimeSegment;
import timerulers.yongxiang.com.timerulerslib.views.TimebarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = MainActivity.class.getSimpleName();
    private TextView currentTimeTextView;
    private ImageView zoomInButton, zoomOutButton;
    private TimebarView mTimebarView;

    private Button mDayBt;
    private Button mHourBt;
    private Button mMinuteBt;
    private int recordDays = 7;
    private long currentRealDateTime = System.currentTimeMillis();
    private Calendar calendar;

    private static long ONE_MINUTE_IN_MS = 60 * 1000;
    private static long ONE_HOUR_IN_MS = 60 * ONE_MINUTE_IN_MS;
    private static long ONE_DAY_IN_MS = 24 * ONE_HOUR_IN_MS;

    private SimpleDateFormat zeroTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimebarView = (TimebarView) findViewById(R.id.my_timebar_view);
        currentTimeTextView = (TextView) findViewById(R.id.current_time_tv);
        zoomInButton = (ImageView) findViewById(R.id.timebar_zoom_in_btn);
        zoomOutButton = (ImageView) findViewById(R.id.timebar_zoom_out_btn);
        mDayBt = (Button) findViewById(R.id.day);
        mHourBt = (Button) findViewById(R.id.hour);
        mMinuteBt = (Button) findViewById(R.id.minute);

        zoomInButton.setOnClickListener(this);
        zoomOutButton.setOnClickListener(this);
        mDayBt.setOnClickListener(this);
        mHourBt.setOnClickListener(this);
        mMinuteBt.setOnClickListener(this);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //long timebarLeftEndPointTime = currentRealDateTime - 7 * 24 * 3600 * 1000;
        long timebarLeftEndPointTime = calendar.getTimeInMillis();

        System.out.println("calendar:" + calendar.getTime() + "  currentRealDateTime:" + currentRealDateTime);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long timebarRightEndPointTime = calendar.getTimeInMillis();
        //long timebarRightEndPointTime = currentRealDateTime + 3 * 3600 * 1000;

        mTimebarView.initTimebarLengthAndPosition(timebarLeftEndPointTime,
                timebarRightEndPointTime - 1000, currentRealDateTime);

        final List<RecordDataExistTimeSegment> recordDataList = new ArrayList<>();
       /* recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime - ONE_HOUR_IN_MS * 1, timebarLeftEndPointTime + ONE_HOUR_IN_MS * 3));
        recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime + ONE_HOUR_IN_MS * 4, timebarLeftEndPointTime + ONE_HOUR_IN_MS * 8));
        recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime + ONE_HOUR_IN_MS * 12, timebarLeftEndPointTime + ONE_HOUR_IN_MS * 19));
        recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime + ONE_HOUR_IN_MS * 20, timebarRightEndPointTime));*/

        recordDataList.add(new RecordDataExistTimeSegment(1496160000000l, 1496185833000l));
        recordDataList.add(new RecordDataExistTimeSegment(1496185833000l, 1496211081000l));
        mTimebarView.openMove();
        mTimebarView.checkVideo(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mTimebarView.setRecordDataExistTimeClipsList(recordDataList);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        mTimebarView.setOnBarMoveListener(new TimebarView.OnBarMoveListener() {
            @Override
            public void onBarMove(long screenLeftTime, long screenRightTime, long currentTime) {
                if (currentTime == -1) {
                    Toast.makeText(MainActivity.this, "当前时刻没有录像", Toast.LENGTH_SHORT).show();
                }
                currentTimeTextView.setText(zeroTimeFormat.format(currentTime));
                Log.d(TAG, "onBarMove()");
            }

            @Override
            public void OnBarMoveFinish(long screenLeftTime, long screenRightTime, long currentTime) {
                currentTimeTextView.setText(zeroTimeFormat.format(currentTime));
                Log.d(TAG, "OnBarMoveFinish()");
            }
        });

        mTimebarView.setOnBarScaledListener(new TimebarView.OnBarScaledListener() {
            @Override
            public void onBarScaled(long screenLeftTime, long screenRightTime, long currentTime) {
                currentTimeTextView.setText(zeroTimeFormat.format(currentTime));
                Log.d(TAG, "onBarScaled()");
            }

            @Override
            public void onBarScaleFinish(long screenLeftTime, long screenRightTime, long currentTime) {
                Log.d(TAG, "onBarScaleFinish()");
            }
        });


      /*  Date d = new Date(1495818022L * 1000l);
        String texttime = zeroTimeFormat.format(d);

        Log.d(TAG, "NOWTIME:" + texttime);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* mTimebarView.openMove();
        mTimebarView.checkVideo(true);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timebar_zoom_in_btn:
                mTimebarView.scaleByPressingButton(true);
                break;
            case R.id.timebar_zoom_out_btn:
                mTimebarView.scaleByPressingButton(false);
                break;
            case R.id.day:
                mTimebarView.setMode(3);
                break;
            case R.id.hour:
                mTimebarView.setMode(2);
                break;
            case R.id.minute:
                mTimebarView.setMode(1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimebarView.recycle();
    }
}
