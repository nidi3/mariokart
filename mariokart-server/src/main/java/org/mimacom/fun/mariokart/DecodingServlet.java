package org.mimacom.fun.mariokart;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class DecodingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        try {
            String code = req.getPathInfo().substring(1);
            String id = Codec.decodeId(code);
            int person = Codec.decodePerson(code);
            int car = Codec.decodeCar(code);
            writeHtml(out, req.getContextPath(), id,new Selection(person, car));
        } catch (Exception e) {
            out.write("Uh oh...");
        }
        out.flush();
    }

    protected abstract void writeHtml(PrintWriter out, String path, String id,Selection selection);
}
