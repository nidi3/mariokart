package org.mimacom.fun.mariokart;


import java.io.PrintWriter;


public class MemeServlet extends MainServlet {
    @Override
    protected void writeHtml(PrintWriter out, String path, String id, Selection selection) {
        out.write("<div style='text-align: center'>");
        out.write("<img src='" + path + "/image/" + Codec.urlEncode(id) + "/" + Codec.urlEncode(selection.getPersonName()) + "/"
                + Codec.urlEncode(selection.getCarName()) + "' style='vertical-align: middle' height='300px' width='300px' />");
        out.write("</div>");
    }

}
