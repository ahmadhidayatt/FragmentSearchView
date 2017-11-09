package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Created by janolaskar on 9/10/17.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static DBOpenHelper helper;
    private static final String TAG = "TwsnOpenHelper";

    public static DBOpenHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (DBOpenHelper.class) {
                if (helper == null) {
                    helper = new DBOpenHelper(context);
                }
            }
        }
        return helper;
    }

    private DBOpenHelper(Context context) {
        super(context, DB.DATABASE_NAME, null, DB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL(WoDB.getCreateTableQuery());
        arg0.execSQL(PersonDB.getCreateTableQuery());
        arg0.execSQL(FriendDB.getCreateTableQuery());
        arg0.execSQL(GroupDB.getCreateTableQuery());
        arg0.execSQL(GroupMemberDB.getCreateTableQuery());
//        arg0.execSQL(EmailHRH.getCreateTableQuery());
//        arg0.execSQL(ComplaintFormTHRH.getCreateFormTableQuery());
        arg0.execSQL(PrivateChatDB.getCreateTableQuery());
        arg0.execSQL(GroupChatDB.getCreateTableQuery());
//        arg0.execSQL(PersonTHRH.getCreateTableQuery());
//        arg0.execSQL(EmailHRH.getCreateTableQuery());
//        arg0.execSQL(ComplaintFormTHRH.getCreateFormTableQuery());
//        arg0.execSQL(ComplaintFormTHRH.getCreateReffTableQuery());
//        arg0.execSQL(ComplaintFormTHRH.getCreateTableQuery());
//        arg0.execSQL(ComplaintFormTHRH.getCreateValueTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        switch (arg1) {
            case 2:
                break;
            default:
                break;
        }
    }

    public static final File getFileSQLite(Context context, String path, String name) {
        File file = context.getDatabasePath(name);
        if (!file.exists()) {
            file = new File(path + name);
            file.mkdirs();
            file.delete();
        }
        return file;
    }
}
