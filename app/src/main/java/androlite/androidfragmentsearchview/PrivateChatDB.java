package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by ahmad on 11/09/17.
 */

public class PrivateChatDB extends DB{
    public static final String TABLE_NAME = "CHAT_DB";
    public static final String[] TABLE_FIELDS = new String[] {
            PrivateChatHolder.FIELD__ID,
            PrivateChatHolder.FIELD_PERSON,
            PrivateChatHolder.FIELD_FRIEND,
            PrivateChatHolder.FIELD_MESSAGE,
            PrivateChatHolder.FIELD_WHEN,
            PrivateChatHolder.FIELD_DELIVERED,
            PrivateChatHolder.FIELD_READ,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\""+ PrivateChatHolder.FIELD__ID+"\" INTEGER PRIMARY KEY  NOT NULL " +
            ",\""+ PrivateChatHolder.FIELD_PERSON+"\" INTEGER " +
            ",\""+ PrivateChatHolder.FIELD_FRIEND+"\" INTEGER " +
            ",\""+ PrivateChatHolder.FIELD_MESSAGE+"\" TEXT" +
            ",\""+ PrivateChatHolder.FIELD_WHEN+"\" TEXT" +
            ",\""+ PrivateChatHolder.FIELD_DELIVERED+"\" TEXT" +
            ",\""+ PrivateChatHolder.FIELD_READ+"\"TEXT)";
    public PrivateChatDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized PrivateChatHolder getFirstRecord(String p_where) {
        PrivateChatHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new PrivateChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized PrivateChatHolder getLastRecord(String p_where) {
        PrivateChatHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new PrivateChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<PrivateChatHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<PrivateChatHolder> getRecords(String p_where, String p_order) {
        ArrayList<PrivateChatHolder> holders = new ArrayList<PrivateChatHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new PrivateChatHolder(cursor));
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
                    p_fetch.onFetch(new PrivateChatHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        public void onFetch(PrivateChatHolder person);
    }

    public static String getCreateTableQuery() {
        return PrivateChatDB.CREATE_QUERY;
    }

//    public static synchronized PrivateChatHolder getById(Integer p_id) {
//        return getRecord(PrivateChatHolder.FIELD__ID + "=" + p_id);
//    }

    public synchronized PrivateChatHolder getRecord(String p_where) {
        PrivateChatHolder holder = null;
        Cursor cursor = null;

        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, null, "1");
            if (cursor != null) {
                if(cursor.moveToFirst()) {
                    holder = new PrivateChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }

        return holder;
    }
    public	synchronized ArrayList<PrivateChatHolder> getRecords(String p_where, String p_groupby, String p_order, String p_limit) {
        ArrayList<PrivateChatHolder> holders = new ArrayList<PrivateChatHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS,p_where, p_groupby, p_order, p_limit);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new PrivateChatHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holders;
    }
}
