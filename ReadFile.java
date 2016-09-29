package com.jetmap.read;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 2016/9/13.
 */
public class ReadFile {

    public List<String> read(String fileName){
        File file =  new File(fileName);
        BufferedReader reader = null;
        List<String> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String str = null;
            while ((str = reader.readLine()) != null) {
                String[] temp = str.trim().split("\\,");
                for (String a : temp) {
                    list.add(a.trim());
//                    System.out.println(a);
                }
            }
//            System.out.println(list.size());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  list;

    }

    public String[][] reshape(String[] onearr, int m, int n) {
        String [][]arr = new String[m][n];
        int l = 0;
        for (int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                arr[i][j] = onearr[l];
                l++;
            }
        }
        return arr;
    }

    public static void main(String[] args) throws IOException {
        List<String> lngList = new ReadFile().read("files/lng.txt");
        List<String> latList = new ReadFile().read("files/lat.txt");

        String[] onearr = new String[4402];
        for (int i = 0; i < lngList.size(); i++) {
//            fw.write(lngList.get(i));
//            fw.write(",");
//            fw.write(latList.get(i));
//            fw.write("\r");
            onearr[i] = lngList.get(i) + "," + latList.get(i);
        }
        System.out.println(onearr.length);
        String[][] reshape = new ReadFile().reshape(onearr, 71, 62);
//        for (int k=0;k<71;k++) {
//            for (int q=0;q<62;q++) {
//                System.out.print(reshape[k][q]+"   ");
//            }
//            System.out.println("\r");
//        }
//        FileWriter fw = new FileWriter("E:\\Lnglat\\v.txt");
//        fw.write("NO"+" ");
//        fw.write("qur");
//        fw.write("\r");
        int mark = 1;
        List<LngLat[]> qurList = new ArrayList<>();
        for (int x = 0, y = x + 1; x < 70; x++, y++) {
            for (int z = 0, w = z + 1; z < 61; z++, w++) {
                String qur = reshape[x][z] + " " + reshape[x][w] + " " + reshape[y][z] + " " + reshape[y][w];
                LngLat[] arr = new LngLat[4];
                LngLat l1 = new LngLat(Double.valueOf(reshape[x][z].split(",")[0]), Double.valueOf(reshape[x][z].split(",")[1]));
                LngLat l2 = new LngLat(Double.valueOf(reshape[x][w].split(",")[0]), Double.valueOf(reshape[x][w].split(",")[1]));
                LngLat l3 = new LngLat(Double.valueOf(reshape[y][z].split(",")[0]), Double.valueOf(reshape[y][z].split(",")[1]));
                LngLat l4 = new LngLat(Double.valueOf(reshape[y][w].split(",")[0]), Double.valueOf(reshape[y][w].split(",")[1]));
                arr[0] = l1;
                arr[1] = l2;
                arr[2] = l3;
                arr[3] = l4;


                qurList.add(arr);
//                fw.write(mark+" "+qur);
//                fw.write("\r");
                mark++;
            }
        }

        new ReadFile().jud(qurList);
    }

