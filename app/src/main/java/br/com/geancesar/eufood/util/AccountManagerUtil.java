package br.com.geancesar.eufood.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class AccountManagerUtil {

    private static AccountManagerUtil instance;

    private AccountManagerUtil(){}
    public static AccountManagerUtil getInstance() {
        if(instance == null) {
            instance = new AccountManagerUtil();
        }
        return instance;
    }

    public String getToken(Context context){
        AccountManager manager = AccountManager.get(context);
        Account account = manager.getAccounts()[0];
        return manager.peekAuthToken(account, "token");
    }
}
