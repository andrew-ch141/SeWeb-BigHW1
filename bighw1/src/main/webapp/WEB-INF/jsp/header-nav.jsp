<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- File: header-nav.jsp — Shared top navigation. --%>
<header class="site">
  <h1><a href="${pageContext.request.contextPath}/">Recipe recommender</a></h1>
  <nav>
    <ul>
      <li><a href="${pageContext.request.contextPath}/recipes">Recipes</a></li>
      <li><a href="${pageContext.request.contextPath}/recipes/xsl">XSL table</a></li>
      <li><a href="${pageContext.request.contextPath}/recipes/add">Add recipe</a></li>
      <li><a href="${pageContext.request.contextPath}/user/edit">User</a></li>
      <li><a href="${pageContext.request.contextPath}/recommend/skill">Skill rec.</a></li>
      <li><a href="${pageContext.request.contextPath}/recommend/full">Full rec.</a></li>
      <li><a href="${pageContext.request.contextPath}/recipes/by-cuisine">Cuisine</a></li>
    </ul>
  </nav>
</header>
