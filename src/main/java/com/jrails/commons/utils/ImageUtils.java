/* ImageUtils.java
 * --------------------------------------
 * CREATED ON Jun 19, 2006 4:44:25 AM
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;

/**
 * Utilities methods for image manipulation.
 * It does not support writting of GIF images, but it
 * can read from. GIF images will be saved as PNG.
 * <p/>
 * Date: Jun 19, 2006 4:44:25 AM
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public class ImageUtils {

    public static final int IMAGE_UNKNOWN = -1;
    public static final int IMAGE_JPEG = 0;
    public static final int IMAGE_PNG = 1;
    public static final int IMAGE_GIF = 2;

    /**
     * Resizes an image
     *
     * @param imgName   The image name to resize. Must be the complet path to the file
     * @param maxWidth  The image's max width
     * @param maxHeight The image's max height
     * @return A resized <code>BufferedImage</code>
     * @throws IOException If the file is not found
     */
    public static BufferedImage resizeImage(File file, int type, int maxWidth, int maxHeight) throws IOException {
        return resizeImage(ImageIO.read(file), type, maxWidth, maxHeight);
    }

    /**
     * Resizes an image
     *
     * @param imgName   The image name to resize. Must be the complet path to the file
     * @param maxWidth  The image's max width
     * @param maxHeight The image's max height
     * @return A resized <code>BufferedImage</code>
     * @throws IOException If the file is not found
     */
    public static BufferedImage resizeImage(String imgName, int type, int maxWidth, int maxHeight) throws IOException {
        return resizeImage(ImageIO.read(new File(imgName)), type, maxWidth, maxHeight);
    }

    public static BufferedImage resizeImage(URL url, int type, int maxWidth, int maxHeight) throws IOException {
        return resizeImage(ImageIO.read(url), type, maxWidth, maxHeight);
    }

    /**
     * Resizes an image.
     *
     * @param image     The image to resize
     * @param maxWidth  The image's max width
     * @param maxHeight The image's max height
     * @return A resized <code>BufferedImage</code>
     */
    public static BufferedImage resizeImage(Image image, int type, int maxWidth, int maxHeight) {
        Dimension largestDimension = new Dimension(maxWidth, maxHeight);

        // Original size
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        float aspectRation = (float) imageWidth / imageHeight;

        if (imageWidth > maxWidth || imageHeight > maxHeight) {
            if ((float) largestDimension.width / largestDimension.height > aspectRation) {
                largestDimension.width = (int) Math.ceil(largestDimension.height * aspectRation);
            } else {
                largestDimension.height = (int) Math.ceil(largestDimension.width / aspectRation);
            }

            imageWidth = largestDimension.width;
            imageHeight = largestDimension.height;
        }

        return createBufferedImage(image, type, imageWidth, imageHeight);
    }

    /**
     * Saves an image to the disk.
     *
     * @param image      The image to save
     * @param toFileName The filename to use
     * @param type       The image type. Use <code>ImageUtils.IMAGE_JPEG</code> to save as JPEG
     *                   images, or <code>ImageUtils.IMAGE_PNG</code> to save as PNG.
     * @return <code>false</code> if no appropriate writer is found
     * @throws IOException
     */
    public static boolean saveImage(BufferedImage image, String toFileName, int type) throws IOException {
        return ImageIO.write(image, type == IMAGE_JPEG ? "jpg" : "png", new File(toFileName));
    }

    /**
     * Compress and save an image to the disk.
     * Currently this method only supports JPEG images.
     *
     * @param image      The image to save
     * @param toFileName The filename to use
     * @param type       The image type. Use <code>ImageUtils.IMAGE_JPEG</code> to save as JPEG
     *                   images, or <code>ImageUtils.IMAGE_PNG</code> to save as PNG.
     * @param compress   Set to <code>true</code> if you want to compress the image.
     * @return <code>false</code> if no appropriate writer is found
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void saveCompressedImage(BufferedImage image, String toFileName, int type) throws IOException {
        if (type == IMAGE_PNG) {
            throw new UnsupportedOperationException("PNG compression not implemented");
        }

        ImageWriter writer = null;

        Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
        writer = (ImageWriter) iter.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(new File(toFileName));
        writer.setOutput(ios);

        ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());

        iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwparam.setCompressionQuality(0.7F);

        writer.write(null, new IIOImage(image, null, null), iwparam);

        ios.flush();
        writer.dispose();
        ios.close();
    }

    /**
     * Creates a <code>BufferedImage</code> from an <code>Image</code>.
     *
     * @param image The image to convert
     * @param w     The desired image width
     * @param h     The desired image height
     * @return The converted image
     */
    public static BufferedImage createBufferedImage(Image image, int type, int w, int h) {
        if (type == ImageUtils.IMAGE_PNG && hasAlpha(image)) {
            type = BufferedImage.TYPE_INT_ARGB;
        } else {
            type = BufferedImage.TYPE_INT_RGB;
        }

        BufferedImage bi = new BufferedImage(w, h, type);

        Graphics g = bi.createGraphics();
        g.drawImage(image, 0, 0, w, h, null);
        g.dispose();

        return bi;
    }

    /**
     * Determines if the image has transparent pixels.
     *
     * @param image The image to check for transparent pixel.s
     * @return <code>true</code> of <code>false</code>, according to the result
     * @throws InterruptedException
     */
    public static boolean hasAlpha(Image image) {
        try {
            PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
            pg.grabPixels();

            return pg.getColorModel().hasAlpha();
        } catch (InterruptedException e) {
            return false;
        }
    }
}