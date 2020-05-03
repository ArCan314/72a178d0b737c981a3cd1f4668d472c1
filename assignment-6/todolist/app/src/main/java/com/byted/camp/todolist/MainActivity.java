package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;
import com.byted.camp.todolist.operation.activity.DatabaseActivity;
import com.byted.camp.todolist.operation.activity.DebugActivity;
import com.byted.camp.todolist.operation.activity.SettingActivity;
import com.byted.camp.todolist.ui.NoteListAdapter;
import com.facebook.stetho.inspector.database.SqliteDatabaseDriver;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1002;

    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;
    private HandlerThread handlerThread;
    private WorkHandler workHandler;
    private TodoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(MainActivity.this, NoteActivity.class),
                        REQUEST_CODE_ADD);
            }
        });

        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(Note note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(notesAdapter);

        handlerThread = new HandlerThread("operation_todos_db");
        handlerThread.start();
        workHandler = new WorkHandler(handlerThread.getLooper());
        dbHelper = new TodoDbHelper(this);

        notesAdapter.refresh(loadNotesFromDatabase());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quitSafely();
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            case R.id.action_database:
                startActivity(new Intent(this, DatabaseActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            notesAdapter.refresh(loadNotesFromDatabase());
        }
    }

    private List<Note> loadNotesFromDatabase() {
        // TODO 从数据库中查询数据，并转换成 JavaBeans
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Note> notes = new ArrayList<Note>();
        String sortOrder = TodoContract.TodoTable.COL_NAME_PRIORITY + " DESC, " + TodoContract.TodoTable.COL_NAME_TIME + " DESC";

        Cursor cursor = db.query(
                TodoContract.TodoTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndex(TodoContract.TodoTable._ID));
            State state = (cursor.getInt(cursor.getColumnIndex(TodoContract.TodoTable.COL_NAME_IS_FIN)) == 1) ? State.DONE : State.TODO;

            Date date = new Date();
            date.setTime(cursor.getLong(cursor.getColumnIndex(TodoContract.TodoTable.COL_NAME_TIME)));
            String content = cursor.getString(cursor.getColumnIndex(TodoContract.TodoTable.COL_NAME_CONTENT));
            int priority = cursor.getInt(cursor.getColumnIndex(TodoContract.TodoTable.COL_NAME_PRIORITY));

            Note note = new Note(itemId);
            note.setContent(content);
            note.setDate(date);
            note.setState(state);
            note.setPriority(priority);
            notes.add(note);
        }
        return notes;
    }

    private void deleteNote(Note note) {
        // TODO 删除数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = TodoContract.TodoTable._ID + " = ?";
        String selectionArgs[] = {String.valueOf(note.id)};
        int deleteRows = db.delete(TodoContract.TodoTable.TABLE_NAME, selection, selectionArgs);

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notesAdapter.refresh(loadNotesFromDatabase());
            }
        });
    }

    private void updateNode(Note note) {
        // TODO 更新数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoTable.COL_NAME_CONTENT, note.getContent());
        values.put(TodoContract.TodoTable.COL_NAME_IS_FIN, note.getState() == State.DONE ? 1 : 0);
        values.put(TodoContract.TodoTable.COL_NAME_TIME, note.getDate().getTime());
        values.put(TodoContract.TodoTable.COL_NAME_PRIORITY, note.getPriority());
        String selection = TodoContract.TodoTable._ID + " = ?";
        String selectionArgs[] = {String.valueOf(note.id)};

        int updateRows = db.update(TodoContract.TodoTable.TABLE_NAME, values, selection, selectionArgs);
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notesAdapter.refresh(loadNotesFromDatabase());
            }
        });
    }

    private class WorkHandler extends Handler {

        public static final int MSG_LOAD_NOTES = 1;
        public static final int MSG_DELETE_NOTE = 2;
        public static final int MSG_UPDATE_NOTE = 3;

        public WorkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Note note = (Note) msg.obj;
            switch (msg.what) {
                case MSG_LOAD_NOTES:
                    loadNotesFromDatabase();
                    break;
                case MSG_DELETE_NOTE:
                    deleteNote(note);
                    break;
                case MSG_UPDATE_NOTE:
                    updateNode(note);
                    break;
                default:
                    break;
            }
        }
    }

}
