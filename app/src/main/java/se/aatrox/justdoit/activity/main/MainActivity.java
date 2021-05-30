package se.aatrox.justdoit.activity.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.aatrox.justdoit.R;
import se.aatrox.justdoit.activity.settings.SettingsActivity;
import se.aatrox.justdoit.activity.todo.TodoActivity;
import se.aatrox.justdoit.tools.Task;
import se.aatrox.justdoit.tools.TaskSqlHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Aatrox";
    private final List<Task> tasks = new ArrayList<>();
//    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab_todo = findViewById(R.id.fab_todo);
        fab_todo.show();
        fab_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TodoActivity.class));
                finish();
            }
        });

        fresh_tasks();

        RecyclerView rv = findViewById(R.id.view_R);
        TaskAdapter ta = new TaskAdapter(tasks, this, rv);
        rv.setAdapter(ta);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ta.setRecycleItemClickListener(new TaskAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position, View view) {
                Log.e(TAG, "onRecyclerItemClick: " + position);
                Task task = tasks.get(position);
                AlertDialog.Builder ad_builder = new AlertDialog.Builder(MainActivity.this);
                ad_builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle(task.getType())
                        .setMessage(task.getTask())
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e(TAG, "onClick: cancel");
                            }
                        })
                        .setNeutralButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e(TAG, "onClick: delete");
                                SQLiteOpenHelper sql_helper = TaskSqlHelper.getTaskSqlHelper(MainActivity.this);
                                SQLiteDatabase w_db = sql_helper.getWritableDatabase();

                                if (w_db.isOpen()) {
                                    Log.e(TAG, "fresh_tasks: Writing DB.");
                                    String sql = "DELETE FROM tasks WHERE  _id = ?;";
                                    w_db.execSQL(sql, new Object[]{task.get_id()});
                                    Log.e(TAG, "delete task: " + task.getTask());
                                    w_db.close();

                                    finish();
                                    startActivity(new Intent(getIntent()));
                                }
                            }
                        });
                if (!task.isDone() && task.isDeadlineSet()) {
                    ad_builder.setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e(TAG, "onClick: Done");
                                    SQLiteOpenHelper sql_helper = TaskSqlHelper.getTaskSqlHelper(MainActivity.this);
                                    SQLiteDatabase w_db = sql_helper.getWritableDatabase();

                                    if (w_db.isOpen()) {
                                        Log.e(TAG, "fresh_tasks: Writing DB.");
                                        Timestamp ts = new Timestamp(new Date().getTime());
                                        String sql = "UPDATE tasks SET makespan = ? WHERE  _id = ?;";
                                        w_db.execSQL(sql, new Object[]{ts.toString(), task.get_id()});
                                        Log.e(TAG, "update task " + position);
                                        w_db.close();

                                        finish();
                                        startActivity(new Intent(getIntent()));
                                    }
                                }
                            });
                }
                ad_builder.create().show();
            }
        });

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e(TAG, "onItemClick: "+ id );;
//            }
//        });
//
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                return false;
//            }
//        });
    }

    public void fresh_tasks() {

        boolean isDoneShowed = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDoneShowed", true);
        SQLiteDatabase r_db = TaskSqlHelper.getTaskSqlHelper(this).getReadableDatabase();

        if (r_db.isOpen()) {
            Log.e(TAG, "fresh_tasks: Reading DB.");
            Cursor cursor = r_db.rawQuery("SELECT * FROM tasks ORDER BY _id DESC;", null);

            while (cursor.moveToNext()) {
//                Log.e(TAG, "fresh_tasks: " +
//                        cursor.getString(cursor.getColumnIndex("task"))+
//                        cursor.getString(cursor.getColumnIndex("deadline"))+
//                        cursor.getString(cursor.getColumnIndex("makespan")));
                Task task = new Task(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("task")),
                        cursor.getString(cursor.getColumnIndex("deadline")),
                        cursor.getString(cursor.getColumnIndex("makespan"))
                );
                if (isDoneShowed || !task.isDone()) {
                    tasks.add(task);
                }
            }
            cursor.close();
            r_db.close();
        }
    }

    public void setting_click(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
        finish();

//        View popup_view = getLayoutInflater().inflate(R.layout.popup_view, null);
//        PopupWindow pw = new PopupWindow(popup_view,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                true);
//        pw.showAsDropDown(view, 100, 100);
    }
}