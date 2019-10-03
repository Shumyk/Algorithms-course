import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> jSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // check corner cases
        if (points == null)
            throw new NullPointerException();

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        if (hasDuplicate(pointsCopy)) {
            throw new IllegalArgumentException("U have duplicate points");
        }

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < pointsCopy.length; last++) {
                while (last < pointsCopy.length && Double.compare(pointsCopy[p].slopeTo(pointsCopy[first]), pointsCopy[p].slopeTo(pointsCopy[last])) == 0) {
                    last++;
                }
                if (last - first >= 3 && pointsCopy[p].compareTo(pointsCopy[first]) < 0) {
                    jSegments.add(new LineSegment(pointsCopy[p], pointsCopy[last - 1]));
                }
                first = last;
            }
        }
    }

    public int numberOfSegments() {
        return jSegments.size();
    }

    public LineSegment[] segments() {
        return jSegments.toArray(new LineSegment[jSegments.size()]);
    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

}