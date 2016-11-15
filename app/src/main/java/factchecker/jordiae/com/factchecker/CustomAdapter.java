package factchecker.jordiae.com.factchecker;

/**
 * Created by jordiae on 8/03/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.AdapterViewHolder>{

    ArrayList<Fact> facts;
    private String tab;
    private int nFacts = 0;

    CustomAdapter(Context context, String fragment){
        tab = fragment;
        updateFacts(context);
    }

    public int getNFacts() {
        return nFacts;
    }

    public void updateFacts(Context context) {
        facts = new ArrayList<>();

        DBAdapter dbAdapter;
        dbAdapter = new DBAdapter(context);
        dbAdapter = dbAdapter.open();
        long factsArray[] = dbAdapter.getAllFacts();
        int n = factsArray.length;

        for (int i = 0; i < n; ++i) {
            Date today = new Date(System.currentTimeMillis());
            Date gotDate = new Date(factsArray[i]);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(today);
            cal2.setTime(gotDate);

            boolean same = false;
            switch (tab) {
                case "lastHour":
                    same = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                            && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY);
                    break;

                case "today":
                    same = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                    break;
                case "lastMonth":
                    same = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
                    break;
                case "history":
                    same = true;
                    break;

            }

            if (same)
                facts.add(new Fact(factsArray[i]));
        }
        nFacts = facts.size();
        dbAdapter.close();
    }

    @Override
    public CustomAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // We instantiate an XML layout in the view
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        // We inflate the layout for each element
        View view = inflater.inflate(R.layout.row_layout, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.AdapterViewHolder adapterViewholder, int position) {
        adapterViewholder.date.setText(DateFormat.getDateTimeInstance().format(new Date(facts.get(position).getDate())));
    }

    @Override
    public int getItemCount() {
        // We must return the size of all the elements contained in the viewholder
        // If we return 0, it won't show any element
        return facts.size();
    }



    /* We define a viewholder class that works as an adapter for mantaining a reference to the elements
    * of ListView while the user scrolls. Like this we avoid several calls to findViewById*/
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}