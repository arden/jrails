/* XMLUtil.java
 * --------------------------------------
 * CREATED ON Jun 20, 2006 6:23:07 PM
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

/**
 * XML处理
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class XMLUtil {

    public XMLUtil() {
    }

    public static String getChildText(Element parent, String name) {
        Element e = getChildByName(parent, name);
        if (e == null)
            return "";
        else
            return getText(e);
    }

    public static String getText(Element e) {
        NodeList nl = e.getChildNodes();
        int max = nl.getLength();
        for (int i = 0; i < max; i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == 3)
                return n.getNodeValue();
        }

        return "";
    }

    public static Element getChildByName(Element e, String name) {
        Element list[] = getChildrenByName(e, name);
        if (list.length == 0)
            return null;
        if (list.length > 1)
            throw new IllegalStateException("Too many (" + list.length + ") '" + name + "' elements found!");
        else
            return list[0];
    }

    @SuppressWarnings("unchecked")
    public static Element[] getChildrenByName(Element e, String name) {
        NodeList nl = e.getChildNodes();
        int max = nl.getLength();
        ArrayList list = new ArrayList();
        for (int i = 0; i < max; i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == 1 && n.getNodeName().equals(name))
                list.add(n);
        }

        return (Element[]) list.toArray(new Element[list.size()]);
    }

    public static Element getChildByName(Document doc, String name) {
        NodeList nl = doc.getChildNodes();
        int max = nl.getLength();
        for (int i = 0; i < max; i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == 1 && n.getNodeName().equals(name))
                return (Element) n;
        }

        return null;
    }

    public static Element createChild(Document doc, Element root, String name) {
        Element elem = doc.createElement(name);
        root.appendChild(elem);
        return elem;
    }

    public static void createChildText(Document doc, Element elem, String name, String value) {
        Element child = doc.createElement(name);
        child.appendChild(doc.createTextNode(value != null ? value : ""));
        elem.appendChild(child);
    }

    public static void createOptionalChildText(Document doc, Element elem, String name, String value) {
        if (value == null || value.length() == 0) {
            return;
        } else {
            Element child = doc.createElement(name);
            child.appendChild(doc.createTextNode(value));
            elem.appendChild(child);
            return;
        }
    }
}

