package com.jrails.commons.image;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.imageio.ImageIO.*;
import javax.imageio.IIOException;
import java.io.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import javax.servlet.http.HttpSession;
//������  
// ͼƬ�������ı��С��ˮӡ  
public class ImageOperate {


    public void waterMark(String strOriginalFileName, String strWaterMarkFileName) {
        try {
            //String root=session.getServletContext().getRealPath("/");
            //File fileOriginal = new File(root+strOriginalFileName);
            File fileOriginal = new File(strOriginalFileName);
            Image imageOriginal = ImageIO.read(fileOriginal);
            int widthOriginal = imageOriginal.getWidth(null);
            int heightOriginal = imageOriginal.getHeight(null);
            System.out.println("widthOriginal:" + widthOriginal + "theightOriginal:" + heightOriginal);
            BufferedImage bufImage = new BufferedImage(widthOriginal, heightOriginal, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufImage.createGraphics();
            g.drawImage(imageOriginal, 0, 0, widthOriginal, heightOriginal, null);

            //File fileWaterMark = new File(root+strWaterMarkFileName);
            File fileWaterMark = new File(strWaterMarkFileName);
            Image imageWaterMark = ImageIO.read(fileWaterMark);
            int widthWaterMark = imageWaterMark.getWidth(null);
            int heightWaterMark = imageWaterMark.getHeight(null);
            System.out.println("widthWaterMark:" + widthWaterMark + "theightWaterMark:" + heightWaterMark);

            g.drawImage(imageWaterMark, widthOriginal - widthWaterMark, heightOriginal - heightWaterMark, widthWaterMark, heightWaterMark, null);
            g.dispose();
            //FileOutputStream fos = new FileOutputStream( root+strOriginalFileName);
            FileOutputStream fos = new FileOutputStream(strOriginalFileName);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
            encoder.encode(bufImage);
            fos.flush();
            fos.close();
            fos = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ImageOperate().waterMark("c:/java/2.jpg", "c:/java/2.gif");
    }
}
