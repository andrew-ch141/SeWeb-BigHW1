<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%-- File: recipe-detail.jsp — Exercise 9: XPath-selected recipe, formatted for reading. --%>
<%!
  private static String esc(String s) {
    if (s == null) {
      return "";
    }
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
  }

  private static String diffClass(String d) {
    if (d == null) {
      return "unknown";
    }
    switch (d) {
      case "Beginner":
        return "beginner";
      case "Intermediate":
        return "intermediate";
      case "Advanced":
        return "advanced";
      default:
        return "unknown";
    }
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <%
    @SuppressWarnings("unchecked")
    Map<String, String> _r = (Map<String, String>) request.getAttribute("recipe");
    String _pageTitle = (_r != null && !_r.isEmpty()) ? esc(_r.getOrDefault("title", "Recipe")) : "Recipe";
  %>
  <title><%= _pageTitle %> — Recipe</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body class="page-recipe-detail">
<div class="wrap">
  <jsp:include page="/WEB-INF/jsp/header-nav.jsp"/>

  <%
    @SuppressWarnings("unchecked")
    Map<String, String> r = (Map<String, String>) request.getAttribute("recipe");
    if (r == null || r.isEmpty()) {
  %>
  <article class="card recipe-sheet recipe-sheet--empty">
    <h1 class="recipe-title">Recipe not found</h1>
    <p class="muted">No recipe matches this id. Pick one from the list.</p>
    <p class="recipe-actions"><a href="${pageContext.request.contextPath}/recipes" class="btn btn-ghost">← All recipes</a></p>
  </article>
  <%
    } else {
      String id = r.getOrDefault("id", "");
      String title = r.getOrDefault("title", "");
      String c1 = r.getOrDefault("cuisine1", "");
      String c2 = r.getOrDefault("cuisine2", "");
      String diff = r.getOrDefault("difficulty", "");
      String dClass = diffClass(diff);
  %>
  <article class="card recipe-sheet">
    <nav class="recipe-crumb" aria-label="Breadcrumb">
      <a href="${pageContext.request.contextPath}/recipes">Recipes</a>
      <span class="recipe-crumb-sep" aria-hidden="true">/</span>
      <span class="recipe-crumb-here"><%= esc(title) %></span>
    </nav>

    <header class="recipe-hero">
      <p class="recipe-eyebrow">Recipe <span class="recipe-num">#<%= esc(id) %></span></p>
      <h1 class="recipe-title"><%= esc(title) %></h1>
      <p class="recipe-lead">
        A mix of <strong><%= esc(c1) %></strong> and <strong><%= esc(c2) %></strong> flavours,
        pitched at <strong><%= esc(diff) %></strong> level.
      </p>
      <div class="recipe-tags" aria-label="Tags">
        <span class="tag tag-cuisine"><%= esc(c1) %></span>
        <span class="tag tag-cuisine"><%= esc(c2) %></span>
        <span class="tag tag-diff tag-diff-<%= dClass %>"><%= esc(diff) %></span>
      </div>
    </header>

    <section class="recipe-summary" aria-labelledby="summary-heading">
      <h2 id="summary-heading" class="recipe-section-title">At a glance</h2>
      <dl class="recipe-meta-grid">
        <div class="recipe-meta-item">
          <dt>Recipe id</dt>
          <dd><%= esc(id) %></dd>
        </div>
        <div class="recipe-meta-item">
          <dt>First cuisine</dt>
          <dd><%= esc(c1) %></dd>
        </div>
        <div class="recipe-meta-item">
          <dt>Second cuisine</dt>
          <dd><%= esc(c2) %></dd>
        </div>
        <div class="recipe-meta-item">
          <dt>Difficulty</dt>
          <dd><span class="tag tag-diff tag-diff-<%= dClass %> tag-diff--inline"><%= esc(diff) %></span></dd>
        </div>
      </dl>
    </section>

    <details class="recipe-tech">
      <summary>Technical (XPath for coursework)</summary>
      <p class="hint tech-line">
        <code>/cookbook/recipes/recipe[@id = '<%= esc(String.valueOf(request.getAttribute("recipeId"))) %>']</code>
      </p>
    </details>

    <footer class="recipe-actions">
      <a href="${pageContext.request.contextPath}/recipes" class="btn btn-ghost">← Back to all recipes</a>
    </footer>
  </article>
  <%
    }
  %>
</div>
</body>
</html>
