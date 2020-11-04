package com.company;

import com.itextpdf.text.*;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        //create new instance of the processPDF class
        processPDF newPDFs = new processPDF();
        //supply the path (the text file you want to read) and the output (what you want the output PDF to show).
        newPDFs.processingFunc("threePageTester.txt", "PreDeterminedTest.pdf");
        newPDFs.processingFunc("Assumptions.txt", "myAssumptions.pdf");
    }
}
