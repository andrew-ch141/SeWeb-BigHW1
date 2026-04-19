<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%-- File: cuisine-filter.jsp — Exercise 10: XPath by cuisine. --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Recipes by cuisine</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="wrap">
  <jsp:include page="/WEB-INF/jsp/header-nav.jsp"/>
  <section class="card">
    <h2>Filter by cuisine (XPath)</h2>
    <p class="hint">Expression: <code>/cookbook/recipes/recipe[cuisine = '…']</code></p>
    <form method="get" action="${pageContext.request.contextPath}/recipes/by-cuisine" class="grid">
      <label for="cuisine">Cuisine</label>
      <select name="cuisine" id="cuisine" onchange="this.form.submit()">
        <option value="">— choose —</option>
        <%
          List<String> opts = (List<String>) request.getAttribute("cuisines");
          String sel = (String) request.getAttribute("selected");
          if (opts != null) {
            for (String c : opts) {
        %>
        <option value="<%= c %>" <%= c.equals(sel != null ? sel : "") ? "selected" : "" %>><%= c %></option>
        <%
            }
          }
        %>
      </select>
    </form>
  </section>
  <% if (request.getAttribute("recipes") != null) { %>
  <section class="card">
    <h3>Results</h3>
    <table class="data-table">
      <thead>
      <tr><th>ID</th><th>Title</th><th>Cuisines</th><th>Difficulty</th></tr>
      </thead>
      <tbody>
      <%
        @SuppressWarnings({"rawtypes", "unchecked"})
        List<Map<String, String>> rows = (List) request.getAttribute("recipes");
        for (Map<String, String> r : rows) {
      %>
      <tr>
        <td><%= r.getOrDefault("id", "") %></td>
        <td><%= r.getOrDefault("title", "") %></td>
        <td><%= r.getOrDefault("cuisines", "") %></td>
        <td><%= r.getOrDefault("difficulty", "") %></td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </section>
  <% } %>
</div>
</body>
</html>
