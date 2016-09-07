package com.ld.sftp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.ld.common.Dictionary;

public class SftpUtil {
    private final static Logger log = Logger.getLogger(SftpUtil.class.getName());   

    /** SFTP */
    public static final String SFTP = "sftp";
    /** 通道 */
    private ChannelSftp channel;
    /** session */
    private Session session;

    /** 规避多线程并发 */
    private static ThreadLocal<SftpUtil> sftpLocal = new ThreadLocal<SftpUtil>();

    /**
     * 获取sftpchannel
     * 
     * @param connectConfig 连接配置
     * @return
     * @throws Exception 
     * @throws JSchException
     */
    private void init() throws Exception {
    	String ftpHost = Dictionary.SFTP_SERV_IP;
    	int ftpPort = Dictionary.SFTP_SERV_PORT;
    	String ftpUserName = Dictionary.SFTP_SERV_USER;
    	String ftpPassword = Dictionary.SFTP_SERV_PWD;
    	int timeout = Dictionary.SFTP_SERV_TIMEOUT;
       

        //创建JSch对象
        JSch jsch = new JSch();
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); 
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); 
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); 
        session.setTimeout(timeout); 
        session.connect(); 
        channel = (ChannelSftp)session.openChannel(SFTP);
        channel.connect(); 
    }

    /**
     * 是否已连接
     * 
     * @return
     */
    private boolean isConnected() {
        return null != channel && channel.isConnected();
    }


    /**
     * 构造函数
     * <p>
     * 非线程安全，故权限为私有
     * </p>
     * 
     * @throws Exception 
     */
    private SftpUtil() throws Exception {
        super();
        init();
    }

    /**
     * 关闭通道
     * 
     * @throws Exception
     */
    public void closeChannel() {
        if (null != channel) {
            try {
                channel.disconnect();
            } catch (Exception e) {
                log.error("关闭SFTP通道发生异常:", e);
            }
        }
        if (null != session) {
            try {
                session.disconnect();
            } catch (Exception e) {
                log.error("SFTP关闭 session异常:", e);
            }
        }
    }
    
    /**
     * 获取本地线程存储的sftp客户端
     * 
     * @return
     * @throws Exception 
     */
    public static SftpUtil getSftpUtil() throws Exception {
        SftpUtil sftpUtil = sftpLocal.get();
        if (null == sftpUtil || !sftpUtil.isConnected()) {
            sftpLocal.set(new SftpUtil());
        }
        return sftpLocal.get();
    }

    /**
     * 释放本地线程存储的sftp客户端
     */
    public static void release() {
        if (null != sftpLocal.get()) {
            sftpLocal.get().closeChannel();
            sftpLocal.set(null);
        }
    }
    

    /**
     * 删除文件
     * 
     * @param filePath 文件全路径
     * @throws SftpException
     */
    public void deleteFile(String filePath)throws SftpException  {
        channel.rm(filePath);
    }
    
//列出文件清单
    public Vector<String> listFileDes(String remoteFilePath) throws SftpException{
    	return	channel.ls(remoteFilePath);
    }
    
  //获取远程文件流
    public InputStream getFileStream(String remoteFilePath) throws SftpException{
    	return	channel.get(remoteFilePath);
    }
    
    //上传文件
    public void putFile(String local, String remote) throws SftpException{
    		channel.put(local, remote, ChannelSftp.OVERWRITE);
    }
    
    //备用
    @SuppressWarnings("unchecked")
    public List<String> listFiles(String dir) throws SftpException {
        Vector<LsEntry> files = channel.ls(dir);
        if (null != files) {
            List<String> fileNames = new ArrayList<String>();
            Iterator<LsEntry> iter = files.iterator();
            while (iter.hasNext()) {
                String fileName = iter.next().getFilename();
                if (StringUtils.equals(".", fileName) || StringUtils.equals("..", fileName)) {
                    continue;
                }
                fileNames.add(fileName);
            }
            return fileNames;
        }
        return null;
    }

}
