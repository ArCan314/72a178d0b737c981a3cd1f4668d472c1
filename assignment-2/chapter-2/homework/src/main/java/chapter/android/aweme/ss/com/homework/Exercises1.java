package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * 作业1：
 * Logcat在屏幕旋转的时候 #onStop() #onDestroy()会展示出来
 * 但我们的 mLifecycleDisplay 由于生命周期的原因(Tips:执行#onStop()之后，UI界面我们是看不到的)并没有展示
 * 在原有@see Exercises1 基础上如何补全它，让其跟logcat的展示一样?
 * <p>
 * Tips：思考用比Activity的生命周期要长的来存储？  （比如：application、static变量）
 */
public class Exercises1 extends AppCompatActivity {

    private final static String EX1_LOG_TAG = "EX1";
    private final static String ON_CREATE_LOG_STR = "onCreate";
    private final static String ON_START_LOG_STR = "onStart";
    private final static String ON_RESUME_LOG_STR = "onResume";
    private final static String ON_STOP_LOG_STR = "onStop";
    private final static String ON_DESTROY_LOG_STR = "onDestroy";
    private final static String ON_RESTART_LOG_STR = "onRestart";
    private final static String ON_PAUSE_LOG_STR = "onPause";
    private final static String ON_SAVE_INSTANCE_STATE_STR = "onSaveInstanceState";
    private final static String ON_RESTORE_INSTANCE_STATE_STR = "onRestoreInstanceState";
    private final static String SIS_GET_LOG_STR_KEY = "LOG_TEXTVIEW_EX1";

    private TextView logDisplay;
    private static String logDisplayStr = "";

    private void logAndAppend(String logString) {
        logDisplayStr += logString + "\n";
        Log.d(EX1_LOG_TAG, logString);
        logDisplay.setText(logDisplayStr);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);
        logDisplay = findViewById(R.id.logTextView_ex1);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SIS_GET_LOG_STR_KEY)) {
                logDisplay.setText((String)savedInstanceState.get(SIS_GET_LOG_STR_KEY));
            }
        }
        logAndAppend(ON_CREATE_LOG_STR);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAndAppend(ON_START_LOG_STR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logAndAppend(ON_RESUME_LOG_STR);
    }

    @Override
    protected void onPause() {
        super.onPause();
        logAndAppend(ON_PAUSE_LOG_STR);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logAndAppend(ON_STOP_LOG_STR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logAndAppend(ON_DESTROY_LOG_STR);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAndAppend(ON_RESTART_LOG_STR);
    }

    @Override
    protected void onSaveInstanceState(Bundle outBundle) {
        super.onSaveInstanceState(outBundle);
        logAndAppend(ON_SAVE_INSTANCE_STATE_STR);
        outBundle.putString(SIS_GET_LOG_STR_KEY, logDisplay.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logAndAppend(ON_RESTORE_INSTANCE_STATE_STR);
    }
}
