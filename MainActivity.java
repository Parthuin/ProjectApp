package com.example.blalonde9489.projectapp;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static int TIME_OUT = 4000;
    SharedPreferences my_preferences;
    ImageView image;
    String URL = "https://vignette.wikia.nocookie.net/leonhartimvu/images/d/d3/001_Bulbasaur_DW.png/revision/latest?cb=20131106174201";
    ProgressDialog mProgressDialog;
    private User user;
    private AppDatabase database;
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.my_preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        database = AppDatabase.getDatabase(getApplicationContext());
        image=(ImageView)findViewById(R.id.imgView2);
        listview=(ListView) findViewById(R.id.listview);
        // cleanup for testing some initial data
        // add some data
        List<User> users = database.userDao().getAllUser();
        if (users.size()==0) {
            database.userDao().addUser(new User(1, "Brandon","email@", "hello"));
            user = database.userDao().getAllUser().get(0);
            Toast.makeText(this, String.valueOf(user.id), Toast.LENGTH_SHORT).show();
            Pokemon pokemon = new Pokemon(user.id, "Charizard");
            Inventory inventory=new Inventory(user.id, "Potion");
            database.inventoryDao().addItem(inventory);
            database.pokemonDao().addPokemon(pokemon);
        }

        updateFirstUserData();


    }
    private void updateFirstUserData() {
        List<User> user = database.userDao().getAllUser();
        List<Pokemon> pokemonForUser = database.pokemonDao().findPokemonForUser(user.get(0).id);
        TextView textView = (TextView) findViewById(R.id.result);
        textView.setText("");
        //TextView caught=(TextView) findViewById(R.id.txtPokemon);
        //caught.setText("");
        if (user.size()>0){
            for(int i=0;i<pokemonForUser.size();i++) {
                textView.setText("Number of Pokemon: " + pokemonForUser.size());
                String[] values=new String[pokemonForUser.size()];;
                for (int y = 0; y <pokemonForUser.size(); y++){
                    values[y] = pokemonForUser.get(y).description;

            }

                final ArrayList<String> list = new ArrayList<String>();

                for (int x = 0; x < values.length; ++x) {
                    list.add(values[x]);
                }
                final StableArrayAdapter adapter = new StableArrayAdapter(this,
                        android.R.layout.simple_list_item_1, list);
                listview.setAdapter(adapter);

            }
        }
    }
    private void updateInventoryData() {
        List<User> user = database.userDao().getAllUser();
        List<Inventory> inventoryForUser = database.inventoryDao().findItems(user.get(0).id);
        TextView textView = (TextView) findViewById(R.id.result);
        textView.setText("");
       // TextView caught=(TextView) findViewById(R.id.txtPokemon);
        //caught.setText("");
        if (user.size()>0){
            for(int i=0;i<inventoryForUser.size();i++) {
                textView.setText("Number of Items: " + inventoryForUser.size());
               // caught.setText("Items " + inventoryForUser.get(i).description + "\n");
            }
        }
    }
    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnViewPokemon) {
            // TODO add trophy
            // TODO call updatefirstUserData
            updateFirstUserData();
        }
        if (view.getId()==R.id.btnFindPokemon) {
            // TODO add trophy
            // TODO call updatefirstUserData

            Random rnd=new Random();
            int rand=rnd.nextInt(9);
            String[] wildPokemon={"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon", "Charizard", "Squirtle", "Wartortle", "Blastoise"};

            Pokemon pokemon = new Pokemon(1, wildPokemon[rand]);
            database.pokemonDao().addPokemon(pokemon);
            updateFirstUserData();
        }
        if(view.getId()==R.id.btnInventory)
        {
            updateInventoryData();
        }
        if(view.getId()==R.id.btnpoke)
        {
            new DownloadImage().execute(URL);
        }
        // TODO call updatefirstUserData

    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            image.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }
}
