package androlite.androidfragmentsearchview;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmad on 19/09/17.
 */

public class GroupView extends AppCompatActivity {
    private static final String TAG = "GroupVIew";
    private static final String EXTRA_IMAGE = "com.GroupView.janolaskar.easysoftchat.extraImage";
    private static final String EXTRA_TITLE = "com.GroupView.janolaskar.easysoftchat.extraTitle";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private List<PersonHolder> groupList = new ArrayList<>();
    private RecyclerView recyclerView;
    public GroupviewAdapter groupAdapter;
    Toolbar toolbar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        String name;
        int id;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
                id = 0;

            } else {
                name = extras.getString("name");
                id = extras.getInt("id");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("name");
            id = (int) savedInstanceState.getSerializable("id");
        }

        setContentView(R.layout.example);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPink));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));


        final ImageView image = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
        if(id == 1){
            image.setImageResource(R.mipmap.logo_img1);
        }
        else {
            image.setImageResource(R.mipmap.logo_img2);
        }
        recyclerView = (RecyclerView) findViewById(R.id.group_member_list);
        groupList = new ArrayList<>();
        groupAdapter = new GroupviewAdapter(getApplicationContext(), groupList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupAdapter);
        prepareConversationData();
    }

    private void prepareConversationData() {
        ArrayList<GroupMemberHolder> arCh = new GroupMemberDB(getApplicationContext()).getRecords(GroupMemberHolder.FIELD_GROUP + " = 300");
        for (GroupMemberHolder groupHolder : arCh) {
            Log.e(TAG, "onBindViewHolder: " + groupHolder.person);
            ArrayList<PersonHolder> arC = new PersonDB(getApplicationContext()).getRecords("(" + PersonHolder.FIELD__ID + " = " + groupHolder.person + ") AND (" + groupHolder.person + " != 20002)");
            for (PersonHolder pervHolder : arC) {
                Log.e(TAG, "onBindViewHolder: " + pervHolder.name);
                groupList.add(pervHolder);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorAccent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }
}
