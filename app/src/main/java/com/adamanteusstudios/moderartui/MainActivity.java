package com.adamanteusstudios.moderartui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 *Hello from Atlanta, GA,
 *
 * My name is Frank Lamar and I am a student at Kennesaw State University. I took this course on
 * coursera to supplemnt my instruction at school. This App displays 5 boxes. When the app launches
 * there are two yellow boxes, two blue boxes, and one white box. There is a slider at the bottom of the screen.
 * When the slider moves the boxes change colors. The actionbar has one setting. Tapping this setting
 * launches a dialog about the Museum of Modern Art. This gives the user the option to visit the
 * MoMa website or dismiss the dialog and return back to the Main activity. Thanks for taking the
 * time to look at my work and good luck on yours.
 *
 */


public class MainActivity extends ActionBarActivity {

    private SeekBar colorSlider;
    String TAG = "ModernArt";// for debugging;

    MediaPlayer mp;//plays sound


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup slider sound
        mp = MediaPlayer.create(this, R.raw.electro_loop);
        mp.setLooping(true);


        //attaching boxes
        final ImageView box1 = (ImageView) findViewById(R.id.box1);
        final ImageView box2 = (ImageView) findViewById(R.id.box2);
        final ImageView box3 = (ImageView) findViewById(R.id.box3);
        final ImageView box4 = (ImageView) findViewById(R.id.box4);
        final ImageView box5 = (ImageView) findViewById(R.id.box5);
        final ImageView box6 = (ImageView) findViewById(R.id.box6);


        //Log.v(TAG, colorOfBox1 + ""); for debugging

        colorSlider = (SeekBar) findViewById(R.id.seekBar);
        colorSlider.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){


            //tracks slider movement
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
                progressChanged = progressChanged * 1000;

                //add to the int color value of each box which results in the colors changing
                box1.setBackgroundColor(Color.BLUE + progressChanged);
                box2.setBackgroundColor(Color.YELLOW - progressChanged);
                box3.setBackgroundColor(Color.YELLOW - progressChanged);
                box4.setBackgroundColor(Color.BLUE + progressChanged);
                box5.setBackgroundColor(Color.GRAY + progressChanged);


                //show Coursera Logo while slider is active
                box6.setMaxHeight(50);
                box6.setMaxWidth(50);
                box6.setVisibility(View.VISIBLE);

                //Starting media player while slider is active
                mp.start();

            }

            public void onStartTrackingTouch(SeekBar seekBar){

            };

            public void onStopTrackingTouch(SeekBar seekBar){

                //removes coursera logo when slider becomes inactive
                box6.setVisibility(View.INVISIBLE);

                //pauses music when slider becomes inactive
                mp.pause();

            };

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //setting up the Dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("MoMa is the most influential museum in the world. Our collection" +
                    " includes: Architecture, Design, Sculpture, Photography, Film, and" +
                    " Electronic Media.");
            alert.setTitle("Museum Of Modern Art");
            //setting action for Negative button to direct user to MoMA.org site
            alert.setNegativeButton("Visit MoMA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent MoMAIntent = new Intent();
                    MoMAIntent.setAction(Intent.ACTION_VIEW);
                    MoMAIntent.setData(Uri.parse("http://www.moma.org/"));
                    startActivity(MoMAIntent);
                }
            });
            //setting action for Positive button to clear the dialog
            alert.setPositiveButton("Not Now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Intent dismissIntent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(dismissIntent);
                }
            });

            //using the builder to create the actual alert dialog
            AlertDialog alertDialog = alert.create();
            alertDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
