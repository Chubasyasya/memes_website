package servlet;

import freemarker.template.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FreemarkerBuilder {
    private final Configuration configuration;

    public FreemarkerBuilder(ServletContextEvent sce) {
        try {
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setSharedVariable("contextPath", sce.getServletContext().getContextPath());

        String templatePath = sce.getServletContext().getRealPath("/WEB-INF/templates");
        configuration.setDirectoryForTemplateLoading(new File(templatePath));

        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        } catch (TemplateModelException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(String templateName, Map<String, Object> model, HttpServletResponse response) throws IOException {
        try {
            Template template = configuration.getTemplate(templateName);
            template.process(model, response.getWriter());
        } catch (TemplateNotFoundException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
