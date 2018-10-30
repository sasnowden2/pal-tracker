package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class TimeEntry {
    private long id;
    private final long projectId;
    private final long userId;
    private final LocalDate date;
    private final int hours;

    public TimeEntry(long projectId, long userId, LocalDate localDate, int hours) {
        this(0L,projectId, userId, localDate, hours);
    }

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate localDate, int hours) {
        this.id = timeEntryId;
        this.projectId = projectId;
        this.userId = userId;
        this.date = localDate;
        this.hours = hours;
    }

    public TimeEntry() {
        this(0L,0L,0L,null,0);
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return id == timeEntry.id &&
                projectId == timeEntry.projectId &&
                userId == timeEntry.userId &&
                hours == timeEntry.hours &&
                Objects.equals(date, timeEntry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, userId, date, hours);
    }
}
