package com.binomo.parsers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

@Component
public class XmlParser implements IParser {

    private static final List<Short> TEXT_NODES = Arrays.asList(Node.TEXT_NODE, Node.CDATA_SECTION_NODE);

    @Override
    public Map<String, String> extract(Resource resource) throws Exception {
        DocumentBuilder documentBuilder = newDocumentBuilder();
        Map<String, List<String>> map = new LinkedHashMap<>();
        Document document = documentBuilder.parse(resource.getInputStream());
        document.normalizeDocument();
        document.normalize();
        traverseDOM(map, document.getDocumentElement());
        return null;
    }

    private DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        return newFactory().newDocumentBuilder();
    }

    private DocumentBuilderFactory newFactory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setNamespaceAware(false);
        factory.setIgnoringElementContentWhitespace(true);
        return factory;
    }

    private void traverseDOM(Map<String, List<String>> map, Node root) {
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);
        String mapKey = ""; //root.getNodeName();
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            String nn = node.getNodeName();
            mapKey += (mapKey == "") ? nn : (nn != "#text" ? '.' + nn : "");
            System.out.println(node.getNodeType());
            if (TEXT_NODES.contains(node.getNodeType())
                    && StringUtils.isNotBlank(node.getNodeValue())) {

                List<String> values = map.computeIfAbsent(mapKey, k -> new ArrayList<>());
                values.add(node.getNodeValue());
            }

            if (node.hasAttributes()) {
                NamedNodeMap attributes = node.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    String attrName = attribute.getNodeName();
                    List<String> values = map
                            .computeIfAbsent(mapKey + '.' + attrName, k -> new ArrayList<>());
                    values.add(attribute.getNodeValue());
                }
            }

            List<Node> childs = new ArrayList<>();
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                childs.add(node.getChildNodes().item(i));
            }
            Collections.reverse(childs);
            childs.forEach(stack::push);

        }
        System.out.println();

    }
}
