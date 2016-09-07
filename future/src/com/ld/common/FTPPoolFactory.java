//package com.ld.common;
//
//import java.io.IOException;
//import java.net.SocketException;
//
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
//import org.apache.commons.pool2.BasePooledObjectFactory;
//import org.apache.commons.pool2.PooledObject;
//import org.apache.commons.pool2.impl.DefaultPooledObject;
//import org.apache.log4j.Logger;
//
//
//public class FTPPoolFactory extends BasePooledObjectFactory<FTPClient> {
//
//    protected Logger log = Logger.getLogger(FTPTools.class);
//    
//    public FTPClient connect(FTPClient ftpClient) throws SocketException, IOException {
//        if (ftpClient != null && ftpClient.isConnected()) {
//            try {
//                ftpClient.disconnect();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        ftpClient.connect(Dictionary.FTP_SERV_IP, Dictionary.FTP_SERV_PORT);
//        ftpClient.setControlEncoding("GBK");
//        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
//            if (ftpClient.login(Dictionary.FTP_SERV_USER, Dictionary.FTP_SERV_PWD)) {
//                log.debug("::::::::::FTP Login::::::::::");
//                ftpClient.setBufferSize(10000);
//            }
//        }
//        ftpClient.enterLocalPassiveMode();
//        ftpClient.setKeepAlive(true);
//        ftpClient.setConnectTimeout(24 * 60 * 60);
//        ftpClient.setControlKeepAliveReplyTimeout(10 * 60);
//        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//        return ftpClient;
//    }
//
//    @Override
//    public FTPClient create() throws Exception {
//        FTPClient ftpClient = new FTPClient();
//        log.debug("::::::::::Create FTPClient::::::::::");
//        this.connect(ftpClient);
//        return ftpClient;
//    }
//
//    @Override
//    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
//        log.debug("::::::::::Wrap FTPClient::::::::::");
//        return new DefaultPooledObject<FTPClient>(ftpClient);
//    }
//
//    @Override
//    public void activateObject(PooledObject<FTPClient> p) throws Exception {
//        FTPClient ftpClient = p.getObject();
//        log.debug("::::::::::Activate FTPClient, "
//                + ", isConnected = " + ftpClient.isConnected()
//                + ", isAvailable = " + ftpClient.isAvailable()
//                + ":::::::::: Reply = " + ftpClient.getReplyString());
//        if (ftpClient.isConnected() && FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
//            ftpClient.sendNoOp();
//            return;
//        }
//        log.info("::::::::::Connection Close, Create FTPClient::::::::::");
//        this.destroyObject(p);
//        this.connect(ftpClient);
//    }
//
//    @Override
//    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
//        log.info("::::::::::Connection Disconnect::::::::::");
//        p.getObject().disconnect();
//    }
//
//    @Override
//    public void passivateObject(PooledObject<FTPClient> p) throws Exception {
//        FTPClient ftpClient = p.getObject();
//        log.debug("::::::::::Passivate FTPClient::::::::::, Reply = " + ftpClient.getReplyString());
//    }
//
//
//}
