package ua.ukraine.ukrroad.ukrroad.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ua.ukraine.ukrroad.ukrroad.database.table.Issue;

public class IssueDAO extends BaseDaoImpl<Issue, Integer> {

    public IssueDAO(ConnectionSource connectionSource,
                    Class<Issue> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Issue> getAllIssues() throws SQLException{
        return this.queryForAll();
    }
}
