package com.ld.sftp;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class SftpServ {
	private static String REMOTEPATH = "./shared/upload/images/";
	private static Log log = LogFactory.getLog(SftpServ.class);

	public enum UploadStatus {
		Connectin_Fail, Change_Directory_Fail, Change_Directory_Success, Create_Directory_Fail, // 远程服务器相应目录创建失败
		Create_Directory_Success, // 远程服务器闯将目录成功
		Upload_New_File_Success, // 上传新文件成功
		Upload_New_File_Failed, // 上传新文件失败
		File_Exits, // 文件已经存在
		Remote_Bigger_Local, // 远程文件大于本地文件
		Upload_From_Break_Success, // 断点续传成功
		Upload_From_Break_Failed, // 断点续传失败
		Delete_Remote_Faild; // 删除远程文件失败
	}

	public enum DownloadStatus {
		Connectin_Fail, Change_Directory_Fail, Change_Directory_Success, Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed, // 断点下载文件失败
		Download_New_Success, // 全新下载文件成功
		Download_New_Failed; // 全新下载文件失败
	}


	public SFTPStatus download(String remote, HttpServletResponse response) {
		InputStream in = null;
		OutputStream os = null;
		try {
			Vector<String> files = SftpUtil.getSftpUtil()
					.listFileDes(REMOTEPATH + new String(remote.getBytes("UTF-8"), "iso-8859-1"));
			if (files.isEmpty()) {
				log.error("远程文件不存在");
				return SFTPStatus.Remote_File_Noexist;
			}
			long lRemoteSize = Long.valueOf(
					SftpUtil.getSftpUtil().listFileDes(REMOTEPATH + new String(remote.getBytes("UTF-8"), "iso-8859-1"))
							.toString().split("\\s+")[4].trim());
			response.setContentLength((int) lRemoteSize);
			log.debug("file size:" + lRemoteSize);
			in = SftpUtil.getSftpUtil().getFileStream(REMOTEPATH + new String(remote.getBytes("UTF-8"), "iso-8859-1"));
			os = response.getOutputStream();
			IOUtils.copy(in, os);

			return SFTPStatus.Download_New_Success;

		} catch (Exception e) {
			e.printStackTrace();
			return SFTPStatus.Connectin_Fail;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);
			closeSfrpChannel();
		}
	}

	public UploadStatus upload(String local, String remote) {
		try {
			SftpUtil.getSftpUtil().putFile(local, REMOTEPATH + remote);
		} catch (Exception e) {

		} finally {
			closeSfrpChannel();
		}
		return UploadStatus.Upload_New_File_Success;
	}

	public boolean delete(String remoteFile) {
		try {
			SftpUtil.getSftpUtil().deleteFile(REMOTEPATH + remoteFile);
		} catch (Exception e) {
			return false;
		} finally {
			closeSfrpChannel();
		}
		return true;
	}

	public boolean batchDelete(List<String> remoteFileNameList) {
		try {
			for (String remoteFileName : remoteFileNameList) {
				SftpUtil.getSftpUtil().deleteFile(REMOTEPATH + remoteFileName);
			}
		} catch (Exception e) {
			return false;
		} finally {
			closeSfrpChannel();
		}
		return true;
	}
	
	public void closeSfrpChannel(){
//		try {
//			SftpUtil.getSftpUtil().closeChannel();
//		} catch (Exception e) {
//			log.error("关闭sftp连接失败");
//			e.printStackTrace();
//		}
	}
}
