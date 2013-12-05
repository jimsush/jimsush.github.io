package net.sf.modu.base.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.modu.base.xml.ArrayListItems;
import net.sf.modu.base.xml.XmlFileReader;

/**
 * <p>
 * Title: ClassPathXMLFileReaderImpl
 * </p>
 * <p>
 * Description: ∂¡»°xml
 * </p>
 * 
 */
public class ClassPathXMLFileReaderImpl implements ClassPathXMLFileReader {

    private ClassPathFileSearcher classPathFileSearcher=new ClassPathFileSearcherImpl();
    
    /**
     * 
     * @see com.yuep.base.resource.ClassPathXMLFileReader#read(java.lang.String, java.lang.String, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> read(String xmlDataFile, String xmlFileMapping,Class<? extends T> objClass) {
        ArrayListItems<T> data = (ArrayListItems<T>) XmlFileReader.getXmlConfig(xmlDataFile, xmlFileMapping);
        return data.getItems();
    }
    
    /**
     * 
     * @see com.yuep.base.resource.ClassPathXMLFileReader#searchAndRead(java.lang.String, java.lang.String, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> searchAndRead(String xmlFileNameMatchPattern,String xmlFileMapping, Class<? extends T> objClass) {
        List<URL> filePathResources = classPathFileSearcher.searchResource(xmlFileNameMatchPattern);
       
        List<T> fileData=new ArrayList<T>();
        
        for (URL filePathResource : filePathResources) { 
            InputStream inputStream = null;
            try {
                inputStream = filePathResource.openStream();
            } catch (IOException ex) {
                throw new IllegalStateException("getInputStream failure", ex);
            }
            ArrayListItems<T> data = (ArrayListItems<T>) XmlFileReader.getXmlConfig(inputStream, xmlFileMapping);
            if(data!=null)
                 fileData.addAll(data.getItems());
        }
        
        return fileData;
    }

}
