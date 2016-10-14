package it.unisa.gitdm.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class BasicStatistics {

	protected ArrayList<Double> dataSet;
	protected Double minimum = null;
	protected Double maximum = null;
	protected Double average = null;
	protected Double median = null;
	protected Double lowerQuartile = null;
	protected Double upperQuartile = null;

	/**
	 * 
	 */
	public BasicStatistics() {
		super();
		dataSet = new ArrayList<Double>();
	}
	
	public BasicStatistics(ArrayList<Double> dataSet) {
		super();
		this.dataSet = dataSet;
	}

	/**
	 * @return the clusterSizes
	 */
	public List<Double> getDataset() {
		return dataSet;
	}
	
	

	
	public void calculateStatistics() throws Exception {
		
		if(dataSet.size()<1) {
			return;
		}
		
		Iterator<Double> dataIter = dataSet.iterator();
		double total = 0.0;
		maximum = Double.MIN_VALUE;
		minimum = Double.MAX_VALUE; 
		
		while (dataIter.hasNext()) {
			Double dataItem = dataIter.next();

			if(maximum<dataItem) {
				maximum = dataItem;
			}
			
			if(minimum>dataItem) {
				minimum = dataItem;
			}
			
			total += dataItem;
		}
		average = total / dataSet.size();
		
		double[] quartiles;
		
		quartiles = Quartiles(dataSet);
		lowerQuartile = quartiles[0];
		median = quartiles[1];
		upperQuartile = quartiles[2];
		
		
		
		
	}
	
	

	/**
	 * @return the average
	 * @throws Exception 
	 */
	public double getAverage() throws Exception {
		if(average==null) calculateStatistics();
		return average;
	}

	/**
	 * @return the median
	 * @throws Exception 
	 */
	public double getMedian() throws Exception {
		if(median==null) calculateStatistics();
		return median;
	}

	/**
	 * @return the lowerQuartile
	 * @throws Exception 
	 */
	public double getLowerQuartile() throws Exception {
		if(lowerQuartile==null) calculateStatistics();
		return lowerQuartile;
	}

	/**
	 * @return the upperQuartile
	 * @throws Exception 
	 */
	public double getUpperQuartile() throws Exception {
		if(upperQuartile==null) calculateStatistics();
		return upperQuartile;
	}

	/**
	 * @return the minimum
	 * @throws Exception 
	 */
	public double getMinimum() throws Exception {
		if(minimum==null) calculateStatistics();
		return minimum;
	}

	/**
	 * @return the maximum
	 * @throws Exception 
	 */
	public double getMaximum() throws Exception {
		if(maximum==null) calculateStatistics();
		return maximum;
	}
	
	public static double[] Quartiles(ArrayList<Double> values)
	{
	    if (values.size() < 3)
	    	return new double[] {Double.NaN,Double.NaN,Double.NaN};
	    
	    
	    double median;
	    int splitIndex;
	    List<Double> lowerHalf;
	    List<Double> upperHalf;
	    
	    Collections.sort(values);
		 
	    if (values.size() % 2 == 1) {
	    	splitIndex = (values.size()+1)/2-1;
	    	median = values.get(splitIndex);
	    	lowerHalf = values.subList(0, splitIndex);
	    	upperHalf = values.subList(splitIndex, values.size()-1);
		} else {
			double lower = values.get(values.size()/2-1);
			double upper = values.get(values.size()/2);
	 
			median = (lower + upper) / 2.0;
			
			lowerHalf = values.subList(0, values.size()/2-1);
			upperHalf = values.subList(values.size()/2, values.size()-1);
	    }
	    
	 
	    
	    return new double[] {Median(lowerHalf), median, Median(upperHalf)};
	}
	 
	public static ArrayList<Double> GetValuesGreaterThan(ArrayList<Double> values, double limit, boolean orEqualTo)
	{
	    ArrayList<Double> modValues = new ArrayList<Double>();
	 
	    for (double value : values)
	        if (value > limit || (value == limit && orEqualTo))
	            modValues.add(value);
	 
	    return modValues;
	}
	 
	public static ArrayList<Double> GetValuesLessThan(ArrayList<Double> values, double limit, boolean orEqualTo)
	{
	    ArrayList<Double> modValues = new ArrayList<Double>();
	 
	    for (double value : values)
	        if (value < limit || (value == limit && orEqualTo))
	            modValues.add(value);
	 
	    return modValues;
	}
	
	public static double Median(List<Double> values)
	{
	    Collections.sort(values);
	 
	    if (values.size() % 2 == 1)
	    	return values.get((values.size()+1)/2-1);
	    else
	    {
			double lower = values.get(values.size()/2-1);
			double upper = values.get(values.size()/2);
	 
			return (lower + upper) / 2.0;
	    }	
	}
	
	public static double square(int number) {
		return number*number;
	}
	

	
}