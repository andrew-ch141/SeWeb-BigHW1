// File: RecipeListServlet.java — Exercise 3: list recipes loaded from XML in memory.
package com.semweb.recipes.web;

import com.semweb.recipes.RecipeQueryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecipeListServlet", urlPatterns = "/recipes")
public class RecipeListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("recipes", RecipeQueryService.allRecipes(WebAppSupport.doc(req.getServletContext())));
            req.getRequestDispatcher("/WEB-INF/jsp/recipe-list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
