package assignment.test.raghu.peppersqaure.provider;

/**
 * Created by Raghunandan on 22-11-2015.
 */
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Raghunandan on 22-10-2015.
 */
public class ActorsDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "hollywoodactors.db";
    private static final int DB_VERSION = 1;
    private Context mContext;

    public ActorsDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //private String name,description,dob,country,height,spouse,children,image;

        db.execSQL("CREATE TABLE " + ActorContract.Actors.TABLE_MOVIES + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + ActorContract.ActorColumns.ACTOR_ID + " INTEGER NOT NULL,"
                + ActorContract.ActorColumns.ACTOR_NAME + " TEXT,"
                + ActorContract.ActorColumns.ACTOR_DESCRIPTION+ " TEXT,"
                + ActorContract.ActorColumns.ACTOR_DOB + " TEXT,"
                + ActorContract.ActorColumns.ACTOR_COUNTRY+ " TEXT,"
                + ActorContract.ActorColumns.ACTOR_HEIGHT+ " TEXT,"
                + ActorContract.ActorColumns.ACTOR_SPOUSE+ " TEXT,"
                + ActorContract.ActorColumns.ACTOR_CHILDREN + " TEXT,"
                + ActorContract.ActorColumns.ACTOR_IMAGE + " TEXT,"
                + ActorContract.ActorColumns.ACTOR_FAV + " INTEGER"
                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}