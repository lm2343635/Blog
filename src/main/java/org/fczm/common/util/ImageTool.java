package org.fczm.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 * @author 浮尘追梦
 * @version 3.1
 */
public final class ImageTool {
    //图片格式：JPG
    private static final String PICTRUE_FORMATE_JPG = "jpg";
    
    private static final int[] RGB_MASKS={0xFF0000, 0xFF00, 0xFF};
    private static final ColorModel RGB_OPAQUE=new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
    
    /**
     * 添加图片水印
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param waterImg  水印图片路径，如：C://myPictrue//logo.png
     * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            //水印文件
            Image waterImage = ImageIO.read(new File(waterImg));    
            int width_1 = waterImage.getWidth(null);
            int height_1 = waterImage.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if(x < 0){
                x = widthDiff / 2;
            }else if(x > widthDiff){
                x = widthDiff;
            }
            if(y < 0){
                y = heightDiff / 2;
            }else if(y > heightDiff){
                y = heightDiff;
            }
            //水印文件结束
            g.drawImage(waterImage, x, y, width_1, height_1, null); 
            g.dispose();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加文字水印
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName 字体名称，    如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize 字体大小，单位为像素
     * @param color 字体颜色
     * @param x 水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	*/
    public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);
            
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            
            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if(x < 0){
                x = widthDiff / 2;
            }else if(x > widthDiff){
                x = widthDiff;
            }
            if(y < 0){
                y = heightDiff / 2;
            }else if(y > heightDiff){
                y = heightDiff;
            }
            
            g.drawString(pressText, x, y + height_1);
            g.dispose();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

	/**
	 * 根据尺寸图片居中裁剪
	 * @param src
	 * @param dest
	 * @param w
	 * @param h
	 * @throws IOException
	 */
	 @SuppressWarnings("rawtypes")
	public static void cutCenterImage(String src,String dest,int w,int h) throws IOException { 
		 Iterator iterator = ImageIO.getImageReadersByFormatName("jpg"); 
         ImageReader reader = (ImageReader)iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, (reader.getHeight(imageIndex)-h)/2, w, h);  
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         ImageIO.write(bi, PICTRUE_FORMATE_JPG, new File(dest));           
	 }
	 
    /**
     * 图片缩放
     * @param pathname 图片路径
     * @param height 高度
     * @param width 宽度
     * @param bb 比例不对时是否需要补白
     */
    public static void resize(String pathname, int height, int width, boolean bb) {
        try {
            double ratio = 0; //缩放比例    
            File f = new File(pathname);   
            BufferedImage bi = ImageIO.read(f);   
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);   
            //计算比例   
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {   
                if (bi.getHeight() > bi.getWidth()) {   
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();   
                } else {   
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();   
                }   
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);   
                itemp = op.filter(bi, null);   
            }   
            if (bb) {   
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
                Graphics2D g = image.createGraphics();   
                g.setColor(Color.white);   
                g.fillRect(0, 0, width, height);   
                if (width == itemp.getWidth(null))   
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);   
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);   
                g.dispose();   
                itemp = image;   
            }
            ImageIO.write((BufferedImage) itemp, PICTRUE_FORMATE_JPG, f);   
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * 图片缩放
     * @param pathname 图片路径
     * @param height 高度
     * @param width 宽度
     * @param degree 旋转度数
     */
    public static boolean createThumbnail(String pathname, int width,int height, int degree) {
    	//缩放比例    
        double ratio = 0; 
        File file=new File(pathname);   
        try {
        	//修复png格式图片文件变红
        	Image img=Toolkit.getDefaultToolkit().createImage(pathname);
        	PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);
        	pg.grabPixels();
        	DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
        	WritableRaster raster = Raster.createPackedRaster(buffer, pg.getWidth(), pg.getHeight(), pg.getWidth(), RGB_MASKS, null);
        	//以上修复png格式图片文件变红的问题
        	BufferedImage image= new BufferedImage(RGB_OPAQUE, raster, false, null);
        	image=rotateImage(image, degree);
            Image itemp = image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);   
            //计算比例   
            if ((image.getHeight() > height)||(image.getWidth() > width)) {   
                if (image.getWidth()*1.0/image.getHeight()>1.0*width/height) {
                	 ratio=height*1.0/image.getHeight();   
                }  else {
                	 ratio=width*1.0/image.getWidth();   
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);   
                itemp = op.filter(image, null);   
                ImageIO.write((BufferedImage) itemp, PICTRUE_FORMATE_JPG, file);   
                cutCenterImage(pathname, pathname, width, height);
                return true;
            }      
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /**
     * 旋转图片
     * @param bufferedimage
     * @param degree 旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
    
    /**
     * 得到图片高度
     * @param pathname
     * @return
     */
    public static int getImageHeight(String pathname) {
    	BufferedImage image;
		try {
			image = ImageIO.read(new File(pathname));
			return image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return 0;
    }
    
    /**
     * 得到图片宽度
     * @param pathname
     * @return
     */
    public static int getImageWidth(String pathname) {
    	BufferedImage image;
		try {
			image = ImageIO.read(new File(pathname));
			return image.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return 0;
    }
      
}