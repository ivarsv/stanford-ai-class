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

public class Cluster {
	/** 
	 * Points that belong to this cluster at the time.  
	 */
	private List<Point2D> dataSet = new ArrayList<Point2D>();
	
	/** 
	 * Current cluster centroid. 
	 */
	private Point2D centroid;

	public Cluster(Point2D centroid) { 
		this.centroid = centroid;
	}
	
	public Point2D getCentroid() {
		return centroid;
	}

	public void setCentroid(Point2D centroid) {
		this.centroid = centroid;
	}
	
	public List<Point2D> getDataSet() { 
		return dataSet;
	}
	
	public void addPoint(Point2D point) {
		dataSet.add(point); 
	}
	
	public void reset() { 
		getDataSet().clear(); 
	}
	
	/** 
	 * Take the average of X and Y from all points 
	 * in cluster and set as new centroid. 
	 */
	public void updateCentroidPosition() { 
		double averageX = 0, averageY = 0;
		for (Point2D point : getDataSet()) {
			averageX += point.X; 
			averageY += point.Y; 
		}
		int size = getDataSet().size();
		averageX /= size; 
		averageY /= size;
		
		setCentroid(new Point2D(averageX, averageY));
	}
}