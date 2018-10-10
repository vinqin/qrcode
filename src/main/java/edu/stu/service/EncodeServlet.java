package edu.stu.service;

import edu.stu.domain.QRCode;
import edu.stu.util.QRCodeFactor;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodeServlet extends HttpServlet {

    private QRCodeFactor factor = QRCodeFactor.getInstance();

    private static final Logger LOGGER = Logger.getLogger(EncodeServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content = request.getParameter("content");
        String format = request.getParameter("format");
        String valid = request.getParameter("valid");

        if (!isValidParam(content, format, valid)) {
            response.sendRedirect("encode.jsp");
            return;
        }

        QRCode qrCode = new QRCode(format, valid, 300, 300,
                new String(content.getBytes("ISO-8859-1"), "UTF-8"));
        response.setContentType("image/" + qrCode.getFormat().toLowerCase()); // 相应类型为图片
        try {
            LOGGER.info("Encode: " + qrCode.getContent());
            factor.createQRCode(qrCode, response.getOutputStream());
        } catch (Exception e) {
            response.sendRedirect("encode.jsp");
            LOGGER.error(e.getMessage(), e);
        }

    }

    private boolean isValidParam(String content, String format, String valid) {
        if (content == null || format == null || valid == null) {
            return false;
        }

        return !content.equals("") && !format.equals("") && !valid.equals("");
    }
}
