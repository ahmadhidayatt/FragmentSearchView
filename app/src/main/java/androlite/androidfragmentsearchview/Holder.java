package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by ahmad on 08/09/17.
 */

public abstract class Holder {

    private boolean mChecked;
    public final boolean isChecked() {
        return mChecked;
    }

    public final void setChecked(boolean p_selected) {
        mChecked = p_selected;
    }


    public abstract void clone(Cursor p_cursor);


    public abstract ContentValues toInsertValues();
    public abstract ContentValues toCommValues();
}
