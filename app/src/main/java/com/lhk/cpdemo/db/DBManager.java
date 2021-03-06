package com.lhk.cpdemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取数据总条目
     * @param db
     * @param tableName
     * @return 数据总条目
     */
    public static int getDataTotal(SQLiteDatabase db,String tableName){
        int total = 0;
        if (db!=null){
            Cursor cursor = db.rawQuery("select * from "+tableName,null);
            total = cursor.getCount();//获取cursor中的数据总数
        }
        return total;
    }

    /**
     * 根据当前页码查询获取该页码对应的数据集合
     * @param db
     * @param tableName
     * @param currentPage
     * @return
     */
    public static List<User> getListByCurrentPage(SQLiteDatabase db, String tableName, int currentPage, int pageSize){
        Cursor cursor = null;
        int index = (currentPage-1)*pageSize;
        if (db!=null){
            String sql = "select * from "+tableName+" limit ?,?";
            cursor = db.rawQuery(sql,new String[]{index+"",pageSize+""});
        }
        return cursorToList(cursor);
    }

    /**
     * 根据sql语句查询获得cursor对象
     * @param db 数据库对象
     * @param sql 查询的sql语句
     * @param selectionArgs 查询条件的占位符
     * @return 查询结果
     */
    public static Cursor selectDataBySql(SQLiteDatabase db, String sql, String[] selectionArgs){
        Cursor cursor = null;
        if (db!=null){
            if (sql!=null&&!"".equals(sql)){
                cursor = db.rawQuery(sql,selectionArgs);
            }
        }
        return cursor;
    }

    /**
     * 将查询的cursor队对象转换成list集合
     * @param cursor
     * @return
     */
    public static List<User> cursorToList(Cursor cursor){
        List<User> list = new ArrayList<>();
        //cursor.moveToNext() 如果返回true表示下一条记录存在，否则表示游标中数据读取完毕
        while (cursor.moveToNext()){
            //getColumnIndex 根据参数中指定的字段名称获取字段下标
            int columnIndex = cursor.getColumnIndex(DBConstant.TBUser._ID);
            //getInt 根据参数中指定的字段下标获取对应int类型的数据
            int _id = cursor.getInt(columnIndex);

            String name = cursor.getString(cursor.getColumnIndex(DBConstant.TBUser.NAME));

            User user = new User();
            user.set_id(_id);
            user.setName(name);

            list.add(user);
        }
        return list;
    }

    /**
     * 根据sql语句在数据库中执行语句
     * @param db
     * @param sql
     */
    public static void execSQL(SQLiteDatabase db,String sql){
        if (db!=null){
            if (sql!=null&&!"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }
}
