package androlite.androidfragmentsearchview;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by janolaskar on 9/10/17.
 */

public abstract class DB {
    @SuppressLint("SdCardPath")
    public static final String DATABASE_PATH = "/data/data/com.easysoftid.mwa.auriel/databases/";
    public static final String DATABASE_NAME = "eSChat.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TAG = "DB";

    private Context mContext;
    private String mTable;
    private SQLiteDatabase mSQLite;

    public DB(Context p_context, String p_table) {
        mContext = p_context;
        mTable = p_table;
    }

    protected final SQLiteDatabase getSqLiteDatabase() {
        return mSQLite;
    }

    public final Context getContext() {
        return mContext;
    }

    public final String getTableName() {
        return mTable;
    }

    public final DB open() throws SQLException {
        mSQLite = DBOpenHelper.getInstance(getContext()).getWritableDatabase();
        return this;
    }

    public boolean isOpen() {
        if (mSQLite == null) {
            return false;
        }
        return mSQLite.isOpen();
    }

    protected long insert(ContentValues p_values) {
        long insert = -3;
        if (p_values == null) {
            return insert;
        }
        try {
            insert = mSQLite.insertOrThrow(mTable, null, p_values);
        } catch (SQLiteConstraintException sqlce) {
            Log.e(TAG, "SQLiteConstraintException! " + Log.getStackTraceString(sqlce));
            insert = -2;
        } catch (SQLiteDiskIOException sqldioe) {
            Log.e(TAG, "SQLiteDiskIOException! " + Log.getStackTraceString(sqldioe));
            insert = -4;
        } catch (SQLiteException sqle) {
            Log.e(TAG, "SQLiteException! " + Log.getStackTraceString(sqle));
            insert = -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
            insert = -3;
        }
        return insert;
    }

    protected long replace(ContentValues p_values) {
        long insert = -3;
        if (p_values == null) {
            return insert;
        }
        try {
            insert = mSQLite.insertWithOnConflict(mTable, null, p_values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (SQLiteConstraintException sqlce) {
            Log.e(TAG, "SQLiteConstraintException! " + Log.getStackTraceString(sqlce));
            insert = -2;
        } catch (SQLiteDiskIOException sqldioe) {
            Log.e(TAG, "SQLiteDiskIOException! " + Log.getStackTraceString(sqldioe));
            insert = -4;
        } catch (SQLiteException sqle) {
            Log.e(TAG, "SQLiteException! " + Log.getStackTraceString(sqle));
            insert = -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
            insert = -3;
        }
        return insert;
    }

    protected int delete(String p_where) {
        int delete = -3;
        try {
            delete = mSQLite.delete(mTable, p_where, null);
        } catch (SQLiteException sqle) {
            Log.e(TAG, "SQLiteException! " + Log.getStackTraceString(sqle));
            delete = -3;
        }
        return delete;
    }

    protected int update(ContentValues p_values, String p_where) {
        int update = -3;
        try {
            update = mSQLite.update(mTable, p_values, p_where, null);
        } catch (SQLiteException sqle) {
            Log.e(TAG, "SQLiteException! " + Log.getStackTraceString(sqle));
            update = -3;
        }
        return update;
    }

    protected final Cursor fetch(String[] p_fields, String p_where) throws SQLException {
        return fetch(p_fields, p_where, null, null);
    }

    protected final Cursor fetch(String[] p_fields, String p_where, String p_orderBy) throws SQLException {
        return fetch(p_fields, p_where, p_orderBy, null);
    }

    protected final Cursor fetch(String[] p_fields, String p_where, String p_orderBy, String p_limit) throws SQLException {
        return mSQLite.query(mTable, p_fields, p_where, null, null, null, p_orderBy, p_limit);
    }

    protected final Cursor fetch(String[] p_fields, String p_where, String p_groupBy, String p_orderBy, String p_limit) {
        Cursor cursor = null;
        try {
            cursor = mSQLite.query(mTable, p_fields, p_where, null, p_groupBy, null, p_orderBy, p_limit);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return cursor;
    }

    public final Cursor rawQuery(String p_query, String[] p_fields) {
        Cursor cursor = null;
        try {
            cursor = mSQLite.rawQuery(p_query, p_fields);
        } catch (SQLiteException sqle) {
            Log.e(TAG, "SQLiteException! " + Log.getStackTraceString(sqle));
        }
        return cursor;
    }


    public void execQuery(String p_query) {
        try {
            open();
            getSqLiteDatabase().execSQL(p_query);
        } catch (SQLException sqle) {
            Log.e(TAG, "SQLiteException! " + Log.getStackTraceString(sqle));
        }
    }

    public final long[] insertTransaction(ArrayList<ContentValues> p_values) {
        if (p_values == null) {
            return new long[0];
        }
        long[] result = new long[p_values.size()];
        try {
            open();
            mSQLite.beginTransaction();
            for (int x = 0; x < result.length; x++) {
                result[x] = insert(p_values.get(x));
            }
            mSQLite.setTransactionSuccessful();
            mSQLite.endTransaction();
        } catch (SQLiteDiskIOException sqldioe) {
            Log.e(TAG, "SQLiteDiskIOException! " + Log.getStackTraceString(sqldioe));
            result = new long[] { -4 };
        } catch (SQLiteException sqle) {
            Log.e(TAG, "SQLiteException! " + Log.getStackTraceString(sqle));
            result = new long[] { -1 };
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
            result = new long[] { -3 };
        }
        return result;
    }

    public final long insertRecord(ContentValues p_values) {
        return insertRecord(p_values, false);
    }

    public final long insertRecord(ContentValues p_values, boolean p_replace) {
        long insert = -1;
        try {
            open();
            insert = p_replace ? replace(p_values) : insert(p_values);
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
        }
        return insert;
    }

    public final int[] updateTransaction(ArrayList<ContentValues> p_values, ArrayList<String> p_where) {
        if (p_values == null || p_where == null) {
            return new int[0];
        }
        int[] result = new int[p_values.size()];
        try {
            open();
            mSQLite.beginTransaction();
            for (int x = 0; x < result.length; x++) {
                result[x] = update(p_values.get(x), p_where.get(x));
            }
            mSQLite.setTransactionSuccessful();
            mSQLite.endTransaction();
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
        }
        return result;
    }

    public final int updateRecord(ContentValues p_values, String p_where) {
        int update = -1;
        try {
            open();
            update = update(p_values, p_where);
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
        }
        return update;
    }

    public final int[] deleteTransaction(ArrayList<String> p_where) {
        int[] result = new int[p_where.size()];
        try {
            open();
            mSQLite.beginTransaction();
            for (int x = 0; x < p_where.size(); x++) {
                result[x] = delete(p_where.get(x));
            }
            mSQLite.setTransactionSuccessful();
            mSQLite.endTransaction();
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
        }
        return result;
    }

    public final int deleteRecord(String p_where) {
        int delete = -1;
        try {
            open();
            delete = delete(p_where);
        } catch (Exception e) {
            Log.e(TAG, "Exception! " + Log.getStackTraceString(e));
        }
        return delete;
    }

}
