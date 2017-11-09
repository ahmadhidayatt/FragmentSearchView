package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by ahmad on 11/09/17.
 */

public class GroupChatDB extends DB{
    public static final String TABLE_NAME = "CHAT_GROUP_DB";
    public static final String[] TABLE_FIELDS = new String[] {
            GroupChatHolder.FIELD__ID,
            GroupChatHolder.FIELD_PERSON,
            GroupChatHolder.FIELD_FRIEND,
            GroupChatHolder.FIELD_MESSAGE,
            GroupChatHolder.FIELD_WHEN,
            GroupChatHolder.FIELD_DELIVERED,
            GroupChatHolder.FIELD_READ,
//            WoHolder.FIELD_USER_ACCESS,
//            WoHolder.FIELD_USER_CODE,
//            WoHolder.FIELD_THUMB_ID,
//            WoHolder.FIELD_QUOTE,
//            WoHolder.FIELD_CONNECTED,
//            WoHolder.FIELD_LAST_UPDATE,
//            WoHolder.FIELD_NAME,
//            WoHolder.FIELD_TIMEZONE,
//            WoHolder.FIELD_EX_BUDDY_STATUS,
//            WoHolder.FIELD_EX_BUDDY_BLOCK,
//            WoHolder.FIELD_POSITION_ID,
//            WoHolder.FIELD_POSITION_DESC,
//            WoHolder.FIELD_MEDIA,
//            WoHolder.FIELD_CONNECTION_ID,
//            WoHolder.FIELD_EMAIL,
//            WoHolder.FIELD_PHONE,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\""+ GroupChatHolder.FIELD__ID+"\" INTEGER PRIMARY KEY  NOT NULL " +
            ",\""+ GroupChatHolder.FIELD_PERSON+"\" INTEGER " +
            ",\""+ GroupChatHolder.FIELD_FRIEND+"\" INTEGER " +
            ",\""+ GroupChatHolder.FIELD_MESSAGE+"\" TEXT" +
            ",\""+ GroupChatHolder.FIELD_WHEN+"\" TEXT" +
            ",\""+ GroupChatHolder.FIELD_DELIVERED+"\" TEXT" +
            ",\""+ GroupChatHolder.FIELD_READ+"\"TEXT)";


//            ",\""+WoHolder.FIELD_USER_ACCESS+"\" TEXT UNIQUE" +
//            ",\""+WoHolder.FIELD_USER_CODE+"\" TEXT" +
//            ",\""+WoHolder.FIELD_THUMB_ID+"\" TEXT" +
//            ",\""+WoHolder.FIELD_QUOTE+"\" TEXT" +
//            ",\""+WoHolder.FIELD_CONNECTED+"\" TEXT" +
//            ",\""+WoHolder.FIELD_LAST_UPDATE+"\" TEXT" +
//            ",\""+WoHolder.FIELD_NAME+"\" TEXT" +
//            ",\""+WoHolder.FIELD_TIMEZONE+"\" TEXT" +
//            ",\""+WoHolder.FIELD_EX_BUDDY_STATUS+"\" TEXT" +
//            ",\""+WoHolder.FIELD_EX_BUDDY_BLOCK+"\" TEXT" +
//            ",\""+WoHolder.FIELD_POSITION_ID+"\" TEXT" +
//            ",\""+WoHolder.FIELD_POSITION_DESC+"\" TEXT" +
//            ",\""+WoHolder.FIELD_MEDIA+"\" TEXT" +
//            ",\""+WoHolder.FIELD_CONNECTION_ID+"\" TEXT" +
//            ",\""+WoHolder.FIELD_EMAIL+"\" TEXT" +
//            ",\""+WoHolder.FIELD_PHONE+"\" TEXT)";

    public GroupChatDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized GroupChatHolder getFirstRecord(String p_where) {
        GroupChatHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new GroupChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized GroupChatHolder getLastRecord(String p_where) {
        GroupChatHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new GroupChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<GroupChatHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<GroupChatHolder> getRecords(String p_where, String p_order) {
        ArrayList<GroupChatHolder> holders = new ArrayList<GroupChatHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new GroupChatHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holders;
    }


    public void getRecords(String p_where, String p_order, GroupChatDB.FetchListener p_fetch) {
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    p_fetch.onFetch(new GroupChatHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        void onFetch(GroupChatHolder person);
    }

    public static String getCreateTableQuery() {
        return GroupChatDB.CREATE_QUERY;
    }

    public synchronized GroupChatHolder getRecord(String p_where) {
        GroupChatHolder holder = null;
        Cursor cursor = null;

        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, null, "1");
            if (cursor != null) {
                if(cursor.moveToFirst()) {
                    holder = new GroupChatHolder(cursor);
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
