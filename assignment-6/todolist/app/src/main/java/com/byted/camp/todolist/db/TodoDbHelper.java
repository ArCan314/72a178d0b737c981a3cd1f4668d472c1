package com.byted.camp.todolist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class TodoDbHelper extends SQLiteOpenHelper {

    // TODO 定义数据库名、版本；创建数据库
    public static final int DB_VER = 2;
    public static final String DB_NAME = "Todos.db";

    public TodoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TodoContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2)
        {
            db.execSQL(TodoContract.SQL_MIGRATE_TO_TWO);
            db.execSQL(TodoContract.SQL_MIGRATE_SET_PRIORITY_DEFAULT);
        }
    }

}
