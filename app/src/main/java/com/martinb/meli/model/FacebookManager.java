package com.martinb.meli.model;

import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookManager {

    public static void manageProfileInfo(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        getProfileInfo(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private static void getProfileInfo(JSONObject jsonObject) {
        try {
            String email = jsonObject.getString("email");
            String name = jsonObject.getString("name");

//            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
//            profilePictureView.setProfileId(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
