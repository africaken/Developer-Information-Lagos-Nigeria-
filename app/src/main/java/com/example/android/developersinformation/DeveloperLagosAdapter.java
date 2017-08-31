package com.example.android.developersinformation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import com.squareup.picasso.Picasso;

/*
* {@link AndroidFlavorAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link AndroidFlavor} objects.
**/

public class DeveloperLagosAdapter extends ArrayAdapter<DeveloperLagos> {

    private String username = "";

    private final Context mContext;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param lagosDeveloper A List of Developers in Lagos(Nigeria) objects to display in a list
     */
    public DeveloperLagosAdapter(Activity context, ArrayList<DeveloperLagos> lagosDeveloper) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, lagosDeveloper);
        this.mContext = context;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link DeveloperLagos} object located at this position in the list
        DeveloperLagos currentDeveloperLagos = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID developer_username
        TextView usernameTextView = (TextView) listItemView.findViewById(R.id.developer_username);
        // Get the Developer Username from the current DeveloperLagos object and
        // set this text on the usernameTextView
        username = mContext.getString(R.string.developer_username) + " " + currentDeveloperLagos.getGitHubUsername();
        usernameTextView.setText(username);

        // Find the TextView in the list_item.xml layout with the ID developer_username
        TextView urlTextView = (TextView) listItemView.findViewById(R.id.developer_url);
        // Get the Developer Url from the current DeveloperLagos object and
        // set this text on the urlTextView
        urlTextView.setText(currentDeveloperLagos.getGitHubDeveloperUrl());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView pictureView = (ImageView) listItemView.findViewById(R.id.developer_image);
        // Get the Developer AvatarUrl from the current DeveloperLagos object and
        // use the Picasso library Library to get the image from the Developer AvatarUrl
        Picasso.with(mContext).load(currentDeveloperLagos.getGitHubImageUrl()).placeholder(R.drawable.image).resize(64, 64).into(pictureView);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

}