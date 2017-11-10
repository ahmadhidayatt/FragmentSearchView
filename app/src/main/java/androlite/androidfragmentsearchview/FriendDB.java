package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by janolaskar on 9/10/17.
 */

public class FriendDB extends DB {
    public static final String TABLE_NAME = "FRIEND";
    public static final String[] TABLE_FIELDS = new String[] {
            FriendHolder.FIELD_PERSON,
            FriendHolder.FIELD_FRIEND,

    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\""+FriendHolder.FIELD_PERSON+"\" integer UNIQUE" +
            ",\""+FriendHolder.FIELD_FRIEND+"\" integer UNIQUE)";


    public FriendDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized FriendHolder getFirstRecord(String p_where) {
        FriendHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new FriendHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized FriendHolder getLastRecord(String p_where) {
        FriendHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new FriendHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<FriendHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<FriendHolder> getRecords(String p_where, String p_order) {
        ArrayList<FriendHolder> holders = new ArrayList<FriendHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new FriendHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holders;
    }


    public void getRecords(String p_where, String p_order, FetchListener p_fetch) {
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    p_fetch.onFetch(new FriendHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        public void onFetch(FriendHolder person);
    }

    public static String getCreateTableQuery() {
        return FriendDB.CREATE_QUERY;
    }
}
