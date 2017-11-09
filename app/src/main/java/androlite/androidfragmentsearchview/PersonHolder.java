package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by janolaskar on 9/11/17.
 */

public class PersonHolder extends Holder {

    public static final String FIELD__ID = "_id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ONLINE = "online";
    public static final String FIELD_UPDATED = "updated";

    public int _id = -1;
    public String name = "";
    public int online = -1;
    public int updated = -1;

    public PersonHolder() {

    }

    public PersonHolder(Cursor p_cursor) {
        clone(p_cursor);
    }

    @Override
    public void clone(Cursor p_cursor) {
        if (p_cursor == null) {
            return;
        }
        try {
            set_Id(p_cursor.getInt(0));
            name                = p_cursor.getString(1);
            online              = p_cursor.getInt(2);
            updated             = p_cursor.getInt(3);
        } catch (IndexOutOfBoundsException ioobe) {
            ioobe.printStackTrace();
        }
    }

    @Override
    public ContentValues toInsertValues() {
        ContentValues cvalues = new ContentValues();
        cvalues.put(FIELD__ID,                  _id < 1 ? null : _id);
        cvalues.put(FIELD_NAME,                 name);
        cvalues.put(FIELD_ONLINE,               online);
        cvalues.put(FIELD_UPDATED,              updated);

        return cvalues;
    }

    @Override
    public ContentValues toCommValues() {
        return toInsertValues();
    }

    public PersonHolder set_Id(Integer p_value) {
        _id = p_value == null ? -1 : p_value;
        return this;
    }

    public void setName(String p_name) {
        name = p_name;
    }

    public void setOnline(Integer p_online) {
        online = p_online;
    }

    public void setUpdated(Integer p_updated) {
        updated = p_updated;
    }

    public String getName() {
        return name;
    }
}
