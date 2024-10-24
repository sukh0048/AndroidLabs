package com.example.androidlabs;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CatImages";
    private ImageView catImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        catImageView = findViewById(R.id.catImageView);
        progressBar = findViewById(R.id.progressBar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            new CatImages().execute();
        }
    }

    private class CatImages extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... params) {
            String localImagePath = null;
            try {
                // Fetch cat image JSON
                URL url = new URL("https://cataas.com/cat?json=true");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Read JSON response
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                String jsonResponse = jsonBuilder.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String catId = jsonObject.getString("id");
                String catImageUrl = jsonObject.getString("url");

                // Check if the image already exists
                File directory = new File(Environment.getExternalStorageDirectory() + "/CatImages");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                localImagePath = directory.getPath() + "/" + catId + ".jpg";
                File localImageFile = new File(localImagePath);

                if (!localImageFile.exists()) {
                    // Download the image
                    URL imageUrl = new URL(catImageUrl);
                    HttpURLConnection imageConnection = (HttpURLConnection) imageUrl.openConnection();
                    imageConnection.connect();
                    InputStream imageStream = new BufferedInputStream(imageConnection.getInputStream());
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                    // Save the image to local storage
                    FileOutputStream fos = new FileOutputStream(localImageFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    Log.d(TAG, "Image downloaded and saved: " + localImagePath);
                } else {
                    Log.d(TAG, "Image already exists: " + localImagePath);
                }

            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage(), e);
            }
            return localImagePath;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Update the ImageView with the downloaded image
            if (result != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(result);
                catImageView.setImageBitmap(bitmap);
            }
        }
    }
}
