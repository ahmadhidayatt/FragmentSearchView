package androlite.androidfragmentsearchview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    private List<GroupHolder> groupList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }


    public GroupAdapter(List<GroupHolder> groupList) {
        this.groupList = groupList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GroupHolder group = groupList.get(position);
        holder.title.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
