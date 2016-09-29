package com.jetmap.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/8/30.
 */
public class ReadMIF {
    /**
     * 读取坐标文件
     * 分行读取
     * @param srcPath
     * @return
     */
    public static List<LngLat> readFileByLine(String srcPath) {
        File file = new File(srcPath);
        List<LngLat> lngLatList = new ArrayList<LngLat>();
        LngLat lnglat = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
//            int lineNo = 1;
            while ((tempString = reader.readLine()) != null) {

                String temp[] = tempString.split(" ");
                lnglat = new LngLat(Double.valueOf(temp[0]), Double.valueOf(temp[1]));
                lngLatList.add(lnglat);
//                lineNo++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return lngLatList;
    }

}
