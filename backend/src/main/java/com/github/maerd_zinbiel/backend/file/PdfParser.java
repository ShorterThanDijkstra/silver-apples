package com.github.maerd_zinbiel.backend.file;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class PdfParser {

    private static void generateHTMLFromPDF() throws IOException {
        String in = "src/main/resources/com/github/maerd_zinbiel/backend/file/Merriam-Websters Vocabulary Builder.pdf";
        String out = "src/main/resources/com/github/maerd_zinbiel/backend/file/out.pdf";
        PDDocument pdf = PDDocument.load(new File(in));
        PDPage page = pdf.getPage(12);
        Writer output = new PrintWriter(out, StandardCharsets.UTF_8);
        new PDFTextStripper().writeText();
        OutputStream outStream = new FileOutputStream(out);

        InputStream contents = pdf.getPage(11).getContents();
        outStream.write(contents.readAllBytes());
    }

    public static void main(String[] args) throws IOException {
        generateHTMLFromPDF();
    }
}
