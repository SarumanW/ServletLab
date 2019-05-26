package com.servlet;

import com.google.gson.Gson;
import com.servlet.entity.Product;
import com.servlet.machinestate.MachineContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/machine")
public class MainServlet extends HttpServlet {

    private MachineContext machineContext;
    private Gson gson;

    public MainServlet() {
        machineContext = new MachineContext();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> listOfProducts = MachineContext.getListOfProducts();
        req.setAttribute("products", listOfProducts);
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("productNumber") != null) {
            String productNumber = request.getParameter("productNumber");
            HashMap<String, Object> responseObject =
                    this.machineContext.dispenseProduct(Integer.valueOf(productNumber));

            String responseString = this.gson.toJson(responseObject);

            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responseString);

        } else {
            String nominal = request.getParameter("nominal");
            String amount = this.machineContext.insertMoney(nominal);

            response.setContentType("text/plain");
            response.getWriter().write(amount);
        }

    }

}