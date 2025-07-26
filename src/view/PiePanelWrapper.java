package view;

import java.awt.Color;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PiePanelWrapper extends JPanel {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private double proteinFoods;
    private double wholeGrains;
    private double fruitVeggies;
    private String title;
    private double total; // This will now be the sum of all components
    private DefaultPieDataset<String> dataset;
    private JFreeChart pieChart;
    private ChartPanel chartPanel;

    public PiePanelWrapper(double proteinFoods, double wholeGrains, double fruitVeggies,
                           double total,String title) {
        this.proteinFoods = proteinFoods;
        this.wholeGrains = wholeGrains;
        this.fruitVeggies = fruitVeggies;
        this.total = total;
        this.title = title;
        initializeChart();
    }

    private void initializeChart() {
        // Create dataset
        dataset = new DefaultPieDataset<>();
        updateDataset();

        // Create chart
        pieChart = ChartFactory.createPieChart(
                title,
                dataset,
                true,   // include legend
                true,
                false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();

        // 🎨 Set fixed section colors using static keys
        plot.setSectionPaint("Protein Foods", new Color(102, 178, 255)); // blue
        plot.setSectionPaint("Whole Grains", new Color(255, 204, 102));   // orange
        plot.setSectionPaint("Fruits and Vegetables", new Color(153, 255, 153)); // green

        // 🏷 Format label: {0}=name, {1}=value, {2}=percent
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));

        // Create ChartPanel
        chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(320, 320));

        // Add ChartPanel to this JPanel
        this.add(chartPanel);
    }



    private void updateDataset() {
    	dataset.clear();
        dataset.setValue("Protein Foods", proteinFoods / total * 100);
        dataset.setValue("Whole Grains", wholeGrains / total * 100);
        dataset.setValue("Fruits and Vegetables", fruitVeggies / total * 100);
    }

    public void incrementProteinFoods(int direction) {
        this.proteinFoods += 100 * direction;
        total += 100 * direction;
        updateDataset();
    }

    public void incrementWholeGrains(int direction) {
        this.wholeGrains += 100 * direction;
        total += 100 * direction;
        updateDataset();
    }

    public void incrementFruitVeggies(int direction) {
        this.fruitVeggies += 100 * direction;
        total += 100 * direction;
        updateDataset();
    }

    public void incrementAll(int direction) {
        this.fruitVeggies += 33.34 * direction;
        this.wholeGrains += 33.33 * direction;
        this.proteinFoods += 33.33 * direction;
        total += 100 * direction;
        updateDataset();
    }

        
    


}