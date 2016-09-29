import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String[] names = {"shijiazhuang","baoding", "lf1", "lf2", "tangshan", "handan","qhd","zjk", "chengde", "cangzhou", "hengshui", "xingtai"};

//        new Main().JudgeCenterPoint(names);
        new Main().Get4P(names);
    }

    public void JudgeCenterPoint(String[] names) {
        for (int q = 0; q < names.length; q++) {


            String path = "E:\\HEBEI\\" + names[q] + ".MIF";
            List<LngLat> lngLats = ReadMIF.readFileByLine(path);
            Polygon polygon = new Polygon(lngLats);
            File file = new File("E:\\zuobiao.txt");
            List<LngLat> lngLatList = new ArrayList<LngLat>();
            LngLat lnglat = null;
            BufferedReader reader = null;
            List<Result> list = new ArrayList<Result>();
            Map<String, Object> map = new HashMap<>();
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;

                while ((tempString = reader.readLine()) != null) {
                    String temp[] = tempString.split(" ");
                    lnglat = new LngLat(Double.valueOf(temp[0]), Double.valueOf(temp[1]));
                    int m = polygon.contains(lnglat);
                    if (m == 0 || m == 1) {

//                    Result result = new Result();
//                    result.setLevel("");
//                    result.setLine(line+"");
//                    result.setZuobiao(lnglat.getLat()+" "+lnglat.getLng());
//                    list.add(result);
                        lnglat.setLineno(line);
                        lngLatList.add(lnglat);
                    }
                    line++;
                }
//            JSONArray jsArr = JSONArray.fromObject(list);

//            map.put("citeCode","1");
//            map.put("results",jsArr);
//            JSONObject jsonObj  = JSONObject.fromObject(map);
//            System.out.println(jsonObj);
                reader.close();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String outPath = "E:\\HEBEI\\center" + File.separator + names[q] + ".dat";
                FileWriter fw = new FileWriter(outPath);
                for (int i = 0; i < lngLatList.size(); i++) {
                    LngLat point = (LngLat) lngLatList.get(i);
//                    System.out.print(point.getLng());
//                    System.out.println(point.getLat());


                    fw.write(point.getLng() + " ");
                    fw.write(point.getLat() + " ");
                    fw.write(point.getLineno() + "");
                    fw.write("\r");
                }
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(lngLatList.size());
        }
    }

    public void Get4P(String[] names) {
        for (int i=0;i<names.length;i++) {
            String dataPath = "E:\\HEBEI\\center"+File.separator+names[i]+".dat";
            String path = "E:\\HEBEI\\" + names[i] + ".MIF";
            List<LngLat> lngLats = ReadMIF.readFileByLine(path);

            Polygon polygon = new Polygon(lngLats);
            File dataFile = new File(dataPath);
            BufferedReader reader = null;
            System.out.println("==========="+names[i]+"=============");
            try {
                reader = new BufferedReader(new FileReader(dataFile));
                String tempString = null;
                while ((tempString = reader.readLine()) != null) {
                    String temp[] = tempString.split(" ");
                    double lng = Double.valueOf(temp[0]);
                    double lat = Double.valueOf(temp[1]);
                    double lngLeft = Arith.sub(Double.valueOf(temp[0]),0.13);
                    double lngRight = Arith.add(Double.valueOf(temp[0]), 0.12);
                    double latDown = Arith.sub(Double.valueOf(temp[1]), 0.13);
                    double latUp = Arith.add(Double.valueOf(temp[1]), 0.12);
                    LngLat lngLat1 = new LngLat(lngLeft,latUp);
                    LngLat lngLat2 = new LngLat(lngLeft,latDown);
                    LngLat lngLat3 = new LngLat(lngRight,latUp);
                    LngLat lngLat4 = new LngLat(lngRight,latDown);

                    double avglng = Arith.div(Arith.sub(lngRight, lngLeft),10,2);
                    double avglat = Arith.div(Arith.sub(latUp, latDown),10,2);
                    double clng = Arith.div(avglng, 2, 2);
                    double clat = Arith.div(avglat, 2, 2);
                    List<LngLat> llList = new ArrayList<>();
                    for (int qq=0;qq<10;qq++) {
                        for (int ww=0;ww<10;ww++) {
                            LngLat ll = new LngLat(lngLeft + Arith.mul(qq, clng), latDown + Arith.mul(ww, clat));
                            llList.add(ll);

                        }
                    }

                    int m1 = polygon.contains(lngLat1);
                    int m2 = polygon.contains(lngLat2);
                    int m3 = polygon.contains(lngLat3);
                    int m4 = polygon.contains(lngLat4);

                    int yy =0;
                    for (int uuu=0;uuu<llList.size();uuu++) {
                        LngLat ttt = (LngLat) llList.get(uuu);
                        int rrr = polygon.contains(ttt);
                        if (rrr == 0 || rrr == 1) {
                            yy++;
                        }

                    }

                    System.out.print(Integer.valueOf(temp[2])+" "+lat+" "+lng+" "+names[i]+","+yy+" ");
                    if (m1 == 2) {
                        for (int k=0;k<names.length;k++) {
                            String ppa1 = "E:\\HEBEI\\" + names[k] + ".MIF";
                            List<LngLat> lngLats1 = ReadMIF.readFileByLine(ppa1);
                            Polygon polygon1 = new Polygon(lngLats1);
                            int q = polygon1.contains(lngLat1);
                            if (q == 0 || q == 1) {

//                                t1 = names[k];
                                int oo = 0;
                                for (int qqqq = 0;qqqq<llList.size();qqqq++) {
                                    LngLat aa = (LngLat) llList.get(qqqq);
                                    int mmm = polygon1.contains(aa);
                                    if (mmm == 0 || mmm == 1) {
                                        oo++;
                                    }
                                }
                                System.out.print(names[k]+","+oo+" ");
                            }
                        }
                    }
                    if (m2 == 2) {
                        for (int l=0;l<names.length;l++) {
                            String ppa2 = "E:\\HEBEI\\" + names[l] + ".MIF";
                            List<LngLat> lngLats2 = ReadMIF.readFileByLine(ppa2);
                            Polygon polygon2 = new Polygon(lngLats2);
                            int q = polygon2.contains(lngLat2);
                            if (q == 0 || q == 1) {

//                                t2 = names[l];
                                int oo = 0;
                                for (int qqqq = 0;qqqq<llList.size();qqqq++) {
                                    LngLat aa = (LngLat) llList.get(qqqq);
                                    int mmm = polygon2.contains(aa);
                                    if (mmm == 0 || mmm == 1) {
                                        oo++;
                                    }
                                }
                                System.out.print(names[l]+","+oo+" ");
                            }
                        }
                    }
                    if (m3 == 2) {
                        for (int n=0;n<names.length;n++) {
                            String ppa3 = "E:\\HEBEI\\" + names[n] + ".MIF";
                            List<LngLat> lngLats3 = ReadMIF.readFileByLine(ppa3);
                            Polygon polygon3 = new Polygon(lngLats3);
                            int q = polygon3.contains(lngLat3);
                            if (q == 0 || q == 1) {

//                                t3 = names[n];
                                int oo = 0;
                                for (int qqqq = 0;qqqq<llList.size();qqqq++) {
                                    LngLat aa = (LngLat) llList.get(qqqq);
                                    int mmm = polygon3.contains(aa);
                                    if (mmm == 0 || mmm == 1) {
                                        oo++;
                                    }
                                }
                                System.out.print(names[n]+","+oo+" ");
                            }
                        }
                    }
                    if (m4 == 2) {
                        for (int v=0;v<names.length;v++) {
                            String ppa4 = "E:\\HEBEI\\" + names[v] + ".MIF";
                            List<LngLat> lngLats4 = ReadMIF.readFileByLine(ppa4);
                            Polygon polygon4 = new Polygon(lngLats4);
                            int q = polygon4.contains(lngLat4);
                            if (q == 0 || q == 1) {

//                                t4 = names[v];
                                int oo = 0;
                                for (int qqqq = 0;qqqq<llList.size();qqqq++) {
                                    LngLat aa = (LngLat) llList.get(qqqq);
                                    int mmm = polygon4.contains(aa);
                                    if (mmm == 0 || mmm == 1) {
                                        oo++;
                                    }
                                }
                                System.out.print(names[v]+","+oo+" ");
                            }
                        }
                    }



                    System.out.println("");


                }
                System.out.println("===========================");
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}