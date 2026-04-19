// File: UserEditServlet.java — Exercise 5: user form persisted to XML.
package com.semweb.recipes.web;

import com.semweb.recipes.CuisineOptions;
import com.semweb.recipes.RecipeMutationService;
import com.semweb.recipes.RecipeQueryService;
import com.semweb.recipes.SkillLevel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserEditServlet", urlPatterns = "/user/edit")
public class UserEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            var u = RecipeQueryService.userById(WebAppSupport.doc(req.getServletContext()), "1");
            req.setAttribute("firstName", u.getOrDefault("firstName", ""));
            req.setAttribute("lastName", u.getOrDefault("lastName", ""));
            req.setAttribute("cookingSkill", u.getOrDefault("cookingSkill", "Intermediate"));
            req.setAttribute("preferredCuisine", u.getOrDefault("preferredCuisine", "Italian"));
            req.setAttribute("cuisines", CuisineOptions.ALL);
            req.setAttribute("skills", SkillLevel.ALL);
            req.setAttribute("allUsers", RecipeQueryService.allUsers(WebAppSupport.doc(req.getServletContext())));
            req.getRequestDispatcher("/WEB-INF/jsp/user-edit.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fn = req.getParameter("firstName");
        String ln = req.getParameter("lastName");
        String skill = req.getParameter("cookingSkill");
        String pref = req.getParameter("preferredCuisine");

        String err = validate(fn, ln, skill, pref);
        if (err != null) {
            setUserFormAttrs(req, fn, ln, skill, pref);
            req.setAttribute("error", err);
            req.getRequestDispatcher("/WEB-INF/jsp/user-edit.jsp").forward(req, resp);
            return;
        }

        String action = req.getParameter("action");
        boolean addNew = "add".equals(action);

        try {
            if (addNew) {
                RecipeMutationService.appendUser(
                        WebAppSupport.doc(req.getServletContext()),
                        fn, ln, skill, pref,
                        WebAppSupport.schema(req.getServletContext()),
                        req.getServletContext());
                resp.sendRedirect(req.getContextPath() + "/user/edit?saved=add");
            } else {
                RecipeMutationService.savePrimaryUser(
                        WebAppSupport.doc(req.getServletContext()),
                        fn, ln, skill, pref,
                        WebAppSupport.schema(req.getServletContext()),
                        req.getServletContext());
                resp.sendRedirect(req.getContextPath() + "/user/edit?saved=1");
            }
        } catch (Exception e) {
            setUserFormAttrs(req, fn, ln, skill, pref);
            req.setAttribute("error", "Could not save user: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/user-edit.jsp").forward(req, resp);
        }
    }

    private static void setUserFormAttrs(HttpServletRequest req, String fn, String ln, String skill, String pref)
            throws ServletException {
        req.setAttribute("cuisines", CuisineOptions.ALL);
        req.setAttribute("skills", SkillLevel.ALL);
        try {
            req.setAttribute("allUsers", RecipeQueryService.allUsers(WebAppSupport.doc(req.getServletContext())));
            copyParams(req, fn, ln, skill, pref);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static void copyParams(HttpServletRequest req, String fn, String ln, String skill, String pref) {
        req.setAttribute("firstName", fn != null ? fn : "");
        req.setAttribute("lastName", ln != null ? ln : "");
        req.setAttribute("cookingSkill", skill);
        req.setAttribute("preferredCuisine", pref);
    }

    private static String validate(String fn, String ln, String skill, String pref) {
        if (fn == null || fn.trim().length() < 1 || fn.trim().length() > 80) {
            return "First name is required (max 80 characters).";
        }
        if (ln == null || ln.trim().length() < 1 || ln.trim().length() > 80) {
            return "Last name is required (max 80 characters).";
        }
        if (!SkillLevel.isValid(skill)) {
            return "Cooking skill must be Beginner, Intermediate, or Advanced.";
        }
        if (!CuisineOptions.isAllowed(pref)) {
            return "Choose a valid preferred cuisine.";
        }
        return null;
    }
}
