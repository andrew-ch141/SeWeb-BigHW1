// File: RecipeDetailServlet.java — Exercise 9: recipe detail via XPath by id.
package com.semweb.recipes.web;

import com.semweb.recipes.RecipeQueryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecipeDetailServlet", urlPatterns = "/recipe/detail")
public class RecipeDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            req.setAttribute("recipe", RecipeQueryService.recipeById(WebAppSupport.doc(req.getServletContext()), id));
            req.setAttribute("recipeId", id);
            req.getRequestDispatcher("/WEB-INF/jsp/recipe-detail.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
