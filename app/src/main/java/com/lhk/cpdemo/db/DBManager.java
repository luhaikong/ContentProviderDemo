package com.lhk.cpdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2019/4/12.
 */

public class DBManager {

    private static DBHelper helper;

    public static DBHelper getInstance(Context context){
        if (helper==null){
            synchronized (DBHelper.class){
                if (helper==null){
                    helper = new DBHelper(context);
                }
            }
        }
        return helper;
    }

    public static void initData(Context context){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        // 初始化两个表的数据(先清空两个表,再各加入一个记录)
        db.execSQL(DBConstant.TBUser.sqlDrop);
        DBConstant.TBUser tbUser = new DBConstant.TBUser();
        db.execSQL(tbUser.buildInsert(1,"Carson"));
        db.execSQL(tbUser.buildInsert(2,"Kobe"));
//        db.execSQL("insert into user values(1,'Carson');");
//        db.execSQL("insert into user values(2,'Kobe');");

        db.execSQL(DBConstant.TBJob.sqlDrop);
        DBConstant.TBJob tbJob = new DBConstant.TBJob();
        db.execSQL(tbJob.buildInsert(1,"Android"));
        db.execSQL(tbJob.buildInsert(2,"iOS"));
//        db.execSQL("insert into job values(1,'Android');");
//        db.execSQL("insert into job values(2,'iOS');");
    }
}
