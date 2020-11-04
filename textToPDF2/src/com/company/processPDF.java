package com.company;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class processPDF {

    public static void processingFunc(String path, String output) throws FileNotFoundException, DocumentException {
        
        //create a new File based on past .txt file
        File myObj = new File(path);
        
        //create a new document where the PDF will be inputted
        var doc = new Document();
        
        //Assign the document the title (output) that the .pdf will receive
        PdfWriter.getInstance(doc, new FileOutputStream(output));
        
        //Open the document for editing
        doc.open();
        
        //Assign default starting values
        int currentAlign = Element.ALIGN_JUSTIFIED;
        int styleInt = Font.NORMAL;
        int textSize = 12;
        int currentIndent = 0;
        
        //create new starting paragraph
        var currFont = new Font(Font.FontFamily.HELVETICA, textSize, styleInt);
        var paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);
        
        //start scanning the .txt file 
        Scanner myReader = new Scanner(myObj);
        var newParagraph = paragraph;

        //ensures that while there is still text in the .txt file, it will continue scanning the next line
        while (myReader.hasNextLine()) {

            //reads the next line in the .txt file and assigns the string to the data variable
            String data = myReader.nextLine();

            //Choose to not use a Switch because I wanted to use the contains function for .indent function
            //if the data equals the large command, the textSize is increased
            if (data.equals(".large")){
                //increases text size
                textSize = 30;
            } else if (data.equals(".normal") || data.equals(".regular")) {
                //resets text size and font style
                textSize = 12;
                styleInt = Font.NORMAL;
            } else if (data.equals(".italics")) {
                //sets font style to italic
                if (styleInt == Font.BOLD) {
                    styleInt = Font.BOLDITALIC;
                } else {
                    styleInt = Font.ITALIC;
                }
            } else if (data.equals(".bold")) {
                //sets font style to bold
                if (styleInt == Font.ITALIC) {
                    styleInt = Font.BOLDITALIC;
                } else {
                    styleInt = Font.BOLD;
                }
            } else if (data.contains(".indent"))  {
                //adds line break for styling
                newParagraph.add("\n");

                //splits the data at the space and then converts it to an integer
                String[] arrOfStr = data.split(" ", 2);
                int num = Integer.parseInt(arrOfStr[1]);

                //integer is then added to the total amount of indent which will be updated at end of while loop
                currentIndent += num;

            } else if (data.equals(".break")) {
                //creates a break line
                newParagraph.add("\n");
            } else if (data.equals(".fill")) {
                //sets justification to full
                currentAlign = Element.ALIGN_JUSTIFIED;
            } else if (data.equals(".nofill")) {
                //sets justification back to left
                currentAlign = Element.ALIGN_LEFT;
            } else if (data.equals(".paragraph")) {
                //creates a new paragraph with line breaks and starts a new paragraph object
                newParagraph.add("\n");
                newParagraph.add("\n");
                doc.add(newParagraph);
                newParagraph = new Paragraph();
            } else {
                //if none of the other commands are met, we can assume that we are supposed to add the text
                newParagraph.add(data);
            }
            //updates the current fields
            currFont = new Font(Font.FontFamily.HELVETICA, textSize, styleInt);
            newParagraph.setFont(currFont);
            newParagraph.setAlignment(currentAlign);
            newParagraph.setIndentationLeft(currentIndent*10);
        }
        //adds the last standing paragraph
        doc.add(newParagraph);

        //closes the doc and generates the new PDF
        doc.close();
    }
}
