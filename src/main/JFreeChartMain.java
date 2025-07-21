package main;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * This class is only used for prototyping the JFreeChart functionality.
 * We won't be using this class in our project as we 
 * will implement different designs and usage for the charts! 
 */


public class JFreeChartMain extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JFreeChartMain() {
    	//Pie chart was created with the help of AI
		
        // Create dataset
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Protein (10g)", 10);
        dataset.setValue("Carbs (20g)", 20);
        dataset.setValue("Fat (5g)", 5);
        dataset.setValue("Others (15g)", 15);

        // Create chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Macronutrient Breakdown (Total: 50g)",
                dataset,
                true,   // include legend
                true,
                false
        );

        //Wrap chart in ChartPanel (a Swing component)
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        //Add ChartPanel to JFrame
        setContentPane(chartPanel);

        setTitle("Meal BreakDown");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // Ensure GUI updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFreeChartMain example = new JFreeChartMain();
            example.setVisible(true);
        });
    }
}
