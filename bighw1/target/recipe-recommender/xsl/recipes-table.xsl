<?xml version="1.0" encoding="UTF-8"?>
<!-- File: recipes-table.xsl — Renders recipe list; yellow row = matches selected user's skill (ex. 8). -->
<xsl:stylesheet version="3.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                exclude-result-prefixes="#all">

  <!-- Selected user's cooking skill (must match recipe difficulty values). -->
  <xsl:param name="userSkill" select="'Intermediate'"/>

  <xsl:output method="html" encoding="UTF-8" omit-xml-declaration="yes"/>

  <xsl:template match="/">
    <section class="xsl-block">
      <h2>Recipes via XSLT (exercise 8)</h2>
      <p class="hint">
        Rows with a <span class="swatch swatch-match"></span> background match the selected user’s cooking skill.
        Other rows use <span class="swatch swatch-other"></span>.
      </p>
      <table class="data-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Cuisines</th>
            <th>Difficulty</th>
          </tr>
        </thead>
        <tbody>
          <xsl:apply-templates select="//recipe"/>
        </tbody>
      </table>
    </section>
  </xsl:template>

  <xsl:template match="recipe">
    <xsl:variable name="match" select="difficulty = $userSkill"/>
    <tr>
      <xsl:attribute name="class">
        <xsl:choose>
          <xsl:when test="$match">row-match</xsl:when>
          <xsl:otherwise>row-other</xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
      <td>
        <xsl:value-of select="@id"/>
      </td>
      <td>
        <xsl:value-of select="title"/>
      </td>
      <td>
        <xsl:value-of select="cuisine[1]"/>
        <xsl:text>, </xsl:text>
        <xsl:value-of select="cuisine[2]"/>
      </td>
      <td>
        <xsl:value-of select="difficulty"/>
      </td>
    </tr>
  </xsl:template>
</xsl:stylesheet>
