package com.validcat.sqlibraries.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Dobrunov on 26.05.2015.
 */
@DatabaseTable(tableName = "accounts")
public class Account {
    public static final String FIELD_ID = "email";
    @DatabaseField(id = true)
    public String email;

    @DatabaseField(canBeNull = false)
    public String password;

    Account() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
