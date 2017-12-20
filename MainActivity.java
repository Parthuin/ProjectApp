package com.example.blalonde9489.projectapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static int TIME_OUT = 4000;
    SharedPreferences my_preferences;

    private User user;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.my_preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        database = AppDatabase.getDatabase(getApplicationContext());

        // cleanup for testing some initial data
        // add some data
        List<User> users = database.userDao().getAllUser();
        if (users.size()==0) {
            //database.userDao().addUser(new User(1, "Brandon", 1));
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
        TextView caught=(TextView) findViewById(R.id.txtPokemon);
        caught.setText("");
        if (user.size()>0){
            for(int i=0;i<pokemonForUser.size();i++) {
                textView.setText("Number of Pokemon: " + pokemonForUser.size());
                caught.append("Pokemon " + pokemonForUser.get(i).description + "\n");
            }
        }
    }
    private void updateInventoryData() {
        List<User> user = database.userDao().getAllUser();
        List<Inventory> inventoryForUser = database.inventoryDao().findItems(user.get(0).id);
        ListView textView = (TextView) findViewById(R.id.result);
        textView.add
        TextView caught=(TextView) findViewById(R.id.txtPokemon);
        caught.setText("");
        if (user.size()>0){
            for(int i=0;i<inventoryForUser.size();i++) {
                textView.setText("Number of Items: " + inventoryForUser.size());
                caught.setText("Items " + inventoryForUser.get(i).description + "\n");
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

            Pokemon pokemon = new Pokemon(user.id, wildPokemon[rand]);
            database.pokemonDao().addPokemon(pokemon);
            updateFirstUserData();
        }
        if(view.getId()==R.id.btnInventory)
        {
            updateInventoryData();
        }
        // TODO call updatefirstUserData

    }
}
