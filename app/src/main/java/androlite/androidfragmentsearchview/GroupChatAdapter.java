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

public class GroupChatAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<GroupChatHolder> chatMessageList;

    public GroupChatAdapter(Activity activity, ArrayList<GroupChatHolder> list) {
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

        GroupChatHolder message = (GroupChatHolder) chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chatbubble_group, null);

        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);
        LinearLayout layout_image = (LinearLayout) vi
                .findViewById(R.id.layout_image);


        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        TextView msg_time = (TextView) vi.findViewById(R.id.message_time);
        TextView username = (TextView) vi.findViewById(R.id.name_text);

        ImageView msg_status = (ImageView) vi.findViewById(R.id.message_status);
        ImageView avatar = (ImageView) vi.findViewById(R.id.avatar_me);
        ImageView avatar_not = (ImageView) vi.findViewById(R.id.avatar_not_me);

        avatar.setImageDrawable(message.image);
        avatar_not.setImageDrawable(message.image);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        // if message is mine then align to right


        avatar.setVisibility(View.GONE);
        avatar_not.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);


        if (message.isMine) {
            layout.setBackgroundResource(R.drawable.bubbles_white);
            avatar_not.setVisibility(View.GONE);
            username.setGravity(Gravity.RIGHT);
            parent_layout.setGravity(Gravity.RIGHT);
            username.setVisibility(View.GONE);
            layout_image.setGravity(Gravity.TOP);
            msg.setTextColor(Color.BLACK);
            msg_status.setImageResource(R.drawable.pending_chat);
            msg_time.setTextColor(Color.LTGRAY);
        }
        // If not mine then align to left
        else {
            if (position > 0 & message.senderName == chatMessageList.get(position - 1).senderName) {

            }
            layout.setBackgroundResource(R.drawable.bubbles_red_2);
            avatar.setVisibility(View.GONE);
            username.setGravity(Gravity.LEFT);
            parent_layout.setGravity(Gravity.LEFT);
            username.setVisibility(View.VISIBLE);
            msg.setTextColor(Color.WHITE);
            msg_status.setImageResource(R.drawable.pending_chat_white);

        }

        msg.setText(message.messages);
        msg_time.setText(message.Time);
        username.setText(message.senderName);

        return vi;
    }

    public void add(GroupChatHolder object) {
        chatMessageList.add(object);
    }

}
