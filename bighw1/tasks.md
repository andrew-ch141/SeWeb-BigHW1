# Tasks — Semantic Web Big HW1

## Phase 1 — Project skeleton
- [x] Maven WAR, Jakarta Servlet 6, Tomcat plugin, Saxon-HE for XQuery/XSLT
- [x] `WEB-INF/data/recipes.xml` (22 recipes + 1 user), `recipes.xsd`

## Phase 2 — XML processing
- [x] Load/validate/save DOM (`RecipeXmlStore`, `RecipeDataContextListener`)
- [x] XPath helpers (`RecipeQueryService`)
- [x] XQuery for combined recommendations (`recommendSkillAndCuisine`)
- [x] XSLT view with skill parameter (`xsl/recipes-table.xsl`, `XsltService`)

## Phase 3 — Web UI
- [x] List recipes from memory; forms for recipe + user; validation
- [x] Recommendation pages (skill-only, skill+cuisine)
- [x] Recipe detail by id; cuisine filter dropdown
- [x] Styling (`css/style.css`), navigation

## Phase 4 — Delivery
- [x] README (team names placeholder, GitHub link, how to run)
- [x] Manual test: `mvn clean package org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:run`

## Notes
- Scraping BBC Good Food for titles (1.5 pts) is optional; this repo uses **manual** titles (0.5 pts). A separate scraper can fill `recipes.xml` if you need the higher band.
- **Users:** User form has two actions — **Update primary user (id 1)** for `user[1]` demos; **Add as new user** appends `<user>` with the next id (does not delete others). Earlier bug was only updating the first DOM node as id 1; fixed to target `@id='1'` and added `appendUser`.
