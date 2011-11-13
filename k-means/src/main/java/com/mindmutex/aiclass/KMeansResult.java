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

import java.util.Collections;
import java.util.List;

/** 
 * Place where to encapsulate the result of the 
 * K-Means clustering algorithm.
 * 
 * Contains the final cluster instances and 
 * number of iterations it took to converge. 
 *  
 * @author ivarsv
 */
public class KMeansResult {
	
	private List<Cluster> clusters; 
	
	private int iterations;
	
	public KMeansResult(List<Cluster> clusters, int iterations) {
		this.clusters = Collections.unmodifiableList(clusters);
		this.iterations = 0; 
	}
	
	public List<Cluster> getClusters() { 
		return clusters; 
	}
	
	public int getIterations() { 
		return iterations; 
	}
}
