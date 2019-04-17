package com.lhk.cpdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ZongHeActivity extends AppCompatActivity {

    Button btn_insert,btn_delete,btn_update,btn_query;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv);
        btn_insert= (Button) findViewById(R.id.btn_insert);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_insert.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        btn_update.setVisibility(View.GONE);
        btn_query.setVisibility(View.GONE);
    }

    private void initListView() {
        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");
        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("name", "ZongHe");
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
}
