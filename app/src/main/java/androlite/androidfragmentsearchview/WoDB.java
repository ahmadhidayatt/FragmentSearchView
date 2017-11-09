package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by janolaskar on 9/10/17.
 */

public class WoDB extends DB {
    public static final String TABLE_NAME = "WO_LIST";
    public static final String[] TABLE_FIELDS = new String[] {
            WoHolder.FIELD__ID,
            WoHolder.FIELD_PERSON_GROUP,
            WoHolder.FIELD_CHAT_GROUP_MESSAGE,
            WoHolder.FIELD_FLAG,
            WoHolder.FIELD_UNREAD_COUNTER,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\"_id\" integer PRIMARY KEY  NOT NULL " +
            ",\""+ WoHolder.FIELD_PERSON_GROUP+"\" integer UNIQUE" +
            ",\""+ WoHolder.FIELD_CHAT_GROUP_MESSAGE+"\" integer UNIQUE" +
            ",\""+ WoHolder.FIELD_FLAG+"\" integer" +
            ",\""+ WoHolder.FIELD_UNREAD_COUNTER+"\" integer)";


    public WoDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized WoHolder getFirstRecord(String p_where) {
        WoHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new WoHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized WoHolder getLastRecord(String p_where) {
        WoHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new WoHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<WoHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<WoHolder> getRecords(String p_where, String p_order) {
        ArrayList<WoHolder> holders = new ArrayList<WoHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new WoHolder(cursor));
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
                    p_fetch.onFetch(new WoHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        void onFetch(WoHolder person);
    }

    public static String getCreateTableQuery() {
        return WoDB.CREATE_QUERY;
    }
}
