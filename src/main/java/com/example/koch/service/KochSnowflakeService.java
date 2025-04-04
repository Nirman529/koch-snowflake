package com.example.koch.service;

import com.example.koch.model.LineSegment;
import com.example.koch.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KochSnowflakeService {

    public List<LineSegment> generateSnowflake(int depth, int edges) {
        List<LineSegment> segments = new ArrayList<>();

        double centerX = 300;
        double centerY = 300;
        double radius = 200;

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < edges; i++) {
            double angle = 2 * Math.PI * i / edges;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            points.add(new Point(x, y));
        }

        for (int i = 0; i < edges; i++) {
            Point start = points.get(i);
            Point end = points.get((i + 1) % edges);
            segments.add(new LineSegment(start, end));
        }

        for (int i = 0; i < depth; i++) {
            segments = iterate(segments);
        }

        return segments;
    }


    private List<LineSegment> iterate(List<LineSegment> input) {
        List<LineSegment> newSegments = new ArrayList<>();

        for (LineSegment segment : input) {
            Point a = segment.getStart();
            Point b = segment.getEnd();

            Point oneThird = interpolate(a, b, 1.0 / 3.0);
            Point twoThird = interpolate(a, b, 2.0 / 3.0);

            double dx = twoThird.getX() - oneThird.getX();
            double dy = twoThird.getY() - oneThird.getY();

            Point peak = new Point(
                    (oneThird.getX() + twoThird.getX()) / 2 - Math.sqrt(3) * dy / 6,
                    (oneThird.getY() + twoThird.getY()) / 2 + Math.sqrt(3) * dx / 6
            );

            newSegments.add(new LineSegment(a, oneThird));
            newSegments.add(new LineSegment(oneThird, peak));
            newSegments.add(new LineSegment(peak, twoThird));
            newSegments.add(new LineSegment(twoThird, b));
        }

        return newSegments;
    }

    private Point interpolate(Point a, Point b, double t) {
        return new Point(
                a.getX() + (b.getX() - a.getX()) * t,
                a.getY() + (b.getY() - a.getY()) * t
        );
    }
}
