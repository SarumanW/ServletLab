package com.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.servlet.entity.ChangeViewModel;
import com.servlet.entity.Product;
import com.servlet.machinestate.MachineContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String change = req.getParameter("param");

        if (change != null) {
            Type listType = new TypeToken<List<ChangeViewModel>>() {}.getType();
            Object changeViewModelList = gson.fromJson(change, listType);

            req.setAttribute("change", changeViewModelList);
        }

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String productNumber = request.getParameter("productNumber");

        if (request.getParameterMap().containsKey("productNumber")) {
            Map<String, Object> responseObject;
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");

            if (productNumber == null || productNumber.isEmpty()) {
                responseObject = new HashMap<>();
                responseObject.put("success", false);
                responseObject.put("message", "Product number is empty!");
                response.getWriter().write(this.gson.toJson(responseObject));
                return;
            }

            responseObject = this.machineContext.dispenseProduct(Integer.valueOf(productNumber));

            response.getWriter().write(this.gson.toJson(responseObject));
        } else if (request.getParameterMap().containsKey("nominal")) {
            String nominal = request.getParameter("nominal");
            String amount = this.machineContext.insertMoney(nominal);

            response.setContentType("text/plain");
            response.getWriter().write(amount);
        } else if (request.getParameterMap().containsKey("reset")) {
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");

            HashMap<String, Object> responseObject = this.machineContext.reset();
            response.getWriter().write(this.gson.toJson(responseObject));
        }
    }

}