package com.example.pdf.pdftest;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDXFAResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Filling out a PDF form
 *
 */

public class App {
	static PDDocument doc;

	public static void main(String[] args) {
		loadPDF();
		convertToXML();
	}

	public static void loadPDF() {
		try {
			doc = PDDocument.load(new File("/temp/FORM4414.pdf"));
			doc.setAllSecurityToBeRemoved(true);
			PDDocumentCatalog catalog = doc.getDocumentCatalog();
			PDAcroForm form = catalog.getAcroForm();
			List<PDField> fields = form.getFields();
			doc.
			
			for (PDField f : fields) {
				list(f);
			}

			doc.save("/temp/aaa.pdf");
			doc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PDField list(PDField field) throws IOException {
		System.out.println(field.getFullyQualifiedName());
		System.out.println(field.getPartialName());
		System.out.println(field.getValueAsString());
		if (field instanceof PDNonTerminalField) {
			PDNonTerminalField nonTerminalField = (PDNonTerminalField) field;
			for (PDField child : nonTerminalField.getChildren()) {
				child.setValue("face" + Math.random());
				list(child);
			}
		}
		return field;
	}

	public static void convertToXML() {
		try {
			PDDocument doc = PDDocument.load(new File("/temp/FORM4414.pdf"));
			doc.setAllSecurityToBeRemoved(true);
			PDDocumentCatalog catalog = doc.getDocumentCatalog();
			PDAcroForm form = catalog.getAcroForm();

			PDXFAResource xfa = form.getXFA();

			PrintWriter out = new PrintWriter("/temp/pdf.xml");
			StreamResult result = new StreamResult(new StringWriter());

			Document domDoc = xfa.getDocument();
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			NodeList nl = domDoc.getElementsByTagName("textEdit");
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				// System.out.println(n.);
				n.setNodeValue("**************************blah");
				
			}
			DOMSource src = new DOMSource(domDoc);

			trans.transform(src, result);
			// System.out.println(result.getWriter().toString());
			out.println(result.getWriter().toString());
			out.close();

		} catch (IOException | ParserConfigurationException | TransformerException | SAXException
				| TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
