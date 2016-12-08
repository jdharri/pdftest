package com.example.pdf.pdftest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDXFAResource;
import org.omg.CORBA.portable.OutputStream;

/**
 * Hello world!
 *
 */

public class App {
	static PDDocument doc;

	public static void main(String[] args) {
		loadPDF();
	}

	public static void loadPDF() {
		try {
			doc = PDDocument.load(new File("/tmp/aaa.pdf"));

			PDDocumentCatalog catalog = doc.getDocumentCatalog();
			PDPage page1 = doc.getPage(0);

			PDAcroForm form = catalog.getAcroForm();
			List<PDField> fields = form.getFields();

			System.out.println(fields);
			for (PDField f : fields) {

				PDField nField = list(f);

			}

			doc.save("/tmp/aaa.pdf");
			doc.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static PDField list(PDField field) throws IOException {
		System.out.println(field.getFullyQualifiedName());
		System.out.println(field.getPartialName());
		if (field instanceof PDNonTerminalField) {
			PDNonTerminalField nonTerminalField = (PDNonTerminalField) field;
			for (PDField child : nonTerminalField.getChildren()) {
				child.setValue("Joe" + Math.random());
				list(child);

			}

		}
		return null;
	}

}
