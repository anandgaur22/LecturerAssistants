package anandgaur.com.lecturerassistants;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Student_Registartion extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    private AdView mAdView;
    Activity activity = this;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__registartion);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AppBase.divisions);
        spinner.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.buttonSAVE);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase(v);
            }
        });

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = (AdView) findViewById(R.id.adView);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_student_reg));

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

        });

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

    }


    public void saveToDatabase(View view) {
        EditText name = (EditText)findViewById(R.id.edit_name);
        EditText roll = (EditText)findViewById(R.id.roll);
        EditText register = (EditText)findViewById(R.id.register);
        EditText contact = (EditText)findViewById(R.id.contact);
        String classSelected = spinner.getSelectedItem().toString();

        if(name.getText().length()<2||roll.getText().length()==0||register.getText().length()<2||
                contact.getText().length()<2||classSelected.length()<2)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle("Invalid");
            alert.setMessage("Insufficient Data");
            alert.setPositiveButton("OK", null);
            alert.show();
            return;
        }

        String qu = "INSERT INTO STUDENT VALUES('" +name.getText().toString()+ "'," +
                "'" + classSelected +"',"+
                "'" + register.getText().toString().toUpperCase() +"',"+
                "'" + contact.getText().toString() +"',"+
                "" + Integer.parseInt(roll.getText().toString()) +");";
        Log.d("Student Reg" , qu);
        AppBase.handler.execAction(qu);
        qu = "SELECT * FROM STUDENT WHERE regno = '" + register.getText().toString() +  "';";
        Log.d("Student Reg" , qu);
        if(AppBase.handler.execQuery(qu)!=null)
        {
            Toast.makeText(getBaseContext(),"Student Added", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
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
