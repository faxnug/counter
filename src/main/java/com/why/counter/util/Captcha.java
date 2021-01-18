package com.why.counter.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * @Author WHY
 * @Date 2021-01-14
 * @Version 1.0
 */
public class Captcha {
    /**
     * 验证码
     */
    private String code;

    /**
     * 图片
     */
    private BufferedImage bufferedImage;

    /**
     * 随机数发生器
     */
    private Random random = new Random();

    /**
     * 验证码构造函数
     * @param width         验证码宽度
     * @param height        验证码高度
     * @param codeCount     验证码个数
     * @param lineCount     线条数
     */
    public Captcha(int width, int height, int codeCount, int lineCount){
        //1.生成图像
        //定义一个图像，指定宽度和高度，定义为RGB
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //2.背景色
        //定义一个画笔
        Graphics g = bufferedImage.getGraphics();
        //设定画笔的颜色
        g.setColor(getRandColor(200,250));
        //指定画笔填充的区域
        g.fillRect(0,0,width,height);
        //指定画笔的字体
        Font font = new Font("Fixedsys",Font.BOLD,height-5);
        g.setFont(font);

        //3.生成干扰线
        for(int i=0;i<lineCount;i++){
            //生成第一个点的横坐标和纵坐标
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            //生成第二个点的横坐标和纵坐标
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            //设置画笔颜色
            g.setColor(getRandColor(1,255));
            //画线
            g.drawLine(xs, ys, xe, ye);
        }

        //4.生成噪点
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            //设置指定坐标点的颜色
            bufferedImage.setRGB(x, y, random.nextInt(255));
        }

        /**
         * 5.添加字符
         */
        //生成指定数量的字符串
        this.code = randomStr(codeCount);
        //字符的宽度和高度
        int fontWidth = width / codeCount;
        int fontHeight = height - 5;
        for(int i = 0;i < codeCount;i++){
            //获取字符
            String str = this.code.substring(i,i+1);
            //指定画笔颜色
            g.setColor(getRandColor(1,255));
            //画字符
            g.drawString(str,i * fontWidth + 3,fontHeight - 3);
        }
    }

    /**
     * 随机生成字符
     * @param codeCount
     * @return
     */
    private String randomStr(int codeCount) {
        //定义一个字符集
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

        StringBuilder sb = new StringBuilder();

        int len = str.length() - 1;

        double r;

        for(int i=0;i<codeCount;i++){
            r = (Math.random())*len;
            sb.append(str.charAt((int)r));
        }

        return sb.toString();
    }

    /**
     * 获取指定区间的随机颜色，1-255
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        if(fc > 255){
            fc = 255;
        }

        if(bc > 255){
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }

    /**
     * 获取小写的字符串
     * @return
     */
    public String getCode(){
        return code.toLowerCase();
    }

    /**
     *  获取 base64 编码的字符串
     * @return
     * @throws IOException
     */
    public String getBase64ByteStr() throws IOException {
        //定义一个字节输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        //
        String s = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        s.replaceAll("\n","").replaceAll("\r","");

        return "data:image/jpg;base64," + s;
    }
}
