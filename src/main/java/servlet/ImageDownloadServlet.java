package servlet;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@WebServlet("/image-download")
public class ImageDownloadServlet extends HttpServlet {
    private final String imageRootPath = "C:\\JavaProjects\\oris\\semesterwork\\memesWebAppStorage\\";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String filename = req.getParameter("image_name");
            File file = new File(imageRootPath + filename);

            resp.setContentType("image/jpeg");

            resp.setContentLength((int) file.length());
            try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                ServletOutputStream outputStream = resp.getOutputStream()){
                int readBytes = 0;
                while ((readBytes = bufferedInputStream.read()) != -1)
                    outputStream.write(readBytes);

                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
