package bg.sofia.uni.fmi.mjt.foodAnalyzer.reader;

import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

// reference:
// https://www.geeksforgeeks.org/how-to-generate-and-read-qr-code-with-java-using-zxing-library/

public class BarcodeReader {
    public String read(String path) throws IOException, NotFoundException {

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));

        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
}
