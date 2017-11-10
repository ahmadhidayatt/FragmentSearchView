package androlite.androidfragmentsearchview;

import android.content.Context;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmad on 15/09/17.
 */

public class GroupviewAdapter extends RecyclerView.Adapter<GroupviewAdapter.MyViewHolder> {
    private static final String TAG = "GroupviewAdapter";

    private List<GroupMemberHolder> GroupviewList;
    private List<PersonHolder> PersonList;
//    MainActivity mainActivity;

    private Context mContext;
    private boolean multiSelect = false;
    private ActionMode actionMode = null;
    private ArrayList<PersonHolder> selectedItems = new ArrayList<PersonHolder>();

    @Override
    public GroupviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.group_list_member_view, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupviewAdapter.MyViewHolder myViewHolder, int position) {
        final PersonHolder person = PersonList.get(position);
        myViewHolder.thumbnail.setImageResource(R.mipmap.wo_img4);
        myViewHolder.name.setText(person.name);
        Log.e(TAG, "onBindViewHolder: " + person.name);
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Intent intent = new Intent(mContext, PrivateChatList.class);
//                    Bundle extras = new Bundle();
//                    extras.putString("person_group",person._id+"");
//                    extras.putString("conversation_message","");
//                    extras.putString("name",person.name);
//                    intent.putExtras(extras);
//                    mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return PersonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircularImageView thumbnail;
        public TextView name;
        public LinearLayout container;
        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.group_member_image);
            name = (TextView) itemView.findViewById(R.id.group_member_name);
            container =(LinearLayout)itemView.findViewById(R.id.container);

        }


    }

    public GroupviewAdapter(Context context, List<PersonHolder> gruplist) {
        List<GroupMemberHolder> perlist;
        mContext = context;

        this.PersonList = gruplist;
    }
}
