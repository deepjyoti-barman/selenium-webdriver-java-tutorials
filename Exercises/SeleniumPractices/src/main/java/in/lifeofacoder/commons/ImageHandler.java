package in.lifeofacoder.commons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageHandler {

    public static void convert(URL source, String format, String destination) throws IOException {
        BufferedImage image = ImageIO.read(source);
        ImageIO.write(image, format, new File(destination));
    }

    public static void convert(File source, String format, String destination) throws IOException {
        BufferedImage image = ImageIO.read(source);
        ImageIO.write(image, format, new File(destination));
    }

    public static void main(String[] args) {
        String imgUrl = "https://images-eu.ssl-images-amazon.com/images/G/31/img21/CEPC/Clearance/May21/V238940049_IN_PC_BAU_Edit_Creation_Laptops2X._SY608_CB667377204_.jpg";

        try {
            convert(new URL(imgUrl), "png", "src/test/resources/download/laptop.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
