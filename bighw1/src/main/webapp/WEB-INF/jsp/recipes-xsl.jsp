<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%-- File: recipes-xsl.jsp — Exercise 8: XSLT output embedded; user selector. --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Recipes (XSL)</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="wrap">
  <jsp:include page="/WEB-INF/jsp/header-nav.jsp"/>
  <section class="card">
    <form method="get" action="${pageContext.request.contextPath}/recipes/xsl" class="grid">
      <label for="userId">User for skill-based row colours (exercise 8)</label>
      <select name="userId" id="userId" onchange="this.form.submit()">
        <%
          @SuppressWarnings("unchecked")
          List<Map<String, String>> users = (List<Map<String, String>>) request.getAttribute("users");
          String sel = String.valueOf(request.getAttribute("selectedUserId"));
          if (users != null) {
            for (Map<String, String> u : users) {
              String id = u.getOrDefault("id", "");
              String label = u.getOrDefault("firstName", "") + " " + u.getOrDefault("lastName", "")
                  + " (" + u.getOrDefault("cookingSkill", "") + ")";
              boolean on = id.equals(sel);
        %>
        <option value="<%= id %>" <%= on ? "selected" : "" %>><%= label %></option>
        <%
            }
          }
        %>
      </select>
      <p class="hint">Changing the user reloads the XSLT view with that user’s cooking skill as the colour key.</p>
    </form>
  </section>
  <%= request.getAttribute("xslFragment") %>
</div>
</body>
</html>
