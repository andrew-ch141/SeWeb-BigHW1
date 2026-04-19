# Recipe recommender — Semantic Web Big HW1

## Team

| Name | Contribution |
|------|----------------|
| *(add name)* | *(describe: XML model, servlets, UI, …)* |
| *(add name)* | *(describe)* |

**Public GitHub repository:** *(paste `https://github.com/...` here before upload)*

## What this is

A **Java** web application (WAR) that stores recipes and one primary user in **XML**, validates with **XML Schema**, queries with **XPath** and **XQuery**, and renders a coloured table with **XSLT**. The UI is built with **JSP** and **servlets**.

## Requirements checklist (from the assignment)

1. **Input data:** `src/main/webapp/WEB-INF/data/recipes.xml` — 22 recipes + 1 user (manual entry).
2. **Schema:** `recipes.xsd` (same folder).
3. **In-memory list + UI:** `/recipes`
4. **Add recipe (validate + save XML):** `/recipes/add`
5. **User form → XML:** `/user/edit` (updates `user[1]`)
6. **Recommend by skill (XPath, first user):** `/recommend/skill`
7. **Recommend by skill + cuisine (XQuery, first user):** `/recommend/full`
8. **XSL list with colours (user selectable):** `/recipes/xsl` — matching skill = yellow row, others = green
9. **Recipe detail (XPath by id):** `/recipe/detail?id=…`
10. **Filter by cuisine (XPath):** `/recipes/by-cuisine?cuisine=…`

## How to run

Requires **JDK 17+** and **Maven**.

```text
mvn clean package org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:run
```

Then open [http://localhost:8081/](http://localhost:8081/) (default port **8081** so it does not clash with another app on 8080). To use 8080: `mvn ... -Djetty.port=8080`. Stop the server with Ctrl+C in the terminal.

If you see **Address already in use**, either stop the other process on that port or pick another port with `-Djetty.port=9090`.

Data file path on disk is under the exploded WAR (`WEB-INF/data/recipes.xml`); edits from the app overwrite that file.

## Optional: scraping for extra points

The brief allows scraping titles from [BBC Good Food — budget autumn](https://www.bbcgoodfood.com/recipes/collection/budget-autumn). This project does **not** include a scraper; you can generate titles externally and merge them into `recipes.xml`, then assign two random cuisines per recipe from predefined arrays in code or by hand.

## Technologies

- Jakarta Servlet / JSP  
- JAXP DOM, XSD validation  
- Saxon-HE (XQuery, XSLT 3)  
