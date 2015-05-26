package com.validcat.sqlibraries.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.validcat.sqlibraries.R;
import com.validcat.sqlibraries.model.Account;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by Dobrunov on 26.05.2015.
 */
public class OrmDBHelper extends OrmLiteSqliteOpenHelper {
    private RuntimeExceptionDao<Account, String> accountRuntimeDao = null;
    public static final String DATABASE_NAME = "account.db";
    public static final int DATABASE_VERSION = 1;

    public OrmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(OrmDBHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Account.class);
        } catch (SQLException e) {
            Log.e(OrmDBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
        // here we try inserting data in the on-create as a test
        Log.i(OrmDBHelper.class.getName(), "db was created in onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(OrmDBHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Account.class, true);
            // drop the old db, create a new one
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(OrmDBHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our class.
     * It will create it or just give the cached value.
     */
    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz)
            throws SQLException {
        return super.getDao(clazz);
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    protected RuntimeExceptionDao<Account, String> getAccountDao() {
        if (accountRuntimeDao == null)
            accountRuntimeDao = getRuntimeExceptionDao(Account.class);
        return accountRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        accountRuntimeDao = null;
    }
}
