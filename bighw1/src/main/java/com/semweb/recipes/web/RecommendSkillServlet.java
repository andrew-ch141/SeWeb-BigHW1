// File: RecommendSkillServlet.java — Exercise 6: XPath recommendations by skill (first user).
package com.semweb.recipes.web;

import com.semweb.recipes.RecipeQueryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecommendSkillServlet", urlPatterns = "/recommend/skill")
public class RecommendSkillServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("mode", "skill");
            req.setAttribute("heading", "Recommendations by cooking skill (XPath, first user)");
            req.setAttribute("recipes", RecipeQueryService.recommendBySkillOnly(WebAppSupport.doc(req.getServletContext())));
            req.getRequestDispatcher("/WEB-INF/jsp/recommend.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
