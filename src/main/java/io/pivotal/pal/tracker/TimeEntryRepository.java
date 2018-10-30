package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry find(long timeEntryId);
    public TimeEntry create(TimeEntry timeEntry);
    public List<TimeEntry> list();
    public TimeEntry update(long eq, TimeEntry timeEntry);
    public void delete(long timeEntryId);
}
