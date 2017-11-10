package androlite.androidfragmentsearchview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nirhart.parallaxscroll.views.ParallaxScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmad on 15/09/17.
 */

public class GroupView_backup extends AppCompatActivity {
    private static final String TAG = "GroupVIew";
    private List<PersonHolder> groupList = new ArrayList<>();
    private RecyclerView recyclerView;
    public GroupviewAdapter groupAdapter;
    ParallaxScrollView parlax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_view);
        recyclerView = (RecyclerView) findViewById(R.id.group_member_list);
        parlax = (ParallaxScrollView) findViewById(R.id.parlax);
//        parlax.scrollTo(100,10000);
        groupList = new ArrayList<>();
        groupAdapter = new GroupviewAdapter(getApplicationContext(), groupList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupAdapter);
        prepareConversationData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    private void prepareConversationData() {
        ArrayList<GroupMemberHolder> arCh = new GroupMemberDB(getApplicationContext()).getRecords(GroupMemberHolder.FIELD_GROUP + " = 300");
        for (GroupMemberHolder groupHolder : arCh) {
            Log.e(TAG, "onBindViewHolder: " + groupHolder.person );
                ArrayList<PersonHolder> arC = new PersonDB(getApplicationContext()).getRecords(PersonHolder.FIELD__ID + " = " + groupHolder.person);
                for (PersonHolder pervHolder : arC) {
                    Log.e(TAG, "onBindViewHolder: " + pervHolder.name );
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                    groupList.add(pervHolder);
                }
        }
    }
}
