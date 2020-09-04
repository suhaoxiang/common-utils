package com.haoisou.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类：
 * 需要google zxing实现
 * <p>
 * 具体思路：
 * 将需要的生成的信息，生成二维码
 * 写入输出流
 * 前端获取到输出流,将流显示出来
 *
 * @author 廖双龙
 * @date 2019/2/27.
 */
public class QrCodeUtil {


    /**
     * 二维码宽
     */
    private static final int QRCODE_WIDTH = 300;

    /**
     * 二维码高
     */
    private static final int QRCODE_HEIGHT = 300;

    /**
     * 二维码图片格式
     */
    private static final String QRCODE_FORMAT = "png";

    /**
     * 二维码颜色，黑色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 二维码背景色,白色
     */
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 二维码编码参数
     */
    private static final Map<EncodeHintType, Object> HINTS = new HashMap<>();

    static {
        // 纠错信息,共四级，纠错级别越高，纠错信息占用的空间越多，那么能存储的有用讯息就越少
        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        HINTS.put(EncodeHintType.MARGIN, 1);
        HINTS.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    }

    /**
     * 生成二维码为base64的字符串
     *
     * @param content 二维码内容
     * @param logoUrl 二维码logo图url
     * @return 返回二维码base64字符串
     */
    public static String genQrcodeToBase64Str(String content, String logoUrl) {
        byte[] bytes = genQrcodeToByteArray(content, logoUrl);
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * 生成二维码为byte数组
     *
     * @param content 二维码内容
     * @param logoUrl 二维码图标url
     * @return 返回结果
     */
    public static byte[] genQrcodeToByteArray(String content, String logoUrl) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        genQrcodeToStream(content, logoUrl, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 生成二维码到文件
     *
     * @param content 二维码内容
     * @param logoUrl logo地址
     * @param file    生成到哪个文件
     * @return 返回图片文件
     */
    public static File genQrcodeToFile(String content, String logoUrl, File file) {
        BufferedImage bufferedImage = genLogoQrcode(content, logoUrl);

        try {
            ImageIO.write(bufferedImage, QRCODE_FORMAT, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 生成二维码到输出流
     *
     * @param content      二维码内容
     * @param logoUrl      logo标签
     * @param outputStream 输出流
     * @return 输出流
     */
    public static void genQrcodeToStream(String content, String logoUrl, OutputStream outputStream) {
        BufferedImage bufferedImage = genLogoQrcode(content, logoUrl);

        try {
            ImageIO.write(bufferedImage, QRCODE_FORMAT, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析二维码文件
     *
     * @param inputStream 二维码文件输入流
     * @return 解析成功后的字符串
     */
    public static String decodeQrcode(InputStream inputStream) {
        // 解析二维码内容
        BufferedImage image;
        try {
            image = ImageIO.read(inputStream);

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>(16);
            //解码设置编码方式为：UTF-8
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            //优化精度
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            //复杂模式，开启PURE_BARCODE模式
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

            // 对图像进行解码
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @return 返回图片
     */
    private static BufferedImage genQrcode(String content) {

        BufferedImage bufferedImage = null;
        try {
            BitMatrix bm = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT, HINTS);
            int width = bm.getWidth();
            int height = bm.getHeight();

            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bm.get(x, y) ? BLACK : WHITE);
                }
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    /**
     * 生成带logo的图片
     *
     * @param content 二维码内容
     * @param logo    logo图片url地址
     * @return 返回输出流
     */
    private static BufferedImage genLogoQrcode(String content, String logo) {

        BufferedImage bufferedImage = genQrcode(content);

        Image image = readLogoImage(logo);

        if (image != null) {
            insertImage(image, bufferedImage);
        }

        return bufferedImage;
    }

    /**
     * 从url中读取图片
     *
     * @param logoUrl 二维码logo的url地址
     * @return 返回图片image对象
     */
    private static Image readLogoImage(String logoUrl) {

        if (StringUtils.isEmpty(logoUrl)) {
            return null;
        }

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(logoUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image image = null;
        try {
            assert httpURLConnection != null;
            image = ImageIO.read(httpURLConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }


    /**
     * 插入logo到二维码图片中
     *
     * @param logo          logo图片
     * @param bufferedImage 二维码图片
     */
    private static void insertImage(Image logo, BufferedImage bufferedImage) {
        int logoWidth = 60;
        int logoHeight = 60;

        Graphics2D graph = bufferedImage.createGraphics();
        // x,y坐标为logo的位置
        int x = (bufferedImage.getWidth() - logoWidth) / 2;
        int y = (bufferedImage.getHeight() - logoHeight) / 2;

        // 开始绘图
        graph.drawImage(logo, x, y, logoWidth, logoHeight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 15, 15);
        graph.setStroke(new BasicStroke(3f));
        graph.setColor(Color.WHITE);
        graph.draw(shape);
        graph.dispose();
        logo.flush();
        bufferedImage.flush();
    }
}
