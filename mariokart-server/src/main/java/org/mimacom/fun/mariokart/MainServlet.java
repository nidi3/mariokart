package org.mimacom.fun.mariokart;


import java.io.PrintWriter;




public class MainServlet extends DecodingServlet {
    @Override
    protected void writeHtml(PrintWriter out, String path, String id, Selection selection) {
        out.write("<div style='text-align: center'>");
        out.write("<img src='" + path + "/image/" + selection.getPerson()
                + "' style='vertical-align: middle' height='300px' width='300px' />");
        out.write("<span style='font-size: 70px; font-weight: bold; margin-left: 40px'>" + selection.getCar() + "</span>");
        out.write("</div>");
    }
}
