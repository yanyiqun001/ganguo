package com.gank.data.database;

import android.content.Context;

import com.gank.data.database.entity.DaoMaster;
import com.gank.di.qualifier.ApplicationContext;
import com.gank.di.qualifier.DbName;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class DbOpenHelper extends  DaoMaster.OpenHelper{
    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DbName String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

}
