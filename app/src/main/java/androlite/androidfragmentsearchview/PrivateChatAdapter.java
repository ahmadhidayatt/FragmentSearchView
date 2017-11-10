package androlite.androidfragmentsearchview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ahmad on 07/09/17.
 */

public class PrivateChatAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<PrivateChatHolder> chatMessageList;

    public PrivateChatAdapter(Activity activity, ArrayList<PrivateChatHolder> list) {
        chatMessageList = list;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PrivateChatHolder message = chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chatbubble, null);

        TextView msg = vi.findViewById(R.id.message_text);
        TextView msg_time = vi.findViewById(R.id.message_time);
        ImageView msg_status = vi.findViewById(R.id.message_status);
        msg_time.setText(message.Time);
        msg.setText(message.messages);
        LinearLayout layout = vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = vi
                .findViewById(R.id.bubble_layout_parent);
        ImageView avatar = vi.findViewById(R.id.avatar_me);
        ImageView avatar_not = vi.findViewById(R.id.avatar_not_me);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        // if message is mine then align to right
        if (message.person ==20002 || message.isMine) {
            layout.setBackgroundResource(R.drawable.bubbles_white);
            avatar_not.setVisibility(View.GONE);
            msg_status.setImageResource(R.drawable.pending_chat);
            parent_layout.setGravity(Gravity.RIGHT);
            msg.setTextColor(Color.BLACK);
            msg_time.setTextColor(Color.LTGRAY);

        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bubbles_red_2);
            avatar.setVisibility(View.GONE);
            msg_status.setImageResource(R.drawable.pending_chat_white);
            parent_layout.setGravity(Gravity.LEFT);
            msg.setTextColor(Color.WHITE);

        }
        avatar_not.setVisibility(View.GONE);
        avatar.setVisibility(View.GONE);

        return vi;
    }

    public void add(PrivateChatHolder object) {
        chatMessageList.add(object);
    }
}
