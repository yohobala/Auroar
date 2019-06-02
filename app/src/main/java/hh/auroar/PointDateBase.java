package hh.auroar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class PointDateBase extends SQLiteOpenHelper {
    public static final String point= "point";
  public  static  final  String CREATE_POINT="create table "+ point +"("+
        "id integer primary key autoincrement,"+
          "latitude double,"+
          "longitude double)";

    public PointDateBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase pd) {
        pd.execSQL(CREATE_POINT);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}