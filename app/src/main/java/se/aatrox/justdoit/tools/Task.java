package se.aatrox.justdoit.tools;

import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Task {
    public final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private int _id;
    private final String task;
    private final Timestamp deadline, makespan;

    public String getTask() {
        return task;
    }

    public boolean isDeadlineSet() {
        return this.deadline != null;
    }

    public boolean isDone() {
        return this.makespan != null;
    }

    public String get_id() {
        return "" + _id;
    }

    public String getType() {
        return isDone() ? "Done" : "Todo";
    }

    public String getS_deadline() {
        return isDeadlineSet() ? deadline.toString() : "Free to go.";
    }

    public String getS_makespan() {
        return isDone()? makespan.toString() : "Wait to do.";
    }

    public Task(int _id, String task, String deadline, String makespan) {
        Log.e("Task", "Task: " + _id + task + deadline + (makespan == null));
        this._id = _id;
        this.task = task;
        this.deadline = deadline == null ? null : Timestamp.valueOf(deadline);
        this.makespan = makespan == null ? null : Timestamp.valueOf(makespan);
    }
}
