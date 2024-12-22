package servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import properties.ServletContextAttributeProperties;
import service.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FreemarkerConfigSingleton.init(sce.getServletContext());
        sce.getServletContext().setAttribute(ServletContextAttributeProperties.COMMENT_SERVICE.getKey(), new CommentService());
        sce.getServletContext().setAttribute(ServletContextAttributeProperties.ACCOUNT_SERVICE.getKey(), new AccountService());
        sce.getServletContext().setAttribute(ServletContextAttributeProperties.FAVORITE_SERVICE.getKey(), new FavoriteService());
        sce.getServletContext().setAttribute(ServletContextAttributeProperties.IMAGE_SERVICE.getKey(), new ImageService());
        sce.getServletContext().setAttribute(ServletContextAttributeProperties.PUBLICATION_SERVICE.getKey(), new PublicationService());
        sce.getServletContext().setAttribute(ServletContextAttributeProperties.FOLDER_SERVICE.getKey(), new FolderService());
    }
}
