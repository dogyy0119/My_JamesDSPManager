package james.dsp.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import james.dsp.R;

@SuppressLint("ValidFragment")
public class AcousticFieldFrameLayout extends Fragment {

    SharedPreferences preferencesMode;
    SharedPreferences preferences;

    private String name;
    private String route;

    public AcousticFieldFrameLayout(String name, String route) {
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
        return inflater.inflate(R.layout.acoustic_field_framelayout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        String value = preferencesMode.getString(MainActivity.AUDIO_EQUALIZER_ARRAY, null);

        registerClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Liuhang", "SingleFrameLayout: " + " onResume");

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

    private void initPrefecence() {
        preferencesMode = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_EQUALIZER + "." + name + "." + "settings", 0);
        preferences = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_BASENAME + "." + route, 0);
    }

    private void registerClickListener() {

        Button btn = getView().findViewById(R.id.sig_stand_music_btn);
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

                default:
                    break;
            }
        }
    };

}