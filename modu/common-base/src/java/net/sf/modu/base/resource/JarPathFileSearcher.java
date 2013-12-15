package net.sf.modu.base.resource;

import java.util.List;

/**
 * <p>
 * Title: JarPathFileSearcher
 * </p>
 * <p>
 * Description:
 * <br>从Jar包中搜索符合条件的文件
 * </p>
 */
public interface JarPathFileSearcher {
    /**
     * 从指定Jar包中搜索文件
     * @param jarPath
     *        jar包路径
     * @param fileNameMatchPattern
     *        文件名称匹配规则,比如**yuep*.txt
     * @return
     */
    public List<String> search(String jarPath,String fileNameMatchPattern);

}
