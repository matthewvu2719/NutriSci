package view;

import java.awt.Color;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class ChartPanelWrapper extends JPanel {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private double protein;
    private double carbs;
    private double fat;
    private double fibre;
    private double sodium;
    private double cal;
    private double other;
    private double total; 
    private DefaultPieDataset<String> dataset;
    private JFreeChart pieChart;
    private ChartPanel chartPanel;

    public ChartPanelWrapper(double protein, double carbs, double fat,
                           double fibre, double sodium, double cal, double total,double other) {
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fibre = fibre;
        this.sodium = sodium;
        this.cal = cal;
        this.other = other;
        this.total = total; 
        initializeChart();
    }

    private void initializeChart() {
        // Create dataset
        dataset = new DefaultPieDataset<>();
        updateDataset();

        // Create chart
        pieChart = ChartFactory.createPieChart(
                "Macronutrient Breakdown (Total: " + String.format("%.2f", total) + "g) " + String.format("%.2f", cal) + "kcal", // Initial title with calculated total
                dataset,
                true,   // include legend
                true,
                false
        );
        PiePlot plot = (PiePlot) pieChart.getPlot();
        
     // 🎨 Set consistent colors
        plot.setSectionPaint("Protein", new Color(102, 178, 255)); // light blue
        plot.setSectionPaint("Carbs", new Color(255, 204, 102));   // light orange
        plot.setSectionPaint("Fat", new Color(255, 102, 102));     // light red
        plot.setSectionPaint("Fibre", new Color(153, 255, 153));   // light green
        plot.setSectionPaint("Sodium", new Color(204, 153, 255));  // purple
        plot.setSectionPaint("Other", new Color(255, 105, 180));   // pink

        // 🏷 Format: show label, value, and percent
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}g ({2})"));

        // Create ChartPanel
        chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 600));

        // Add ChartPanel to this JPanel
        this.add(chartPanel);
    }

    private double calculateOther() {
        return (total - (protein + carbs + fat + fibre + sodium));
    }

    private void updateDataset() {
        other = calculateOther();

        dataset.clear();
        dataset.setValue("Protein", protein);
        dataset.setValue("Carbs", carbs);
        dataset.setValue("Fat", fat);
        dataset.setValue("Fibre", fibre);
        dataset.setValue("Sodium", sodium);
        dataset.setValue("Other", other);

        if (pieChart != null) {
            pieChart.setTitle("Macronutrient Breakdown (Total: " +
                    String.format("%.2f", total) + "g) " +
                    String.format("%.2f", cal) + "kcal");
        }
    }


    public void incrementValues(double proteinInc, double carbsInc, double fatInc,
                              double fibreInc, double sodiumInc, double calInc,double totalInc) {
        this.protein += proteinInc;
        this.carbs += carbsInc;
        this.fat += fatInc;
        this.fibre += fibreInc;
        this.sodium += sodiumInc;
        this.cal += calInc;
        total +=totalInc;
        updateDataset();
    }

//    public double getProtein() { return protein; }
//    public double getCarbs() { return carbs; }
//    public double getFat() { return fat; }
//    public double getFibre() { return fibre; }
//    public double getSodium() { return sodium; }
//    public double getCal() { return cal; }
}