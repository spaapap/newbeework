package com.jetmap.read;

import java.util.List;

/**
 * Created by Admin on 2016/8/30.
 */
public class Polygon {
    private List<LngLat> Path;

    private double INFINITY = 1e10;
    private double ESP = 1e-5; //控制误差

    public Polygon(List<LngLat> path) {
        this.Path = path;
    }

    public List<LngLat> getPath() {
        return Path;
    }

    public void setPath(List<LngLat> path) {
        Path = path;
    }

    /**
     * 判断多边形是否包含这个点
     *
     * @param point 坐标点
     * @return 返回值 0：在内部，1：在边上，2：在外部
     */
    public int contains(LngLat point) {

        int n = Path.size();
        int count = 0;
        LineSegment line = new LineSegment(point, new LngLat(INFINITY, point.lat));
        for (int i = 0; i < n; i++) {
            //得到多边形的一条边
            LngLat pt1 = Path.get(i);
            LngLat pt2 = Path.get((i + 1) % n);
            LineSegment side = new LineSegment(pt1, pt2);
            if (IsOnline(point, side)) {
                return 1;
            }
            //side平行X轴不考虑
            if (Math.abs(side.pt1.lat - side.pt2.lat) < ESP) {
                continue;
            }

            if (IsOnline(side.pt1, line)) {
                if (side.pt1.lat > side.pt2.lat) {
                    count++;
                }
            } else if (IsOnline(side.pt2, line)) {
                if (side.pt2.lat > side.pt1.lat) {
                    count++;
                }
            } else if (Intersect(line, side)) {
                count++;
            }
        }
        if (count % 2 == 1) {
            return 0;
        } else {
            return 2;
        }
    }

    // 计算叉乘 |P0P1| × |P0P2|
    private double Multiply(LngLat p1, LngLat p2, LngLat p0) {
        return ((p1.lng - p0.lng) * (p2.lat - p0.lat) - (p2.lng - p0.lng) * (p1.lat - p0.lat));
    }

    // 判断线段是否包含点point
    private boolean IsOnline(LngLat point, LineSegment line) {

        return ((Math.abs(Multiply(line.pt1, line.pt2, point)) < ESP) &&
                ((point.lng - line.pt1.lng) * (point.lng - line.pt2.lng) <= 0) &&
                ((point.lat - line.pt1.lat) * (point.lat - line.pt2.lat) <= 0));
    }

    private boolean Intersect(LineSegment L1, LineSegment L2) {

        return ((Math.max(L1.pt1.lng, L1.pt2.lng) >= Math.min(L2.pt1.lng, L2.pt2.lng)) &&
                (Math.max(L2.pt1.lng, L2.pt2.lng) >= Math.min(L1.pt1.lng, L1.pt2.lng)) &&
                (Math.max(L1.pt1.lat, L1.pt2.lat) >= Math.min(L2.pt1.lat, L2.pt2.lat)) &&
                (Math.max(L2.pt1.lat, L2.pt2.lat) >= Math.min(L1.pt1.lat, L1.pt2.lat)) &&
                (Multiply(L2.pt1, L1.pt2, L1.pt1) * Multiply(L1.pt2, L2.pt2, L1.pt1) >= 0) &&
                (Multiply(L1.pt1, L2.pt2, L2.pt1) * Multiply(L2.pt2, L1.pt2, L2.pt1) >= 0)
        );
    }
}
