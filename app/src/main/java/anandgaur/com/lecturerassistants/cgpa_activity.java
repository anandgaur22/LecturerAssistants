package anandgaur.com.lecturerassistants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class cgpa_activity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    private AdView mAdView;
    EditText c1, c2, c3, c4, c5, c6;
    Button b3;
    float sg1, sg2, sg3, sg4, sg5, sg6, cg;
    int g1 = 44, g2 = 28, g3 = 28, g4 = 28, g5 = 28, g6 = 28;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa);
        c1 = (EditText) findViewById(R.id.c1);
        c2 = (EditText) findViewById(R.id.c2);
        c3 = (EditText) findViewById(R.id.c3);
        c4 = (EditText) findViewById(R.id.c4);
        c5 = (EditText) findViewById(R.id.c5);
        c6 = (EditText) findViewById(R.id.c6);
        b3 = (Button) findViewById(R.id.b3);
        //  TextView t=(TextView)findViewById(R.id.tv);
//            t.setVisibility(View.INVISIBLE);


        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sg1 = read(c1);
                sg2 = read(c2);
                sg3 = read(c3);
                sg4 = read(c4);
                sg5 = read(c5);
                sg6 = read(c6);

                if (sg1 == 0)
                    g1 = 0;

                if (sg2 == 0)
                    g2 = 0;

                if (sg3 == 0)
                    g3 = 0;

                if (sg4 == 0)
                    g4 = 0;

                if (sg5 == 0)
                    g5 = 0;

                if (sg6 == 0)
                    g6 = 0;


                cg = ((sg1 * g1) + (sg2 * g2) + (sg3 * g3) + (sg4 * g4) + (sg5 * g5) + (sg6 * g6)) / (g1 + g2 + g3 + g4 + g5 + g6);
                if (sg1 == 0 && sg2 == 0 && sg3 == 0 && sg4 == 0 && sg5 == 0 && sg6 == 0)
                    Toast.makeText(getApplicationContext(), "Insufficient Data ", Toast.LENGTH_LONG).show();

                else if ((sg1 > 10.0) || sg2 > 10.0 || sg3 > 10.0 || sg4 > 10.0 || sg5 > 10.0 || sg6 > 10.0) {
                    Toast.makeText(getApplicationContext(), " Invalid CGPA", Toast.LENGTH_SHORT).show();
                } else {

                    Intent i3 = new Intent(getApplicationContext(), Result_activity.class);
                    i3.putExtra("final_sgpa", cg);
                    i3.putExtra("flag", 0);
                    i3.putExtra("final_perc", 0);
                    startActivity(i3);

                }


                // TODO Auto-generated method stub

            }


        });


        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = (AdView) findViewById(R.id.adView);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_cgpa));

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


    private float read(EditText c) {

        if (c.getText().toString().matches("")) {
            return 0;

        } else {
            return (Float.valueOf(c.getText().toString()).floatValue());
        }

        // TODO Auto-generated method stub
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
