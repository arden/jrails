package com.jrails.commons.image;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import javax.imageio.ImageIO;

public class ChangeImageSize{

    public static void scale(String srcImageFile, String result, int scale, boolean flag)
  	{
        try
        {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            int width = src.getWidth();
            int height = src.getHeight();
            if (flag)
            {
                width = width * scale;
                height = height * scale;
            }
            else
            {
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));
        }
        catch (IOException e)
       	{
            e.printStackTrace();
        }
    }

    public static void cut(String srcImageFile, String descDir, int destWidth, int destHeight)
    {
        try
        {
            Image img;
            ImageFilter cropFilter;
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight();
            int srcHeight = bi.getWidth();
            if (srcWidth > destWidth && srcHeight > destHeight)
            {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                destWidth = 200;
                destHeight = 150;
                int cols = 0;
                int rows = 0;

                if (srcWidth % destWidth == 0)
                {
                    cols = srcWidth / destWidth;
                }
                else
                {
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0)
                {
                    rows = srcHeight / destHeight;
                }
                else
                {
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
                }

                for (int i = 0; i < rows; i++)
                {
                    for (int j = 0; j < cols; j++)
                    {

                        cropFilter = new CropImageFilter(j * 200, i * 150, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                        new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null);
                        g.dispose();
                        ImageIO.write(tag, "JPEG", new File(descDir + "pre_map_" + i + "_" + j + ".jpg"));
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void convert(String source, String result)
    {
        try
        {
            File f = new File(source);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, "JPG", new File(result));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void gray(String source, String result)
    {
        try
        {
            BufferedImage src = ImageIO.read(new File(source));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(result));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /** *//**
     * @param args
     */
    public static void main(String[] args)
    {
        scale("c:\\java\\1.jpg","C:\\java\\image.jpg",10,false);
    }

}
