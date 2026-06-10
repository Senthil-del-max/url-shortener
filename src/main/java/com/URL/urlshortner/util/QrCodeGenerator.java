package com.URL.urlshortner.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class QrCodeGenerator {

    public static String generateQRCode(String text) {

        try {

            QRCodeWriter qrCodeWriter =
                    new QRCodeWriter();

            BitMatrix bitMatrix =
                    qrCodeWriter.encode(
                            text,
                            BarcodeFormat.QR_CODE,
                            300,
                            300
                    );

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(
                    bitMatrix,
                    "PNG",
                    outputStream
            );

            byte[] pngData =
                    outputStream.toByteArray();

            return "data:image/png;base64,"
                    + Base64.getEncoder()
                    .encodeToString(pngData);

        } catch (WriterException | IOException e) {

            throw new RuntimeException(e);
        }
    }
}