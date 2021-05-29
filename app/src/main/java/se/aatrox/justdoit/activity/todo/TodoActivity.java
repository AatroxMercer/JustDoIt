package se.aatrox.justdoit.activity.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.DatabaseMetaData;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import se.aatrox.justdoit.R;
import se.aatrox.justdoit.activity.main.MainActivity;
import se.aatrox.justdoit.activity.settings.SettingsActivity;
import se.aatrox.justdoit.tools.TaskSqlHelper;

public class TodoActivity extends AppCompatActivity {

    private static final String TAG = "Aatrox";
    private int ddl_year, ddl_month, ddl_day, ddl_hour, ddl_min;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        super.onBackPressed();
    }

    public void pick_deadline(View view) {
        Log.e(TAG, "onClick: ddl picker.");
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                TodoActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ddl_hour = hourOfDay;
                        ddl_min = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, ddl_day, ddl_hour, ddl_min);
                    }
                }, 12, 0, false
        );
        timePickerDialog.updateTime(ddl_hour, ddl_min);
        timePickerDialog.show();

        ddl_year = Calendar.getInstance().get(Calendar.YEAR);
        ddl_month = Calendar.getInstance().get(Calendar.MONTH);
        ddl_day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TodoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ddl_year = year;
                        ddl_month = month;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(ddl_year, ddl_month, ddl_day, 0, 0);
                    }
                }, ddl_year, ddl_month, ddl_day
        );
        datePickerDialog.updateDate(ddl_year, ddl_month, ddl_day);
        datePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ddl_year = -1;

        FloatingActionButton fab = findViewById(R.id.fab_confirm);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : deadline could be null
                SQLiteOpenHelper sql_helper = TaskSqlHelper.getTaskSqlHelper(TodoActivity.this);
                SQLiteDatabase w_db = sql_helper.getWritableDatabase();
                if (w_db.isOpen()) {
                    Log.e(TAG, "onClick: Task Confirmed. Inserting. IsDeadlineSet: "+ (ddl_year != -1));
                    if (ddl_year != -1) {
                        Timestamp ts = new Timestamp(new Date(ddl_year, ddl_month, ddl_day, ddl_hour, ddl_min).getTime());
                        String sql = "INSERT INTO tasks(task, deadline, makespan) " +
                                "VALUES (?, ?, NULL);";
                        w_db.execSQL(sql, new Object[]{((TextView) findViewById(R.id.task_in)).getText(), ts.toString()});
                    } else {
                        String sql = "INSERT INTO tasks(task, deadline, makespan) " +
                                "VALUES (?, NULL, NULL);";
                        w_db.execSQL(sql, new Object[]{((TextView) findViewById(R.id.task_in)).getText()});
                    }
                    w_db.close();
                }

                // Todo : set alarm;



                startActivity(new Intent(TodoActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
