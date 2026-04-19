// File: RecipeMutationService.java — DOM updates for new recipes and user records.
package com.semweb.recipes;

import jakarta.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.validation.Schema;

/**
 * Appends validated recipe and user nodes, then persists via {@link RecipeXmlStore}.
 */
public final class RecipeMutationService {

    private RecipeMutationService() {
    }

    public static synchronized void addRecipe(Document doc, String title, String c1, String c2, String difficulty,
                                              Schema schema, ServletContext ctx) throws Exception {
        Element root = doc.getDocumentElement();
        Element recipes = (Element) root.getElementsByTagName("recipes").item(0);
        int nextId = maxId(recipes, "recipe") + 1;

        Element recipe = doc.createElement("recipe");
        recipe.setAttribute("id", String.valueOf(nextId));

        Element t = doc.createElement("title");
        t.setTextContent(title.trim());
        recipe.appendChild(t);

        Element cu1 = doc.createElement("cuisine");
        cu1.setTextContent(c1);
        recipe.appendChild(cu1);
        Element cu2 = doc.createElement("cuisine");
        cu2.setTextContent(c2);
        recipe.appendChild(cu2);

        Element diff = doc.createElement("difficulty");
        diff.setTextContent(difficulty);
        recipe.appendChild(diff);

        recipes.appendChild(recipe);
        RecipeXmlStore.save(ctx, doc, schema);
    }

    /**
     * Inserts or updates the user with {@code id="1"} (XPath {@code user[1]} is document order, not id).
     * Other users in the file are left unchanged.
     */
    public static synchronized void savePrimaryUser(Document doc, String first, String last, String skill,
                                                    String cuisine, Schema schema, ServletContext ctx)
            throws Exception {
        Element users = (Element) doc.getDocumentElement().getElementsByTagName("users").item(0);
        Element user = findUserElementById(users, "1");
        if (user == null) {
            user = doc.createElement("user");
            user.setAttribute("id", "1");
            users.insertBefore(user, users.getFirstChild());
        }
        clearElementChildren(user);

        appendText(doc, user, "firstName", first.trim());
        appendText(doc, user, "lastName", last.trim());
        appendText(doc, user, "cookingSkill", skill);
        appendText(doc, user, "preferredCuisine", cuisine);

        RecipeXmlStore.save(ctx, doc, schema);
    }

    /**
     * Appends a new user element with the next free numeric id. Does not modify existing users.
     */
    public static synchronized void appendUser(Document doc, String first, String last, String skill,
                                               String cuisine, Schema schema, ServletContext ctx)
            throws Exception {
        Element users = (Element) doc.getDocumentElement().getElementsByTagName("users").item(0);
        int nextId = maxId(users, "user") + 1;

        Element user = doc.createElement("user");
        user.setAttribute("id", String.valueOf(nextId));
        appendText(doc, user, "firstName", first.trim());
        appendText(doc, user, "lastName", last.trim());
        appendText(doc, user, "cookingSkill", skill);
        appendText(doc, user, "preferredCuisine", cuisine);
        users.appendChild(user);

        RecipeXmlStore.save(ctx, doc, schema);
    }

    private static Element findUserElementById(Element users, String id) {
        NodeList list = users.getElementsByTagName("user");
        for (int i = 0; i < list.getLength(); i++) {
            Element el = (Element) list.item(i);
            if (id.equals(el.getAttribute("id"))) {
                return el;
            }
        }
        return null;
    }

    private static void clearElementChildren(Element el) {
        while (el.getFirstChild() != null) {
            el.removeChild(el.getFirstChild());
        }
    }

    private static void appendText(Document doc, Element parent, String tag, String text) {
        Element e = doc.createElement(tag);
        e.setTextContent(text);
        parent.appendChild(e);
    }

    private static int maxId(Element container, String childTag) {
        NodeList list = container.getElementsByTagName(childTag);
        int max = 0;
        for (int i = 0; i < list.getLength(); i++) {
            Element el = (Element) list.item(i);
            String id = el.getAttribute("id");
            if (id.matches("\\d+")) {
                max = Math.max(max, Integer.parseInt(id));
            }
        }
        return max;
    }
}
