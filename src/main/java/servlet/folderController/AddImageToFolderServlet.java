package servlet.folderController;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FolderService;
import util.StringToLongUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet("/addImageToFolder")
public class AddImageToFolderServlet extends HttpServlet {
    private static FolderService folderService;

    @Override
    public void init() throws ServletException {
        folderService = (FolderService) getServletContext().getAttribute("folderService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null){
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        Map<String, String> requestData = gson.fromJson(jsonBuilder.toString(), Map.class);
        long imageId = StringToLongUtil.getLongParameter(requestData.get("imageId"));
        long folderId = StringToLongUtil.getLongParameter(requestData.get("folderId"));

        if(folderService.addImage(folderId, imageId)){
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
