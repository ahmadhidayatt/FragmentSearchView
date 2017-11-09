package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by janolaskar on 9/6/17.
 */
public class GroupMemberHolder extends Holder {

    public static final String FIELD_GROUP = "groups";
    public static final String FIELD_PERSON = "person";
    private String title;

    public int group = -1;
    public int person = -1;

    public GroupMemberHolder() {
    }

    public GroupMemberHolder(Cursor p_cursor) {
        clone(p_cursor);
    }

    @Override
    public void clone(Cursor p_cursor) {
        if (p_cursor == null) {
            return;
        }
        try {
            group               = p_cursor.getInt(0);
            person              = p_cursor.getInt(1);
        } catch (IndexOutOfBoundsException ioobe) {
            ioobe.printStackTrace();
        }
    }

    @Override
    public ContentValues toInsertValues() {
        ContentValues cvalues = new ContentValues();
        cvalues.put(FIELD_GROUP,                group);
        cvalues.put(FIELD_PERSON,               person);

        return cvalues;
    }

    @Override
    public ContentValues toCommValues() {
        return toInsertValues();
    }

    public void setGroup(Integer p_group) {
        group = p_group;
    }

    public void setPerson(Integer p_person) {
        person = p_person;
    }
}
