package com.ltnet.hadoopdemo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HdfsUtil {

    private Logger logger = Logger.getLogger(HdfsUtil.class);
    private Configuration conf = null;

    private String defaultHdfsUrl = null;
    private String username = null;

    /**
     * Constructor
     * @param conf
     * @param username
     * @param hdfsUrl
     */
    public HdfsUtil(Configuration conf, String username, String hdfsUrl) {
        this.conf = conf;
        this.username = username;
        this.defaultHdfsUrl = hdfsUrl;
    }

    /**
     * Get HDFS file system
     * @return
     * @throws IOException
     */
    private FileSystem getFileSystem() throws IOException, URISyntaxException, InterruptedException {
//        return FileSystem.get(conf);
        return FileSystem.get(new URI(defaultHdfsUrl), conf, username);
    }

    /**
     * Check the path or file if exist
     * @param path  path or file name
     * @return      boolean
     */
    public boolean checkExists(String path) {
        FileSystem fs = null;
        try {
            fs = getFileSystem();
            String hdfsPath = generateHdfsPath(path);

            return fs.exists(new Path(hdfsPath));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            close(fs);
        }
    }

    /**
     * Transfer the relative path to absolute path
     * @param dstPath   relative path
     * @return          absolute path
     */
    private String generateHdfsPath(String dstPath) {
        String hdfsPath = defaultHdfsUrl;
        if (dstPath.startsWith("/")) {
            hdfsPath += dstPath;
        } else {
            hdfsPath = hdfsPath + "/" + dstPath;
        }

        return hdfsPath;
    }

    /**
     * Close the file system
     * @param fs    file system
     */
    private void close(FileSystem fs) {
        if (fs != null) {
            try {
                fs.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * create directory
     * @param path  directory path
     * @return      if create success
     */
    public boolean mkdir(String path) {
        if (checkExists(path)) {
            return true;
        } else {
            FileSystem fs = null;

            try {
                fs = getFileSystem();
                String hdfsPath = generateHdfsPath(path);
                return fs.mkdirs(new Path(hdfsPath));
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            } finally {
                close(fs);
            }
        }
    }

    public List<Map<String, Object>> listFiles(String path, PathFilter pathFilter) {
        List<Map<String, Object>> retList = new ArrayList<>();

        if (checkExists(path)) {
            FileSystem fs = null;

            try {
                fs = getFileSystem();
                String hdfsPath = generateHdfsPath(path);

                FileStatus[] arrStatus;

                if (pathFilter != null) {
                    arrStatus = fs.listStatus(new Path(hdfsPath), pathFilter);
                } else {
                    arrStatus = fs.listStatus(new Path(hdfsPath));
                }

                if (arrStatus != null) {
                    for (FileStatus status : arrStatus) {
                        Map<String, Object> fileMap = new HashMap<>(2);

                        fileMap.put("path", status.getPath().toString());
                        fileMap.put("isdir", status.isDirectory());
                        fileMap.put("fileStatus", status.toString());
                        retList.add(fileMap);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                close(fs);
            }
        }

        return retList;
    }

    /**
     * Read content of hdfs file
     * @param path
     * @return
     */
    public String readFile(String path) throws Exception {
        if (StringUtils.isEmpty(path) || !checkExists(path)) {
            return null;
        }

        FileSystem fs = getFileSystem();
        // 目标路径
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = null;
        try {
            inputStream = fs.open(srcPath);
            // 防止中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            StringBuilder sb = new StringBuilder();
            while ((lineTxt = reader.readLine()) != null) {
                sb.append(lineTxt).append("\n");
            }
            return sb.toString();
        } finally {
            assert inputStream != null;
            inputStream.close();
            fs.close();
        }
    }

    /**
     * upload file to hdfs
     * @param delSrc
     * @param overwrite
     * @param file
     */
    public boolean put(boolean delSrc, boolean overwrite, String file, String dstPath) {
        boolean ret = false;

        // 源文件路径
        Path localSrcPath = new Path(file);
        // 目标文件路径
        Path hdfsDstPath = new Path(generateHdfsPath(dstPath));

        FileSystem fs = null;

        try {
            fs = getFileSystem();

            fs.copyFromLocalFile(delSrc, overwrite, localSrcPath, hdfsDstPath);
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(fs);
        }

        return ret;
    }

    /**
     * Read file
     * @param path
     * @return
     * @throws Exception
     */
    public String read(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return "Path is empty";
        }

        if (!checkExists(path)) {
            return "Path is not exists";
        }

        FileSystem fs = getFileSystem();
        // 源文件路径
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = null;

        try {
            inputStream = fs.open(srcPath);
            // 防止中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            close(fs);
        }

        return "Error";
    }

    /**
     * download file
     * @param src
     * @param dst
     * @return
     */
    public boolean get(String src, String dst) {
        boolean ret = false;

        // HDFS文件路径
        Path hdfsSrcPath = new Path(src);
        // 本地文件路径
        Path localDstPath = new Path(dst);

        FileSystem fs = null;
        try {
            fs = getFileSystem();

            fs.copyToLocalFile(hdfsSrcPath, localDstPath);
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fs);
        }

        return ret;
    }
}
