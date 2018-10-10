package edu.stu.service;

import com.google.zxing.NotFoundException;
import com.twelvemonkeys.image.ResampleOp;
import edu.stu.util.QRCodeFactor;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;

@MultipartConfig
public class DecodeServlet extends HttpServlet {

    private String imgDir; // 用户上传的图片保存地址

    private static final int MAX_IMG_SIZE = 2 * 1024 * 1024; // 2MB

    private static final Logger LOGGER = Logger.getLogger(DecodeServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        imgDir = getServletContext().getInitParameter("img_dir");
        if (imgDir.charAt(imgDir.length() - 1) != '/') {
            imgDir += "/";
        }

        File dir = new File(imgDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isValidPicture(request)) {
            response.sendRedirect("decode.jsp");
            return;
        }

        InputStream inputStream = (InputStream) request.getAttribute("imgStream");
        BufferedImage image = ImageIO.read(inputStream);
        String result;
        try {
            result = QRCodeFactor.getInstance().parseQRCode(image);
        } catch (Exception e) {
            result = "二维码解码失败！";
            LOGGER.error(e.getMessage(), e);
        }
        request.getSession().setAttribute("result", result);
        response.sendRedirect("decode.jsp");
    }

    private boolean isValidPicture(HttpServletRequest request) throws IOException, ServletException {
        long contentLengthLong = request.getContentLengthLong();
        Part imgPart = request.getPart("file");
        if (imgPart == null) {
            return false;
        }
        String imgName = imgPart.getName();
        if (contentLengthLong == -1 || contentLengthLong > MAX_IMG_SIZE) {
            LOGGER.info("文件" + imgName + "太大了，大小为：" + (contentLengthLong / 1024.0 / 1024.0) + "M");
            String message = "文件" + imgName + "太大了，请上传小于等于2M的图片!";
            request.getSession().setAttribute("MSG", message);
            return false;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(imgPart.getInputStream(), byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        try {
            TikaConfig tika = new TikaConfig();
            Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, imgName);
            MediaType type = tika.getDetector().detect(byteArrayInputStream, metadata);

            if (!"image".equals(type.getType())) {
                String message = "文件" + imgName + "不是图片！";
                LOGGER.info(message);
                LOGGER.info(imgName + "是" + type.getType());
                request.getSession().setAttribute("MSG", message + "请上传格式为图片的文件");
                return false;
            }
        } catch (Exception e) {
            String message = "文件" + imgName + "不是图片！";
            LOGGER.info(message);
            LOGGER.error(e.getMessage(), e);
            request.getSession().setAttribute("MSG", message + "请上传格式为图片的文件");
            return false;
        }

        byteArrayInputStream.reset();
        request.setAttribute("imgStream", byteArrayInputStream);
        return true;
    }
}
