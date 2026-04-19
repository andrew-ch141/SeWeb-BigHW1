// File: RecipeQueryService.java — XPath and XQuery over the cookbook document.
package com.semweb.recipes;

import net.sf.saxon.s9api.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates assignment-required XPath and XQuery expressions.
 * First user is always {@code /cookbook/users/user[1]}.
 */
public final class RecipeQueryService {

    private static final XPath XPATH = XPathFactory.newInstance().newXPath();

    private RecipeQueryService() {
    }

    /**
     * All recipes for in-memory listing (exercise 3).
     */
    public static List<Map<String, String>> allRecipes(Document doc) throws XPathExpressionException {
        return nodesToRecipeRows((NodeList) XPATH.evaluate("/cookbook/recipes/recipe", doc, XPathConstants.NODESET));
    }

    public static List<Map<String, String>> allUsers(Document doc) throws XPathExpressionException {
        NodeList nodes = (NodeList) XPATH.evaluate("/cookbook/users/user", doc, XPathConstants.NODESET);
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            list.add(userRow(nodes.item(i)));
        }
        return list;
    }

    public static Map<String, String> userById(Document doc, String id)
            throws XPathExpressionException {
        if (id == null || !id.matches("[0-9]+")) {
            return Map.of();
        }
        Node n = (Node) XPATH.evaluate("/cookbook/users/user[@id = '" + id + "']", doc, XPathConstants.NODE);
        return n == null ? Map.of() : userRow(n);
    }

    private static Map<String, String> userRow(Node user) {
        Map<String, String> m = new LinkedHashMap<>();
        if (user.getAttributes() != null && user.getAttributes().getNamedItem("id") != null) {
            m.put("id", user.getAttributes().getNamedItem("id").getNodeValue());
        }
        NodeList kids = user.getChildNodes();
        for (int i = 0; i < kids.getLength(); i++) {
            Node c = kids.item(i);
            if (c.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            String tag = c.getLocalName() != null ? c.getLocalName() : c.getNodeName();
            String val = c.getTextContent() != null ? c.getTextContent().trim() : "";
            m.put(tag, val);
        }
        return m;
    }

    /**
     * Exercise 6 — recipes whose difficulty equals the first user’s cooking skill (XPath).
     */
    public static List<Map<String, String>> recommendBySkillOnly(Document doc)
            throws XPathExpressionException {
        String expr = "/cookbook/recipes/recipe[difficulty = /cookbook/users/user[1]/cookingSkill]";
        return nodesToRecipeRows((NodeList) XPATH.evaluate(expr, doc, XPathConstants.NODESET));
    }

    /**
     * Exercise 7 — skill + preferred cuisine (XQuery).
     */
    public static List<Map<String, String>> recommendSkillAndCuisine(Document doc)
            throws SaxonApiException, XPathExpressionException {
        String q = """
                for $r in /cookbook/recipes/recipe
                let $u := /cookbook/users/user[1]
                where $r/difficulty = $u/cookingSkill
                  and $r/cuisine[. = $u/preferredCuisine]
                return $r
                """;
        List<Map<String, String>> xq = runXQuery(doc, q);
        if (!xq.isEmpty()) {
            return xq;
        }
        // Fallback: same logic with XPath 1.0 (DOM nodes) if XQuery items are not DOM-backed.
        String expr = "/cookbook/recipes/recipe[difficulty = /cookbook/users/user[1]/cookingSkill]"
                + "[cuisine = /cookbook/users/user[1]/preferredCuisine]";
        return nodesToRecipeRows((NodeList) XPATH.evaluate(expr, doc, XPathConstants.NODESET));
    }

    /**
     * Exercise 9 — single recipe by id attribute (XPath).
     */
    public static Map<String, String> recipeById(Document doc, String id)
            throws XPathExpressionException {
        if (id == null || !id.matches("[0-9]+")) {
            return Map.of();
        }
        String expr = "/cookbook/recipes/recipe[@id = '" + id + "']";
        Node n = (Node) XPATH.evaluate(expr, doc, XPathConstants.NODE);
        if (n == null) {
            return Map.of();
        }
        return nodeToRecipeRow(n);
    }

    /**
     * Exercise 10 — recipes that list the cuisine (XPath, whitelist-validated input).
     */
    public static List<Map<String, String>> recipesByCuisine(Document doc, String cuisine)
            throws XPathExpressionException {
        if (!CuisineOptions.isAllowed(cuisine)) {
            return List.of();
        }
        String expr = "/cookbook/recipes/recipe[cuisine = '" + cuisine + "']";
        return nodesToRecipeRows((NodeList) XPATH.evaluate(expr, doc, XPathConstants.NODESET));
    }

    private static List<Map<String, String>> nodesToRecipeRows(NodeList nodes) {
        List<Map<String, String>> out = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            out.add(nodeToRecipeRow(nodes.item(i)));
        }
        return out;
    }

    private static Map<String, String> nodeToRecipeRow(Node recipe) {
        Map<String, String> m = new LinkedHashMap<>();
        if (recipe.getAttributes() != null && recipe.getAttributes().getNamedItem("id") != null) {
            m.put("id", recipe.getAttributes().getNamedItem("id").getNodeValue());
        }
        List<String> cuisines = new ArrayList<>();
        NodeList kids = recipe.getChildNodes();
        for (int i = 0; i < kids.getLength(); i++) {
            Node c = kids.item(i);
            if (c.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            String tag = c.getLocalName() != null ? c.getLocalName() : c.getNodeName();
            String val = c.getTextContent() != null ? c.getTextContent().trim() : "";
            switch (tag) {
                case "title" -> m.put("title", val);
                case "cuisine" -> cuisines.add(val);
                case "difficulty" -> m.put("difficulty", val);
                default -> {
                }
            }
        }
        if (!cuisines.isEmpty()) {
            m.put("cuisine1", cuisines.get(0));
        }
        if (cuisines.size() > 1) {
            m.put("cuisine2", cuisines.get(1));
        }
        m.put("cuisines", String.join(", ", cuisines));
        return m;
    }

    private static List<Map<String, String>> runXQuery(Document doc, String xquery)
            throws SaxonApiException {
        Processor proc = new Processor(false);
        DocumentBuilder db = proc.newDocumentBuilder();
        XdmNode xdm = db.build(new DOMSource(doc));
        XQueryCompiler comp = proc.newXQueryCompiler();
        XQueryExecutable exp = comp.compile(xquery.trim());
        XQueryEvaluator eval = exp.load();
        eval.setContextItem(xdm);
        XdmValue result = eval.evaluate();
        List<Map<String, String>> list = new ArrayList<>();
        for (XdmItem item : result) {
            if (item instanceof XdmNode xn && xn.getNodeKind() == XdmNodeKind.ELEMENT) {
                Object ext = xn.getExternalNode();
                if (ext instanceof org.w3c.dom.Node dom) {
                    list.add(nodeToRecipeRow(dom));
                }
            }
        }
        return list;
    }
}
