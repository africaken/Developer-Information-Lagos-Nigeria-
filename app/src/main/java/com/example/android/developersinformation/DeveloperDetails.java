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

    String logInNames, developerUrl, developerAvatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        Intent inputIntent = getIntent();
        logInNames = inputIntent.getStringExtra("gitHubUsername");
        developerUrl = inputIntent.getStringExtra("gitHubDeveloperUrl");
        developerAvatarUrl = inputIntent.getStringExtra("gitHubAvatarUrl");

        // Find the TextView in the developer_detail.xml layout with the ID developer_username
        TextView userView = (TextView) findViewById(R.id.developer_username);
        // set this text on the userView
        userView.setText(logInNames);

        // Find the TextView in the developer_detail.xml layout with the ID developer_url
        TextView urlView = (TextView) findViewById(R.id.developer_url);
        // set this text on the userView
        urlView.setText(developerUrl);

        // OnClickListener to open to open a browser to show Developer page on GitHub
        urlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new intent to view the developer details in web browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);

                // Create URI object (to pass into the Intent constructor)
                browserIntent.setData(Uri.parse(developerUrl));

                // Verify that the intent will resolve to an activity
                if (browserIntent.resolveActivity(getPackageManager()) != null) {

                    // Send the intent to launch an activity that can browse the web
                    startActivity(browserIntent);
                }
            }
        });

        // Find the ImageView in the developer_detail.xml layout with the developer_image
        ImageView pictureView = (ImageView) findViewById(R.id.developer_avatar);
        // Get the Developer AvatarUrl from the current DeveloperLagos object and
        // use the Picasso library Library to get the image from the Developer AvatarUrl
        Picasso.with(this)
                .load(developerAvatarUrl) // Load the image
                .placeholder(R.drawable.image) // Image resource that act as placeholder
                .error(R.drawable.error) // Image resource for error
                .resize(120, 120) // Post processing - Resizing the image
                .into(pictureView); // View where image is loaded

        // OnClickListener to always display the chooser dialog box to choose an app
        // to send a messsage on the click of share button.
        Button shareButton = (Button) findViewById(R.id.awesome_developer);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Content of the body of the message to be sent
                String message="Check out this awesome developer @"+ logInNames +" on "+ developerUrl;

                // Create a new intent to that will send a message
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                Intent chooseIntent = Intent.createChooser(sendIntent,getResources().getText(R.string.share));

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {

                    // Send the intent to launch an activity that can send a message
                    startActivity(chooseIntent);
                }
            }
        });
    }
}
