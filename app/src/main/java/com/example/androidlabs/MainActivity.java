package com.example.androidlabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CharacterAdapter characterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.characterListView);
        characterAdapter = new CharacterAdapter(this, new ArrayList<>());
        listView.setAdapter(characterAdapter);

        new FetchCharactersTask().execute();
    }

    // FetchCharactersTask to retrieve character data
    private class FetchCharactersTask extends AsyncTask<Void, Void, List<Character>> {
        @Override
        protected List<Character> doInBackground(Void... voids) {
            List<Character> characters = new ArrayList<>();
            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder responseText = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseText.append(line);
                }

                JSONArray results = new JSONObject(responseText.toString()).getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject character = results.getJSONObject(i);
                    characters.add(new Character(
                            character.getString("name"),
                            character.getString("height"),
                            character.getString("mass"),
                            character.getString("birth_year")
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return characters;
        }

        @Override
        protected void onPostExecute(List<Character> characters) {
            // Update the ListView with character data
            characterAdapter.updateCharacters(characters);
        }
    }
}
