package factchecker.jordiae.com.factchecker;

import android.os.Bundle;


public class FourFragment extends BaseFragment {

    public FourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutVar(R.layout.fragment_four);
        setCustomAdapterVar("history");
    }

}
