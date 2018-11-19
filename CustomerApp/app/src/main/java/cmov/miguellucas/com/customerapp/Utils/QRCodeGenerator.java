package cmov.miguellucas.com.customerapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import cmov.miguellucas.com.customerapp.Cafeteria.CafeteriaQrCode;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Ticket.TicketValidationFragment;

public class QRCodeGenerator implements Runnable {

    public final static int DIMENSION=900;


    String data;
    QRAnswer qrAnswer;
    Context context;

    public QRCodeGenerator(String data, Context context, TicketValidationFragment tf) {
        this.data = data;
        this.qrAnswer = (QRAnswer)tf;
        this.context = context;
    }

    public QRCodeGenerator(String data, Context context, CafeteriaQrCode tf) {
        this.data = data;
        this.qrAnswer = (QRAnswer)tf;
        this.context = context;
    }



    @Override
    public void run() {
            final Bitmap bitmap;
            final String errorMsg;
            try {
                bitmap = encodeAsBitmap(data);
                qrAnswer.done(bitmap);


            }
            catch (WriterException e) {
                errorMsg = e.getMessage();
                qrAnswer.done(null);
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
                pixels[offset + x] = result.get(x, y) ?  context.getResources().getColor(R.color.colorPrimary):context.getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }


    public interface QRAnswer {
        public void done(Bitmap bitmap);
    }


}
