package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by janolaskar on 9/6/17.
 */
public class GroupHolder extends Holder {
    public static final String FIELD__ID = "_id";
    public static final String FIELD_NAME = "name";

    public int _id = -1;
    public String name = "";

    public GroupHolder() {
    }

    public GroupHolder(Cursor p_cursor) {
        clone(p_cursor);
    }

    public GroupHolder(String name) {
        this.name = name;
    }

    @Override
    public void clone(Cursor p_cursor) {
        if (p_cursor == null) {
            return;
        }
        try {
            set_Id(p_cursor.getInt(0));
            name                = p_cursor.getString(1);
        } catch (IndexOutOfBoundsException ioobe) {
            ioobe.printStackTrace();
        }
    }

    @Override
    public ContentValues toInsertValues() {
        ContentValues cvalues = new ContentValues();
        cvalues.put(FIELD__ID,                  _id < 1 ? null : _id);
        cvalues.put(FIELD_NAME,                 name);

        return cvalues;
    }

    @Override
    public ContentValues toCommValues() {
        return toInsertValues();
    }

    public GroupHolder set_Id(Integer p_value) {
        _id = p_value == null ? -1 : p_value;
        return this;
    }

    public void setName(String p_name) {
        name = p_name;
    }

    public String getName() {
        return name;
    }
}
