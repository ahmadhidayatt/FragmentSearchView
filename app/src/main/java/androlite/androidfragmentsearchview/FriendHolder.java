package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by janolaskar on 9/6/17.
 */
public class FriendHolder extends Holder {

    public static final String FIELD_PERSON = "person";
    public static final String FIELD_FRIEND = "friend";

    public int person = -1;
    public int friend = -1;

    public FriendHolder() {
    }

    public FriendHolder(Cursor p_cursor) {
        clone(p_cursor);
    }

    @Override
    public void clone(Cursor p_cursor) {
        if (p_cursor == null) {
            return;
        }
        try {
            person              = p_cursor.getInt(0);
            friend              = p_cursor.getInt(1);
        } catch (IndexOutOfBoundsException ioobe) {
            ioobe.printStackTrace();
        }
    }

    @Override
    public ContentValues toInsertValues() {
        ContentValues cvalues = new ContentValues();
        cvalues.put(FIELD_PERSON,               person);
        cvalues.put(FIELD_FRIEND,               friend);

        return cvalues;
    }

    @Override
    public ContentValues toCommValues() {
        return toInsertValues();
    }

    public void setPerson(Integer p_person) {
        person = p_person;
    }

    public void setFriend(Integer p_friend) {
        friend = p_friend;
    }
}
