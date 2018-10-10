package edu.stu.domain;


import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {

    private String format; // 二维码输出格式

    public static final String charset = "UTF-8"; // 二维码内容编码

    private String valid; // 纠错级别

    private int width;

    private int height;

    private String content;

    public QRCode() {
    }

    public QRCode(String format, String valid, int width, int height, String content) {
        this.format = format;
        this.valid = valid;
        this.width = width;
        this.height = height;
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        switch (format) {
            case "GIF":
            case "JPEG":
            case "PNG":
                this.format = format;
                break;

            default:
                this.format = "PNG";
        }
    }

    public String getCharset() {
        return charset;
    }


    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        switch (valid) {
            case "L":
            case "M":
            case "Q":
            case "H":
                this.valid = valid;
            default:
                this.valid = "M";
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        switch (getValid()) {
            case "L":
                return ErrorCorrectionLevel.L;
            case "M":
                return ErrorCorrectionLevel.M;
            case "Q":
                return ErrorCorrectionLevel.Q;
            case "H":
                return ErrorCorrectionLevel.H;
        }

        return ErrorCorrectionLevel.M;
    }
}
