package com.aring.utils;

/**
 * 应用属性配置
 * @author aring
 *
 */
public class AppConfig {
	
	/*文件服务器域名*/
	private String fileServerDomain;
	
	/*文件根目录*/
	private String fileRoot;
	
	/*文件临时路径*/
	private String fileTempPath;
	
	/*文件路径*/
	private String filePath;
	
	/*文件最大限制*/
	private int fileMaxSize;

	public String getFileServerDomain() {
		return fileServerDomain;
	}

	public void setFileServerDomain(String fileServerDomain) {
		this.fileServerDomain = fileServerDomain;
	}

	public String getFileRoot() {
		return fileRoot;
	}

	public void setFileRoot(String fileRoot) {
		this.fileRoot = fileRoot;
	}

	public String getFileTempPath() {
		return fileTempPath;
	}

	public void setFileTempPath(String fileTempPath) {
		this.fileTempPath = fileTempPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(int fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	@Override
	public String toString() {
		return "AppConfig [fileServerDomain=" + fileServerDomain + ", fileRoot=" + fileRoot + ", fileTempPath="
				+ fileTempPath + ", filePath=" + filePath + ", fileMaxSize=" + fileMaxSize + "]";
	}
}