//        fw.flush();
//        fw.close();


    public void jud(List<LngLat[]> qurList) {

//        String[] names = {"shijiazhuang", "baoding", "lf1", "lf2", "tangshan", "handan", "qhd", "zjk", "chengde", "cangzhou", "hengshui", "xingtai"};
//
//        for (int i = 0; i < names.length; i++) {
//            String path = "E:\\HEBEI\\" + names[i] + ".MIF";
//            List<LngLat> lngLats = ReadMIF.readFileByLine(path);
//
//            Polygon polygon = new Polygon(lngLats);
        for (int j = 0; j < qurList.size(); j++) {
            String[] names = {"shijiazhuang", "baoding", "lf1", "lf2", "tangshan", "handan", "qhd", "zjk", "chengde", "cangzhou", "hengshui", "xingtai"};

            for (int i = 0; i < names.length; i++) {
                String path = "E:\\HEBEI\\" + names[i] + ".MIF";
                List<LngLat> lngLats = ReadMIF.readFileByLine(path);

                Polygon polygon = new Polygon(lngLats);
                LngLat[] ls = qurList.get(j);
                //原来的4个坐标
                int m1 = polygon.contains(ls[0]);
                int m2 = polygon.contains(ls[1]);
                int m3 = polygon.contains(ls[2]);
                int m4 = polygon.contains(ls[3]);

                if (m1 == 2 & m2 == 2 & m3 == 2 & m4 == 2) {

                    continue;
                }

                double[] lats = {ls[0].getLat(), ls[1].getLat(), ls[2].getLat(), ls[3].getLat()};
                double[] lngs = {ls[0].getLng(), ls[1].getLng(), ls[2].getLng(), ls[3].getLng()};
                Arrays.sort(lats);
                Arrays.sort(lngs);

                double minlat = lats[0];
                double maxlat = lats[3];
                double minlng = lngs[0];
                double maxlng = lngs[3];
                LngLat leftDown = new LngLat(minlng, minlat);
                LngLat rightDown = new LngLat(maxlng, minlat);
                LngLat rightUp = new LngLat(maxlng, maxlat);
                LngLat leftUp = new LngLat(minlng, maxlat);

                double avgLat = Arith.div(Arith.sub(maxlat, minlat), 10, 4);
                double avgLng = Arith.div(Arith.sub(maxlng, minlng), 10, 4);

                double clng = Arith.div(avgLng, 2, 4);
                double clat = Arith.div(avgLat, 2, 4);
                List<LngLat> llList = new ArrayList<>();
                for (int qq = 0; qq < 10; qq++) {
                    for (int ww = 0; ww < 10; ww++) {
                        LngLat ll = new LngLat(minlng + Arith.mul(qq, clng), minlat + Arith.mul(ww, clat));
                        llList.add(ll);

                    }
                }
                List<LngLat> nonqurList = new ArrayList<>();
                nonqurList.add(ls[0]);
                nonqurList.add(ls[1]);
                nonqurList.add(ls[2]);
                nonqurList.add(ls[3]);
                Polygon p = new Polygon(nonqurList);
                List<LngLat> inqurList = new ArrayList<>();
                for (int qq = 0; qq < llList.size(); qq++) {
                    int e = p.contains(llList.get(qq));
                    if (e == 0 || e == 1)
                        inqurList.add(llList.get(qq));

                }

                int yy = 0;
                for (int r = 0; r < inqurList.size(); r++) {
                    LngLat lll = inqurList.get(r);
                    int a = polygon.contains(lll);
                    if (a == 0 || a == 1) {
                        yy++;
                    }
                }

                System.out.print((j + 1) + " " + names[i] + "," + (yy/inqurList.size()) + " ");
                if (m1 == 2) {
                    for (int k = 0; k < names.length; k++) {
                        String ppa1 = "E:\\HEBEI\\" + names[k] + ".MIF";
                        List<LngLat> lngLats1 = ReadMIF.readFileByLine(ppa1);
                        Polygon polygon1 = new Polygon(lngLats1);
                        int q = polygon1.contains(ls[0]);
                        if (q == 0 || q == 1) {
                            int oo = 0;
                            for (int qqqq = 0; qqqq < inqurList.size(); qqqq++) {
                                LngLat aa = (LngLat) inqurList.get(qqqq);
                                int mmm = polygon1.contains(aa);
                                if (mmm == 0 || mmm == 1) {
                                    oo++;
                                }
                            }
                            System.out.print("m1=2 "+names[k] + "," + oo/inqurList.size() + " ");

                        }
                    }
                }

                if (m2 == 2) {
                    for (int k = 0; k < names.length; k++) {
                        String ppa1 = "E:\\HEBEI\\" + names[k] + ".MIF";
                        List<LngLat> lngLats1 = ReadMIF.readFileByLine(ppa1);
                        Polygon polygon1 = new Polygon(lngLats1);
                        int q = polygon1.contains(ls[1]);
                        if (q == 0 || q == 1) {
                            int oo = 0;
                            for (int qqqq = 0; qqqq < inqurList.size(); qqqq++) {
                                LngLat aa = (LngLat) inqurList.get(qqqq);
                                int mmm = polygon1.contains(aa);
                                if (mmm == 0 || mmm == 1) {
                                    oo++;
                                }
                            }
                            System.out.print("m2=2 "+names[k] + "," + oo/inqurList.size() + " ");

                        }
                    }
                }

                if (m3 == 2) {
                    for (int k = 0; k < names.length; k++) {
                        String ppa1 = "E:\\HEBEI\\" + names[k] + ".MIF";
                        List<LngLat> lngLats1 = ReadMIF.readFileByLine(ppa1);
                        Polygon polygon1 = new Polygon(lngLats1);
                        int q = polygon1.contains(ls[2]);
                        if (q == 0 || q == 1) {
                            int oo = 0;
                            for (int qqqq = 0; qqqq < inqurList.size(); qqqq++) {
                                LngLat aa = (LngLat) inqurList.get(qqqq);
                                int mmm = polygon1.contains(aa);
                                if (mmm == 0 || mmm == 1) {
                                    oo++;
                                }
                            }
                            System.out.print("m3=2 "+names[k] + "," + oo/inqurList.size() + " ");

                        }
                    }
                }

                if (m4 == 2) {
                    for (int k = 0; k < names.length; k++) {
                        String ppa1 = "E:\\HEBEI\\" + names[k] + ".MIF";
                        List<LngLat> lngLats1 = ReadMIF.readFileByLine(ppa1);
                        Polygon polygon1 = new Polygon(lngLats1);
                        int q = polygon1.contains(ls[3]);
                        if (q == 0 || q == 1) {
                            int oo = 0;
                            for (int qqqq = 0; qqqq < inqurList.size(); qqqq++) {
                                LngLat aa = (LngLat) inqurList.get(qqqq);
                                int mmm = polygon1.contains(aa);
                                if (mmm == 0 || mmm == 1) {
                                    oo++;
                                }
                            }
                            System.out.print("m4=2 "+names[k] + "," + oo/inqurList.size() + " ");

                        }
                    }
                }

            }
            System.out.println((j+1)+" "+"0");
        }
    }


    public void paixu(double []nums){

        double m=nums[0]; //最大值
        double n=0; //最小值
        for (int i=0;i<nums.length;i++) {
            if (nums[i] > m) {
                m=nums[i];
            }
        }
    }

}
