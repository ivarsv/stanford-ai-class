/**
 * Copyright (C) 2011 by mindmutex.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mindmutex.aiclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** 
 * K-Means algorithm. 
 * 
 * See run example in {@link #main(String[])}. 
 * 
 * @author ivarsv
 */
public class KMeans {
	/** 
	 * Algorithm used to calculate the distance between centroid and other points in data set. 
	 */
	private Distance distanceAlgorithm;
	
	/** 
	 * Number of clusters to use. 
	 */
	private int numberOfClusters;
	
	/** 
	 * The threshold to reach to assume there is no change in any of the clusters.
	 */
	private double convergeThreshold = 0.2d;
	
	public KMeans(int numberOfClusters, Distance distanceAlgorithm) {
		this.numberOfClusters = numberOfClusters;
		this.distanceAlgorithm = distanceAlgorithm;
	}
	
	public int getNumberOfClusters() { 
		return numberOfClusters; 
	}
	
	public void setConvergeThreshold(double convergeThreshold) {
		this.convergeThreshold = convergeThreshold; 
	}
	
	/** 
	 * Run K-Means algorithm. 
	 * 
	 * Create {@link #numberOfClusters} clusters and pick random centroid 
	 * for each cluster from the data set provided as argument. 
	 * 
	 * Iterate until the centroid movement is small - smaller than {@link #convergeThreshold}.
	 * Recalculate centroid position after each iteration. 
	 * 
	 * @param dataSet data set
	 */
	public KMeansResult run(List<Point2D> dataSet) {
		// initialise the clusters and choose random centroids 
		List<Cluster> clusters = new ArrayList<Cluster>(numberOfClusters);
		for (Point2D centroid : extractRandomCentroids(dataSet)) {
			clusters.add(new Cluster(centroid));
		}
		int iterations = 0;
		
		boolean hasMovement = true; 
		while (hasMovement) {
			iterations++; 
			
			for (Point2D point : dataSet) {
				int finalCluster = 0;
				double distancePoint = Integer.MAX_VALUE;
				for (int i = 0; i < clusters.size(); i++) {
					Point2D centroid = clusters.get(i).getCentroid();
					double distanceCentroid = distanceAlgorithm.calculate(centroid, point);
					if (distanceCentroid < distancePoint) {
						distancePoint = distanceCentroid; 
						finalCluster = i; 
					}
				}
				Cluster cluster = clusters.get(finalCluster);
				cluster.addPoint(point);
			}
			
			// check how many clusters have significant movement.
			// this tests whether the algorithm has converged 
			int peacefulClusters = 0; 
			for (Cluster cluster : clusters) {
				Point2D previousCentroid = cluster.getCentroid(); 
				cluster.updateCentroidPosition();
				
				if (distanceAlgorithm.calculate(
					previousCentroid, cluster.getCentroid()) <= convergeThreshold) { 
					peacefulClusters++;  
				} 
			}
			if (peacefulClusters == clusters.size()) {
				hasMovement = false; 
			} else {
				// if we are not finished yet we have to clear 
				// each cluster data set
				for (Cluster cluster : clusters) { 
					cluster.reset(); 
				}
			}
		}
		return new KMeansResult(clusters, iterations); 
	}
	
	/** 
	 * Given the data set extract {@link #numberOfClusters} random centroids. 
	 * 
	 * @param dataSet data set provided
	 * @return list of centroids
	 */
	private List<Point2D> extractRandomCentroids(List<Point2D> dataSet) {
		List<Point2D> centroids = new ArrayList<Point2D>(numberOfClusters);
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < numberOfClusters; i++) {
			Point2D point = dataSet.get(random.nextInt(dataSet.size()));
			while (centroids.contains(point)) { 
				point = dataSet.get(random.nextInt(dataSet.size()));
			}
			centroids.add(point);
		}
		return centroids;
	}
	
	/** 
	 * Create an array of randomly generated data points 
	 * that can be used with {@link KMeans}.
	 * 
	 * @param samples number of samples
	 * @return list of random data points
	 */
	public static List<Point2D> randomize(int samples) {
		List<Point2D> dataSet = new ArrayList<Point2D>(samples); 
		
		Random random = new Random(System.currentTimeMillis()); 
		for (int i = 0; i < samples; i++) {
			dataSet.add(new Point2D(random.nextDouble() * random.nextInt(0100), 
				random.nextDouble() * random.nextInt(100)));
		}
		return dataSet;
	}
	
	public static void main(String[] args) {
		KMeans clustering = new KMeans(2, new EuclideanDistance());
		KMeansResult result = clustering.run(KMeans.randomize(100));
		
		System.out.println("Converge in " + result.getIterations() + " iterations");
		
		for (Cluster cluster : result.getClusters()) {
			System.out.println("Cluster centroid: " + cluster.getCentroid());
			System.out.println("----------------");
			for (Point2D point : cluster.getDataSet()) {
				System.out.println(point.getX() + ", " + point.getY());
			}
		}
	}
}
