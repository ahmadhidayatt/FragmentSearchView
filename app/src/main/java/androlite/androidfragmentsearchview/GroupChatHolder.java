package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;

/**
 * Created by ahmad on 11/09/17.
 */

public class GroupChatHolder extends Holder{
    public static final String FIELD__ID = "_id";
    public static final String FIELD_PERSON = "person";
    public static final String FIELD_FRIEND = "friend";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_WHEN = "date";
    public static final String FIELD_DELIVERED = "delivered";
    public static final String FIELD_READ = "read";

    private String title = "";
    public String message="";
    private String date = "";


    public String messages;
    public int person;//from
    public int friend;//to
    public String senderName;
    public int id;
    public String Date, Time;
    public String when;

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String delivered;
    public String read;
    public Drawable image;
    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
    public String getMessage() {
        return messages;
    }

    public void setMessage(String message) {
        this.messages = message;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getFriend() {
        return friend;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }


    public GroupChatHolder(int id, int person, int friend, String message, String delivered, String read) {
        this.id =id;
        this.messages = message;
        this.person = person;
        this.friend = friend;
        this.delivered = delivered;
        this.read = read;
    }
    public GroupChatHolder(String body, int sender, int receiver, String senderName, String date, String time, String delivered_Date_Time, String read_Date_Time) {
        this.messages = body;
        this.person = sender;
        this.friend = receiver;
        this.senderName = senderName;
        Date = date;
        Time = time;
        this.delivered = delivered_Date_Time;
        this.read = read_Date_Time;
    }
    public GroupChatHolder() {
    }

    public GroupChatHolder(Cursor p_cursor) {
        clone(p_cursor);
    }

    @Override
    public void clone(Cursor p_cursor) {
        if (p_cursor == null) {
            return;
        }
        try {
            title           = p_cursor.getString(1);
            message         = p_cursor.getString(2);
            date            = p_cursor.getString(3);

            setId(p_cursor.getInt(0));
            person          = p_cursor.getInt(1);
            friend    = p_cursor.getInt(2);
            messages                  = p_cursor.getString(3);
            when        = p_cursor.getString(4);
            delivered        = p_cursor.getString(5);
            read        = p_cursor.getString(6);
//            avatar        = p_cursor.getString(5);
        } catch (IndexOutOfBoundsException ioobe) {
            ioobe.printStackTrace();
        }
    }


    @Override
    public ContentValues toInsertValues() {
        ContentValues cvalues = new ContentValues();
        cvalues.put(FIELD__ID,                  id < 1 ? null : id);
        cvalues.put(FIELD_PERSON,         person);
        cvalues.put(FIELD_FRIEND,   friend);
        cvalues.put(FIELD_MESSAGE,                 messages);
        cvalues.put(FIELD_WHEN,       when);
        cvalues.put(FIELD_DELIVERED,       delivered);
        cvalues.put(FIELD_READ,       read);

        return cvalues;
    }
    @Override
    public ContentValues toCommValues() {
        return toInsertValues();
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isMine;



    public GroupChatHolder(int Sender, int Receiver, String messageString
            , boolean isMINE, Drawable images) {
        messages = messageString;
        isMine = isMINE;
        person = Sender;
        friend = Receiver;
//        senderName = person;
        image = images;
    }




}
