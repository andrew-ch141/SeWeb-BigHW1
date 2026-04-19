<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%-- File: recommend.jsp — Exercises 6 & 7: recommendation result tables. --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Recommendations</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="wrap">
  <jsp:include page="/WEB-INF/jsp/header-nav.jsp"/>
  <section class="card">
    <h2><%= request.getAttribute("heading") %></h2>
    <p class="hint">
      <% if ("skill".equals(request.getAttribute("mode"))) { %>
        XPath: <code>/cookbook/recipes/recipe[difficulty = /cookbook/users/user[1]/cookingSkill]</code>
      <% } else { %>
        XQuery: filters recipes where difficulty and one cuisine match <code>user[1]</code>.
      <% } %>
    </p>
    <table class="data-table">
      <thead>
      <tr><th>ID</th><th>Title</th><th>Cuisines</th><th>Difficulty</th></tr>
      </thead>
      <tbody>
      <%
        @SuppressWarnings({"rawtypes", "unchecked"})
        List<Map<String, String>> rows = (List) request.getAttribute("recipes");
        if (rows != null && !rows.isEmpty()) {
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
        } else {
      %>
      <tr><td colspan="4" class="muted">No matching recipes for the current first user.</td></tr>
      <%
        }
      %>
      </tbody>
    </table>
  </section>
</div>
</body>
</html>
