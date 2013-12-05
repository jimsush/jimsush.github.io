package net.sf.modu.base.resource;

import java.util.List;

/**
 * <p>
 * Title: CommonPathFileSearcher
 * </p>
 * <p>
 * Description:
 * <br>从指定文件目录中搜索文件
 * </p>
 */
public interface CommonPathFileSearcher {
    
    /**
     * 从指定文件目录中搜索文件
     * @param filePath
     *        文件目录
     * @param fileNameMatchPattern
     *        文件名称匹配规则,比如**yuep*.txt
     * @return
     *        符合条件的文件
     */
    public List<String> search(String filePath,String fileNameMatchPattern);
    
}
