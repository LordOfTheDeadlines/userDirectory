package org.ulearnProjects.lod;

import org.ulearnProjects.lod.model.MyFile;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/")
public class DirectoryServlet extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        resp.setContentType("text/html");
        String path = req.getParameter("path");
        if(path==null || path.length()==0)
            path = "C:/";
        MyFile file = new MyFile(Paths.get(path));
        if(file.isDirectory()){
            req.setAttribute("dateTimeNow", new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date()));
            req.setAttribute("files", file.getListFiles(Paths.get(path)));
            req.setAttribute("pathFile",Paths.get(path));
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
        if(file.isFile()){
            resp.setHeader("Content-Type", "application/octet-stream");
            resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        }
    }
}
