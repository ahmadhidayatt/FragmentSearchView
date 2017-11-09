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
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {

    private List<PersonHolder> personList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
        }
    }


    public PersonAdapter(List<PersonHolder> personList) {
        this.personList = personList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PersonHolder person = personList.get(position);
        holder.name.setText(person.getName());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
