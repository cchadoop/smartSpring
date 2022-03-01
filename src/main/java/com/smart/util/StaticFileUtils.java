package com.smart.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.Objects;

public class StaticFileUtils {
    public static boolean loadFromFile(String filename, HttpServletRequest reqeust, HttpServletResponse response) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }


        long start = 0L;
        long length = 0L;
        long end = 0L;
        try (final RandomAccessFile fileIn = new RandomAccessFile(file, "r")) {
            length = file.length();
            end = length - 1L;
            // bytes 0-11006012/11006013
            String range = reqeust.getHeader("Range");
            if (range != null) {
                range = range.substring("bytes=".length());
                int index = range.indexOf('-');
                if (index >= 0) {
                    String value = range.substring(0, index);
                    if (!"".equals(value)) {
                        start = Long.parseLong(value);
                        int skipped = fileIn.skipBytes((int) start);
                        if (skipped > start) { // 不可能跳过比实际还多
                            return false;
                        }
                    }
                    value = range.substring(index + 1);
                    if (!"".equals(value)) {
                        end = Long.parseLong(value);
                    }
                }
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
            }

            // response.setContentLength只有使用int,超过2G不能处理,所以直接设置头
            long total = end - start + 1L;
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", total + "");
            String contentRange = new StringBuilder("bytes ").append(start).append("-").append(end).append("/").append(length).toString();
            response.setHeader("Content-Range", contentRange);
            long left = total;
            try (ServletOutputStream sos = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                while (left > 0L) {
                    int maxLen = buffer.length;
                    if (left < maxLen) {
                        maxLen = (int) left;
                    }
                    int len = fileIn.read(buffer, 0, maxLen);
                    if (len < 0) {
                        break;
                    }
                    sos.write(buffer, 0, len);
                    left -= len;
                }
            }
        }
        return end >= length - 1;
    }

    public static boolean loadToStream(InputStream inputStream, HttpServletRequest reqeust, HttpServletResponse response, String loadName) throws IOException {
        if (!Objects.isNull(inputStream) && inputStream.available() <= 0) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            inputStream.close();
            return false;
        }

        long start = 0L;
        long length = 0L;
        long end = 0L;
        try {
            length = inputStream.available();
            end = length - 1;
            // bytes 0-11006012/11006013
            String range = reqeust.getHeader("Range");
            if (range != null) {
                range = range.substring("bytes=".length());
                int index = range.indexOf('-');
                if (index >= 0) {
                    String value = range.substring(0, index);
                    if (!"".equals(value)) {
                        start = Long.parseLong(value);
                        int skipped = (int) inputStream.skip(start);
                        if (skipped > start) { // 不可能跳过比实际还多
                            return false;
                        }
                    }
                    value = range.substring(index + 1);
                    if (!"".equals(value)) {
                        end = Long.parseLong(value);
                    }
                }
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
            }

            // response.setContentLength只有使用int,超过2G不能处理,所以直接设置头
            long total = end - start + 1L;
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", total + "");
            String contentRange = new StringBuilder("bytes ").append(start).append("-").append(end).append("/").append(length).toString();
            response.setHeader("Content-Range", contentRange);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(loadName, "UTF-8"));
            long left = total;
            try (ServletOutputStream sos = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                while (left > 0L) {
                    int maxLen = buffer.length;
                    if (left < maxLen) {
                        maxLen = (int) left;
                    }
                    int len = inputStream.read(buffer, 0, maxLen);
                    if (len < 0) {
                        break;
                    }
                    sos.write(buffer, 0, len);
                    left -= len;
                }
            }
        } finally {
            inputStream.close();
        }
        return end >= length - 1;
    }
}
