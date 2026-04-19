// File: RecommendFullServlet.java — Exercise 7: XQuery — skill + preferred cuisine (first user).
package com.semweb.recipes.web;

import com.semweb.recipes.RecipeQueryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecommendFullServlet", urlPatterns = "/recommend/full")
public class RecommendFullServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("mode", "full");
            req.setAttribute("heading", "Recommendations by skill and cuisine (XQuery, first user)");
            req.setAttribute("recipes", RecipeQueryService.recommendSkillAndCuisine(WebAppSupport.doc(req.getServletContext())));
            req.getRequestDispatcher("/WEB-INF/jsp/recommend.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
