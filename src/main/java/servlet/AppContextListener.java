package servlet;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.File;
import java.io.IOException;
@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setSharedVariable("contextPath", sce.getServletContext().getContextPath());

            String templatePath = sce.getServletContext().getRealPath("/WEB-INF/templates");
            cfg.setDirectoryForTemplateLoading(new File(templatePath));

            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            sce.getServletContext().setAttribute("cfg", cfg);

            System.out.println("FreeMarker configuration initialized!");

            sce.getServletContext().setAttribute("cfg", cfg);
        } catch (TemplateModelException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
