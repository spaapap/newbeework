package com.jetmap.read;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 2016/9/29.
 */
public class checkCity {

    public static void main(String[] args) throws IOException {

        String[] names = {"shijiazhuang","baoding", "lf1", "lf2", "tangshan", "handan","qhd","zjk", "chengde", "cangzhou", "hengshui", "xingtai"};
//        new checkCity().check(names);

    }

    public void check(String[] names) throws IOException {

        for (int i=0;i<names.length;i++) {
            String path = "E:\\HEBEI\\" + names[i] + ".MIF";
            List<LngLat> lngLats = ReadMIF.readFileByLine(path);
            Polygon polygon = new Polygon(lngLats);
            File file = new File("E:\\Lnglat\\v.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            List<String> list = new ArrayList<>();
            while ((tempString = reader.readLine()) != null) {
                String []temp = tempString.split(" ");
                String lineno = temp[0];

                LngLat ll1 = new LngLat(Double.valueOf(temp[1].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));
                LngLat ll2 = new LngLat(Double.valueOf(temp[2].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));
                LngLat ll3 = new LngLat(Double.valueOf(temp[3].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));
                LngLat ll4 = new LngLat(Double.valueOf(temp[4].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));
                int m1 = polygon.contains(ll1);
                int m2 = polygon.contains(ll2);
                int m3 = polygon.contains(ll3);
                int m4 = polygon.contains(ll4);
                if (m1!=2||m2!=2||m3!=2||m4!=2){

                    list.add(tempString);
                }

            }
            reader.close();
            FileWriter fw = new FileWriter("E:\\Lnglat\\"+ names[i]+".dat");
            for (String tem : list) {
                fw.write(tem);
                fw.write("\r");
            }
            fw.flush();
            fw.close();
        }

    }



    public void getPercent(String[] names) throws IOException {

        // calculate percent of point on current city
        for (int i=0;i<names.length;i++) {
            String dataPath = "E:\\Lnglat\\"+names[i]+".dat";
            String path = "E:\\HEBEI\\" + names[i] + ".MIF";

            List<LngLat> lngLats = ReadMIF.readFileByLine(path);
            Polygon polygon = new Polygon(lngLats);
            File dataFile = new File(dataPath);
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String []temp = tempString.split(" ");
                //get 4 points
                LngLat ll1 = new LngLat(Double.valueOf(temp[1].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));
                LngLat ll2 = new LngLat(Double.valueOf(temp[2].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));
                LngLat ll3 = new LngLat(Double.valueOf(temp[3].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));
                LngLat ll4 = new LngLat(Double.valueOf(temp[4].split(",")[0]),Double.valueOf(temp[1].split(",")[1]));

                //request max and min about lng and lat.
                double[] lngs = {ll1.getLng(), ll2.getLng(), ll3.getLng(), ll4.getLng()};
                double[] lats = {ll1.getLat(), ll2.getLat(), ll3.getLat(), ll4.getLat()};
                Arrays.sort(lngs);
                Arrays.sort(lats);

                double minlat = lats[0];
                double maxlat = lats[3];
                double minlng = lngs[0];
                double maxlng = lngs[3];

                //average of lat and lng
                double avgLat = Arith.div(Arith.sub(maxlat, minlat), 10, 4);
                double avgLng = Arith.div(Arith.sub(maxlng, minlng), 10, 4);

                //center point of average lat and lng
                double centerLat = Arith.div(avgLat, 2, 4);
                double centerLng = Arith.div(avgLng, 2, 4);






            }





        }

    }

}
