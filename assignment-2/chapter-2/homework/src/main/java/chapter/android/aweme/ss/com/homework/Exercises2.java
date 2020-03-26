package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {

    private TextView viewNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex2);
        viewNumber = (TextView) findViewById(R.id.view_number_textview);

        viewNumber.setText(String.valueOf(getAllChildViewCount(findViewById(R.id.ex2_root_layout))));
    }

    public int getAllChildViewCount(View view) {
        //todo 补全你的代码

        Queue<ViewGroup> viewQueue = new LinkedList<ViewGroup>();
        int count = 0;

        if (view instanceof ViewGroup) {
            viewQueue.offer((ViewGroup) view);
        }
        else {
            count++;
        }

        while (!viewQueue.isEmpty()) {
            Log.d("EX2", String.valueOf(viewQueue.size()) + ", POP");
            View cur = viewQueue.poll();

            if (cur instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) cur).getChildCount(); i++) {
                    View child = ((ViewGroup) cur).getChildAt(i);
                    if (child instanceof ViewGroup) {
                        viewQueue.offer((ViewGroup) child);
                        Log.d("EX2", "PUSH");
                    }
                    else {
                        count++;
                    }
                }
            }
            else {
                count++;
            }
        }
        Log.d("EX2", String.valueOf(count));
        return count;
    }
}
