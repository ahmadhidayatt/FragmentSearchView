package androlite.androidfragmentsearchview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */
public class WoAdapter extends RecyclerView.Adapter<WoAdapter.MyViewHolder> {
    private static final String TAG = "WoAdapter";
    private WoAdapter conversation_adapter;
    private List<WoHolder> conversationList;
    private List<String> wo_name = new ArrayList<>();
    private Context mContext;
    public SearchView searchView;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircularImageView thumbnail;
        public TextView name;
        public TextView last_message;
        public TextView date;
        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.img);
            name = view.findViewById(R.id.title);
            last_message = view.findViewById(R.id.message);
            date = view.findViewById(R.id.date);


        }
    }


    public WoAdapter(Context context, List<WoHolder> conversationList) {
        mContext = context;
        this.conversationList = conversationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wo_list_row, parent, false);
        RecyclerView listView = (RecyclerView) parent.findViewById(android.R.id.list);
        TextView emptyTextView = (TextView) parent.findViewById(android.R.id.empty);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WoHolder conversation = conversationList.get(position);

        holder.name.setText(conversation.getTitle());
        holder.last_message.setText(conversation.getLastMessage());
        holder.date.setText(conversation.getDate());
        if (getItemCount() == 0) {

        }


        Log.e(TAG, conversation.toCommValues() + "");

        if (conversation.flag == 0) {
            holder.thumbnail.setImageResource(R.mipmap.logo_img1);
        } else {
            holder.thumbnail.setImageResource(R.mipmap.logo_img2);
        }

//        Glide.with(mContext).load(R.drawable.group_profile)
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }
}
