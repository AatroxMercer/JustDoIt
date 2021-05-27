package se.aatrox.justdoit.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import se.aatrox.justdoit.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String TAG = "Aatrox";


    private List<Task> tasks;
    private Context context;

    public TaskAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.list_item, null);
        return new TaskViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {

        Task task = tasks.get(position);
        holder.task.setText(task.getTask());
        Log.e(TAG, "onBindViewHolder: " + position);
        holder.deadline.setText(task.getS_deadline());
        holder.makespan.setText(task.getS_makespan());


        if (!task.isDone()) {
            holder.itemView.setBackgroundColor(R.color.Todo);
        }
    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0 : tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView task, deadline, makespan;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.task);
            deadline = itemView.findViewById(R.id.deadline);
            makespan = itemView.findViewById(R.id.makespan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onRecyclerItemClick(getAdapterPosition(), view);
                    }
                }
            });
        }
    }

    private OnRecyclerItemClickListener itemClickListener;

    public void setRecycleItemClickListener(OnRecyclerItemClickListener listener) {
        itemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(int position, View view);
    }

}


//public class TaskAdapter extends BaseAdapter {
//
//    private List<Task> tasks;
//    private Context context;
//
//    @Override
//    public int getCount() {
//        return tasks.size();
//    }
//
//    public TaskAdapter(List<Task> tasks, Context context) {
//        this.tasks = tasks;
//        this.context = context;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder vh;
//        if (convertView == null) {
//            vh = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
//            vh.deadline = convertView.findViewById(R.id.deadline);
//            vh.makespan = convertView.findViewById(R.id.makespan);
//            vh.task = convertView.findViewById(R.id.task);
//            convertView.setTag(vh);
//        } else {
//            vh = (ViewHolder) convertView.getTag();
//        }
//
//        vh.task.setText(tasks.get(position).getTask());
//
//        return convertView;
//    }
//
//    private final class ViewHolder {
//        TextView task, deadline, makespan;
//    }
//}
