//package nodium.group.backend.utils;
//
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//
//import java.io.File;
//
//public class ImageTesxtScrapper {
//    public static void main(String[] args) throws TesseractException {
//        File file = new File("C:\\Users\\DELL\\OneDrive\\Pictures\\Screenshots\\" +
//                        "Screenshot 2024-07-04 142337.png");
//        Tesseract tesseract = new Tesseract();
//        tesseract.setLanguage("eng");
//        String text = tesseract.doOCR(file);
//        System.out.println("text = " + text);
//    }
//}
//
