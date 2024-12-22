package servlet;

import freemarker.template.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FreemarkerConfigSingleton {
    private static Configuration cfg;
    private static ServletContext sc;
    public static void init(ServletContext context) {
        if (cfg != null) {
            throw new IllegalStateException("Freemarker configuration has already been initialized");
        }
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setSharedVariable("contextPath", context.getContextPath());

            String templatePath = context.getRealPath("/WEB-INF/templates");
            cfg.setDirectoryForTemplateLoading(new File(templatePath));

            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (TemplateModelException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration getCfg() {
        if (cfg == null) {
            throw new IllegalStateException("Freemarker configuration is not initialized. Call init() first.");
        }
        return cfg;
    }

    public static void render(String templateName, Map<String, Object> model, HttpServletRequest req, HttpServletResponse response) throws IOException {
        try {
            Template template = cfg.getTemplate(templateName);
            model.put("session", req.getSession());
            template.process(model, response.getWriter());
        } catch (TemplateNotFoundException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }


    public static Template getTemplate(String templateName) throws IOException {
        return cfg.getTemplate(templateName);
    }
}
