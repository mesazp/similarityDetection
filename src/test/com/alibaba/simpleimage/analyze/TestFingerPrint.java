package com.alibaba.simpleimage.analyze;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;


public class TestFingerPrint {

    @Test
    public void testCompare() throws IOException{
        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File("d:/img/1.jpg")));
        FingerPrint fp2 =new FingerPrint(ImageIO.read(new File("d:/img/2.jpg")));
        System.out.println(fp1.toString(true));
        System.out.printf("sim=%f",fp1.compare(fp2));
    }
}