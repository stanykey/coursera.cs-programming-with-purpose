import java.util.Arrays;
import java.util.Comparator;

public class BarChartRacer {
    public static void main(String[] args) {
        final String dataFile = args[0];
        final int visibleBarsCount = Integer.parseInt(args[1]);

        final In in = new In(dataFile);

        final String title = in.readLine();
        final String xAxisLabel = in.readLine();
        final String dataSource = in.readLine();
        BarChart barsChart = new BarChart(title, xAxisLabel, dataSource);

        StdDraw.setCanvasSize(1280, 1024);
        StdDraw.enableDoubleBuffering();

        in.readLine(); // need to empty line
        while (in.hasNextLine()) {
            final String countLine = in.readLine();
            final int recordsCount = Integer.parseInt(countLine);

            Bar[] bars = new Bar[recordsCount];
            for (int i = 0; i < recordsCount; i++) {
                final String record = in.readLine();

                final int captionEnd = record.indexOf(',');
                final String caption = record.substring(0, captionEnd);
                barsChart.setCaption(caption);

                final int nameStart = captionEnd + 1;
                final int nameEnd = record.indexOf(',', nameStart);
                final String name = record.substring(nameStart, nameEnd);

                final int ignoredStart = nameEnd + 1;
                final int ignoredEnd = record.indexOf(',', ignoredStart);
                final String ignored  = record.substring(ignoredStart, ignoredEnd);

                final int valueStart = ignoredEnd + 1;
                final int valueEnd = record.indexOf(',', valueStart);
                final int value = Integer.parseInt(record.substring(valueStart, valueEnd));

                final String category  = record.substring(valueEnd + 1);

                bars[i] = new Bar(name, value, category);
            }
            Arrays.sort(bars, Comparator.reverseOrder());

            updateChart(barsChart, bars, visibleBarsCount);
            drawChart(barsChart);

            in.readLine(); // skip empty line
        }
    }

    private static void updateChart(BarChart chart, Bar[] bars, int limit) {
        chart.reset();
        for (int i = 0; i < bars.length && i < limit; i++) {
            final Bar bar = bars[i];
            chart.add(bar.getName(), bar.getValue(), bar.getCategory());
        }
    }

    private static void drawChart(BarChart chart) {
        StdDraw.clear();
        chart.draw();
        StdDraw.show();
        StdDraw.pause(50);
    }
}
