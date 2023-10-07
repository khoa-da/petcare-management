/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Resources.ResourcesDAO;
import Resources.ResourcesDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN-PC
 */
public class InsertResourceController extends HttpServlet {

    private int author_id = 1;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String content_resources = request.getParameter("content");
            String category = request.getParameter("category");
            String author_name = request.getParameter("author_name");
            int author_id = Integer.parseInt(request.getParameter("author_id"));
            String created_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String image_link = request.getParameter("image_link");
            ResourcesDAO resourceDAO = new ResourcesDAO();
            List<ResourcesDTO> newList = resourceDAO.insertResource(title, description, content_resources, category, author_id, author_name, created_date, image_link);

            HttpSession session = request.getSession();
            List<ResourcesDTO> currentList = (List<ResourcesDTO>) session.getAttribute("list");

            if (newList != null && !newList.isEmpty()) {
                // Thêm bài viết mới vào danh sách hiện tại
                if (currentList != null) {
                    currentList.addAll(newList);
                } else {
                    currentList = newList;
                }
                request.setAttribute("message", "You have successfully inserted your post! Please return to your directory");
                 request.getRequestDispatcher("AddResources.jsp").forward(request, response);

            } else {
                request.setAttribute("message", "Insert failed ! Please update later");
                request.getRequestDispatcher("AddResources.jsp").forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}