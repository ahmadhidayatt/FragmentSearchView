package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by janolaskar on 9/10/17.
 */

public class GroupMemberDB extends DB {
    public static final String TABLE_NAME = "GROUP_MEMBER";
    public static final String[] TABLE_FIELDS = new String[] {
            GroupMemberHolder.FIELD_GROUP,
            GroupMemberHolder.FIELD_PERSON,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\""+GroupMemberHolder.FIELD_GROUP+"\" integer UNIQUE" +
            ",\""+GroupMemberHolder.FIELD_PERSON+"\" integer UNIQUE)";

    public GroupMemberDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized GroupMemberHolder getFirstRecord(String p_where) {
        GroupMemberHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new GroupMemberHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized GroupMemberHolder getLastRecord(String p_where) {
        GroupMemberHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new GroupMemberHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<GroupMemberHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<GroupMemberHolder> getRecords(String p_where, String p_order) {
        ArrayList<GroupMemberHolder> holders = new ArrayList<GroupMemberHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new GroupMemberHolder(cursor));
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
                    p_fetch.onFetch(new GroupMemberHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        public void onFetch(GroupMemberHolder person);
    }

    public static String getCreateTableQuery() {
        return GroupMemberDB.CREATE_QUERY;
    }
}
