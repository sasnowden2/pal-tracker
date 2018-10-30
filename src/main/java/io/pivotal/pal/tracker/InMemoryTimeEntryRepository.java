package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.*;


public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private Map<Long,TimeEntry> dataStore = new HashMap<>();
    private Long timeEntryId = 1L;

    public TimeEntry find(long id) {
       return dataStore.get(id);
    }

    public TimeEntry create(TimeEntry requestTimeEntry) {
        requestTimeEntry.setId(timeEntryId);
        dataStore.put(timeEntryId,requestTimeEntry);
        ++timeEntryId;
        return requestTimeEntry;
    }

    public List<TimeEntry> list() {
        List<TimeEntry> values = new ArrayList<>();
        for (Map.Entry<Long,TimeEntry> entry : dataStore.entrySet())
        {
            values.add(entry.getValue());
        }
        return values;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        dataStore.put(id, timeEntry);
        return timeEntry;
    }

    public void delete(long id) {
        dataStore.remove(id);
    }
}
