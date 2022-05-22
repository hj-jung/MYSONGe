package com.cookandroid.exam.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.RoutineData;

import java.util.ArrayList;
import java.util.List;

public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.ViewHolder> {

    private Context context;
    private List<RoutineData> list = new ArrayList<RoutineData>();

    public RoutineListAdapter(Context context, List<RoutineData> list) {
        this.context = context;
        this.list = list;
        System.out.println(list.isEmpty());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_listview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineListAdapter.ViewHolder holder, int position) {
        holder.routineName.setText(list.get(position).routineName);
        holder.routineTime.setText(list.get(position).routineTime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox routineCheck;
        public TextView routineTime;
        public TextView routineName;

        public ViewHolder (View view) {
            super(view);
            routineCheck = (CheckBox) view.findViewById(R.id.routine_check);
            routineTime = (TextView) view.findViewById(R.id.routine_list_time);
            routineName = (TextView) view.findViewById(R.id.routine_list_name);
        }
    }
}
