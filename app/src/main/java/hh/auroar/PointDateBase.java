package hh.auroar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class PointDateBase extends SQLiteOpenHelper {
    private static final int DB_VERSION =1;
    private static final String DB_NAME ="myTest.db";
    public static  final String TABLE_NAME ="Points";

    public  static  final  String CREATE_POINT="create table if not exists "+ TABLE_NAME +"("+"id integer primary key autoincrement,"+"latitude double,"+"longitude double)";

    public PointDateBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase pd) {
        pd.execSQL(CREATE_POINT);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    String sql ="DROP TABLE IF EXISTS " + TABLE_NAME;
    sqLiteDatabase.execSQL(sql);
    onCreate(sqLiteDatabase);
    }


}