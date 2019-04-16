package com.lhk.cpdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lhk.cpdemo.db.DBManager;

public class MainActivity extends AppCompatActivity {

    Button btn_insert,btn_delete,btn_update,btn_query;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager.initData(this);

        initView();
        initListView();

        /**
         * 对job表进行操作
         */
        // 和上述类似,只是URI需要更改,从而匹配不同的URI CODE,从而找到不同的数据资源
        Uri uri_job = Uri.parse("content://cn.scu.myprovider/job");

        // 插入表中数据
        ContentValues values2 = new ContentValues();
        values2.put("_id", 3);
        values2.put("job", "NBA Player");

        // 获取ContentResolver
        ContentResolver resolver2 =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver2.insert(uri_job,values2);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor2 = resolver2.query(uri_job, new String[]{"_id","job"}, null, null, null);
        while (cursor2.moveToNext()){
            System.out.println("query job:" + cursor2.getInt(0) +" "+ cursor2.getString(1));
        }
        cursor2.close();
        // 关闭游标
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv);
        btn_insert= (Button) findViewById(R.id.btn_insert);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_query = (Button) findViewById(R.id.btn_query);
        View.OnClickListener listener = new OnBtnClickListener();
        btn_insert.setOnClickListener(listener);
        btn_delete.setOnClickListener(listener);
        btn_update.setOnClickListener(listener);
        btn_query.setOnClickListener(listener);
    }

    private void initListView(){
        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");
        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name", "Iverson");
        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"}, null, null, null);

        /**
         * SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
         * context 上下文对象
         * layout 表示适配器控件中每一项item的布局id
         * c 表示Cursor数据源
         * from 表示Cursor中数据表字段的数组
         * to 表示字段对应值的控件资源id
         * flags 设置适配器的标记
         */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item,cursor
                ,new String[]{"_id","name"}
                ,new int[]{R.id.tv_id,R.id.tv_name}
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    private void funInsert(){
        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");

        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 4);
        values.put("name", "Iverson");


        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        resolver.registerContentObserver(uri_user, true, new MyContentObserver(oHandler));
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);
    }

    private void funDelete(){
        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");

        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 5);
        values.put("name", "Lina");


        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        resolver.insert(uri_user,values);
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.delete(uri_user,"_id=?", new String[]{"1"});
    }

    private void funUpdate(){
        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");

        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "Mark");


        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        resolver.insert(uri_user,values);
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        ContentValues valuesu = new ContentValues();
        valuesu.put("_id", 2);
        valuesu.put("name", "曾小贤");
        resolver.update(uri_user, valuesu, "_id=?", new String[]{"2"});
    }

    private void funQuery(){
        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");
        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"}, null, null, null);

        MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(myCursorAdapter);
    }

    private class OnBtnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_insert:
                    funInsert();
                    break;
                case R.id.btn_delete:
                    funDelete();
                    break;
                case R.id.btn_update:
                    funUpdate();
                    break;
                case R.id.btn_query:
                    funQuery();
                    break;
            }
        }
    }

    private Handler oHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");
            // 通过ContentResolver 向ContentProvider中查询数据
            Cursor cursor = getContentResolver().query(uri_user, new String[]{"_id","name"}, null, null, null);
            /**
             * SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
             * context 上下文对象
             * layout 表示适配器控件中每一项item的布局id
             * c 表示Cursor数据源
             * from 表示Cursor中数据表字段的数组
             * to 表示字段对应值的控件资源id
             * flags 设置适配器的标记
             */
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.item,cursor
                    ,new String[]{"_id","name"}
                    ,new int[]{R.id.tv_id,R.id.tv_name}
                    ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            listView.setAdapter(adapter);
        }
    };

    private class MyContentObserver extends ContentObserver {

        private Handler handler;

        public MyContentObserver(Handler handler) {
            super(handler);
            this.handler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            handler.sendEmptyMessage(0);
            System.out.println("Thread:"+Thread.currentThread().getName());
        }
    }

    /**
     * 使用方法：
     * MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
     * listView.setAdapter(myCursorAdapter);
     */
    public static class MyCursorAdapter extends CursorAdapter {

        public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        public MyCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        /**
         * 表示创建适配器控件中每个item对应的view对象
         * @param context 上下文
         * @param cursor 数据源cursor对象
         * @param viewGroup 当前item的父布局
         * @return 每一项item的view对象
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View view = LayoutInflater.from(context).inflate(R.layout.item,null);
            return view;
        }

        /**
         * 通过newView()方法确定了每个item展示view对象，在bindView()中对布局中的控件进行填充
         * @param view 由newView（）返回的每项view对象
         * @param context 上下文
         * @param cursor 数据源cursor对象
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

            tv_id.setText(cursor.getInt(cursor.getColumnIndex("_id"))+"");
            tv_name.setText(cursor.getString(cursor.getColumnIndex("name")));
        }
    }
}
