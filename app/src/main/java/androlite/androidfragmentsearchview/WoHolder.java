package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by janolaskar on 9/6/17.
 */
public class WoHolder extends Holder {

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DATE = "date";

    public static final String FIELD__ID = "_id";
    public static final String FIELD_PERSON_GROUP = "person_group";
    public static final String FIELD_CHAT_GROUP_MESSAGE = "chat_group_message";
    public static final String FIELD_FLAG = "flag";
    public static final String FIELD_UNREAD_COUNTER = "unread_counter";


    public static final String FIELD_LAST_MESSAGE = "last_message";


    private String title = "";
    private String date = "";

    public int _id = -1;
    public int person_group = -1;
    public int chat_group_message = -1;
    public int flag = -1;
    public int unread_counter = -1;
    public String last_message = "";

    public WoHolder() {
    }

    public WoHolder(Cursor p_cursor) {
        clone(p_cursor);
    }

    @Override
    public void clone(Cursor p_cursor) {
        if (p_cursor == null) {
            return;
        }
        try {
            title           = p_cursor.getString(1);
//            last_message         = p_cursor.getString(2);
            date            = p_cursor.getString(3);

            set_Id(p_cursor.getInt(0));
            person_group          = p_cursor.getInt(1);
            chat_group_message    = p_cursor.getInt(2);
            flag                  = p_cursor.getInt(3);
            unread_counter        = p_cursor.getInt(4);
        } catch (IndexOutOfBoundsException ioobe) {
            ioobe.printStackTrace();
        }
    }

    @Override
    public ContentValues toInsertValues() {
        ContentValues cvalues = new ContentValues();
        cvalues.put(FIELD__ID,                  _id < 1 ? null : _id);
        cvalues.put(FIELD_PERSON_GROUP,         person_group);
        cvalues.put(FIELD_CHAT_GROUP_MESSAGE,   chat_group_message);
        cvalues.put(FIELD_FLAG,                 flag);
        cvalues.put(FIELD_UNREAD_COUNTER,       unread_counter);
        return cvalues;
    }

    @Override
    public ContentValues toCommValues() {
        return toInsertValues();
    }

    public WoHolder(String p_title, String p_last_message, String p_date) {
        this.title = p_title;
        this.last_message = p_last_message;
        this.date = p_date;
    }

    public WoHolder set_Id(Integer p_value) {
        _id = p_value == null ? -1 : p_value;
        return this;
    }

    /**
     * Setter
     */
    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String p_title) {
        this.title = p_title;
    }

    public void setPersonGroup(Integer p_pg) {
        person_group = p_pg;
    }

    public void setChatGroupMessage(Integer p_chgp) {
        chat_group_message = p_chgp;
    }

    public void setFlag(Integer p_flag) {
        flag = p_flag;
    }

    public void setUnreadCounter(Integer p_unread_counter) {
        unread_counter = p_unread_counter;
    }

    public void setLastMessage(String message) {
        this.last_message = message;
    }

    /**
     * Getter
     */
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLastMessage() {
        return last_message;
    }

    public int getUnreadCounter() {
        return unread_counter;
    }
}
