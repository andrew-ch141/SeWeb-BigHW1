<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%-- File: recipe-add.jsp — Exercise 4: add recipe form. --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Add recipe</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="wrap">
  <jsp:include page="/WEB-INF/jsp/header-nav.jsp"/>
  <section class="card">
    <h2>Add a recipe</h2>
    <p class="hint">Two cuisine types and one difficulty level are stored in XML and validated against the schema.</p>
    <% if (request.getAttribute("error") != null) { %>
      <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/recipes/add" class="grid">
      <label for="title">Title</label>
      <input type="text" id="title" name="title" required maxlength="200"
             value="<%= request.getAttribute("title") != null ? request.getAttribute("title") : "" %>"/>

      <label for="cuisine1">First cuisine</label>
      <select id="cuisine1" name="cuisine1" required>
        <%
          List<String> cuisines = (List<String>) request.getAttribute("cuisines");
          String c1 = (String) request.getAttribute("cuisine1");
          if (cuisines != null) {
            for (String c : cuisines) {
        %>
        <option value="<%= c %>" <%= c != null && c.equals(c1) ? "selected" : "" %>><%= c %></option>
        <%
            }
          }
        %>
      </select>

      <label for="cuisine2">Second cuisine</label>
      <select id="cuisine2" name="cuisine2" required>
        <%
          String c2 = (String) request.getAttribute("cuisine2");
          if (cuisines != null) {
            for (String c : cuisines) {
        %>
        <option value="<%= c %>" <%= c != null && c.equals(c2) ? "selected" : "" %>><%= c %></option>
        <%
            }
          }
        %>
      </select>

      <label>Difficulty</label>
      <%
        List<String> skills = (List<String>) request.getAttribute("skills");
        String diff = (String) request.getAttribute("difficulty");
        if (skills != null) {
          int si = 0;
          for (String s : skills) {
            boolean chk = diff != null ? s.equals(diff) : si == 0;
            si++;
      %>
      <label style="display:flex;align-items:center;gap:0.4rem;">
        <input type="radio" name="difficulty" value="<%= s %>" required <%= chk ? "checked" : "" %>/>
        <%= s %>
      </label>
      <%
          }
        }
      %>

      <button type="submit" class="btn">Save to XML</button>
    </form>
  </section>
</div>
</body>
</html>
