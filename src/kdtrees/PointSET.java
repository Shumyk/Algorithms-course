package kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> points;

    public         PointSET() {
        this.points = new TreeSet<>();
    }

    public           boolean isEmpty() {
        return this.points.isEmpty();
    }

    public               Integer size() {
      return this.points.size();
    }

    public              void insert(Point2D p) {
        checkNull(p);
        if (!points.contains(p))
            this.points.add(p);
    }
    public           boolean contains(Point2D p) {
        checkNull(p);
        return this.points.contains(p);
    }
    public              void draw() {                        // draw all points to standard draw
        for (Point2D point2D : this.points) {
            point2D.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary)
        checkNull(rect);
        Point2D minPoint = new Point2D(rect.xmin(), rect.ymin());
        Point2D maxPoint = new Point2D(rect.xmax(), rect.ymax());
        List<Point2D> pointsInRect = new LinkedList<>();

        for (Point2D p : points.subSet(minPoint, true, maxPoint, true)) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax()) {
                pointsInRect.add(p);
            }
        }
        return pointsInRect;
    }
    public           Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        checkNull(p);
        if (isEmpty()) return null;

        Point2D next = points.ceiling(p);
        Point2D prev = points.floor(p);
        if (next == null && prev == null)
            return null;

        double distNext = next == null ? Double.MAX_VALUE : p.distanceTo(next);
        double distPrev = prev == null ? Double.MAX_VALUE : p.distanceTo(prev);
        double d = Math.min(distNext, distPrev);

        Point2D minPoint = new Point2D(p.x(), p.y() - d);
        Point2D maxPoint = new Point2D(p.x(), p.y() + d);
        Point2D nearest = next == null ? prev : next;

        for (Point2D point : points.subSet(minPoint, true, maxPoint, true)) {
            if (p.distanceTo(point) < p.distanceTo(nearest))
                nearest = point;
        }

        return nearest;
    }

    public static void main(String[] args) {}                 // unit testing of the methods (optional)

    private static void checkNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }
}