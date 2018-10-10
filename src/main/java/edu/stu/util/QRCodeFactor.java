package edu.stu.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import edu.stu.domain.QRCode;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;

public class QRCodeFactor {

    private static final Logger LOGGER = Logger.getLogger(QRCodeFactor.class);

    private QRCodeFactor() {
    }

    private static QRCodeFactor factor = new QRCodeFactor();

    public static QRCodeFactor getInstance() {
        return factor;
    }

    // 往输出流里面写入新创建的二维码
    public void createQRCode(QRCode qrCode, OutputStream outputStream) throws Exception {
        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, qrCode.getCharset());
        hints.put(EncodeHintType.ERROR_CORRECTION, qrCode.getErrorCorrectionLevel());
        hints.put(EncodeHintType.MARGIN, 2);

        //生成二维码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCode.getContent(), BarcodeFormat.QR_CODE,
                qrCode.getWidth(), qrCode.getHeight(), hints);
        MatrixToImageWriter.writeToStream(bitMatrix, qrCode.getFormat(), outputStream);
    }


    public String parseQRCode(BufferedImage image) throws Exception {
        MultiFormatReader formatReader = new MultiFormatReader();

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, QRCode.charset);

        Result result = formatReader.decode(binaryBitmap, hints);

        LOGGER.info("二维码解析结果：" + result.toString());
        LOGGER.info("二维码的格式：" + result.getBarcodeFormat());
        LOGGER.info("二维码的文本内容：" + result.getText());

        return result.getText();
    }


}
