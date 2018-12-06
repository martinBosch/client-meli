package com.martinb.meli.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;

public class AccountAuthenticator extends AbstractAccountAuthenticator {

    private static final String ARG_ACCOUNT_TYPE = "com.martinb.meli";
    private static final String ARG_AUTH_TOKEN_TYPE = "full_access";
    private static final String USERID = "userId";
    private static final String USERNAME = "username";

    private final Context mContext;

    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
                             String authTokenType, String[] requiredFeatures,
                             Bundle options) {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
            String authTokenType, Bundle options) {
        return null;
    }

    @Override
    public String getAuthTokenLabel(String arg0) {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse arg0, Account arg1,
                              String[] arg2) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse arg0,
                                    Account arg1, String arg2, Bundle arg3) {
        return null;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse arg0, String arg1) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse arg0,
                                     Account arg1, Bundle arg2) {
        return null;
    }

    public static void createAccount(Context context, String email, String password) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(email, ARG_ACCOUNT_TYPE);

        accountManager.addAccountExplicitly(account, password, null);
    }

    public static void setUserId(Context context, String id) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        if (accounts.length == 0) {
            return;
        }

        Account account = accounts[0];
        accountManager.setUserData(account, USERID, id);
    }

    public static String getUserId(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        if (accounts.length == 0) {
            return null;
        }

        Account account = accounts[0];
        return accountManager.getUserData(account, USERID);
    }

    public static void setAuthToken(Context context, String authToken) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        if (accounts.length == 0) {
            return;
        }

        Account account = accounts[0];
        accountManager.setAuthToken(account, ARG_AUTH_TOKEN_TYPE, authToken);
    }

    public static String getAuthToken(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        if (accounts.length == 0) {
            return null;
        }

        Account account = accounts[0];
        String token = accountManager.peekAuthToken(account, ARG_AUTH_TOKEN_TYPE);
        return token;
    }

    public static void updateAuthToken(Context context, String newToken) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        if (accounts.length == 0) {
            return;
        }

        Account account = accounts[0];
        accountManager.setAuthToken(account, ARG_AUTH_TOKEN_TYPE, newToken);
    }

    public static void setUsername(Context context, String username) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        if (accounts.length == 0) {
            return;
        }

        Account account = accounts[0];
        accountManager.setUserData(account, USERNAME, username);
    }

    public static String getUsername(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        if (accounts.length == 0) {
            return null;
        }

        Account account = accounts[0];
        return accountManager.getUserData(account, USERNAME);
    }


    public static void logOut(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        Account account = accounts[0];
        accountManager.removeAccount(account, null, null);
    }
}
