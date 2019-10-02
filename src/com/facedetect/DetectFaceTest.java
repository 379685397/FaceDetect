package com.facedetect;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.facedetect.func.DetectFace;
import com.facedetect.func.ImageUtils;
import com.sun.imageio.plugins.common.ImageUtil;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * DrakKing
 */
public class DetectFaceTest {

    public static void main(String[] args) throws Exception {
        DetectFace df = new DetectFace();
         String basePath =System.getProperty("user.dir");
        String s1= basePath+ "\\src\\com\\facedetect\\image\\test1.jpg";
        String s2= basePath+ "\\src\\com\\facedetect\\image\\test2.jpg";
        String s3= basePath+ "\\src\\com\\facedetect\\image\\test3.jpg";
        String e1= basePath+ "\\src\\com\\facedetect\\image\\1.png";
        String faceTemp = basePath+"\\src\\com\\facedetect\\tmp\\faceTemp.png";
        String eyeTemp = basePath+"\\src\\com\\facedetect\\tmp\\eyeTemp.png";
        //人脸识别
        df.detectFace(s1, faceTemp);

        //人眼识别
      df.detectEye(e1,  eyeTemp);

        //图片裁切
       // ImageUtils.imageCut(s3,temp, 50, 50,100,100);

        //设置图片为半透明
//        ImageUtils.setAlpha(s, temp);


        //为图片添加水印
//        ImageUtils.watermark(s,"E:\\ling.jpg",temp, 0.2f);


        //图片合成
//        ImageUtils.simpleMerge(s, "E:\\ling.jpg", 45, 50, temp);

    }
}