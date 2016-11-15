package factchecker.jordiae.com.factchecker;

import android.os.Bundle;


public class OneFragment extends BaseFragment {

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutVar(R.layout.fragment_one);
        setCustomAdapterVar("lastHour");
    }

}
