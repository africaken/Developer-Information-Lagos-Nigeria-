package com.example.android.developersinformation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<DeveloperLagos>> {

    private static final int DEVELOPERLAGOS_LOADER_ID = 1;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView connectionAbsentView;

    /**
     * Progress Indicator that is displayed when fetching lagosDeveloper
     */
    private ProgressBar progressIndicator;

    /**
     * URL to query the GITHUB api for Developers in Lagos(Nigeria) for data
     */
    private static final String GITHUB_REQUEST_URL =
            "https://api.github.com/search/users?q=location:Lagos&per_page=100";

    /**
     * Adapter for the list of DeveloperLagos
     */
    private DeveloperLagosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView developerLagosListView = (ListView) findViewById(R.id.listview_developer_lagos);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new DeveloperLagosAdapter(this, new ArrayList<DeveloperLagos>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        developerLagosListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to another activity
        // with more information about the developer.
        developerLagosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Get the {@link DeveloperLagos} object located at this position in the list
                DeveloperLagos currentDeveloperLagos = mAdapter.getItem(i);

                // Create a new intent to view the developer details
                Intent developerDetailsIntent = new Intent(MainActivity.this, DeveloperDetails.class);

                if (currentDeveloperLagos != null) {
                    developerDetailsIntent.putExtra("gitHubUsername", currentDeveloperLagos.getGitHubUsername());
                    developerDetailsIntent.putExtra("gitHubDeveloperUrl", currentDeveloperLagos.getGitHubDeveloperUrl());
                    developerDetailsIntent.putExtra("gitHubAvatarUrl", currentDeveloperLagos.getGitHubImageUrl());
                }

                // Send the intent to launch a new activity
                startActivity(developerDetailsIntent);
            }
        });

        // TextView that is displayed when the list is empty
        connectionAbsentView = (TextView) findViewById(R.id.connection_absent_view);
        developerLagosListView.setEmptyView(connectionAbsentView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getSupportLoaderManager().initLoader(DEVELOPERLAGOS_LOADER_ID, null, this).forceLoad();

            // Progress Indicator shows that the data is being fetched
            progressIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
            progressIndicator.setIndeterminate(true);
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            // If there is no network connection
            progressIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
            progressIndicator.setVisibility(View.GONE);
            connectionAbsentView.setText(getString(R.string.connection_absent));
        }
    }

    @Override
    public Loader<List<DeveloperLagos>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new DeveloperLagosLoader(this, GITHUB_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<DeveloperLagos>> loader, List<DeveloperLagos> data) {

        // Hide loading indicator because the data has been loaded
        progressIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
        progressIndicator.setIndeterminate(false);
        progressIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No lagosDeveloper found."
        connectionAbsentView.setText(getString(R.string.developer_absent));

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link DeveloperLagos}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DeveloperLagos>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
