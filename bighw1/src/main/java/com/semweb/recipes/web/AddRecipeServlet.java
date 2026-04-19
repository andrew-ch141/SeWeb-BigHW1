// File: AddRecipeServlet.java — Exercise 4: form + validation + persist to recipes.xml.
package com.semweb.recipes.web;

import com.semweb.recipes.CuisineOptions;
import com.semweb.recipes.RecipeMutationService;
import com.semweb.recipes.SkillLevel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AddRecipeServlet", urlPatterns = "/recipes/add")
public class AddRecipeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("cuisines", CuisineOptions.ALL);
        req.setAttribute("skills", SkillLevel.ALL);
        req.getRequestDispatcher("/WEB-INF/jsp/recipe-add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = req.getParameter("title");
        String c1 = req.getParameter("cuisine1");
        String c2 = req.getParameter("cuisine2");
        String diff = req.getParameter("difficulty");

        String err = validate(title, c1, c2, diff);
        if (err != null) {
            req.setAttribute("cuisines", CuisineOptions.ALL);
            req.setAttribute("skills", SkillLevel.ALL);
            req.setAttribute("error", err);
            req.setAttribute("title", title == null ? "" : title);
            req.setAttribute("cuisine1", c1);
            req.setAttribute("cuisine2", c2);
            req.setAttribute("difficulty", diff);
            req.getRequestDispatcher("/WEB-INF/jsp/recipe-add.jsp").forward(req, resp);
            return;
        }

        try {
            RecipeMutationService.addRecipe(
                    WebAppSupport.doc(req.getServletContext()),
                    title, c1, c2, diff,
                    WebAppSupport.schema(req.getServletContext()),
                    req.getServletContext());
            resp.sendRedirect(req.getContextPath() + "/recipes?added=1");
        } catch (Exception e) {
            req.setAttribute("cuisines", CuisineOptions.ALL);
            req.setAttribute("skills", SkillLevel.ALL);
            req.setAttribute("error", "Could not save: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/recipe-add.jsp").forward(req, resp);
        }
    }

    private static String validate(String title, String c1, String c2, String diff) {
        if (title == null || title.trim().length() < 2 || title.trim().length() > 200) {
            return "Title must be between 2 and 200 characters.";
        }
        if (!CuisineOptions.isAllowed(c1) || !CuisineOptions.isAllowed(c2)) {
            return "Pick two valid cuisine types from the lists.";
        }
        if (c1.equals(c2)) {
            return "Choose two different cuisine types.";
        }
        if (!SkillLevel.isValid(diff)) {
            return "Difficulty must be Beginner, Intermediate, or Advanced.";
        }
        return null;
    }
}
