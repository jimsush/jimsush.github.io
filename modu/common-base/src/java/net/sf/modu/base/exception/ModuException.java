package net.sf.modu.base.exception;

/**
 * <p>
 * Title: ModuException
 * </p>
 * <p>异常基类，包括Error Code,Source,cause,message
 * </p>
 * 
 */
public class ModuException extends RuntimeException {
    
    private static final long serialVersionUID = 5893725839548086058L;
    
    /**
     * 未归类的内部错误,{0}
     */
    public final static int INTERNAL_ERROR=0;
    
    /** 错误码 */
    private int errorCode;
    
    /** 附加信息，比如可以放入对象源等 */
    private String[] source;

    public ModuException(int errorCode, String... source){
        super(getDefaultDetailMessage(errorCode,source)); //需要设一个message,否则打印出来的异常信息过于简单,不便于查错
        this.errorCode = errorCode;
        this.source = source;
    }
    
    /**
     * 通过error code,source得到缺省的显示信息
     * @param errorCode
     * @param source
     * @return
     */
    private static String getDefaultDetailMessage(int errorCode, String... source){
        if(source==null||source.length==0){
            return "code="+errorCode;
        }else{
            if(source.length==1){
                return "code="+errorCode+",source="+source[0];
            }else{
                StringBuilder sb=new StringBuilder();
                sb.append("code=").append(errorCode);
                for(int i=0;i<source.length;i++){
                    sb.append(",source=").append(source[i]);
                }
                return sb.toString();
            }
        }
    }
    
    public ModuException(int errorCode,Throwable th,String... source) {
        super(th);
        this.errorCode = errorCode;
        this.source = source;
    }

    public ModuException(String message) {
        super(message);
    }
    
    /**
     * 根异常
     * @param th rootCause
     */
    public ModuException(Throwable th) {
        super(th);
    }
    
    /**
     * 错误码
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 附加信息，比如可以放入对象源等
     * @return the source
     */
    public String[] getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String[] source) {
        this.source = source;
    }
}
