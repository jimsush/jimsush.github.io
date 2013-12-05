package net.sf.modu.base.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import net.sf.modu.base.exception.ModuException;
import net.sf.modu.base.resource.FileLoader;

/**
 * <p>
 * Title: XmlWriterImpl
 * </p>
 * <p>
 * Castor写实现
 * </p>
 */
public class XmlFileWriter {

    /**
     * 通过Castor将数据写入指定目录的文件
     * @param mappingFileName Mapping路径和文件名
     * @param dataFile 包含写入文件的路径和文件名
     * @param obj 需要写入的数据
     * return true成功,false失败
     */
    public static boolean writeFile(String mappingFileName, String dataFile,
            Object obj) throws Exception {
        InputStream fis = null;
        OutputStreamWriter osw = null;
        try {
            Mapping mapping = new Mapping(Thread.currentThread().getContextClassLoader());
            fis=FileLoader.getInputStream(mappingFileName);
            InputSource is = new InputSource(fis);
            mapping.loadMapping(is);
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Marshaller ma = new Marshaller(doc);
            ma.setMapping(mapping);
            ma.marshal(obj);
            XMLOutputter outputter = new XMLOutputter("    ", true);
            DOMBuilder domBuilder = new DOMBuilder();
            org.jdom.Document document = domBuilder.build(doc);
            FileOutputStream fos = new FileOutputStream(dataFile);
            osw = new OutputStreamWriter(fos, "UTF-8");
            outputter.output(document, osw);
            return true;
        } catch (Exception ex) {
            throw new ModuException(ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    throw new ModuException(ex);
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException ex) {
                    throw new ModuException(ex);
                }
            }
        }
    }
    
    
    /**
     *  通过Castor将数据写入指定目录的文件
     * @param mappingFileName
     *       
     * @param dataOutPutStream
     * @param obj
     * @return
     * @throws Exception
     */
    public static boolean writeFile(InputStream mappingFileInputStream, OutputStream dataFileOutPutStream,
            Object obj) throws Exception {
        InputStream fis = mappingFileInputStream;
        OutputStreamWriter osw = null;
        try {
            Mapping mapping = new Mapping(Thread.currentThread().getContextClassLoader());
            InputSource is = new InputSource(mappingFileInputStream);
            mapping.loadMapping(is);
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Marshaller ma = new Marshaller(doc);
            ma.setMapping(mapping);
            ma.marshal(obj);
            XMLOutputter outputter = new XMLOutputter("    ", true);
            DOMBuilder domBuilder = new DOMBuilder();
            org.jdom.Document document = domBuilder.build(doc);
            osw = new OutputStreamWriter(dataFileOutPutStream, "UTF-8");
            outputter.output(document, osw);
            return true;
        } catch (Exception ex) {
            throw new ModuException(ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    throw new ModuException(ex);
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException ex) {
                    throw new ModuException(ex);
                }
            }
        }
    }
    
    /**
     * 通过Castor将数据写入指定目录的文件
     * @param mappingInputStream mapping文件的输入流
     * @param dataFile 包含写入文件的路径和文件名
     * @param obj 需要写入的数据
     * return true成功,false失败
     */
    public static boolean writeFile(InputStream mappingInputStream, String dataFile,
            Object obj) throws Exception {
        InputStream fis = mappingInputStream;
        OutputStreamWriter osw = null;
        try {
            InputSource is = new InputSource(fis);
            Mapping mapping = new Mapping(Thread.currentThread().getContextClassLoader());
            mapping.loadMapping(is);
            // 以well-formated的格式输出
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Marshaller ma = new Marshaller(doc);
            ma.setMapping(mapping);
            ma.marshal(obj);
            XMLOutputter outputter = new XMLOutputter("    ", true);
            DOMBuilder domBuilder = new DOMBuilder();
            org.jdom.Document document = domBuilder.build(doc);
            FileOutputStream fos = new FileOutputStream(dataFile);
            osw = new OutputStreamWriter(fos, "UTF-8");
            outputter.output(document, osw);
            return true;
        } catch (Exception ex) {
            throw new ModuException(ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    throw new ModuException(ex);
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException ex) {
                    throw new ModuException(ex);
                }
            }
        }
    }

}
