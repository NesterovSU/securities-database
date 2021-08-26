package ru.nesterov.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.nesterov.app.domain.History;
import ru.nesterov.app.domain.Security;
import ru.nesterov.app.exceptions.MyParseException;
import ru.nesterov.app.repositories.HistRepo;
import ru.nesterov.app.repositories.SecRepo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class writes records to history and security databases from .xml files
 * @author Sergey Nesterov
 */
@Component
public class XmlToDataBase {

    @Autowired
    SecRepo secRepo;

    @Autowired
    HistRepo histRepo;


    public void parseSecurityXml(MultipartFile multipartFile){
        System.out.printf("\nЗагрузка ценных бумаг из файла %s\n",
                multipartFile.getOriginalFilename());
        try{
        NodeList rowList = prepare(multipartFile);
        for (int temp = 0; temp < rowList.getLength(); temp++) {

                Node rowNode = rowList.item(temp);
                Element row = (Element) rowNode;
                Security security = new Security(
                        Integer.parseInt(row.getAttribute("id")),
                        row.getAttribute("secid"),
                        row.getAttribute("regnumber"),
                        row.getAttribute("name"),
                        row.getAttribute("emitent_title"));
                secRepo.save(security);
            }//for
            System.out.println("Выполнено");
        } catch (
                NumberFormatException |
                        SAXException |
                        ParserConfigurationException |
                        IOException e)
        {
            System.err.println("Неверный формат данных!");
            e.printStackTrace();
            throw new MyParseException();
        }
    }//parseSecurityXml()


    public void parseHistoryXml(MultipartFile multipartFile){
        System.out.printf("\nЗагрузка истории торгов из файла %s\n",
                multipartFile.getOriginalFilename());
        try{
            NodeList rowList = prepare(multipartFile);

            for (int temp = 0; temp < rowList.getLength(); temp++) {

                Node rowNode = rowList.item(temp);
                Element row = (Element) rowNode;

                String tradedate = row.getAttribute("TRADEDATE");

                int numtrades;
                double open;
                double close;
                if (row.getAttribute("NUMTRADES").equals("0")){
                    numtrades = 0;
                    open = 0.;
                    close = 0.;
                }
                else {
                        numtrades = Integer.parseInt(row.getAttribute("NUMTRADES"));
                        open = Double.parseDouble(row.getAttribute("OPEN"));
                        close = Double.parseDouble(row.getAttribute("CLOSE"));
                    }
                String secid = row.getAttribute("SECID");
                try{
                    histRepo.save(new History(
                            tradedate,
                            numtrades,
                            open,
                            close,
                            secRepo.findBySecid(secid)
                    ));
                }
                catch (org.springframework.dao.InvalidDataAccessApiUsageException |
                        javax.validation.ConstraintViolationException |
                        NullPointerException e)
                {
                        System.out.printf("Отсутствует ценная бумага, secid = %s\n", secid);
                }
            }//for
            System.out.println("Выполнено");

        } catch (
                NumberFormatException |
                SAXException |
                ParserConfigurationException |
                IOException e)
        {
            System.err.println("Неверный формат данных!");
            e.printStackTrace();
            throw new MyParseException();
        }
    }//parseHistoryXml()

/**
* Parse .xml file 
* @return row elements list from .xml file
*/
    private NodeList prepare(MultipartFile multipartFile)
            throws
            IOException,
            ParserConfigurationException,
            SAXException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // parse XML file
            InputStream inputStream = multipartFile.getInputStream();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputStream);

            // get <data>
            NodeList dataList = doc.getElementsByTagName("data");
            Node dataNode = dataList.item(0);
            Element dataElement = (Element) dataNode;

            // get <row> in <data>
            return dataElement.getElementsByTagName("row");
    }//prepare()
}
