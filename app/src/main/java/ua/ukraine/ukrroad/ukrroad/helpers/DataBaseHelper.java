package ua.ukraine.ukrroad.ukrroad.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ua.ukraine.ukrroad.ukrroad.database.dao.ImageDAO;
import ua.ukraine.ukrroad.ukrroad.database.dao.IssueDAO;
import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DataBaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME ="ukrhyyama.db";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 1;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private ImageDAO imageDAO = null;
    private IssueDAO issueDAO = null;

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, Image.class);
            TableUtils.createTable(connectionSource, Issue.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    //Выполняется, когда БД имеет версию отличную от текущей
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
            TableUtils.dropTable(connectionSource, Image.class, true);
            TableUtils.dropTable(connectionSource, Issue.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }

    //синглтон для ImageDAO
    public ImageDAO getImageDAO() throws SQLException {
        if(imageDAO == null){
            imageDAO = new ImageDAO(getConnectionSource(), Image.class);
        }
        return imageDAO;
    }
    //синглтон для IssueDAO
    public IssueDAO getIssueDAO() throws SQLException{
        if(issueDAO == null){
            issueDAO = new IssueDAO(getConnectionSource(), Issue.class);
        }
        return issueDAO;
    }

    //выполняется при закрытии приложения
    @Override
    public void close(){
        super.close();
        imageDAO = null;
        issueDAO = null;
    }
}