package cmov.miguellucas.com.customerapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    ImageView qrCodeImageview;
    public final static int DIMENSION=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //qrCodeImageview = findViewById(R.id.img_qr_code);
        //generate();
    }

    void generate() {
        new Thread(new convertToQR("teste")).start();
    }

    class convertToQR implements Runnable {
        String content;

        convertToQR(String value) {
            content = value;
        }

        @Override
        public void run() {
            final Bitmap bitmap;
            final String errorMsg;
            try {
                bitmap = encodeAsBitmap(content);
                runOnUiThread(new Runnable() {  // runOnUiThread method used to do UI task in main thread.
                    @Override
                    public void run() {
                        qrCodeImageview.setImageBitmap(bitmap);
                    }
                });
            }
            catch (WriterException e) {
                errorMsg = e.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //show error
                        //errorTv.setText(errorMsg);
                    }
                });
            }
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;

        //Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        //hints.put(EncodeHintType.CHARACTER_SET,  str);
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, DIMENSION, DIMENSION, null);
        }
        catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
