package james.dsp.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import james.dsp.R;


@SuppressLint("ValidFragment")
public class TimeDelayFrameLayout extends Fragment {

    private SharedPreferences preferencesMode;
    private SharedPreferences preferences;

    private String name;
    private String route;
    private int left_front_delay;
    private int left_mid_delay;
    private int left_last_delay;
    private int right_front_delay;
    private int right_mid_delay;
    private int right_last_delay;

    public TimeDelayFrameLayout(String name, String rotue) {
        this.name = name;
        this.route = rotue;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPrefecence();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_delay_framelayout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        setEditTextFromPreference();
        registerClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Liuhang", "SingleFrameLayout: " + " onResume");
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

    private void registerClickListener() {
        EditText eText = getView().findViewById(R.id.left_front_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.left_mid_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.left_last_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.right_front_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.right_mid_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);

        eText = getView().findViewById(R.id.right_last_editText);
        if (eText != null)
            eText.setOnFocusChangeListener(listen);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i("liuhang", "输入文字中的状态，count是输入字符数");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            Log.i("liuhang", "输入文本之前的状态");
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i("liuhang", "输入文字后的状态");

        }
    };

    private void initPrefecence() {
        preferencesMode = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_EQUALIZER + "." + name + "." + "settings", 0);
        preferences = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_BASENAME + "." + route, 0);
    }

    TextView.OnEditorActionListener listener = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (!event.isShiftPressed()) {
                    // the user is done typing.

                }
            }
            return false;
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
                    case R.id.left_front_editText: {
                        EditText eText = getView().findViewById(R.id.left_front_editText);
                        try {
                            left_front_delay = Integer.valueOf(eText.getText().toString());
                            Log.e("Liuhang", "TimeDelayFrameLayout: " + " OnFocusChangeListener， left_front_delay" + left_front_delay);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.right_front_editText: {
                        EditText eText = getView().findViewById(R.id.right_front_editText);
                        try {
                            right_front_delay = Integer.valueOf(eText.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.left_mid_editText: {
                        EditText eText = getView().findViewById(R.id.left_mid_editText);
                        try {
                            left_mid_delay = Integer.valueOf(eText.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.right_mid_editText: {
                        EditText eText = getView().findViewById(R.id.right_mid_editText);
                        try {
                            right_mid_delay = Integer.valueOf(eText.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.left_last_editText: {
                        EditText eText = getView().findViewById(R.id.left_last_editText);
                        try {
                            left_last_delay = Integer.valueOf(eText.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case R.id.right_last_editText: {
                        EditText eText = getView().findViewById(R.id.right_last_editText);
                        try {
                            right_last_delay = Integer.valueOf(eText.getText().toString());
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
        left_front_delay = preferencesMode.getInt("left_front_editText", 30);
        EditText eText = getView().findViewById(R.id.left_front_editText);
        eText.setText( Integer.toString( left_front_delay ) );

        left_mid_delay = preferencesMode.getInt("left_mid_editText", 30);
        eText = getView().findViewById(R.id.left_mid_editText);
        eText.setText( Integer.toString( left_mid_delay ) );

        left_last_delay = preferencesMode.getInt("left_last_editText", 30);
        eText = getView().findViewById(R.id.left_last_editText);
        eText.setText( Integer.toString( left_last_delay ) );

        right_front_delay = preferencesMode.getInt("right_front_editText", 30);
        eText = getView().findViewById(R.id.right_front_editText);
        eText.setText( Integer.toString( right_front_delay ) );

        right_mid_delay = preferencesMode.getInt("right_mid_editText", 30);
        eText = getView().findViewById(R.id.right_mid_editText);
        eText.setText( Integer.toString( right_mid_delay ) );

        right_last_delay = preferencesMode.getInt("right_last_editText", 30);
        eText = getView().findViewById(R.id.right_last_editText);
        eText.setText( Integer.toString( right_last_delay ) );
    }
}