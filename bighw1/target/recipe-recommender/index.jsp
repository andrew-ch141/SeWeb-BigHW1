<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- File: index.jsp — Landing page with nav and welcome hero. --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Recipe recommender</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body class="home">
<div class="wrap">
  <header class="site">
    <h1>Recipe recommender</h1>
    <nav>
      <ul>
        <li><a href="${pageContext.request.contextPath}/recipes">Recipes (memory)</a></li>
        <li><a href="${pageContext.request.contextPath}/recipes/xsl">Recipes (XSL)</a></li>
        <li><a href="${pageContext.request.contextPath}/recipes/add">Add recipe</a></li>
        <li><a href="${pageContext.request.contextPath}/user/edit">User profile</a></li>
        <li><a href="${pageContext.request.contextPath}/recommend/skill">Rec. by skill</a></li>
        <li><a href="${pageContext.request.contextPath}/recommend/full">Rec. skill+cuisine</a></li>
        <li><a href="${pageContext.request.contextPath}/recipes/by-cuisine">By cuisine</a></li>
      </ul>
    </nav>
  </header>

  <section class="hero-intro" aria-label="Welcome">
    <p class="hero-kicker">Cook · taste · learn</p>
    <h2 class="hero-title">Ideas for what to cook next</h2>
    <p class="hero-lead">
      Browse recipes by skill and cuisine, add your own, and explore suggestions that fit how you like to cook.
    </p>
  </section>
</div>
</body>
</html>
