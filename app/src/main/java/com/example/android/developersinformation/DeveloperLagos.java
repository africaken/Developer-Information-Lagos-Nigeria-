package com.example.android.developersinformation;

/**
 * {@link DeveloperLagos} represents a Developer Information from Lagos(Nigeria) on GitHub.
 * Each object has 3 properties: developer username, developer url and developer image.
 */

public class DeveloperLagos {

    // Username of the Developer in Lagos(Nigeria) on GitHub
    private String mGitHubUsername;

    // Url of the Developer in Lagos(Nigeria) on GitHub
    private String mGitHubDeveloperUrl;

    // Url of the image of the Developer in Lagos(Nigeria) on GitHub
    private String mGitHubImageUrl;

    /*
    * Create a new DeveloperLagos object.
    *
    * @param gitHubUsername is the name of the Developer in Lagos(Nigeria) on GitHub
    * @param gitHubDeveloperUrl is the url of the Developer in Lagos(Nigeria) on GitHub
    * @param  gitHubImageUrl is the url of the image of the Developer in Lagos(Nigeria) on GitHub
    * */
    public DeveloperLagos(String gitHubUsername, String gitHubDeveloperUrl, String gitHubImageUrl)
    {
        mGitHubUsername = gitHubUsername;
        mGitHubDeveloperUrl = gitHubDeveloperUrl;
        mGitHubImageUrl = gitHubImageUrl;
    }

    /**
     * Get the username of the Developer in Lagos(Nigeria) from GitHub
     */
    public String getGitHubUsername() {
        return mGitHubUsername;
    }

    /**
     * Get the url of the Developer in Lagos(Nigeria) from GitHub
     */
    public String getGitHubDeveloperUrl() {
        return mGitHubDeveloperUrl;
    }

    /**
     * Get the image resource ID
     */
    public String getGitHubImageUrl() {
        return mGitHubImageUrl;
    }

}
