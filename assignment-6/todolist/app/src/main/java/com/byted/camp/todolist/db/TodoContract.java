package com.byted.camp.todolist.db;

import android.arch.persistence.room.Entity;
import android.provider.BaseColumns;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TodoTable.TABLE_NAME + "("
            + TodoTable._ID + " INTEGER PRIMARY KEY,"
            + TodoTable.COL_NAME_CONTENT + " TEXT,"
            + TodoTable.COL_NAME_TIME + " TEXT,"
            + TodoTable.COL_NAME_IS_FIN + " INTEGER"
            + ");";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TodoTable.TABLE_NAME;

    public static final String SQL_MIGRATE_TO_TWO = "ALTER TABLE " + TodoTable.TABLE_NAME
            + " ADD COLUMN " + TodoTable.COL_NAME_PRIORITY + " INTEGER;";
    public static final String SQL_MIGRATE_SET_PRIORITY_DEFAULT = "UPDATE " + TodoTable.TABLE_NAME
            + " SET " + TodoTable.COL_NAME_PRIORITY + " = 0;";

    private TodoContract() {
    }

    public static class TodoTable implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COL_NAME_CONTENT = "content";
        public static final String COL_NAME_TIME = "create_time";
        public static final String COL_NAME_IS_FIN = "is_finished";
        public static final String COL_NAME_PRIORITY = "priority";
    }
}
