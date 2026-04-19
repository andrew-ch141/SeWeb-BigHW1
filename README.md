# SeWeb-BigHW1

**Semantic Web — Big Homework 1**  
Recipe recommender: a Java web app that stores data in **XML** (no SQL database), validates with **XML Schema**, and uses **XPath**, **XQuery**, and **XSLT** as required by the assignment.

---

## Team members

| | |
|--|--|
| **STUDENT 1** | *MAZILU Stefan* |
| **STUDENT 2** | *CHIAR Andrei-Cristian* |

**Public repository:** https://github.com/andrew-ch141/SeWeb-BigHW1/

---

## Contributions



| Person | Main responsibilities |
|--------|------------------------|
| **MAZILU Stefan** | XML sample data (`recipes.xml`), XSD (`recipes.xsd`), DOM load/save and validation (`RecipeXmlStore`, context listener), XPath/XQuery helpers (`RecipeQueryService`), XSLT pipeline (`XsltService`, `recipes-table.xsl`). |
| **CHIAR Andrei-Cristian** | Servlets and JSP pages (list, add recipe, user profile, recommendations, detail, cuisine filter, XSL view), form validation, navigation and **CSS** styling. |


---

## What this project does

- **List recipes** from the in-memory DOM (`/recipes`).
- **Add a recipe** with validation; persists to `WEB-INF/data/recipes.xml` (`/recipes/add`).
- **User profile(s)** — update the primary user (`id="1"`) for XPath `user[1]` demos, or **append** extra users (`/user/edit`).
- **Recommendations:** by cooking skill only **(XPath)** (`/recommend/skill`), and by skill **and** preferred cuisine **(XQuery)** (`/recommend/full`).
- **XSLT table** with row colours by skill (`/recipes/xsl`).
- **Recipe detail** by id **(XPath)** (`/recipe/detail?id=…`).
- **Filter by cuisine** **(XPath)** (`/recipes/by-cuisine?cuisine=…`).

Data lives in **`src/main/webapp/WEB-INF/data/recipes.xml`** (bundled into the WAR). The app overwrites this file when you save from the UI.

---

## Requirements checklist (assignment)

1. Input XML + manual recipes/user  
2. XML Schema (`recipes.xsd`)  
3. In-memory list + UI  
4. Add recipe + validate + save  
5. User form → XML  
6. Recommend by skill (XPath, first user)  
7. Recommend by skill + cuisine (XQuery)  
8. XSL list with colours (user selectable for skill comparison)  
9. Recipe detail by id (XPath)  
10. Filter by cuisine (XPath)  

**Optional:** scraping recipe titles from BBC Good Food for extra points — this repo uses **manual** titles; you can merge scraped titles into `recipes.xml` if needed.

---

## How to run

**Requirements:** JDK **17+**, **Maven**.

```bash
mvn clean package org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:run
