// File: RecipeXslServlet.java — Exercise 8: XSL-rendered table with user skill colouring.
package com.semweb.recipes.web;

import com.semweb.recipes.RecipeQueryService;
import com.semweb.recipes.XsltService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecipeXslServlet", urlPatterns = "/recipes/xsl")
public class RecipeXslServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        try {
            var ctx = req.getServletContext();
            var doc = WebAppSupport.doc(ctx);
            String userId = req.getParameter("userId");
            if (userId == null || userId.isBlank()) {
                userId = "1";
            }
            var user = RecipeQueryService.userById(doc, userId);
            String skill = user.getOrDefault("cookingSkill", "Intermediate");
            req.setAttribute("users", RecipeQueryService.allUsers(doc));
            req.setAttribute("selectedUserId", userId);
            req.setAttribute("xslFragment", XsltService.renderRecipeTable(doc, skill, ctx));
            req.getRequestDispatcher("/WEB-INF/jsp/recipes-xsl.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
