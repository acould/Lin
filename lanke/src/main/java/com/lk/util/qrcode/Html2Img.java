package com.lk.util.qrcode;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicEditorPaneUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;



/**
 * @author myq
 * @description
 * @create 2018-12-21 15:32
 */
public class Html2Img {

    public static int DEFAULT_IMAGE_WIDTH = 1200;
    public static int DEFAULT_IMAGE_HEIGHT = 700;

    public static void main(String[] args) throws Exception{
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();

        String url = "http://www.baidu.com";
        String base64 = QrcodeUtil.putBase64(url, 218, 218);

        String img =
        "<img src= \""+base64+"\" width=\"218\" height=\"218\"/>";

        String html =
            "<style>.general_code {padding: 40px;}"
            +".general_code .code_img {width: 400px;height: 500px;background: url(\"https://wkbar.cc/uploadFiles/uploadImgs/QR_code.jpg\")no-repeat center center;background-size: 100%;}"
            +".general_code .code_img .qrcode {width: 218px;height: 218px;margin: 0 auto;padding-top: 32px;}"
            +".general_code .code_img .qrcode img{width: 100%;height: 100%;}"
            +".general_code .code_img h1{color: #fff;font-size: 24px;padding: 22px 50px;margin: 0;border: none;}"
            +".general_code .code_img,.general_code .code_msg{float: left;}</style>"

            +"<div class=\"code_img\" id=\"code_img\">"
            +"<h1><span id=\"num\">1号电脑</span></h1>"
            +"<div class=\"qrcode\" id=\"qrcode\">"+img+"</div>"
            +"</div>";


        imageGenerator.loadHtml(html);//也可以根据html url引用 loadUrl的方式加载
        imageGenerator.getBufferedImage();
        imageGenerator.saveAsImage("D:\\1.png");

    }

    public static byte[] html2jpeg(Color bgColor, String html, int width,
                                   int height, EmptyBorder eb) throws Exception {
        String imageName = "D:\\"+ UUID.randomUUID().toString() + ".jpg";
        JTextPane tp = new JTextPane();
        tp.setSize(width, height);
        if (eb == null) {
            eb = new EmptyBorder(0, 50, 0, 50);
        }
        if (bgColor != null) {
            tp.setBackground(bgColor);
        }
        if (width <= 0) {
            width = DEFAULT_IMAGE_WIDTH;
        }
        if (height <= 0) {
            height = DEFAULT_IMAGE_HEIGHT;
        }
        tp.setBorder(eb);
        tp.setContentType("text/html");
        tp.setText(html);

        int pageIndex = 1;
        boolean bcontinue = true;
        String resUrl = "";
        byte[] bytes = null;
        while (bcontinue) {
            BufferedImage image = new BufferedImage(width,
                    height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setClip(0, 0, width, height);
            bcontinue = paintPage(g, height, pageIndex, tp);
            g.dispose();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);

            baos.flush();
            bytes = baos.toByteArray();
            baos.close();
            FileUtils.writeByteArrayToFile(new File(imageName), bytes);
            //写入阿里云
            pageIndex++;
        }
        return bytes;
    }


    public static boolean paintPage(Graphics g, int hPage, int pageIndex, JTextPane panel) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = ((BasicEditorPaneUI) panel.getUI()).getPreferredSize(panel);
        double panelHeight = d.height;
        double pageHeight = hPage;
        int totalNumPages = (int) Math.ceil(panelHeight / pageHeight);
        g2.translate(0f, -(pageIndex - 1) * pageHeight);
        panel.paint(g2);
        boolean ret = true;

        if (pageIndex >= totalNumPages) {
            ret = false;
            return ret;
        }
        return ret;
    }

}
