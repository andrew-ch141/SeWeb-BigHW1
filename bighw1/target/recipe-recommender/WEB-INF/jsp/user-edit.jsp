<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%-- File: user-edit.jsp — Exercise 5: user profile stored in XML. --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>User profile</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="wrap">
  <jsp:include page="/WEB-INF/jsp/header-nav.jsp"/>
  <% if ("1".equals(request.getParameter("saved"))) { %>
    <p class="hint" style="color:var(--ok);">Primary user (id 1) updated.</p>
  <% } %>
  <% if ("add".equals(request.getParameter("saved"))) { %>
    <p class="hint" style="color:var(--ok);">New user appended to XML (next free id).</p>
  <% } %>
  <section class="card">
    <h2>Primary user (XPath / XQuery use <code>user[1]</code>)</h2>
    <p class="hint">Use <strong>Update primary user</strong> to change the profile with <code>id="1"</code> (assignment demos).
      Use <strong>Add as new user</strong> to append another person without removing anyone.</p>
    <% if (request.getAttribute("error") != null) { %>
      <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/user/edit" class="grid">
      <label for="firstName">First name</label>
      <input type="text" id="firstName" name="firstName" required maxlength="80"
             value="<%= request.getAttribute("firstName") != null ? request.getAttribute("firstName") : "" %>"/>

      <label for="lastName">Last name</label>
      <input type="text" id="lastName" name="lastName" required maxlength="80"
             value="<%= request.getAttribute("lastName") != null ? request.getAttribute("lastName") : "" %>"/>

      <label for="cookingSkill">Cooking skill</label>
      <select id="cookingSkill" name="cookingSkill" required>
        <%
          List<String> skills = (List<String>) request.getAttribute("skills");
          String sk = (String) request.getAttribute("cookingSkill");
          if (skills != null) {
            for (String s : skills) {
        %>
        <option value="<%= s %>" <%= s.equals(sk != null ? sk : "") ? "selected" : "" %>><%= s %></option>
        <%
            }
          }
        %>
      </select>

      <label for="preferredCuisine">Preferred cuisine</label>
      <select id="preferredCuisine" name="preferredCuisine" required>
        <%
          List<String> cuisines = (List<String>) request.getAttribute("cuisines");
          String pc = (String) request.getAttribute("preferredCuisine");
          if (cuisines != null) {
            for (String c : cuisines) {
        %>
        <option value="<%= c %>" <%= c.equals(pc != null ? pc : "") ? "selected" : "" %>><%= c %></option>
        <%
            }
          }
        %>
      </select>

      <div class="form-actions">
        <button type="submit" name="action" value="save" class="btn">Update primary user (id 1)</button>
        <button type="submit" name="action" value="add" class="btn btn-ghost">Add as new user</button>
      </div>
    </form>
  </section>

  <%
    List<Map<String, String>> allUsers = (List<Map<String, String>>) request.getAttribute("allUsers");
    if (allUsers != null && !allUsers.isEmpty()) {
  %>
  <section class="card">
    <h2>Users in <code>recipes.xml</code></h2>
    <table class="data">
      <thead>
      <tr><th>ID</th><th>Name</th><th>Skill</th><th>Preferred cuisine</th></tr>
      </thead>
      <tbody>
      <%
        for (Map<String, String> row : allUsers) {
          String id = row.getOrDefault("id", "");
          String name = (row.getOrDefault("firstName", "") + " " + row.getOrDefault("lastName", "")).trim();
      %>
      <tr>
        <td><%= id %></td>
        <td><%= name %></td>
        <td><%= row.getOrDefault("cookingSkill", "") %></td>
        <td><%= row.getOrDefault("preferredCuisine", "") %></td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </section>
  <%
    }
  %>
</div>
</body>
</html>
