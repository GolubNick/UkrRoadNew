package ua.ukraine.ukrroad.ukrroad.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;

public class ImageDAO extends BaseDaoImpl<Image, Integer> {

    public ImageDAO(ConnectionSource connectionSource,
                    Class<Image> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Image> getAllImages() throws SQLException{
        return this.queryForAll();
    }

    public List<Image> getImagesByIssue(Issue issue) throws SQLException {
        return this.queryBuilder().where().eq(Image.ISSUE,issue).query();
    }
}
