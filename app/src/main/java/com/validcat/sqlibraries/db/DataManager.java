package com.validcat.sqlibraries.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.validcat.sqlibraries.model.Account;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dobrunov on 26.05.2015.
 */
public class DataManager {
    public static final String LOG_TAG = "DataManager";
    private RuntimeExceptionDao<Account, String> accountDao;

    public DataManager(Context context) {
        OrmDBHelper dbHelper = new OrmDBHelper(context);
        accountDao = dbHelper.getAccountDao();
    }

    public void createAccount(Account account) {
        accountDao.create(account);
    }

    public void deleteAccount(String email) {
        try {
            DeleteBuilder<Account, String> deleteBuilder = accountDao.deleteBuilder();
            deleteBuilder.where().eq(Account.FIELD_ID, email);
            accountDao.delete(deleteBuilder.prepare());
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Error deleting Account");
            e.printStackTrace();
        }
    }

    public List<Account> getAllAccount() {
        return accountDao.queryForAll();
    }
}
