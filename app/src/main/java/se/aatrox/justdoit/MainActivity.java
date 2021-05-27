package se.aatrox.justdoit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Aatrox";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setting_click(View view) {






//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.ic_launcher)
//                .setTitle("Settings")
//                .setMessage("Fuck")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.e(TAG, "onClick: Yes");
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.e(TAG, "onClick: No");
//                    }
//                })
//                .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.e(TAG, "onClick: cancel");
//                    }
//                })
//                .create().show();

//        View popup_view = getLayoutInflater().inflate(R.layout.popup_view, null);
//        PopupWindow pw = new PopupWindow(popup_view,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                true);
//        pw.showAsDropDown(view, 100, 100);
    }
}