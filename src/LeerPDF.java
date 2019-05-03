import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class LeerPDF {

private PDFParser parser;
private String parsedText;
private PDFTextStripper pdfStripper;
private PDDocument pdDoc;
private COSDocument cosDoc;

// PDFTextParser Constructor
public LeerPDF() {}

// Extract text from PDF Document
public void pdftoText(String fileName, String nomb) {
	File f = new File(fileName);
	if (!f.isFile()) {
		System.out.println("File " + fileName + " does not exist.");
	}
	try {
		parser = new PDFParser(new FileInputStream(f));
	} catch (Exception e) {
		System.out.println("Unable to open PDF Parser.");
	}
	String FileOutput = "./Rankings/" + nomb + ".txt";
	if (!FileOutput.equals("")){
		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.out.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null) cosDoc.close();
				if (pdDoc != null) pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		File archivo;
		archivo = new File(FileOutput);
		//Escritura
		try{
			FileWriter w = new FileWriter(archivo);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);  
			wr.write(parsedText);//escribimos en el archivo
			//ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
			//de no hacerlo no se escribirá nada en el archivo
			wr.close();
			bw.close();
		}catch(IOException e){};
	}
 	}

}

