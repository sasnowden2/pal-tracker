package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        String sql = "SELECT * FROM time_entries WHERE id = ?;";
        return jdbcTemplate.query(sql,new Object[]{timeEntryId},extractor);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        String sql = "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(sql, new String[] {"id"});
                        pst.setLong(1, timeEntry.getProjectId());
                        pst.setLong(2, timeEntry.getUserId());
                        pst.setDate(3, Date.valueOf(timeEntry.getDate()));
                        pst.setInt(4, timeEntry.getHours());
                        return pst;
                    }
                },
                keyHolder);
        timeEntry.setId(keyHolder.getKey().longValue());
        return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        String sql = "SELECT * FROM time_entries;";
        return jdbcTemplate.query(sql,mapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        //String sql = "UPDATE time_entries SET project_id =" + timeEntry.getProjectId() + ",user_id =" + timeEntry.getUserId()
        //        + ", date =" + timeEntry.getDate() + ",hours=" + timeEntry.getHours() + "WHERE id =" + id + ";";
        String sql = "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours= ? WHERE id = ?;";
        jdbcTemplate.update(sql, timeEntry.getProjectId(), timeEntry.getUserId(), Date.valueOf(timeEntry.getDate()), timeEntry.getHours(), id);
        timeEntry.setId(id);
        return timeEntry;
    }

    @Override
    public void delete(long timeEntryId) {
        String sql = "DELETE FROM time_entries WHERE id = ?;";
        jdbcTemplate.update(sql,timeEntryId);
    }


    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
