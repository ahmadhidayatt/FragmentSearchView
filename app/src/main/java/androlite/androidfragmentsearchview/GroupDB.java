package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by janolaskar on 9/10/17.
 */

public class GroupDB extends DB {
    public static final String TABLE_NAME = "GROUPS";
    public static final String[] TABLE_FIELDS = new String[] {
            GroupHolder.FIELD__ID,
            GroupHolder.FIELD_NAME,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\"_id\" integer PRIMARY KEY  NOT NULL " +
            ",\""+GroupHolder.FIELD_NAME+"\" TEXT)";

    public GroupDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized GroupHolder getFirstRecord(String p_where) {
        GroupHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new GroupHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized GroupHolder getLastRecord(String p_where) {
        GroupHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new GroupHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<GroupHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<GroupHolder> getRecords(String p_where, String p_order) {
        ArrayList<GroupHolder> holders = new ArrayList<GroupHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new GroupHolder(cursor));
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
                    p_fetch.onFetch(new GroupHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        public void onFetch(GroupHolder person);
    }

    public static String getCreateTableQuery() {
        return GroupDB.CREATE_QUERY;
    }
    public synchronized GroupHolder getRecord(String p_where) {
        GroupHolder holder = null;
        Cursor cursor = null;

        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, null, "1");
            if (cursor != null) {
                if(cursor.moveToFirst()) {
                    holder = new GroupHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }

        return holder;
    }
}
