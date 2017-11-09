package androlite.androidfragmentsearchview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */

public class GroupList extends Fragment {

    private List<GroupHolder> groupList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GroupAdapter group_adapter;

    public GroupList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView =  inflater.inflate(R.layout.group_list_fragment, container, false);
        recyclerView = mRootView.findViewById(R.id.recycler_view);

        groupList = new ArrayList<>();
        group_adapter = new GroupAdapter(groupList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(group_adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                GroupHolder group_holder = groupList.get(position);
                Toast.makeText(getActivity(), group_holder.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GroupChatList.class);
                intent .putExtra("name",group_holder.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareConversationData();
        return mRootView;
    }

    private void prepareConversationData() {
        ArrayList<GroupHolder> arCh = new GroupDB(getContext()).getRecords(null);
        for (GroupHolder gholder : arCh) {
//            if (pholder._id != 1) {
            groupList.add(gholder);
//            }
        }
//        GroupHolder objGroup = new GroupHolder("Mad Max: Fury Road");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Inside Out");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Star Wars: Episode VII - The Force Awakens");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Shaun the Sheep");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("The Martian");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Mission: Impossible Rogue Nation");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Up");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Star Trek");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("The LEGO Movie");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Iron Man");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Aliens");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Chicken Run");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Back to the Future");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Raiders of the Lost Ark");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Goldfinger");
//        groupList.add(objGroup);
//
//        objGroup = new GroupHolder("Guardians of the Galaxy");
//        groupList.add(objGroup);

        group_adapter.notifyDataSetChanged();
    }

}