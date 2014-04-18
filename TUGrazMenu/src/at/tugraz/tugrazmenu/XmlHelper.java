package at.tugraz.tugrazmenu;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XmlHelper {

    public String getSiteContent(String urlString) throws IOException {
        URL url = new URL(urlString);

        InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
        BufferedReader input = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String lineOfContent;
        while ((lineOfContent = input.readLine()) != null) {
            stringBuilder.append(lineOfContent);
        }
        input.close();
        return stringBuilder.toString();
    }

    public NodeList splitBodyIntoSingleItems(String xmlFormattedInput, String nameOfTagToGet) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        StringReader stringReader = new StringReader(xmlFormattedInput);
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(stringReader);
        Document xmlDoc = documentBuilder.parse(inputSource);

        return xmlDoc.getElementsByTagName(nameOfTagToGet);
    }

    public List<MenuItemContainer> createMenuList(NodeList nodeList) {
        List<MenuItemContainer> menuList = new ArrayList<MenuItemContainer>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            String title = null;
            String link = null;
            String guid = null;
            String description = null;
            String pubDate = null;

            NodeList childNodes = nodeList.item(i).getChildNodes();
            if (childNodes.getLength() >= 5) {
                for (int j = 0; j < childNodes.getLength(); j++) {
                    String nodeName = childNodes.item(j).getNodeName();
                    if (nodeName.equals("title")) {
                        title = childNodes.item(j).getTextContent();
                    } else if (nodeName.equals("link")) {
                        link = childNodes.item(j).getTextContent();
                    } else if (nodeName.equals("guid")) {
                        guid = childNodes.item(j).getTextContent();
                    } else if (nodeName.equals("description")) {
                        description = childNodes.item(j).getTextContent();
                    } else if (nodeName.equals("pubDate")) {
                        pubDate = childNodes.item(j).getTextContent();
                    }
                }
                menuList.add(new MenuItemContainer(title, link, guid, description, pubDate));
            }

        }
        if (menuList.isEmpty()) {
            throw new IllegalStateException(String.valueOf(R.string.invalid_node_list_exception));
        }
        return menuList;
    }


}
