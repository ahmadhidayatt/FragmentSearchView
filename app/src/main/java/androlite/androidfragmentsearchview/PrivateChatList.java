    package androlite.androidfragmentsearchview;

    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.MenuItem;
    import android.view.View;
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
    import java.util.Random;

    /**
     * Created by ahmad on 06/09/17.
     */

    public class PrivateChatList extends AppCompatActivity implements View.OnClickListener {
        private static final String TAG = "PrivateChatList";
        private EditText msg_edittext;
        private Random random;
        private CircularImageView avatar;
        private TextView title;
        private ImageView image_back;
        public static ArrayList<PrivateChatHolder> chatlist;
        public static PrivateChatAdapter privateChatAdapter;
        ListView msgListView;
        private Toolbar toolbar;

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
                } else {
                    name = extras.getString("name");
                }
            } else {
                name = (String) savedInstanceState.getSerializable("name");
            }
            setContentView(R.layout.fragment_chat);

            random = new Random();

    //        getSupportActionBar().setTitle(
    //                Html.fromHtml("<font color=\"black\">" + name +
    //                        "</font>"));

            msg_edittext = (EditText) findViewById(R.id.messageEditText);
            msgListView = (ListView) findViewById(R.id.msgListView);
            ImageButton sendButton = (ImageButton) findViewById(R.id.sendMessageButton);
            sendButton.setOnClickListener(this);

            // ----Set autoscroll of listview when a new message arrives----//
            msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            msgListView.setStackFromBottom(true);

            chatlist = new ArrayList<PrivateChatHolder>();
            privateChatAdapter = new PrivateChatAdapter(this, chatlist);
            msgListView.setAdapter(privateChatAdapter);
            prepareChatData();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            avatar = toolbar.findViewById(R.id.avatar);

            title = toolbar.findViewById(R.id.title);
            title.setText(name);


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
                Log.e(TAG, chatMessage.when);
                chatMessage.Date = CommonMethods.getCurrentDate();
                chatMessage.Time = CommonMethods.getCurrentTime();
                msg_edittext.setText("");
                privateChatAdapter.add(chatMessage);
                privateChatAdapter.notifyDataSetChanged();
                PrivateChatDB dbss = new PrivateChatDB(getApplicationContext());
                Log.e(TAG, String.valueOf(chatMessage.person + " " + chatMessage.friend));

                PrivateChatHolder holders = new PrivateChatHolder();
                holders.setPerson(20002);
                holders.setFriend(20001);
                holders.setMessage(message.toString());
                holders.setWhen(CommonMethods.getFullCurrentDate());
                holders.setDelivered(CommonMethods.getCurrentDate());
                holders.setRead(CommonMethods.getCurrentDate());
                holders.setMine(true);

                insert5 = dbss.insertRecord(holders.toCommValues(), false);
                Log.e(TAG, String.valueOf(insert5 + " " + holders.toCommValues()));
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
            ArrayList<PrivateChatHolder> arC = new PrivateChatDB(getApplicationContext()).getRecords("("+PrivateChatHolder.FIELD_PERSON+" = 20002 AND "+PrivateChatHolder.FIELD_FRIEND+" = 20001) OR ("+PrivateChatHolder.FIELD_PERSON+" = 20001 AND "+PrivateChatHolder.FIELD_FRIEND+" = 20002)",PrivateChatHolder.FIELD_WHEN);
            Log.e(TAG, String.valueOf(arC.size()));
            for (PrivateChatHolder cholder : arC) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("d/MMM/yyyy HH:mm:ss", Locale.ENGLISH).parse(cholder.when);
                    cholder.Time = new SimpleDateFormat("HH:mm").format(date);
                    cholder.Date = new SimpleDateFormat("d/MMM/yyyy").format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

    //            PrivateChatHolder PrivateChatHolder = new PrivateChatDB(getApplicationContext()).getRecords(PrivateChatHolder.FIELD_FRIEND + " = '20001' "
    //                            + "AND " + PrivateChatHolder.FIELD_PERSON + " = '20002' ",
    //                    PrivateChatHolder.FIELD_WHEN, PrivateChatHolder.FIELD_WHEN, null);
                //   PrivateChatHolder privateChatHolder =  new PrivateChatDB(getApplicationContext()).getRecord(PrivateChatHolder.FIELD__ID + "=" + cholder.chat_group_message);
                Log.e(TAG, String.valueOf(cholder.getPerson()));
                privateChatAdapter.add(cholder);
            }
          new PrivateChatDB(getApplicationContext()).execQuery("SELECT *" +
                    "FROM CHAT_DB " +
                    "WHERE person = 20002 AND (person = 20001 and person = 20002)");
    //            WoHolder objConv = new WoHolder(cholder._id+"", cholder.person_group+"", cholder.unread_counter+"");


            privateChatAdapter.notifyDataSetChanged();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home :
                    this.finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }
