package chapter.android.aweme.ss.com.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ChatRoom extends AppCompatActivity {
    private TextView contentText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        contentText = findViewById(R.id.tv_content_info);
        Intent intent = getIntent();
        if (intent != null) {
            int clickPos = intent.getIntExtra(Exercises3.CLICK_POS_KEY, 0);
            contentText.setText("Index: " + String.valueOf(clickPos));
        }
    }
}
