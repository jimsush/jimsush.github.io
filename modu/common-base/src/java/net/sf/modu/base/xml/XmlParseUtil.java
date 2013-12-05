package net.sf.modu.base.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import net.sf.modu.base.exception.ModuException;

/**
 * <p>
 * Title: XmlParseUtil
 * </p>
 * <p>
 * Description: XML解析工具类,能从file,url,inputstream中读取XML文件
 * </p>
 * 
 */
public class XmlParseUtil {
    
    private URL xmlFile = null;
    private InputStream inputStream=null;
    
    private Element root = null;

    /**
     * @param root
     * @param nameAttr
     * @return
     */
    public static String getNodeAttr(Element node, String nameAttr) {
        return node.getAttributeValue(nameAttr);
    }

    /**
     * @param node
     * @param nameAttr
     * @param string
     * @return
     */
    public static String getNodeAttr(Element node, String nameAttr, String defaultValue) {
        return node.getAttributeValue(nameAttr, defaultValue);
    }
    
    /**
     * xml file url
     * 
     * @param file
     */
    public XmlParseUtil(URL file) {
        this.xmlFile = file;
        parseByFile();
    }

    public XmlParseUtil(InputStream inputStream){
        this.inputStream=inputStream;
        parseByInputStream();
    }
    

    private void parseByFile() {
        
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(xmlFile);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            throw new ModuException(ex);
        } catch (IOException ex) {
            throw new ModuException(ex);
        }
    }
    
    private void parseByInputStream() {

        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(inputStream);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            throw new ModuException(ex);
        } catch (IOException ex) {
            throw new ModuException(ex);
        }
    }

    /**
     * @return
     */
    public Element getDocumentElement() {
        return root;
    }
    
    /**
     * 关闭XML文件
     */
    public void close(){
        if(inputStream!=null){
            try{
                inputStream.close();
            }catch(Exception ex){
                throw new ModuException(ex);
            }
        }
    }
    
}
