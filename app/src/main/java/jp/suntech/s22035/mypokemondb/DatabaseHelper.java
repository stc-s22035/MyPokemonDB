package jp.suntech.s22035.mypokemondb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pokemon.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuilder sd = new StringBuilder();
        sd.append("CREATE TABLE pokemon(");
        sd.append("_id INTEGER PRIMARY KEY,");
        sd.append("name TEXT,");
        sd.append("hp INTEGER");
        sd.append(");");
        String sql = sd.toString();

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
