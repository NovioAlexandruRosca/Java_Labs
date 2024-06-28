package org.example.commands;

import org.example.datatypes.DataModel;
import org.example.enums.Display;
import org.example.repository.DocumentRepo;
import org.example.utility.ServiceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.utility.Utility;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * The report class creates an html report of all the files for each and every employee
 */
public class Report extends CommandAbstract implements Command{

    /**
     * The document repository
     */
    private final DocumentRepo repository;

    public Report(String repositoryPath, DocumentRepo repository) {
        super(repositoryPath);
        this.repository = repository;
    }

    /**
     * Creates the html report
     * @throws ServiceException If the file is not supported on windows it s gonna throw an error
     * @throws IOException  If the file that s been created cant be opened or doesnt exits the exception will be thrown
     */
    @Override
    public void run() throws ServiceException, IOException {
        generateHTMLReport();
    }

    /**
     * Generates an HTML report based on the data retrieved from the service
     * The generated HTML content is written to a file and then opened in a web browser
     *
     * @throws ServiceException If an error occurs while retrieving data from the service
     * @throws IOException       If an I/O error occurs while writing the HTML content to a file
     */
    public void generateHTMLReport() throws ServiceException, IOException {
        try {

            String htmlContent = getHtmlReport();
            String outputFile = getRepositoryPath() + "report.html";
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(htmlContent);
            fileWriter.close();

            openHTMLReport(outputFile);

        } catch ( TemplateException e) {
            System.out.println(Utility.textColoring("There is a template error", Utility.ansiEscapeCodes.RED));
        }
    }

    /**
     * Generates HTML content for an employee report based on data retrieved from the repository
     * and a list of employee files present there
     * @return The generated HTML content for the employee report
     * @throws IOException       If an I/O error occurs while loading the template file or processing the template
     * @throws TemplateException If an error occurs while processing the FreeMarker template
     */
    private String getHtmlReport() throws IOException, TemplateException {

        DataModel data = repository.displayRepo(Display.DisplayOff);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("title", "Employee Report");
        dataModel.put("content", "Report of the files of every employee of the company");
        dataModel.put("idList", data.getIdList());

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File(getRepositoryPath()));
        Template template = cfg.getTemplate("report_template.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(dataModel, stringWriter);
        return stringWriter.toString();
    }

    /**
     *  Opens the file where the report has been saved
     * @param filePath   the path to the file
     * @throws ServiceException the file cant be opened on windows(probably a NotCompatibleException exits)
     * @throws IOException   If an I/O error occurs while loading the template file or processing the template
     */
    public void openHTMLReport(String filePath) throws ServiceException, IOException{

        if (Desktop.isDesktopSupported()) {
            File htmlFile = new File(filePath);
            Desktop desktop = Desktop.getDesktop();

            if (htmlFile.exists()) {
                desktop.open(htmlFile);
            } else {
                throw new FileNotFoundException("File does not exist: " + filePath);
            }
        } else {
            throw new ServiceException("The file is not supported on windows");
        }
    }
}
