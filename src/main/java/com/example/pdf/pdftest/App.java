package com.example.pdf.pdftest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;

/**
 * Filling out a PDF form
 *
 */

public class App {
	static PDDocument doc;

	public static void main(String[] args) {
		loadPDF();
	}

	public static void loadPDF() {
		try {
			doc = PDDocument.load(new File("/tmp/FORM4414.pdf"));

			PDDocumentCatalog catalog = doc.getDocumentCatalog();
		

			PDAcroForm form = catalog.getAcroForm();
			List<PDField> fields = form.getFields();

			System.out.println(fields);
			for (PDField f : fields) {

				list(f);

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
				child.setValue("face"+ Math.random());
				list(child);

			}

		}
		return null;
	}

}
