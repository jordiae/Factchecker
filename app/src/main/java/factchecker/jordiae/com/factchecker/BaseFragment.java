package factchecker.jordiae.com.factchecker;

/**
 * Created by jordiae on 8/03/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BaseFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayout;

    private CustomAdapter customAdapter;

    private TextView textView;

    private View rootView;

    private int nFacts = 0;

    private int layout;

    private String customA;

    public BaseFragment() {
        // Required empty public constructor
    }

    public void setLayoutVar(int l) {
        layout = l;
    }

    public void setCustomAdapterVar(String string) {
        customA = string;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(layout, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);

        // LayoutManager manages the position of the items within recyclerview
        mLinearLayout = new LinearLayoutManager(getActivity());

        //We assign the LinearLayoutManager to the recycler
        mRecyclerView.setLayoutManager(mLinearLayout);

        // The adapter adapts a programmatically defined object to an XML view
        //customAdapter = new CustomAdapter(getActivity().getApplicationContext());
        customAdapter = new CustomAdapter(getActivity().getApplicationContext(), customA);
        //commented out because now refresh handles it:
        // mRecyclerView.setAdapter(customAdapter);

        refresh();

        return rootView;
    }

    public void refresh() {
        customAdapter.updateFacts(getActivity().getApplicationContext());
        nFacts = customAdapter.getNFacts();
        textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText(String.valueOf(nFacts));
        mRecyclerView.setAdapter(customAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onVisible();
        }
    }

    public void onVisible() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        refresh();
    }
}
