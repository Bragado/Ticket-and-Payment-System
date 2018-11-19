package cmov.miguellucas.com.customerapp.Cafeteria;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.QRCodeGenerator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CafeteriaQrCode extends Fragment implements QRCodeGenerator.QRAnswer {

    ImageView imageView;
    ProgressBar progressBar;

    public CafeteriaQrCode() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafeteria_qr_code, container, false);
        String data = getArguments().getString("data");

        imageView = view.findViewById(R.id.qr_code);
        progressBar = view.findViewById(R.id.progressBar);
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator(data, getContext(),this);
        Thread thread = new Thread(qrCodeGenerator);
        thread.start();

        return view;

    }

    @Override
    public void done(final Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
