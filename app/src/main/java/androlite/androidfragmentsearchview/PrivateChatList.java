package androlite.androidfragmentsearchview;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

public class PrivateChatList extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PrivateChatList";
    private EditText msg_edittext;
    private CircularImageView avatar;
    private TextView title;
    private ImageView image_back;
    private ImageButton sendButton;
    public static ArrayList<PrivateChatHolder> chatlist;
    public static PrivateChatAdapter privateChatAdapter;
    ListView msgListView;
    private Toolbar toolbar;
    private String person_group = "";
    private String conversation_message = "";
    private Boolean canSend = false;


    public PrivateChatList() {
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
                conversation_message = null;
                person_group=null;
            } else {
                name = extras.getString("name");
                conversation_message = extras.getString("conversation_message");
                person_group =extras.getString("person_group");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("name");
            conversation_message = (String) savedInstanceState.getSerializable("conversation_message");
            person_group=(String) savedInstanceState.getSerializable("person_group");
        }

        setContentView(R.layout.fragment_chat);

        msg_edittext = (EditText) findViewById(R.id.messageEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        sendButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);
        sendButton.setVisibility(View.GONE);


        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<PrivateChatHolder>();
        privateChatAdapter = new PrivateChatAdapter(this, chatlist);
        msgListView.setAdapter(privateChatAdapter);
        prepareChatData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        image_back = (ImageView) findViewById(R.id.back);
//        image_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        setSupportActionBar(toolbar);
        avatar = toolbar.findViewById(R.id.avatar);

        title = toolbar.findViewById(R.id.title);
        title.setText(name);

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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
        long insert5 = 0;
        if (!message.equalsIgnoreCase("")) {
            final PrivateChatHolder chatMessage = new PrivateChatHolder(20002, 20001,
                    message, true);
            chatMessage.message = message;
            chatMessage.when = CommonMethods.getFullCurrentDate();
            chatMessage.Date = CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();
            msg_edittext.setText("");
            privateChatAdapter.add(chatMessage);
            privateChatAdapter.notifyDataSetChanged();
            PrivateChatDB dbss = new PrivateChatDB(getApplicationContext());

            PrivateChatHolder holders = new PrivateChatHolder();
            holders.setPerson(20002);
            holders.setFriend(Integer.parseInt(person_group));
            holders.setMessage(message.toString());
            holders.setWhen(CommonMethods.getFullCurrentDate());
            holders.setDelivered(CommonMethods.getCurrentDate());
            holders.setRead(CommonMethods.getCurrentDate());
            holders.setMine(true);

            insert5 = dbss.insertRecord(holders.toCommValues(), false);

            PrivateChatHolder pvholder = new PrivateChatDB(getApplicationContext()).getLastRecord(null);

            if (conversation_message.length() > 0) { // Existing Conversation
                ContentValues cvalues = new ContentValues();
                cvalues.put(WoHolder.FIELD_CHAT_GROUP_MESSAGE, pvholder.id);
                new WoDB(getApplicationContext()).updateRecord(cvalues, WoHolder.FIELD__ID + " = " + conversation_message);
            } else { // New Conversation
                WoHolder cholder = new WoHolder();
                cholder.setPersonGroup(Integer.parseInt(person_group));
                cholder.setChatGroupMessage(pvholder.id);
                cholder.setFlag(0);
                cholder.setUnreadCounter(0);
                WoDB dbs = new WoDB(getApplicationContext());
                insert5 = dbs.insertRecord(cholder.toCommValues(), true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                sendTextMessage(v);

        }
    }

    public void change_actionbar(String Title) {
        this.getSupportActionBar().setTitle(Title);
    }

    private void prepareChatData() {
        ArrayList<PrivateChatHolder> arC = new PrivateChatDB(getApplicationContext()).getRecords("(" + PrivateChatHolder.FIELD_PERSON + " = 20002 AND " + PrivateChatHolder.FIELD_FRIEND + " = "+person_group+") OR (" + PrivateChatHolder.FIELD_PERSON + " = "+person_group+" AND " + PrivateChatHolder.FIELD_FRIEND + " = 20002)", PrivateChatHolder.FIELD_WHEN);
        for (PrivateChatHolder cholder : arC) {
            Date date = null;
            try {
                date = new SimpleDateFormat("d/MMM/yyyy HH:mm:ss", Locale.ENGLISH).parse(cholder.when);
                cholder.Time = new SimpleDateFormat("HH:mm").format(date);
                cholder.Date = new SimpleDateFormat("d/MMM/yyyy").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            privateChatAdapter.add(cholder);
        }
        privateChatAdapter.notifyDataSetChanged();
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
