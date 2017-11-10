package androlite.androidfragmentsearchview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by janolaskar on 9/10/17.
 */

public class PersonDB extends DB {
    public static final String TABLE_NAME = "PERSON";
    public static final String[] TABLE_FIELDS = new String[] {
            PersonHolder.FIELD__ID,
            PersonHolder.FIELD_NAME,
            PersonHolder.FIELD_ONLINE,
            PersonHolder.FIELD_UPDATED,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\"_id\" integer PRIMARY KEY  NOT NULL " +
            ",\""+PersonHolder.FIELD_NAME+"\" TEXT" +
            ",\""+PersonHolder.FIELD_ONLINE+"\" integer" +
            ",\""+PersonHolder.FIELD_UPDATED+"\" integer)";

    public PersonDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized PersonHolder getFirstRecord(String p_where) {
        PersonHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new PersonHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized PersonHolder getLastRecord(String p_where) {
        PersonHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new PersonHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<PersonHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<PersonHolder> getRecords(String p_where, String p_order) {
        ArrayList<PersonHolder> holders = new ArrayList<PersonHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new PersonHolder(cursor));
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
                    p_fetch.onFetch(new PersonHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        public void onFetch(PersonHolder person);
    }

    public static String getCreateTableQuery() {
        return PersonDB.CREATE_QUERY;
    }

    public synchronized PersonHolder getRecord(String p_where) {
        PersonHolder holder = null;
        Cursor cursor = null;

        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, null, "1");
            if (cursor != null) {
                if(cursor.moveToFirst()) {
                    holder = new PersonHolder(cursor);
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
