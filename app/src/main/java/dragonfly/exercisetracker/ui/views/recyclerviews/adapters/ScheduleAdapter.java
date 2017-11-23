package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DSchedule;


public class ScheduleAdapter extends BaseAdapter {
    private DSchedule schedule;
    private List<Object> items = new ArrayList<>();

    public ScheduleAdapter(DSchedule schedule) {
        //super();setScheduleActivity(schedule);
    }

    public void setScheduleActivity(DSchedule schedule) {
        this.schedule = schedule;
        this.items.clear();
        if(this.schedule != null) {
            //if(this.schedule.getRoutines() != null) {
                //for (DRoutine routine : this.schedule.getRoutines()) {
                    //this.indexedItems.add(new CalendarDayViewHolderController());
                //}
            //}
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class CalendarDayViewHolderController {
        private Date date;

        public CalendarDayViewHolderController(Date date) {
            this.date = date;
        }

        public void bindCalendarDayViewHolder(CalendarDayViewHolder calendarDayViewHolder) {
            calendarDayViewHolder.dateTv.setText("");
            calendarDayViewHolder.dayOfWeekTv.setText("");
        }
    }

    private class CalendarDayViewHolder extends RecyclerView.ViewHolder {
        private CalendarDayViewHolderController calendarDayViewHolderController;
        private TextView dateTv;
        private TextView dayOfWeekTv;

        public CalendarDayViewHolder(View itemView, CalendarDayViewHolderController calendarDayViewHolderController) {
            super(itemView);
            this.dateTv = (TextView)itemView.findViewById(R.id.date);
            this.dayOfWeekTv = (TextView)itemView.findViewById(R.id.day_of_week);
        }

        public void bindCalendarDayViewHolderController(CalendarDayViewHolderController calendarDayViewHolderController) {
            this.calendarDayViewHolderController = calendarDayViewHolderController;
        }
    }
}
