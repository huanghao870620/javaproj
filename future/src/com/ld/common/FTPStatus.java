package com.ld.common;

public enum FTPStatus {

    Connectin_Fail, 
    Change_Directory_Fail, 
    Change_Directory_Success, 
    Remote_File_Noexist(), // 远程文件不存?
    Local_Bigger_Remote, // 本地文件大于远程文件
    Download_From_Break_Success, // 断点下载文件成功
    Download_From_Break_Failed, // 断点下载文件失败
    Download_New_Success, // 全新下载文件成功
    Download_New_Failed; // 全新下载文件失败

}
