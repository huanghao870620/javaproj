//package com.ld.common;
//
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.pool2.ObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.apache.log4j.Logger;
//
//public class FTPTools {
//
//    private ObjectPool<FTPClient> pool;
//    private String rootDirPath;
//    protected Logger log = Logger.getLogger(FTPTools.class);
//
//    private FTPTools() {
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        int poolSize = 10;
//        poolConfig.setMaxTotal(poolSize);
//        poolConfig.setMaxIdle(poolSize);
//        poolConfig.setMinIdle(poolSize);
//        pool = new GenericObjectPool<FTPClient>(new FTPPoolFactory(), poolConfig);
//        try {
//            FTPClient ftp = pool.borrowObject();
//            String temp = new String(ftp.printWorkingDirectory().getBytes("UTF-8"), "iso-8859-1");
//            if(!temp.equals("/")){
//            	this.rootDirPath = temp.substring(1, temp.lastIndexOf("\""));
//            }else{
//            	this.rootDirPath = temp;
//            }
//            log.info("::::::::::FTP Root Path: " + this.rootDirPath + "::::::::::");
//            pool.returnObject(ftp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public ObjectPool<FTPClient> getPool() {
//        return pool;
//    }
//    
//    public String getRootDirPath() {
//        return rootDirPath;
//    }
//
//    static class SingletonHolder {
//        static FTPTools instance = new FTPTools();
//    }
//
//    public static FTPTools getInstance() {
//        return SingletonHolder.instance;
//    }
//
//}
