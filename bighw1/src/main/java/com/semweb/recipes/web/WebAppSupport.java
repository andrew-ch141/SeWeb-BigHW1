// File: WebAppSupport.java — Accessors for application-scoped XML and schema.
package com.semweb.recipes.web;

import com.semweb.recipes.XmlDataKeys;
import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;

import javax.xml.validation.Schema;

public final class WebAppSupport {

    public static final String SCHEMA_ATTR = "com.semweb.recipes.SCHEMA";

    private WebAppSupport() {
    }

    public static Document doc(ServletContext ctx) {
        return (Document) ctx.getAttribute(XmlDataKeys.COOKBOOK_DOCUMENT);
    }

    public static Schema schema(ServletContext ctx) {
        return (Schema) ctx.getAttribute(SCHEMA_ATTR);
    }
}
