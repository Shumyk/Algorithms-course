package collinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        checkCornerCases(points);
        List<LineSegment> foundSegments = new ArrayList<>();

        Point[] pointCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointCopy);

        for (int i = 0; i < pointCopy.length - 3; i++) {
            for (int j = i + 1; j < pointCopy.length - 2; j++) {
                for (int k = j + 1; k < pointCopy.length - 1; k++) {
                    for (int q = k + 1; q < pointCopy.length; q++) {
                        if (pointCopy[i].slopeTo(pointCopy[j]) == pointCopy[i].slopeTo(pointCopy[k]) &&
                            pointCopy[i].slopeTo(pointCopy[j]) == pointCopy[i].slopeTo(pointCopy[q])) {
                            foundSegments.add(new LineSegment(pointCopy[i], pointCopy[q]));
                        }
                    }
                }
            }
        }
        segments = foundSegments.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void checkCornerCases(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Argument is null in BruteCollinearPoints");
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null || points[j] == null || points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Duplicated points in given array.");
            }
        }
    }
}
