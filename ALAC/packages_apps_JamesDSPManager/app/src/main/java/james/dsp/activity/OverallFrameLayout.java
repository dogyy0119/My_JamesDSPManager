package james.dsp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;

@SuppressLint("ValidFragment")
public class OverallFrameLayout extends Fragment {
    private SharedPreferences preferencesMode;
    private SharedPreferences preferences;

    private String name;
    private String route;
    private EqualizerSurface mListEqualizer;

    public OverallFrameLayout(String name,String route) {
        this.name = name;
        this.route = route;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPrefecence();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.overall_frame, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Liuhang", "OverallFrameLayout: onStart");

        mListEqualizer = (EqualizerSurface) getView().findViewById(R.id.all_equalizer_surface);
        if (mListEqualizer != null) {
            mListEqualizer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itent = new Intent();
                    itent.putExtra(MainActivity.ACTIVITY_REQUEST, name);
                    itent.putExtra(MainActivity.ACTIVITY_ROUTE, route);
                    itent.setClass(getContext(), EqualizerActivity.class);
                    startActivity(itent);
                }
            });
        }

        setSwitchFromPreference();
        initEqualizerValue();
        registerClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Liuhang", "OverallFrameLayout: " + " onResume");
        setSwitchFromPreference();
        initEqualizerValue();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Liuhang", "OverallFrameLayout: " + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Liuhang", "OverallFrameLayout: " + " onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Liuhang", "OverallFrameLayout: " + " onDestroy");
    }

    public void initEqualizerValue() {
        String value = preferencesMode.getString(MainActivity.AUDIO_EQUALIZER_ARRAY, null);

        if (mListEqualizer != null) {
            if (value != null && mListEqualizer != null) {
                String[] levelsStr = value.split(";");
                for (int i = 0; i < EqualizerSurface.mNumLevels; i++)
                    mListEqualizer.setBand(i, Float.valueOf(levelsStr[i]));
            }
        }
    }

    private void initPrefecence() {
        preferencesMode = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_EQUALIZER + "." + name + "." + "settings", 0);
        preferences = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_BASENAME + "." + route, 0);
    }

    private void registerClickListener() {
        Switch sit = getView().findViewById(R.id.switch_btn);
        if (sit != null) {
            sit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setSwitchPreference( isChecked );
                }
            });
        }

        Button btn = getView().findViewById(R.id.save_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.save_as_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.stand_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.surround_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.cinema_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.little_concert_hall_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.middle_concert_hall_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.large_concert_hall_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.stand_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.self_define_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.jazz_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.popular_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.classic_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.hip_hop_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
    }

    View.OnClickListener overrideListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.save_btn: {
                    break;
                }
                case R.id.save_as_btn: {
                    break;
                }

                case R.id.stand_btn: {
                    break;
                }
                case R.id.surround_btn: {

                    break;
                }
                case R.id.cinema_btn: {
                    break;
                }

                case R.id.little_concert_hall_btn: {
                    break;
                }

                case R.id.middle_concert_hall_btn: {
                    break;
                }

                case R.id.large_concert_hall_btn: {
                    break;
                }

                case R.id.stand_music_btn: {
                    break;
                }
                case R.id.self_define_music_btn: {
                    break;
                }

                case R.id.jazz_music_btn: {
                    break;
                }
                case R.id.popular_music_btn: {
                    break;
                }
                case R.id.classic_music_btn: {
                    break;
                }

                case R.id.hip_hop_music_btn: {
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void setSwitchFromPreference() {
        Boolean use = preferences.getBoolean("dsp.masterswitch.enable", false);
        Switch sit = getView().findViewById(R.id.switch_btn);
        sit.setChecked( use );
    }

    private void setSwitchPreference( Boolean bool ) {
        Log.e("Liuhang", "EqualizerActivity:" + "putBoolean:" + bool);
        preferences.edit().putBoolean("dsp.masterswitch.enable", bool).commit();
        sendBroadCase();
    }

    /*
     * 切换均衡器
     */
    private void changeEqualizerFromPreference( String style ) {

    }

    /*
     * 切换声场
     */
    private void changeAcousticFromPreference( String acoustic ) {

    }

    private void sendBroadCase() {
        getActivity().sendBroadcast(new Intent(DSPManager.ACTION_UPDATE_PREFERENCES));
    }
}
