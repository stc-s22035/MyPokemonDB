package jp.suntech.s22035.mypokemondb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bttouroku = findViewById(R.id.btRegistation);
        Button btkensaku = findViewById(R.id.btSearch);
        Button btsyoukyo = findViewById(R.id.btDelete);

        btClickListener listener = new btClickListener();
        bttouroku.setOnClickListener(listener);
        btkensaku.setOnClickListener(listener);
        btsyoukyo.setOnClickListener(listener);
        _helper = new DatabaseHelper(MainActivity.this);
        UpdateList();
    }
    @Override
    protected void onDestroy(){
        _helper.close();
        super.onDestroy();
    }
    private  class btClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
            EditText idtext = findViewById(R.id.idtext);
            EditText nametext = findViewById(R.id.nametext);
            EditText hptext = findViewById(R.id.hptext);
            int idvalue = Integer.parseInt( idtext.getText().toString());
            String namevalue = nametext.getText().toString();
            int hpvalue = Integer.parseInt( hptext.getText().toString());
            if (id == R.id.btRegistation){
                SQLiteDatabase db = _helper.getWritableDatabase();


                String sqlDelete = "DELETE From pokemon WHERE _id = ?";
                SQLiteStatement stmt = db.compileStatement(sqlDelete);
                stmt.bindLong(1, idvalue);
                stmt.executeUpdateDelete();

                String sqlInsert = "INSERT INTO pokemon (_id,name,hp) VALUES(?,?,?)";
                stmt = db.compileStatement(sqlInsert);

                stmt.bindLong(1,idvalue);
                stmt.bindString(2,namevalue);
                stmt.bindLong(3,hpvalue);
                stmt.executeInsert();
                UpdateList();
            }

            if (id == R.id.btSearch){
                ListView list = findViewById(R.id.list);
                SQLiteDatabase db = _helper.getWritableDatabase();
                String sql = "SELECT * FROM pokemon WHERE _id = " + idvalue;
                Cursor cursor = db.rawQuery(sql,null);
                List<Map<String,Object>> menuList = new ArrayList<>();
                while(cursor.moveToNext()){
                    int idxid = cursor.getColumnIndex("_id");
                    int idxname = cursor.getColumnIndex("name");
                    int idxhp = cursor.getColumnIndex("hp");

                    int _id = cursor.getInt(idxid);
                    String name = cursor.getString(idxname);
                    int hp = cursor.getInt(idxhp);
                    Map<String, Object> menu = new HashMap<>();
                    menu.put("id",_id);
                    menu.put("name",name);
                    menu.put( "hp",hp);
                    menuList.add(menu);
                }
                final String [] FROM ={"id","name","hp"};
                final int[] TO = {R.id.tvid,R.id.tvname,R.id.tvhp};
                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,menuList,R.layout.row,FROM,TO);
                list.setAdapter(adapter);
            }
            if (id == R.id.btDelete){
                SQLiteDatabase db = _helper.getWritableDatabase();


                String sqlDelete = "DELETE From pokemon WHERE _id = ?";
                SQLiteStatement stmt = db.compileStatement(sqlDelete);
                stmt.bindLong(1, idvalue);
                stmt.executeUpdateDelete();
                UpdateList();
            }
        }
    }
    private void UpdateList(){
        ListView list = findViewById(R.id.list);
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sql = "SELECT * FROM pokemon";
        Cursor cursor = db.rawQuery(sql,null);
        List<Map<String,Object>> menuList = new ArrayList<>();
        while(cursor.moveToNext()){
            int idxid = cursor.getColumnIndex("_id");
            int idxname = cursor.getColumnIndex("name");
            int idxhp = cursor.getColumnIndex("hp");

            int _id = cursor.getInt(idxid);
            String name = cursor.getString(idxname);
            int hp = cursor.getInt(idxhp);
            Map<String, Object> menu = new HashMap<>();
            menu.put("id",_id);
            menu.put("name",name);
            menu.put( "hp",hp);
            menuList.add(menu);
        }
        final String [] FROM ={"id","name","hp"};
        final int[] TO = {R.id.tvid,R.id.tvname,R.id.tvhp};
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,menuList,R.layout.row,FROM,TO);
        list.setAdapter(adapter);
    }

}