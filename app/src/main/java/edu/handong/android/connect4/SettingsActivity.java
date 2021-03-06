package edu.handong.android.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private EditText player_name;
    private Spinner language;
    private ToggleButton music;
    boolean launchSounds;
    private ToggleButton sounds;
    private String player;


    SharedPreferences preferences;
    public static final String PREF = "PlayerPref";
    public static final String TEXT = "player1";
    public static final String MUSIC = "music";
    public static final String SOUNDS = "sounds";
    public static final String PREF_LANG = "pref_lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_settings);
        SoundEffect clickSound=new SoundEffect(this);
        SharedPreferences preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        launchSounds=preferences.getBoolean(SOUNDS,false);
        player_name = findViewById(R.id.playerName);
        language = findViewById(R.id.spLanguage);
        //music =  findViewById(R.id.toggle_music);
        sounds =  findViewById(R.id.toggle_sounds);
        launchSounds=preferences.getBoolean(SOUNDS,false);
        ImageButton back_Button = findViewById(R.id.back_button);
        back_Button.setOnClickListener(view -> {

            //Checking if the sounds option is set ON or OFF
            if(launchSounds){
                clickSound.playSound();
            }
            onBackPressed();
            loadPref();
        }
        );

        ImageButton save_Button = findViewById(R.id.btnSaveSettings);
        save_Button.setOnClickListener(view -> {
            if(launchSounds){
                clickSound.playSound();
            }
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            savePref();
                            loadPref();
                            onBackPressed();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage("Save the changes?").setPositiveButton(getResources().getString(R.string.Yes), dialogClickListener)
                        .setTitle("CONNECT 4")
                        .setNegativeButton(getResources().getString(R.string.No), dialogClickListener).show();
                 }
        );

        ImageButton cancel_Button = findViewById(R.id.btnCancelSettings);
        cancel_Button.setOnClickListener(view -> {
            if(launchSounds){
                clickSound.playSound();
            }
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Toast.makeText(SettingsActivity.this,"changes cancelled successfully",Toast.LENGTH_SHORT).show();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage("Cancel the changes?").setPositiveButton(getResources().getString(R.string.Yes), dialogClickListener)
                        .setTitle("CONNECT 4")
                        .setNegativeButton(getResources().getString(R.string.No), dialogClickListener).show();

                    }
        );

        sounds.setOnClickListener(view -> {
            if(launchSounds){
                clickSound.playSound();
            }
                    }
        );

        loadPref();
    }

    /***
     * This method will Load saved preferences from the previously saved SharedPreferences object instance
     */

    public void loadPref(){
        // Fetching the stored data from the SharedPreference
        SharedPreferences preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String lang;
        Locale locale;
        //Checking if we already have a preferred language selected
        if (preferences.contains(PREF_LANG)){

            lang = preferences.getString(PREF_LANG, "");
            if (lang.equals("French")){
                locale = new Locale("fr");
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                language.setSelection(1);

            }
            else if (lang.equals("Spanish")){
                locale = new Locale("es");
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                language.setSelection(2);
            }
            else{
                locale = new Locale("en");
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                language.setSelection(0);
            }

        }
        player = preferences.getString(TEXT, "");
        player_name.setText(player);

        sounds.setChecked(preferences.getBoolean(SOUNDS,false));
        }

    /***
     * This method will Save all changes related to the user settings into an object SharedPreferences
     */

    public void savePref(){
        preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean(SOUNDS,sounds.isChecked());
        editor.putString(TEXT,player_name.getText().toString());
        editor.putString(PREF_LANG, language.getSelectedItem().toString());
        editor.apply();
        Toast.makeText(SettingsActivity.this,"Settings saved successfully",Toast.LENGTH_SHORT).show();


    }








}