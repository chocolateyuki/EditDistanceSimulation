import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<Integer> DValues = new ArrayList<>();
        DValues.add(10);
        DValues.add(50);
        DValues.add(100);
        DValues.add(200);
        DValues.add(400);
        DValues.add(600);
        DValues.add(800);
        DValues.add(1000);

        XYSeries myers = new XYSeries("Myers Algorithm");
        XYSeries wuManber = new XYSeries("Wu-Manber Algorithm");

        for(Integer i : DValues){
            GenerateArrays ga = new GenerateArrays();

            ArrayList<Integer> list1 = ga.getArrayA();
            ArrayList<Integer> list2 = ga.getArrayB(i);

            Myers myr = new Myers();
            myr.runMyersAlgorithm(list1, list2);

            WuManber mbr = new WuManber();
            mbr.runWuManberAlgorithm(list1, list2);

            System.out.println("Myers Algorithm: D = " + i + " Comparisons = " + myr.comparisonCount);
            System.out.println("Myers Algorithm: D = " + i + " Edit Distance = " + myr.editDistance);

            System.out.println("Wu-Manber Algorithm: D = " + i + " Comparisons = " + mbr.comparisonCount);
            System.out.println("Wu-Manber Algorithm: D = " + i + " Edit Distance = " + mbr.editDistance);

            myers.add((double)i, (double)myr.comparisonCount);
            wuManber.add((double)i, (double)mbr.comparisonCount);
        }

        //Generate Graphs
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(myers);
        dataset.addSeries(wuManber);

        JFreeChart chart = ChartFactory.createXYLineChart("Number of Comparisons VS Number of Deletions on Edit Distance Algorithms ",
                "Number of Deletions", "Number of Comparisons", dataset);

        //generateChart(dataset, chart, "AlgoAssignment2.jpeg");
    }

    private static void generateChart(XYSeriesCollection dataset, JFreeChart chart, String fileName){
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);

        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setOutlinePaint(Color.GRAY);
        plot.setOutlineStroke(new BasicStroke(1.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        int width = 940;    /* Width of the image */
        int height = 780;   /* Height of the image */

        try{
            File lineChart = new File( "D:\\testing\\Desktop\\" + fileName);
            ChartUtilities.saveChartAsJPEG(lineChart, chart, width ,height);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
