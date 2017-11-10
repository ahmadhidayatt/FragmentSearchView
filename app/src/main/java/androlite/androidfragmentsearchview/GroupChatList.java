package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ahmad on 06/09/17.
 */

public class GroupChatList extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GroupChatlist";
    private EditText msg_edittext;
    private CircularImageView avatar;
    private TextView title;
    private ImageView image_back;
    private ImageButton sendButton;
    public static ArrayList<GroupChatHolder> chatlist;
    public static GroupChatAdapter chatAdapter;
    ListView msgListView;
    private Toolbar toolbar;
    private String person_group;
    private String conversation_message = "";
    private Boolean canSend = false;

    public GroupChatList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
                person_group = null;
                conversation_message = null;
            } else {
                name = extras.getString("name");
                conversation_message = extras.getString("conversation_message");
                person_group = extras.getString("person_group");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("name");
            conversation_message = (String) savedInstanceState.getSerializable("conversation_message");
            person_group = (String) savedInstanceState.getSerializable("person_group");
        }
        setContentView(R.layout.fragment_chat_group);

        msg_edittext = (EditText) findViewById(R.id.messageEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        sendButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);
        sendButton.setVisibility(View.GONE);


        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<GroupChatHolder>();
        chatAdapter = new GroupChatAdapter(this, chatlist);
        msgListView.setAdapter(chatAdapter);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        image_back = (ImageView) findViewById(R.id.back);
//        image_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        avatar = (CircularImageView) toolbar.findViewById(R.id.avatar);
        avatar.setImageDrawable(getResources().getDrawable(R.drawable.group_profile));
        title = (TextView) toolbar.findViewById(R.id.title);
        title.setText(name);
        prepareChatData();
        avatar.setOnClickListener(this);

        msg_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    Animation fadeOut = setBehaviorAnim("2");
                    sendButton.setAnimation(fadeOut);
                    sendButton.setVisibility(View.GONE);
                    canSend = false;
                } else{
                    String onKeyPressMsg = msg_edittext.getText().toString().trim();
                    if (onKeyPressMsg.length() > 0) {
                        if (!canSend) {
                            Animation fadeIn = setBehaviorAnim("1");
                            sendButton.setAnimation(fadeIn);
                            sendButton.setVisibility(View.VISIBLE);
                            canSend = true;
                        }
                    } else {
                        if (canSend) {
                            Animation fadeOut = setBehaviorAnim("2");
                            sendButton.setAnimation(fadeOut);
                            sendButton.setVisibility(View.GONE);
                            canSend = false;
                        }

                    }
                }

            }
        });

    }

    private Animation setBehaviorAnim(String pFlag) {
        if (pFlag.equals("1")) {
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setDuration(300);
            return fadeIn;
        } else {
            Animation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setDuration(300);
            return fadeOut;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            final GroupChatHolder chatMessage = new GroupChatHolder(2002, 300,
                    message, true, getResources().getDrawable(R.drawable.friends_profile));
            chatMessage.image = getResources().getDrawable(R.drawable.friends_profile);
            chatMessage.when = CommonMethods.getFullCurrentDate();
            Log.e(TAG, chatMessage.when);
            chatMessage.message = message;
            chatMessage.Date = CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();

            long insert5 = 0;
            GroupChatDB dbss = new GroupChatDB(getApplicationContext());
            Log.e(TAG, String.valueOf(chatMessage.person + " " + chatMessage.groups));

            GroupChatHolder holders = new GroupChatHolder();
            holders.setPerson(20002);
            holders.setGroups(300);
            holders.setMessage(message.toString());
            holders.setWhen(CommonMethods.getFullCurrentDate());
            holders.setDelivered(CommonMethods.getCurrentDate());
            holders.setRead(CommonMethods.getCurrentDate());
            holders.setMine(true);

            insert5 = dbss.insertRecord(holders.toCommValues(), false);

            GroupChatHolder gcholder = new GroupChatDB(getApplicationContext()).getLastRecord(null);
            ContentValues cvalues = new ContentValues();
            cvalues.put(WoHolder.FIELD_CHAT_GROUP_MESSAGE, gcholder.id);
//            new ConversationDB(getApplicationContext()).updateRecord(cvalues, ConversationHolder.FIELD_CHAT_GROUP_MESSAGE + " = " +conversation_message + " AND "+ConversationHolder.FIELD_FLAG +"=0");
            new WoDB(getApplicationContext()).updateRecord(cvalues, WoHolder.FIELD__ID + " = " + conversation_message);
        }
    }
    private static final String EXTRA_IMAGE = "com.GroupView.janolaskar.easysoftchat.extraImage";
    private static final String EXTRA_TITLE = "com.GroupView.janolaskar.easysoftchat.extraTitle";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                sendTextMessage(v);
                break;
            case R.id.avatar:
            case R.id.title:
                ViewModel mdl = new ViewModel(String.valueOf(title.getText()),String.valueOf(R.drawable.group_profile));
                Intent intent = new Intent(this, GroupView.class);
                intent.putExtra(EXTRA_IMAGE, mdl.getImage());
                intent.putExtra(EXTRA_TITLE,mdl.getText());
                getApplicationContext().startActivity(intent);
        }
    }

    public void change_actionbar(String Title) {
        this.getSupportActionBar().setTitle(Title);
    }

    private void prepareChatData() {
//        ArrayList<GroupChatHolder> arC = new GroupChatDB(getApplicationContext()).getRecords(null);
        ArrayList<GroupChatHolder> arC = new GroupChatDB(getApplicationContext()).getRecords("(" + GroupChatHolder.FIELD_PERSON + " = 20002 AND " + GroupChatHolder.FIELD_GROUP + " = " + person_group + ") OR (" + GroupChatHolder.FIELD_GROUP + " = " + person_group + ")", GroupChatHolder.FIELD_WHEN);
        Log.e(TAG, String.valueOf(arC.size()));
//        Log.e(TAG, arC.toString());

        for (GroupChatHolder cholder : arC) {
            Date date = null;
            try {
                ArrayList<PersonHolder> arCh = new PersonDB(getApplicationContext()).getRecords(PersonHolder.FIELD__ID + " = " + cholder.person, null);
                Log.e(TAG, String.valueOf(arC.size()));
                for (PersonHolder pholder : arCh) {
                    cholder.senderName = pholder.name;
                }
                date = new SimpleDateFormat("d/MMM/yyyy HH:mm:ss", Locale.ENGLISH).parse(cholder.when);
                cholder.Time = new SimpleDateFormat("HH:mm").format(date);
                cholder.Date = new SimpleDateFormat("d/MMM/yyyy").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (cholder.person == 20002) {
                cholder.setMine(true);
            } else {
                cholder.setMine(false);
            }
            Log.e(TAG, String.valueOf(cholder.getPerson()));
            cholder.setImage(getResources().getDrawable(R.drawable.friends_profile));
            chatAdapter.add(cholder);
        }
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
