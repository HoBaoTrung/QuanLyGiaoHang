package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.dto.response.OrderDetailResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(htmlContent, true); // true = isHtml
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("trungh7430@gmail.com");

        mailSender.send(mimeMessage);
    }

    public void sendEmailAlertSuccess(String to, OrderDetailResponse p) throws MessagingException {

        String subject = "Bạn được chọn để giao hàng";
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <style>" +
                "        .button {\n" +
                "  border: none;\n" +
                "  color: white;\n" +
                "  padding: 16px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  margin: 4px 2px;\n" +
                "  transition-duration: 0.4s;\n" +
                "  cursor: pointer;\n" +
                "}\n" +
                ".button1 {\n" +
                "  background-color: white; \n" +
                "  color: black; \n" +
                "  border: 2px solid #04AA6D;\n" +
                "}\n" +
                ".button1:hover {\n" +
                "  background-color: #04AA6D;\n" +
                "  color: white;\n" +
                "}\n" +

                "    </style>" +
                "</head>" +
                "<body>" +
                "<h1 style='color:green;'>CÓ ĐƠN HÀNG MỚI CHỜ BẠN GIAO</h1>" +
                "<h2>Tên sản phẩm: " + p.getName() + "</h2>" +
                "<h2>Giao hàng từ: " + p.getFromDistrictId().getDistrictName()  + "</h2>" +
                "<h2>Giao hàng đến: " + p.getToDistrictId().getDistrictName()  + "</h2>" +

                "<img src='" + p.getImage() + "'/>" +
                "<div><a href=\"#\" class=\"button button1\">Xem chi tiết</a></div>" +
                "</body>" +
                "</html>";

        sendHtmlEmail(to, subject, htmlContent);
    }

    @Async
    public CompletableFuture<Void> sendRefuseEmail(Set<DauGia> setDauGia, OrderDetailResponse p) {

        String subject = "Rất tiếc,đã có người khác nhận đơn";
        String htmlContent = "<html>" +
                "<body>" +
                "<h1 style='color:red;'>XIN LỖI, ĐƠN ĐÃ CÓ NGƯỜI KHÁC NHẬN</h1>" +
                "<h2>Tên sản phẩm: " + p.getName() + "</h2>" +
                "<h2>Giao hàng từ: " + p.getFromDistrictId().getDistrictName()  + "</h2>" +
                "<h2>Giao hàng đến: " + p.getToDistrictId().getDistrictName()  + "</h2>" +

                "<img src='" + p.getImage() + "'/>" +
                "</body>" +
                "</html>";

        for (DauGia d : setDauGia) {
            try {
                sendHtmlEmail(d.getShipper().getUser().getEmail(), subject, htmlContent);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    public void sendProofFromShipper(Product p) throws MessagingException {

        String subject = "ĐƠN HÀNG CỦA BẠN ĐÃ ĐƯỢC GIAO";
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <style>" +
                "        .button {\n" +
                "  border: none;\n" +
                "  color: white;\n" +
                "  padding: 16px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  margin: 4px 2px;\n" +
                "  transition-duration: 0.4s;\n" +
                "  cursor: pointer;\n" +
                "}\n" +
                ".button1 {\n" +
                "  background-color: white; \n" +
                "  color: black; \n" +
                "  border: 2px solid #04AA6D;\n" +
                "}\n" +
                ".button1:hover {\n" +
                "  background-color: #04AA6D;\n" +
                "  color: white;\n" +
                "}\n" +

                "    </style>" +
                "</head>" +
                "<body>" +

                "<h2>Tên sản phẩm: " + p.getName() + "</h2>" +
                "<h2>Giao hàng từ: " + p.getFromDistrictId().getDistrictName()  + "</h2>" +
                "<h2>Giao hàng đến: " + p.getToDistrictId().getDistrictName()  + "</h2>" +
                "<h2 style='color:green;'>ẢNH BẰNG CHỨNG</h2>" +
                "<img src='" + p.getProof().getImage() + "'/>" +
                "<div><a href=\"#\" class=\"button button1\">Xem chi tiết</a></div>" +
                "</body>" +
                "</html>";

        sendHtmlEmail(p.getUser().getEmail(), subject, htmlContent);
    }
}

