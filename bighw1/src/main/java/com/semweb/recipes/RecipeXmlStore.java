// File: RecipeXmlStore.java — Load, validate (XSD), and persist recipes.xml.
package com.semweb.recipes;

import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Reads {@code WEB-INF/data/recipes.xml}, keeps it in memory, validates against {@code recipes.xsd},
 * and writes changes back to disk. All mutations should synchronize on the shared Document instance.
 */
public final class RecipeXmlStore {

    private static final String DATA_XML = "/WEB-INF/data/recipes.xml";
    private static final String DATA_XSD = "/WEB-INF/data/recipes.xsd";

    private RecipeXmlStore() {
    }

    public static File xmlFile(ServletContext ctx) {
        String real = ctx.getRealPath(DATA_XML);
        if (real == null) {
            throw new IllegalStateException("Cannot resolve path for recipes.xml (unpack WAR as files).");
        }
        return new File(real);
    }

    public static Schema loadSchema(ServletContext ctx) throws SAXException, MalformedURLException {
        URL xsd = ctx.getResource(DATA_XSD);
        if (xsd == null) {
            throw new IllegalStateException("Missing " + DATA_XSD);
        }
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return sf.newSchema(xsd);
    }

    public static Document parseFresh(ServletContext ctx)
            throws ParserConfigurationException, IOException, SAXException {
        File f = xmlFile(ctx);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(f);
    }

    public static void validate(Document doc, Schema schema) throws SAXException, IOException {
        Validator v = schema.newValidator();
        v.validate(new DOMSource(doc));
    }

    /**
     * Writes XML to disk with basic indentation.
     * Uses the JDK default {@link TransformerFactory#newDefaultInstance()} so we do not rely on
     * Saxon-HE serialization extras (e.g. {@code {http://saxon.sf.net/}indent-spaces} is not in HE).
     */
    public static void write(Document doc, File target) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newDefaultInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        // Common indent hint for the built-in XSLT processor (ignored safely if unsupported).
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.transform(new DOMSource(doc), new StreamResult(target));
    }

    public static void save(ServletContext ctx, Document doc, Schema schema)
            throws SAXException, TransformerException, IOException {
        validate(doc, schema);
        write(doc, xmlFile(ctx));
    }
}
