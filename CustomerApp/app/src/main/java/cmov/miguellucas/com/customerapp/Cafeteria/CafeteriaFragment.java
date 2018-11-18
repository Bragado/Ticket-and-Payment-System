package cmov.miguellucas.com.customerapp.Cafeteria;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cmov.miguellucas.com.customerapp.R;

/**
 * Created by Miguel Lucas on 17/11/2018.
 */

public class CafeteriaFragment extends Fragment {

    public CafeteriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafeteria, container, false);
        return view;
    }
}
