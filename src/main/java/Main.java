import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List parseXML(String fileName) {
        List<Employee> list = new ArrayList<>();
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(fileName));
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                Employee employee = new Employee();
                employee.id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                employee.age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                employee.country = element.getElementsByTagName("country").item(0).getTextContent();
                employee.firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                employee.lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                list.add(employee);
            }
        }

        return list;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String json) {
        try (FileWriter writer = new FileWriter("data2.json", false)) {
            writer.write(json);
            writer.flush();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void main(String[] args) {

        List<Employee> list = parseXML("data.xml");
        String json = listToJson(list);
        writeString(json);

    }
}