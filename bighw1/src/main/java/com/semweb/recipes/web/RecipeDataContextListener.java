// File: RecipeDataContextListener.java — Loads and validates recipes.xml at startup.
package com.semweb.recipes.web;

import com.semweb.recipes.RecipeXmlStore;
import com.semweb.recipes.XmlDataKeys;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;

/**
 * Parses the XML once, validates against XSD, and stores the live DOM in application scope.
 */
@WebListener
public class RecipeDataContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Schema schema = RecipeXmlStore.loadSchema(sce.getServletContext());
            Document doc = RecipeXmlStore.parseFresh(sce.getServletContext());
            RecipeXmlStore.validate(doc, schema);
            sce.getServletContext().setAttribute(XmlDataKeys.COOKBOOK_DOCUMENT, doc);
            sce.getServletContext().setAttribute("com.semweb.recipes.SCHEMA", schema);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load cookbook XML", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(XmlDataKeys.COOKBOOK_DOCUMENT);
    }
}
