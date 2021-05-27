package se.aatrox.justdoit.tools;

import java.sql.Timestamp;

public class Task {
    private int _id;
    private final String  task;
    private final Timestamp  deadline, makespan;

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return this.makespan != null;
    }

    public String  get_id() {
        return ""+_id;
    }

    public String getType() {
        return isDone() ? "Done" : "Todo";
    }

    public String getS_deadline() {
        return deadline.toString();
    }

    public String getS_makespan() {
        return makespan == null ? "TODO" : makespan.toString();
    }

    public Task(int _id, String task, String deadline, String makespan) {
        this._id = _id;
        this.task = task;
        this.deadline = Timestamp.valueOf(deadline);
        this.makespan = makespan.startsWith("null") ? null : Timestamp.valueOf(makespan);
    }
}
