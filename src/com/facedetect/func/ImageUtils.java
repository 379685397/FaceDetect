package com.facedetect.func;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Auther: 王正强
 * @Date: 2019/10/2 11:12
 * @Description:
 */
public class ImageUtils {

    /**
     * 裁剪图片并重新装换大小
     * @param imagePath
     * @param posX
     * @param posY
     * @param width
     * @param height
     * @param outFile
     */
    public static void imageCut(String imagePath,String outFile, int posX,int posY,int width,int height ){

        //原始图像
        Mat image = Imgcodecs.imread(imagePath);

        //截取的区域：参数,坐标X,坐标Y,截图宽度,截图长度
        Rect rect = new Rect(posX,posY,width,height);

        //两句效果一样
        Mat sub = image.submat(rect);   //Mat sub = new Mat(image,rect);

        Mat mat = new Mat();
        Size size = new Size(300, 300);
        Imgproc.resize(sub, mat, size);//将人脸进行截图并保存

        Imgcodecs.imwrite(outFile, mat);
        System.out.println(String.format("图片裁切成功，裁切后图片文件为： %s", outFile));

    }


    /**
     *
     * @param imagePath
     * @param outFile
     */
    public static void setAlpha(String imagePath,  String outFile) {
        /**
         * 增加测试项
         * 读取图片，绘制成半透明
         */
        try {

            ImageIcon imageIcon = new ImageIcon(imagePath);

            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(),
                    imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();

            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

            //循环每一个像素点，改变像素点的Alpha值
            int alpha = 100;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);
                    rgb = ( (alpha + 1) << 24) | (rgb & 0x00ffffff);
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

            //生成图片为PNG
            ImageIO.write(bufferedImage, "png",  new File(outFile));

            System.out.println(String.format("绘制图片半透明成功，图片文件为： %s", outFile));

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 为图像添加水印
     * @param buffImgFile 底图
     * @param waterImgFile 水印
     * @param outFile 输出图片
     * @param alpha   透明度
     * @throws IOException
     */
    private static void watermark(String buffImgFile,String waterImgFile,String outFile, float alpha) throws IOException {
        // 获取底图
        BufferedImage buffImg = ImageIO.read(new File(buffImgFile));

        // 获取层图
        BufferedImage waterImg = ImageIO.read(new File(waterImgFile));

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();

        int waterImgWidth = waterImg.getWidth();// 获取水印层图的宽度

        int waterImgHeight = waterImg.getHeight();// 获取水印层图的高度

        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        // 绘制
        g2d.drawImage(waterImg, 0, 0, waterImgWidth, waterImgHeight, null);

        g2d.dispose();// 释放图形上下文使用的系统资源

        //生成图片为PNG
        ImageIO.write(buffImg, "png",  new File(outFile));

        System.out.println(String.format("图片添加水印成功，图片文件为： %s", outFile));
    }


    /**
     * 图片合成
     * @param image1
     * @param image2
     * @param posw
     * @param posh
     * @param outFile
     * @return
     */
    public static void simpleMerge(String image1, String image2, int posw, int posh, String outFile) throws IOException{

        // 获取底图
        BufferedImage buffImg1 = ImageIO.read(new File(image1));

        // 获取层图
        BufferedImage buffImg2 = ImageIO.read(new File(image2));

        //合并两个图像
        int w1 = buffImg1.getWidth();
        int h1 = buffImg1.getHeight();

        int w2 = buffImg2.getWidth();
        int h2 = buffImg2.getHeight();

        BufferedImage imageSaved = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_ARGB); //创建一个新的内存图像

        Graphics2D g2d = imageSaved.createGraphics();

        g2d.drawImage(buffImg1, null, 0, 0);  //绘制背景图像

        for (int i = 0; i < w2; i++) {
            for (int j = 0; j < h2; j++) {
                int rgb1 = buffImg1.getRGB(i + posw, j + posh);
                int rgb2 = buffImg2.getRGB(i, j);

                /*if (rgb1 != rgb2) {
                    rgb2 = rgb1 & rgb2;
                }*/
                imageSaved.setRGB(i + posw, j + posh, rgb2); //修改像素值
            }
        }

        ImageIO.write(imageSaved, "png", new File(outFile));

        System.out.println(String.format("图片合成成功，合成图片文件为： %s", outFile));

    }
}
