package com.lhk.cpdemo.db;

/**
 * Created by Administrator on 2019/4/12.
 */

class DBConstant {

    // 数据库名
    static final String DATABASE_NAME = "finch.db";

    //数据库版本号
    static final int DATABASE_VERSION = 1;

    // User表信息
    static class TBUser{
        static final String TABLE_NAME = "user";

        static final String _ID = "_id";
        static final String NAME = "name";

        static final String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + " "+NAME+" TEXT)";
        static final String sqlDrop = "delete from "+TABLE_NAME;

        String buildInsert(int _id,String name){
            return "insert into user values("+_id+",'"+name+"');";
        }
    }

    // User表信息
    static class TBJob{
        static final String TABLE_NAME = "job";

        static final String _ID = "_id";
        static final String JOB = "job";

        static final String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + " "+JOB+" TEXT)";
        static final String sqlDrop = "delete from "+TABLE_NAME;

        String buildInsert(int _id,String job){
            return "insert into job values("+_id+",'"+job+"');";
        }
    }
}
