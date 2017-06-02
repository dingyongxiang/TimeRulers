package com.yongxiang.timerulers;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.videogo.util.LocalInfo;
import com.videogo.widget.TimeBarHorizontalScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import timerulers.yongxiang.com.timerulerslib.views.RecordDataExistTimeSegment;
import timerulers.yongxiang.com.timerulerslib.views.RemoteFileTimeBar;
import timerulers.yongxiang.com.timerulerslib.views.TimebarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,TimeBarHorizontalScrollView.TimeScrollBarScrollListener {
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





    private RelativeLayout mRemotePlayBackTimeBarRl = null;
    private TimeBarHorizontalScrollView mRemotePlayBackTimeBar = null;
    private RemoteFileTimeBar mRemoteFileTimeBar = null;
    private TextView mRemotePlayBackTimeTv = null;
    private LocalInfo mLocalInfo = null;

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
        recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime - ONE_HOUR_IN_MS * 1, timebarLeftEndPointTime + ONE_HOUR_IN_MS * 3));
        recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime + ONE_HOUR_IN_MS * 4, timebarLeftEndPointTime + ONE_HOUR_IN_MS * 8));
        recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime + ONE_HOUR_IN_MS * 12, timebarLeftEndPointTime + ONE_HOUR_IN_MS * 19));
        recordDataList.add(new RecordDataExistTimeSegment(timebarLeftEndPointTime + ONE_HOUR_IN_MS * 20, timebarRightEndPointTime));

       /* recordDataList.add(new RecordDataExistTimeSegment(1496160000000l, 1496185833000l));
        recordDataList.add(new RecordDataExistTimeSegment(1496185833000l, 1496211081000l));*/
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
                Toast.makeText(MainActivity.this, "停止移动", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "OnBarMoveFinish()");
                mTimebarView.setDrag(false);
            }
        });

        mTimebarView.setOnBarScaledListener(new TimebarView.OnBarScaledListener() {
            @Override
            public void onOnBarScaledMode(int mode) {
                Log.d(TAG, "onOnBarScaledMode()" + mode);
            }

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
        // mTimebarView.openMove();
       /* mTimebarView.openMove();
        mTimebarView.checkVideo(true);*/

        // 获取配置信息操作对象
        mLocalInfo = LocalInfo.getInstance();
        // 获取屏幕参数
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        //mLocalInfo.setScreenWidthHeight(metric.widthPixels, metric.heightPixels);
        // mLocalInfo.setNavigationBarHeight((int) Math.ceil(25 * getResources().getDisplayMetrics().density));

        mRemotePlayBackTimeBarRl = (RelativeLayout) findViewById(R.id.remoteplayback_timebar_rl);
        mRemotePlayBackTimeBar = (TimeBarHorizontalScrollView) findViewById(R.id.remoteplayback_timebar);
        mRemotePlayBackTimeBar.setTimeScrollBarScrollListener(this);
        mRemotePlayBackTimeBar.smoothScrollTo(0, 0);
        mRemoteFileTimeBar = (RemoteFileTimeBar) findViewById(R.id.remoteplayback_file_time_bar);
        mRemoteFileTimeBar.setX(0, metric.widthPixels * 6);
        mRemotePlayBackTimeTv = (TextView) findViewById(R.id.remoteplayback_time_tv);
        mRemotePlayBackTimeTv.setText("00:00:00");


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

    /**
     * 屏幕当前方向
     */
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    private long mPlayTime = 0;

    @Override
    public void onScrollChanged(int i, int i1, int i2, int i3, HorizontalScrollView horizontalScrollView) {

        Calendar startCalendar = mRemoteFileTimeBar.pos2Calendar(i, mOrientation);
        if (startCalendar != null) {
            mPlayTime = startCalendar.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            mRemotePlayBackTimeTv.setText(sdf.format(mPlayTime));
        }
    }

    @Override
    public void onScrollStart(HorizontalScrollView horizontalScrollView) {

    }

    @Override
    public void onScrollStop(HorizontalScrollView horizontalScrollView) {

    }
}
