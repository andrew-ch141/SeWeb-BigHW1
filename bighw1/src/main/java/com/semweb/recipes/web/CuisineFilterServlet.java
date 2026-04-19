// File: CuisineFilterServlet.java — Exercise 10: XPath filter by cuisine (dropdown).
package com.semweb.recipes.web;

import com.semweb.recipes.CuisineOptions;
import com.semweb.recipes.RecipeQueryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CuisineFilterServlet", urlPatterns = "/recipes/by-cuisine")
public class CuisineFilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String cuisine = req.getParameter("cuisine");
        try {
            req.setAttribute("cuisines", CuisineOptions.ALL);
            req.setAttribute("selected", cuisine);
            if (cuisine != null && !cuisine.isBlank()) {
                req.setAttribute("recipes", RecipeQueryService.recipesByCuisine(
                        WebAppSupport.doc(req.getServletContext()), cuisine));
            }
            req.getRequestDispatcher("/WEB-INF/jsp/cuisine-filter.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
