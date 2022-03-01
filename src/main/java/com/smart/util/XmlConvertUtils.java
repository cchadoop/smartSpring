package com.smart.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @author luopc
 * xml转换工具
 */
public class XmlConvertUtils {

    /**
     * params pathName 文件路径 "D:\\data\\communication\\receive\\xxx.xml"
     * 获取xml文件内容
     */
    public static String readFileXml(String pathName) {
        File file = new File(pathName);
        String strXml = null;
        if (!file.exists()) {
            return null;
        }
        strXml = readeFileContent(file);
        return strXml;
    }

    /**
     * params xmlStr
     * xml字符串转json
     */
    public static JSONObject xmlToJson(String xmlStr) {
        if (StrUtil.isEmpty(xmlStr)) {
            return null;
        }
        JSONObject jsonObject = XML.toJSONObject(xmlStr);
        return jsonObject;
    }

    /**
     * params jsonObject
     * json转xml
     */
    public static String jsonToXml(JSONObject jsonObject) {
        String xml = XML.toXml(jsonObject);
        return xml;
    }

    /**
     * params dataXml 发送或接收到的data xml格式
     * 创建xmL，头部信息拓展使用。data里也包含了发送者和接收者信息。
     */
    public static String createXml(String dataXml, Map<String, String> map) {
        String xmlStr = null;
        try {
            //创建DocumentBuilderFactory对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();
            document.setXmlStandalone(true);
            //建立xml文件及头部信息和赋值
            Element application = document.createElement("Root");
            Element header = document.createElement("Head");

            Element version = document.createElement("Version");
            version.setTextContent("1.0");
            Element applicationID = document.createElement("ApplicationID");
            applicationID.setTextContent(map.get("applicationId"));
            Element sender = document.createElement("Sender");
            sender.setTextContent(map.get("sender"));
            Element receiver = document.createElement("Receiver");
            receiver.setTextContent(map.get("receiver"));
            Element fileName = document.createElement("FileName");
            fileName.setTextContent(map.get("filename"));
            Element sendTime = document.createElement("SendTime");
            sendTime.setTextContent(map.get("sendTime"));
            Element receiverTime = document.createElement("ReceiverTime");
            receiverTime.setTextContent("");

            Element messageId = document.createElement("MessageID");
            String messageContext = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            messageId.setTextContent(map.get("applicationId") + messageContext);

            Element body = document.createElement("body");
            body.setTextContent("dataXml");

            //定义节点信息
            header.appendChild(version);
            header.appendChild(applicationID);
            header.appendChild(sender);
            header.appendChild(receiver);
            header.appendChild(fileName);
            header.appendChild(sendTime);
            header.appendChild(receiverTime);
            header.appendChild(messageId);

            application.appendChild(header);
            application.appendChild(body);
            document.appendChild(application);

            //创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            //读取创建的xml信息
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            tf.transform(new DOMSource(document), new StreamResult(bos));
            xmlStr = bos.toString();
            //插入dataXml信息
            xmlStr = xmlStr.replace("dataXml", dataXml);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlStr;
    }

    /**
     * params xml
     * 创建xmL文件
     */
    public static void createXmlFile(String xml, String fileName, String path) {
        try {
            //创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            //创建xml文件。
            Document docXml = XmlUtil.parseXml(xml);
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(docXml), new StreamResult(new File(path + "/" + fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //文件夹
    public static void xmlFToJson(String pathName) {
        File file = new File("D:\\data\\communication\\receive");
        File[] fileArray = file.listFiles();
        for (int i = 0; i < fileArray.length; i++) {
            //判断是否是文件
            if (fileArray[i].isFile()) {
                String xml = readeFileContent(fileArray[i]);
                xml = xml.substring(xml.indexOf("<body>") + "<body>".length(), xml.indexOf("</body>"));
                JSONObject jsonObject = XML.toJSONObject(xml);
                String jsonStr = jsonObject.toString();
            }
        }
    }


    /**
     * 文件名称
     */
    public static String fileName(String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmss");
        String filename = "";
        String timestamp = sdf.format(new Date());
        if ("send".equals(type)) {
            filename = "success-send" + timestamp + ".xml";
        } else {
            filename = "success-receive" + timestamp + ".xml";
        }
        return filename;
    }

    /**
     * 获取文件夹中所有文件
     *
     * @param
     */
    public static String getFileStr() {
        File file = new File("D:\\data\\communication\\receive");
        File[] fileArray = file.listFiles();
        for (int i = 0; i < fileArray.length; i++) {
            //判断是否是文件
            if (fileArray[i].isFile()) {
                System.out.println(fileArray[i].getName());
            }
        }
        return "";
    }

    /**
     * 获取文件内容
     *
     * @param file
     * @return 返回文件字符串
     */
    public static String readeFileContent(File file) {
        InputStream in;

        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(isr);
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    ;


    /**
     * 文件移动
     *
     * @param oldPath
     * @param newPath
     */
    public static void fileMove(String oldPath, String newPath) {
        File oldName = new File(oldPath);
        File newName = new File(newPath);
        oldName.renameTo(newName);
    }


    public static void main(String[] args) {
        //xmlToJson();
        //String xml=jsonToXml();
        //getFileStr();
        //createXmlFile();
    }
}
