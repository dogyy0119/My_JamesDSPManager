package james.dsp.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;
import james.dsp.service.HeadsetService;

public class EqualizerActivity extends Activity {

    private SharedPreferences preferencesMode;
    private SharedPreferences preferences;

    private String mCurrentAcoustic = "";
    public String name ;
    public String route ;
    private EqualizerSurface mDialogEqualizer;
    private HeadsetService mHeadsetService;


    private final ServiceConnection connectionForDialog = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mHeadsetService = ((HeadsetService.LocalBinder) binder).getService();
            Log.e("Liuhang", "EqualizerActivity:" + "ServiceConnection:" + " onServiceConnected");
            updateDspFromDialogEqualizer();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mHeadsetService = null;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.ACTIVITY_REQUEST);
        route = intent.getStringExtra(MainActivity.ACTIVITY_ROUTE);

        initPrefecence();

        setContentView(R.layout.custom_equalizer);

        mDialogEqualizer = (EqualizerSurface) super.findViewById(R.id.MyFrequencyResponse);
        mDialogEqualizer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.e("Liuhang", "EqualizerActivity:" + "onBindDialogView:" + " onTouch");
                float x = event.getX();
                float y = event.getY();
                //Log.e("Liuhang", "EqualizerActivity:" + "x:" + x + " y:" + y);
                /* Which band is closest to the position user pressed? */
                int band = mDialogEqualizer.findClosest(x);
                int wy = v.getHeight();
                float level = (y / wy) * (EqualizerSurface.MIN_DB - EqualizerSurface.MAX_DB) - EqualizerSurface.MIN_DB;
                //Log.e("Liuhang", "EqualizerActivity:" + "band:" + band + " wy:" + wy + " level:" + level);


                if (level < EqualizerSurface.MIN_DB)
                    level = EqualizerSurface.MIN_DB;
                if (level > EqualizerSurface.MAX_DB)
                    level = EqualizerSurface.MAX_DB;
                mDialogEqualizer.setBand(band, level);
                updateDspFromDialogEqualizer();

                sendBroadcast(new Intent(MainActivity.ACTION_UPDATE_PREFERENCES));
                return true;
            }
        });

        updateListEqualizerFromValue();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Liuhang", "EqualizerActivity:" + "onStart:" + "");
        EqualizerSurface surfaceview = findViewById(R.id.FrequencyResponse);
        if (surfaceview != null)
            surfaceview.setZOrderOnTop(true);

        getApplicationContext().bindService(new Intent(getApplicationContext(), HeadsetService.class), connectionForDialog, 0);

        registerClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveListEqualizer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Liuhang", "EqualizerActivity:" + "onStop:" + "");

        saveListEqualizer();
        if (mHeadsetService != null)
            mHeadsetService.setEqualizerLevels(null);
        getApplicationContext().unbindService(connectionForDialog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initPrefecence() {
        preferencesMode = getSharedPreferences(MainActivity.SHARED_PREFERENCES_EQUALIZER + "." + name + "." + "settings", 0);
        preferences = getSharedPreferences(MainActivity.SHARED_PREFERENCES_BASENAME + "." + route, 0);
    }

    protected void updateDspFromDialogEqualizer() {
//        Log.e("Liuhang", "EqualizerActivity:" + "updateDspFromDialogEqualizer:");
        if (mHeadsetService != null) {
   //         Log.e("Liuhang", "EqualizerActivity:" + "updateDspFromDialogEqualizer:");
            float[] levels = new float[EqualizerSurface.mNumLevels];
            for (int i = 0; i < levels.length; i++)
                levels[i] = mDialogEqualizer.getBand(i);
            mHeadsetService.setEqualizerLevels(levels);
        }
    }

    private void updateListEqualizerFromValue() {
        String value = preferencesMode.getString(MainActivity.AUDIO_EQUALIZER_ARRAY, null);
    //    Log.e("Liuhang", "EqualizerActivity:" + "updateListEqualizerFromValue:" + value);

        if (value != null && mDialogEqualizer != null) {
            String[] levelsStr = value.split(";");
            for (int i = 0; i < EqualizerSurface.mNumLevels; i++)
                mDialogEqualizer.setBand(i, Float.valueOf(levelsStr[i]));
        }
    }

    private void saveListEqualizer() {
        String values = "";
        for (int i = 0; i < EqualizerSurface.mNumLevels; i++) {
            values += Float.toString(mDialogEqualizer.getBand(i)) + ";";
        }
        Log.e("Liuhang", "EqualizerActivity:" + "saveListEqualizer。" + values);
        preferencesMode.edit().putString(MainActivity.AUDIO_EQUALIZER_ARRAY, values).commit();
    }

    private void changeAcousticFile() {
        String fileName = MainActivity.ACOUSTIC_FILE_BASE  + mCurrentAcoustic;
        SharedPreferences preferences = getSharedPreferences( fileName, 0);
        String value = preferences.getString(MainActivity.AUDIO_EQUALIZER_ARRAY, null);
        Log.e("Liuhang", "EqualizerActivity:" + "updateListEqualizerFromValue:" + value);

        if (value != null && mDialogEqualizer != null) {
            String[] levelsStr = value.split(";");
            for (int i = 0; i < EqualizerSurface.mNumLevels; i++)
                mDialogEqualizer.setBand(i, Float.valueOf(levelsStr[i]));
        }
    }

    private void saveAcousticFile() {
        String values = "";
        for (int i = 0; i < EqualizerSurface.mNumLevels; i++) {
            values += Float.toString(mDialogEqualizer.getBand(i)) + ";";
        }
        String fileName = MainActivity.ACOUSTIC_FILE_BASE  + mCurrentAcoustic;
        SharedPreferences preferences = getSharedPreferences( fileName, 0);
        preferences.edit().putString(MainActivity.AUDIO_EQUALIZER_ARRAY, values).commit();
    }

    private void registerClickListener() {
        Button btn = findViewById(R.id.customize_1_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
        btn = findViewById(R.id.customize_2_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
        btn = findViewById(R.id.customize_3_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
        btn = findViewById(R.id.customize_4_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
        btn = findViewById(R.id.customize_5_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
        btn = findViewById(R.id.customize_6_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = findViewById(R.id.equalizer_save_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
        btn = findViewById(R.id.equalizer_unsave_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
    }

    View.OnClickListener overrideListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.customize_1_btn: {
                    mCurrentAcoustic = "custom_1";
                    changeAcousticFile();
                    break;
                }
                case R.id.customize_2_btn: {
                    mCurrentAcoustic = "custom_2";
                    changeAcousticFile();
                    break;
                }
                case R.id.customize_3_btn: {
                    mCurrentAcoustic = "custom_3";
                    changeAcousticFile();
                    break;
                }
                case R.id.customize_4_btn: {
                    mCurrentAcoustic = "custom_4";
                    changeAcousticFile();
                    break;
                }
                case R.id.customize_5_btn: {
                    mCurrentAcoustic = "custom_5";
                    changeAcousticFile();
                    break;
                }
                case R.id.customize_6_btn: {
                    mCurrentAcoustic = "custom_6";
                    changeAcousticFile();
                    break;
                }
                case R.id.equalizer_save_btn: {
                    saveAcousticFile();
                    break;
                }

                case R.id.equalizer_unsave_btn: {
                    finish();
                    break;
                }
                default:
                    break;

            }

        }
    };

}
