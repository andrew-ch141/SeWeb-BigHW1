// File: XsltService.java — Applies XSL stylesheet to the in-memory cookbook (exercise 8).
package com.semweb.recipes;

import jakarta.servlet.ServletContext;
import net.sf.saxon.s9api.*;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * Runs {@code /xsl/recipes-table.xsl} with a parameter for the selected user’s skill level.
 */
public final class XsltService {

    private XsltService() {
    }

    public static String renderRecipeTable(Document doc, String userSkill, ServletContext ctx)
            throws SaxonApiException {
        Processor proc = new Processor(false);
        XsltCompiler comp = proc.newXsltCompiler();
        var xsl = ctx.getResourceAsStream("/xsl/recipes-table.xsl");
        if (xsl == null) {
            throw new IllegalStateException("Missing /xsl/recipes-table.xsl");
        }
        XsltExecutable exec = comp.compile(new StreamSource(xsl));
        XsltTransformer trans = exec.load();
        trans.setParameter(new QName("userSkill"), new XdmAtomicValue(userSkill));
        DocumentBuilder db = proc.newDocumentBuilder();
        XdmNode source = db.build(new DOMSource(doc));
        trans.setInitialContextNode(source);
        StringWriter sw = new StringWriter();
        Serializer ser = proc.newSerializer(sw);
        ser.setOutputProperty(Serializer.Property.METHOD, "html");
        ser.setOutputProperty(Serializer.Property.ENCODING, StandardCharsets.UTF_8.name());
        trans.setDestination(ser);
        trans.transform();
        return sw.toString();
    }
}
