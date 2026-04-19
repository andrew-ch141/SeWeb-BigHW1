<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%-- File: recipe-list.jsp — Exercise 3: recipes from in-memory DOM. --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Recipes (memory)</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="wrap">
  <jsp:include page="/WEB-INF/jsp/header-nav.jsp"/>
  <% if ("1".equals(request.getParameter("added"))) { %>
    <p class="hint" style="color:var(--ok);">Recipe saved to XML.</p>
  <% } %>
  <section class="card">
    <h2>All recipes (in-memory)</h2>
    <table class="data-table">
      <thead>
      <tr><th>ID</th><th>Title</th><th>Cuisines</th><th>Difficulty</th><th>Detail</th></tr>
      </thead>
      <tbody>
      <%
        @SuppressWarnings({"rawtypes", "unchecked"})
        List<Map<String, String>> rows = (List) request.getAttribute("recipes");
        if (rows != null) {
          for (Map<String, String> r : rows) {
            String id = r.getOrDefault("id", "");
      %>
      <tr>
        <td><%= id %></td>
        <td><%= escape(r.get("title")) %></td>
        <td><%= escape(r.get("cuisines")) %></td>
        <td><%= escape(r.get("difficulty")) %></td>
        <td><a href="${pageContext.request.contextPath}/recipe/detail?id=<%= id %>">View</a></td>
      </tr>
      <%
          }
        }
      %>
      </tbody>
    </table>
  </section>
</div>
</body>
</html>
<%!
  private static String escape(Object o) {
    if (o == null) return "";
    String s = String.valueOf(o);
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
  }
%>
