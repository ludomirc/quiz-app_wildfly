package org.qbit.quiz.web.servlet.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.qbit.quiz.model.impl.QuizPage;
import org.qbit.quiz.service.QuizPageService;
import org.qbit.quiz.web.LinkEnum;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.qbit.quiz.web.interceptor.Log;
import org.qbit.quiz.web.servlet.BaseHttpServlet;

/**
 * Created by beniamin.czaplicki on 2016-06-08.
 */
@WebServlet(name = "loadQuizPage", urlPatterns = "/loadquizpage")
@Log
public class LoadQuizPage extends BaseHttpServlet {

    Logger logger = LogManager.getLogger(LoadQuizPage.class);

    private static final String UPLOAD_FAILED = "UPLOAD_FAILED";
    private static final String UPLOAD_DONE = "UPLOAD_DONE";
    private static final String PARM_MESSAGE = "PARM_MESSAGE";
    private static final String PARM_ERROR_MESSAGE = "PARM_ERROR_MESSAGE";

    @EJB(beanName = "QuizPageBean")
    private QuizPageService qpeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp = openHtmlPage(resp, "Load quiz page servlet");

        Writer out = resp.getWriter();
        printUploadFileForm(out, req);
        retriveMessage(req, out);
        out.append(LinkEnum.Index.getHTML(getBaseUrl(req), "return to index"));
        out.flush();

        closeHtmlPage(resp);
    }

    private void retriveMessage(HttpServletRequest req, Writer out) throws IOException {
        out.append("<br/>");
        String message = req.getParameter(PARM_MESSAGE);
        String errMessage = req.getParameter(PARM_ERROR_MESSAGE);
        if (message != null) {
            out.append("<b>" + message + "</b>");
        }
        if (errMessage != null) {
            out.append("<b>" + errMessage + "</b>");
        }
        out.append("<br/>");
        out.flush();
        // showReqParams(req, out);
    }

    private void showReqParams(HttpServletRequest req, Writer out) throws IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        Iterator entries = parameterMap.entrySet().iterator();
        while (entries.hasNext()) {

            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            out.append("Key: " + key + "<br/>");
            String[] value = (String[]) entry.getValue();
            for (String elValue : value) {
                out.append("Key: " + key + " value: " + elValue + "<br/>");
            }
        }
        out.flush();
    }

    private void printUploadFileForm(Writer out, HttpServletRequest req) throws IOException {
        out.append("<h3>Load quiz page servlet</h3><br/>");
        out.append("<form action=\"" + LinkEnum.LoadQuizPage.getURL(getBaseUrl(req)) + "\" method=\"post\" enctype=\"multipart/form-data\">");
        out.append("<input type=\"file\" name=\"file\"  />");
        out.append("<br />");
        out.append("<input type=\"submit\" value=\"Upload File\" />");
        out.append("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isFinished = readFileFromRequest(req);
        String respLink = req.getContextPath() + LinkEnum.LoadQuizPage.getValue();
        respLink += "?";
        if (!isFinished) {
            respLink += PARM_ERROR_MESSAGE + "=" + UPLOAD_FAILED;
        } else {
            respLink += PARM_MESSAGE + "=" + UPLOAD_DONE;
        }

        logger.debug("respLink value: " + respLink);
        resp.sendRedirect(respLink);
    }

    private boolean readFileFromRequest(HttpServletRequest req) {
        boolean isFinished = true;
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(req);
            if (!isMultipart) {

            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;

                try {

                    items = upload.parseRequest(req);
                } catch (Exception e) {
                    isFinished = false;
                    logger.error(e);

                }
                Iterator itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (!item.isFormField()) {
                        String itemname = item.getName();
                        if ((itemname == null || itemname.equals(""))) {
                            continue;
                        }
                        loadQuizPageToDB(item.getInputStream());
                    }
                }
            }

        } catch (Exception e) {
            isFinished = false;
            logger.error(e);
        }
        return isFinished;
    }

    private void loadQuizPageToDB(InputStream is) {
        final int Question = 0;
        final int A = 1;
        final int B = 2;
        final int C = 3;
        final int D = 4;
        final int AN = 5;

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        Iterator rows = sheet.rowIterator();
        QuizPage quizPage = null;
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();

            quizPage = new QuizPage();
            quizPage.setQuestion(row.getCell(Question).getStringCellValue());
            quizPage.setA(row.getCell(A).getStringCellValue());
            quizPage.setB(row.getCell(B).getStringCellValue());
            quizPage.setC(row.getCell(C).getStringCellValue());
            quizPage.setD(row.getCell(D).getStringCellValue());
            quizPage.setAnswer(row.getCell(AN).getStringCellValue());

            qpeService.insert(quizPage);
        }
    }
}
