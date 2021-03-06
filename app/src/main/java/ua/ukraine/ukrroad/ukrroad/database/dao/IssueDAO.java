package ua.ukraine.ukrroad.ukrroad.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
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

    public Issue getIssueById(int id)  throws SQLException{
        QueryBuilder<Issue, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(Issue.ID, id);
        PreparedQuery<Issue> preparedQuery = queryBuilder.prepare();
        Issue goalList = query(preparedQuery).get(0);
        return goalList;
    }

    public void updateIssue(Issue issue) throws SQLException {
        UpdateBuilder<Issue, Integer> updateBuilder = updateBuilder();
        updateBuilder.where().eq("id", issue.getId());
        updateBuilder.updateColumnValue("address", issue.getAddress());
        updateBuilder.updateColumnValue("defect", issue.getDefect());
        updateBuilder.updateColumnValue("comment", issue.getComment());
        updateBuilder.updateColumnValue("email", issue.getEmail());
        updateBuilder.update();
    }
}
