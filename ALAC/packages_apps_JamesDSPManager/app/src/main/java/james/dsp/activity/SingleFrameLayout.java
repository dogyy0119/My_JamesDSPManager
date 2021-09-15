package james.dsp.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.zip.Inflater;

import james.dsp.R;
import james.dsp.preference.EqualizerSurface;


@SuppressLint("ValidFragment")
public class SingleFrameLayout extends Fragment {
    private SharedPreferences preferencesMode;
    private SharedPreferences preferences;

    private String name;
    private String route;
    private EqualizerSurface mListEqualizer;
    private int delay_time;
    private int pregain;
    private int high_pass;
    private int low_pass;

    public SingleFrameLayout(String name, String route) {
        this.name = name;
        this.route = route;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPrefecence();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_framelayout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mListEqualizer = (EqualizerSurface) getView().findViewById(R.id.one_equalizer_surface);
        if (mListEqualizer != null) {
            Log.e("Liuhang", " mListEqualizer all_equalizer_surface ! = null");
            mListEqualizer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itent = new Intent();
                    itent.putExtra(MainActivity.ACTIVITY_REQUEST, name);
                    itent.setClass(getContext(), EqualizerActivity.class);
                    startActivity(itent);
                }
            });
        }

        initEqualizerValue();
        setEditTextFromPreference();
        registerClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Liuhang", "SingleFrameLayout: " + " onResume");

        initEqualizerValue();
        setEditTextFromPreference();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Liuhang", "SingleFrameLayout: " + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Liuhang", "SingleFrameLayout: " + " onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Liuhang", "SingleFrameLayout: " + " onDestroy");
    }

    public void initEqualizerValue() {
        String value = preferencesMode.getString(MainActivity.AUDIO_EQUALIZER_ARRAY, null);
        Log.e("Liuhang", "SingleFrameLayout: initEqualizerValue" + value);
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
        EditText eText = getView().findViewById(R.id.delay_time_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.pregain_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.high_pass_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.low_pass_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        Button btn = getView().findViewById(R.id.sig_stand_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.sig_self_define_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.sig_jazz_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.sig_popular_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.sig_classic_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);

        btn = getView().findViewById(R.id.sig_hip_hop_music_btn);
        if (btn != null)
            btn.setOnClickListener(overrideListener);
    }

    View.OnClickListener overrideListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sig_stand_music_btn: {
                    break;
                }

                case R.id.sig_self_define_music_btn: {
                    break;
                }

                case R.id.sig_jazz_music_btn: {

                    break;
                }
                case R.id.sig_popular_music_btn: {
                    break;
                }
                case R.id.sig_classic_music_btn: {
                    break;
                }

                case R.id.sig_hip_hop_music_btn: {
                    break;
                }

                default:
                    break;
            }
        }
    };

    TextView.OnFocusChangeListener listen = new TextView.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
             * has valid values.
             */
            if (!hasFocus) {
                switch (v.getId()) {
                    case R.id.delay_time_editText: {
                        EditText eText = getView().findViewById(R.id.delay_time_editText);
                        try {
                            saveDelayTimePreference();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.pregain_editText: {
                        EditText eText = getView().findViewById(R.id.pregain_editText);
                        try {
                            savePreGainPreference();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.high_pass_editText: {
                        EditText eText = getView().findViewById(R.id.high_pass_editText);
                        try {
                            high_pass = Integer.valueOf(eText.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.low_pass_editText: {
                        EditText eText = getView().findViewById(R.id.low_pass_editText);
                        try {
                            low_pass = Integer.valueOf(eText.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    default:
                        break;
                }
            }
        }
    };

    private void setEditTextFromPreference() {
        delay_time = preferencesMode.getInt("delay_time_editText", 30);
        EditText eText = getView().findViewById(R.id.delay_time_editText);
        eText.setText( Integer.toString( delay_time ) );

        pregain = preferencesMode.getInt("pregain_editText", 30);
        eText = getView().findViewById(R.id.pregain_editText);
        eText.setText( Integer.toString( pregain ) );

        high_pass = preferencesMode.getInt("high_pass_editText", 30);
        eText = getView().findViewById(R.id.high_pass_editText);
        eText.setText( Integer.toString( high_pass ) );

        low_pass = preferencesMode.getInt("low_pass_editText", 30);
        eText = getView().findViewById(R.id.low_pass_editText);
        eText.setText( Integer.toString( low_pass ) );
    }


    private void savePreGainPreference() {
        EditText eText = getView().findViewById(R.id.pregain_editText);
        String gain = eText.getText().toString() ;

        SharedPreferences advancedPref = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_BASENAME + "." + "advanced" + "." + route, 0);
        advancedPref.edit().putInt("dsp.compression.pregain", Integer.valueOf( gain )).commit();
    }

    private void saveDelayTimePreference() {
        EditText eText = getView().findViewById(R.id.delay_time_editText);
        String gain = eText.getText().toString() ;

        SharedPreferences advancedPref = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_BASENAME + "." + "advanced" + "." + route, 0);
        advancedPref.edit().putInt("dsp.compression.delay", Integer.valueOf( gain )).commit();
    }

}