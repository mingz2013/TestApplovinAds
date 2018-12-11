package com.xiuxayo.numbers_blast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinAdSize;

public class MainActivity extends AppCompatActivity
        implements AppLovinAdLoadListener, AppLovinAdDisplayListener, AppLovinAdClickListener, AppLovinAdVideoPlaybackListener
{


    private AppLovinInterstitialAdDialog interstitialAd;
    private Button showButton;
    private Button loadingButton;

    private AppLovinAd loadedAd;
    private AppLovinAd                   currentAd;

    String TAG = "MainActivity";

    protected void log(final String message)
    {
//        if ( adStatusTextView != null )
//        {
//            runOnUiThread( new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    adStatusTextView.setText( message );
//                }
//            } );
//        }
//        System.out.println( message );
        Log.d(TAG, "log: " + message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppLovinSdk.initializeSdk(getApplicationContext());
        AppLovinSdk.getInstance( getApplicationContext() ).getSettings().setTestAdsEnabled( true );



        interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( this ), this );


        loadingButton = (Button) findViewById(R.id.loadingbutton);


        loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log( "Interstitial loading..." );
                showButton.setEnabled( false );


                AppLovinSdk.getInstance( getApplicationContext() ).getAdService().loadNextAd( AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener()
                {
                    @Override
                    public void adReceived(AppLovinAd ad)
                    {
                        log( "Interstitial Loaded" );

                        currentAd = ad;

                        runOnUiThread( new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                showButton.setEnabled( true );
                            }
                        } );
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode)
                    {
                        // Look at AppLovinErrorCodes.java for list of error codes
                        log( "Interstitial failed to load with error code " + errorCode );
                    }
                } );
            }
        });
//        // Load an Interstitial Ad
//        AppLovinSdk.getInstance( this ).getAdService().loadNextAd( AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener()
//        {
//            @Override
//            public void adReceived(AppLovinAd ad)
//            {
//                loadedAd = ad;
//            }
//
//            @Override
//            public void failedToReceiveAd(int errorCode)
//            {
//                // Look at AppLovinErrorCodes.java for list of error codes.
//            }
//        } );




        showButton = (Button) findViewById( R.id.showbutton );
        showButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showButton.setEnabled( false );

                log( "Showing..." );

                //
                // Optional: Set ad load, ad display, ad click, and ad video playback callback listeners
                //

//                interstitialAd.show();
//                interstitialAd.showAndRender(loadedAd);
                interstitialAd.showAndRender( currentAd );
            }
        } );


        //                interstitialAd.setAdLoadListener( MainActivity.this );
        interstitialAd.setAdDisplayListener( MainActivity.this );
        interstitialAd.setAdClickListener( MainActivity.this );
        interstitialAd.setAdVideoPlaybackListener( MainActivity.this ); // This will only ever be used if you have video ads enabled.




    }



    //
    // Ad Load Listener
    //
    @Override
    public void adReceived(AppLovinAd appLovinAd)
    {
        log( "Interstitial loaded" );
        showButton.setEnabled( true );
    }

    @Override
    public void failedToReceiveAd(int errorCode)
    {
        // Look at AppLovinErrorCodes.java for list of error codes
        log( "Interstitial failed to load with error code " + errorCode );

        showButton.setEnabled( true );
    }

    //
    // Ad Display Listener
    //
    @Override
    public void adDisplayed(AppLovinAd appLovinAd)
    {
        log( "Interstitial Displayed" );
    }

    @Override
    public void adHidden(AppLovinAd appLovinAd)
    {
        log( "Interstitial Hidden" );
    }

    //
    // Ad Click Listener
    //
    @Override
    public void adClicked(AppLovinAd appLovinAd)
    {
        log( "Interstitial Clicked" );
    }

    //
    // Ad Video Playback Listener
    //
    @Override
    public void videoPlaybackBegan(AppLovinAd appLovinAd)
    {
        log( "Video Started" );
    }

    @Override
    public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed)
    {
        log( "Video Ended" );
    }
}
