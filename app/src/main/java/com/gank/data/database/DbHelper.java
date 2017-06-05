package com.gank.data.database;

import com.gank.data.database.entity.Image;
import com.gank.data.network.response.Result;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Administrator on 2017/4/14 0014.
 */

public interface DbHelper {
     Boolean getIsCollnection(String id);

     void addConnection(Result result);

     void addImage(Image img);

     void cancelCollection(String id);

     Observable<List<Result>> queryForList(int offset);

     void addSearchHistory(String content);

     Observable<List<String>> querySearchHistory();

     void deleteSearchHistory();
}
