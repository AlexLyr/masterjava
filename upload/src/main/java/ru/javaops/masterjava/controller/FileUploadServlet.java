package ru.javaops.masterjava.controller;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@WebServlet(urlPatterns = "/fileupload", name = "fileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {


    private TemplateEngine engine = new TemplateEngine();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(getServletContext());
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setCacheable(true);
        resolver.setSuffix(".html");
        resolver.setCacheTTLMs(60000L);
        resolver.setCharacterEncoding("utf-8");
        engine.setTemplateResolver(resolver);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        WebContext ctx = new WebContext(req, res, getServletConfig().getServletContext(), req.getLocale());
        engine.process("fileUpload", ctx, res.getWriter());

        res.setContentType("text/html;charset=UTF-8");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 1000);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            System.out.println("Name:");
            System.out.println(part.getName());
            System.out.println("Header: ");
            for (String headerName : part.getHeaderNames()) {
                System.out.println(headerName);
                System.out.println(part.getHeader(headerName));
            }
            System.out.println("Size: ");
            System.out.println(part.getSize());
            part.write(part.getName() + "-down");
        }
        res.sendRedirect("/upload/fileupload");
    }
}
