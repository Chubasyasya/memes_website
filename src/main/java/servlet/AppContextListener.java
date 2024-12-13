package servlet;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import service.*;

import java.io.File;
import java.io.IOException;
@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("freemarker", new FreemarkerBuilder(sce));
        sce.getServletContext().setAttribute("commentService", new CommentService());
        sce.getServletContext().setAttribute("accountService", new AccountService());
        sce.getServletContext().setAttribute("favoriteService", new FavoriteService());
        sce.getServletContext().setAttribute("imageService", new ImageService());
        sce.getServletContext().setAttribute("publicationService", new PublicationService());
        sce.getServletContext().setAttribute("folderService", new FolderService());
    }
}
