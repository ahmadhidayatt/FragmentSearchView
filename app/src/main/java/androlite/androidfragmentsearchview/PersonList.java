package androlite.androidfragmentsearchview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */

public class PersonList extends Fragment {

    private static final String TAG = "PersonList";


    private List<PersonHolder> personList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PersonAdapter person_adapter;
    private Toolbar toolbar;

    public PersonList() {
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
        View mRootView = inflater.inflate(R.layout.friend_list_fragment, container, false);
        recyclerView = mRootView.findViewById(R.id.recycler_view);
        toolbar = mRootView.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        personList = new ArrayList<>();
        person_adapter = new PersonAdapter(personList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(person_adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PersonHolder friend_holder = personList.get(position);
                Toast.makeText(getActivity(), friend_holder.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PrivateChatList.class);
                intent.putExtra("name", friend_holder.getName());
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
        ArrayList<PersonHolder> arCh = new PersonDB(getContext()).getRecords(null);
        for (PersonHolder pholder : arCh) {
            Log.e(TAG, pholder.toCommValues() + "");
//            if (pholder._id != 1) {
            personList.add(pholder);
//            }
        }
//
//        FriendHolder objFriend = new FriendHolder("Jano");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Laskar");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Dewandaru");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Ahmad");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Hidayat");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Awal");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Muhib");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Halim");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Muhammad");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Ridwan");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Zalbina");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Ahmad");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Nafis");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Shoba");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Yayan");
//        personList.add(objFriend);
//
//        objFriend = new FriendHolder("Dwi");
//        personList.add(objFriend);

        person_adapter.notifyDataSetChanged();
    }

}
