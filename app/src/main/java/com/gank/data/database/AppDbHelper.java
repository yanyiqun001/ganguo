package com.gank.data.database;

import android.database.Cursor;

import com.gank.data.database.entity.DaoMaster;
import com.gank.data.database.entity.DaoSession;
import com.gank.data.database.entity.Image;
import com.gank.data.database.entity.ImageDao;
import com.gank.data.database.entity.SearchHistory;
import com.gank.data.database.entity.SearchHistoryDao;
import com.gank.data.network.response.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.gank.Constants.PAGECOUNT;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;
    private final String SQL_DISTINCT_ENAME="SELECT DISTINCT " +
            SearchHistoryDao.Properties.SearchContent.columnName+" FROM "+SearchHistoryDao.TABLENAME;
    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }


    @Override
    public Boolean getIsCollnection(String id) {
        return mDaoSession.getResultDao().load(id)!=null;
    }

    @Override
    public Observable<List<Result>> queryForList(final int offset) {
        return Observable.fromCallable(
                new Callable<List<Result>>() {
                    @Override
                    public List<Result> call() throws Exception {
                        return mDaoSession.getResultDao().queryBuilder().offset((offset-1)*PAGECOUNT).limit(PAGECOUNT).build().list();
                    }
                });

    }

    @Override
    public void addSearchHistory(String content) {
        mDaoSession.getSearchHistoryDao().insert(new SearchHistory(null,content));
    }

    @Override
    public Observable<List<String>> querySearchHistory() {
        return Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                ArrayList<String> result = new ArrayList<String>();
                Cursor c = mDaoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
                try{
                    if (c.moveToFirst()) {
                        do {
                            result.add(c.getString(0));
                        } while (c.moveToNext());
                    }
                } finally {
                    c.close();
                }
                return result;
            }
        });

    }

    @Override
    public void deleteSearchHistory() {
        mDaoSession.getSearchHistoryDao().deleteAll();
    }

    @Override
    public void addConnection(Result result) {
        mDaoSession.getResultDao().insert(result);
    }

    @Override
    public void addImage(Image img) {
        mDaoSession.getImageDao().insert(img);
    }

    @Override
    public void cancelCollection(String id) {
        mDaoSession.getResultDao().deleteByKey(id);
        mDaoSession.getImageDao().queryBuilder().where(ImageDao.Properties.ImageId.eq(id)).
                buildDelete().executeDeleteWithoutDetachingEntities();

    }


}
