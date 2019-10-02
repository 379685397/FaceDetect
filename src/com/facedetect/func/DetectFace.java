package com.facedetect.func;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * @Auther: DarkKing
 * @Date: 2019/10/2 11:06
 * @Description:
 */
public class DetectFace {
    private String basePath =System.getProperty("user.dir");
    private   String eyeConfigPath=basePath+"\\src\\com\\facedetect\\config\\haarcascade_eye_tree_eyeglasses.xml";
    private   String faceConfigPath=basePath+"\\src\\com\\facedetect\\config\\haarcascade_frontalface_alt2.xml";
    static{
        // 载入opencv的库
        String opencvpath = System.getProperty("user.dir") + "\\libs\\x64\\";
        String opencvDllName = opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll";
        System.load(opencvDllName);
    }
    /**
     * opencv实现人脸识别
     * @param imagePath
     * @param outFile
     * @throws Exception
     */
    public  void detectFace(String imagePath,  String outFile) throws Exception
    {

        System.out.println("Running DetectFace ...,config path is  "+faceConfigPath);
        String basePath =System.getProperty("user.dir");
        String path= basePath+ "\\src\\com\\facedetect\\tmp\\";
        // 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
        CascadeClassifier faceDetector = new CascadeClassifier(faceConfigPath);

        Mat image = Imgcodecs.imread(imagePath);

        // 在图片中检测人脸
        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        Rect[] rects = faceDetections.toArray();

        // 在每一个识别出来的人脸周围画出一个方框
        for (int i = 0; i < rects.length; i++) {
            Rect rect = rects[i];
            Imgproc.rectangle(image, new Point(rect.x-2, rect.y-2),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            Mat copy = new Mat(image,rect);
            Mat temp  = new Mat();
            copy.copyTo(temp);
            Imgcodecs.imwrite(path+i+".png", temp);
        }
        Imgcodecs.imwrite(outFile, image);
        System.out.println(String.format("人脸识别成功，人脸图片文件为： %s", outFile));


    }


    /**
     * opencv实现人眼识别
     * @param imagePath
     * @param outFile
     * @throws Exception
     */
    public  void detectEye(String imagePath,  String outFile) throws Exception {

        System.out.println("Running DetectFace ...,config path is  "+eyeConfigPath);
        CascadeClassifier eyeDetector = new CascadeClassifier(
                eyeConfigPath);

        Mat image = Imgcodecs.imread(imagePath);  //读取图片

        // 在图片中检测人脸
        MatOfRect faceDetections = new MatOfRect();

        eyeDetector.detectMultiScale(image, faceDetections, 2.0,1,1,new Size(20,20),new Size(20,20));

        System.out.println(String.format("Detected %s eyes", faceDetections.toArray().length));
        Rect[] rects = faceDetections.toArray();
        if(rects != null && rects.length <2){
            throw new RuntimeException("不是一双眼睛");
        }
        Rect eyea = rects[0];
        Rect eyeb = rects[1];


        System.out.println("a-中心坐标 " + eyea.x + " and " + eyea.y);
        System.out.println("b-中心坐标 " + eyeb.x + " and " + eyeb.y);

        //获取两个人眼的角度
        double dy=(eyeb.y-eyea.y);
        double dx=(eyeb.x-eyea.x);
        double len=Math.sqrt(dx*dx+dy*dy);
        System.out.println("dx is "+dx);
        System.out.println("dy is "+dy);
        System.out.println("len is "+len);

        double angle=Math.atan2(Math.abs(dy),Math.abs(dx))*180.0/Math.PI;
        System.out.println("angle is "+angle);

        for(Rect rect:faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));


        }
        Imgcodecs.imwrite(outFile, image);

        System.out.println(String.format("人眼识别成功，人眼图片文件为： %s", outFile));

    }
}
