package anandgaur.com.lecturerassistants;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class AppBase extends AppCompatActivity {

    ArrayList<String> basicFields;
    gridAdapter adapter;
    public static ArrayList<String> divisions ;
    GridView gridView;
    public static databaseHandler handler;
    public static Activity activity;
    private AdView mAdView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mai_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        basicFields = new ArrayList<>();
        handler = new databaseHandler(this);
        activity = this;

        getSupportActionBar().show();
        divisions = new ArrayList();
        divisions.add("Computer Science");
        divisions.add("Information Technology");
        divisions.add("Electronics  Engineering");
        divisions.add("Electrical Engineering");
        divisions.add("Mechanical Engineering");
        divisions.add("Civil Engineering");
        divisions.add("Chemical Engineering");
        gridView = (GridView)findViewById(R.id.grid);
        basicFields.add("ATTENDANCE");
        basicFields.add("SCHEDULER");
        basicFields.add("NOTES");
        basicFields.add("PROFILE");
        basicFields.add("CGPA CALCULATOR");
        adapter = new gridAdapter(this,basicFields);
        gridView.setAdapter(adapter);

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

        });

    }

    public void loadSettings(MenuItem item) {
        Intent launchIntent = new Intent(this,SettingsActivity.class);
        startActivity(launchIntent);
    }

    public void loadAbout(MenuItem item) {
        Intent launchIntent = new Intent(this,About.class);
        startActivity(launchIntent);
    }

    public void loadShare(MenuItem item) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = "Lecturer Assistants  is Awesome App! \nIt is helping and saving the time of teacher for storing the attendance of the student and make schedule of teacher and creating day by day notes and calculate the cgpa of any student and storing the information of student and lots of features. \n https://play.google.com/store/apps/details?id=anandgaur.com.lecturerassistants \n I am sure you'll love it.";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Lecturer Assistants");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Share Via "));
    }

    public void loadRate(MenuItem item) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=anandgaur.com.lecturerassistants"));
        startActivity(i);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
