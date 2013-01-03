package org.mimacom.fun.mariokart;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.methods.GetMethod;


public class ImageServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getPathInfo().substring(1);
        if (name.length() <= 2) {
            simpleImage(name, res);
        } else {
            String[] parts = name.split("/");
            memeImage(Codec.urlDecode(parts[0]), Codec.urlDecode(parts[1]), Codec.urlDecode(parts[2]), res);
        }
    }

    private void simpleImage(String person, HttpServletResponse res) throws IOException {
        InputStream in = getClass().getResourceAsStream(person + ".png");
        if (in != null) {
            res.setContentType("image/png");
            byte[] buf = new byte[10000];
            int read;
            while ((read = in.read(buf)) > 0) {
                res.getOutputStream().write(buf, 0, read);
            }
            res.getOutputStream().flush();
        }
    }

    private void memeImage(String id, final String person, final String car, final HttpServletResponse res) throws IOException {
        try {
            Util.doWithGet("http://webservice.imagesauce.net/image/" + id + "/600x.jpg", new Util.Getter() {

                @Override
                public void doGet(GetMethod get) throws IOException {
                    BufferedImage img = ImageIO.read(get.getResponseBodyAsStream());
                    Graphics2D g = img.createGraphics();
                    try {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        drawText(img, g, "" + person, 10);
                        drawText(img, g, "" + car, -10);
                    } finally {
                        g.dispose();
                    }
                    ImageIO.write(img, "png", res.getOutputStream());
                    res.getOutputStream().flush();
                }

            });
        } catch (IOException e) {
            simpleImage(person, res);
        }
    }

    private void drawText(BufferedImage img, Graphics2D g, String text, int y) {
        Shape outline = null;
        TextLayout tl = null;
        int fontSize = 100;
        do {
            Font f = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
            tl = new TextLayout(text, f, g.getFontRenderContext());
            outline = tl.getOutline(null);
            fontSize -= 10;
        } while (outline.getBounds().getWidth() > img.getWidth());

        AffineTransform trans = g.getTransform();
        int yeff = (int)(y > 0 ? tl.getAscent() - tl.getDescent() + y : img.getHeight() + y);
        g.translate((int)(img.getWidth() - outline.getBounds().getWidth()) / 2, yeff);
        g.setColor(Color.WHITE);
        tl.draw(g, 0, 0);
        g.setColor(Color.BLUE);
        BasicStroke stroke = new BasicStroke(1 + fontSize / 25);
        g.setStroke(stroke);
        g.draw(outline);
        g.setTransform(trans);
    }

}
