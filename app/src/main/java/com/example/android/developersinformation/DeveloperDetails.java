package com.example.android.developersinformation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DeveloperDetails extends AppCompatActivity {

    String logInNames, developerUrl, developerAvatarUrl, username, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_details);

        Intent inputIntent = getIntent();
        logInNames = inputIntent.getStringExtra("gitHubUsername");
        developerUrl = inputIntent.getStringExtra("gitHubDeveloperUrl");
        developerAvatarUrl = inputIntent.getStringExtra("gitHubAvatarUrl");

        username = logInNames;

        url = developerUrl;

        // Find the TextView in the developer_detail.xml layout with the ID developer_username
        TextView userView = (TextView) findViewById(R.id.developer_username);
        // set this text on the userView
        logInNames = getString(R.string.developer_username)+ " " + logInNames;
        userView.setText(logInNames);

        // Find the TextView in the developer_detail.xml layout with the ID developer_url
        TextView urlView = (TextView) findViewById(R.id.developer_url);
        // set this text on the userView
        developerUrl = getString(R.string.developer_url) + " "+ developerUrl;
        urlView.setText(developerUrl);

        // OnClickListener to open to open a browser to show Developer page on GitHub
        urlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new intent to view the developer details
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);

                // Create URI object (to pass into the Intent constructor)
                browserIntent.setData(Uri.parse(url));

                // Send the intent to launch a new web browser activity
                startActivity(browserIntent);

            }
        });

        // Find the ImageView in the developer_detail.xml layout with the developer_image
        ImageView pictureView = (ImageView) findViewById(R.id.developer_avatar);
        // Get the Developer AvatarUrl from the current DeveloperLagos object and
        // use the Picasso library Library to get the image from the Developer AvatarUrl
        Picasso.with(this).load(developerAvatarUrl).placeholder(R.drawable.image).resize(64, 64).into(pictureView);

        Button shareButton = (Button) findViewById(R.id.awesome_developer);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message="Check out this awesome developer @"+ username +" on "+ url;
                Intent intents = new Intent(Intent.ACTION_SEND);
                intents.setType("text/plain");
                intents.putExtra(Intent.EXTRA_TEXT,message);
                Intent chooseIntent = Intent.createChooser(intents,"Share");
                startActivity(chooseIntent);
            }
        });
    }
}
