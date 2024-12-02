package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class TriggerOverFlowTest {
    final BufferedImage[] image = new BufferedImage[999999];
    final byte[][] imgBytes = new byte[999999][];

    public TriggerOverFlowTest() throws IOException {
        for (int i = 0; i < 999999; i++) {
            image[i] = ImageIO.read(new File("C:\\Users\\marco\\Desktop\\image-1 .jpg"));
        }
        System.out.println("Imagem Reads");
        for (int i = 0; i < 999999; i++) {
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(image[i], "jpg", boas);
            imgBytes[i] = boas.toByteArray();
        }
        System.out.println("Bytes Read");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("chamaaaa");
        new TriggerOverFlowTest();

    }
}
