package edu.neu.huang.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import edu.neu.huang.symbolTable.BSTSimple;

public class TestN {
	/**
	 * in this class, I will set M = 100000, give a sequence of N(start at 100, grows by 20, ends at 1600), to see the relation between the final
	 * depth of BST and N.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BSTSimple<Integer, Integer> bst = new BSTSimple<>();
		Integer[] Ns = new Integer[76];
		for(int i = 0; i < Ns.length; i++) {
			Ns[i] = 100 + i*20;
		}
		int D = 50;//initial depth
		int M = 100000;//total count of operation
		int tries = 20;//experiment repeat times
		//random seed;
		Random random = new Random();
		//initialize
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data2.csv")))){
			bw.write("M, N, D\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int N : Ns) {
			double averageDepth = 0;
			for(int k = 0; k < tries; k++) {					
				while(bst.size() < D) {
					bst.put(random.nextInt(N), random.nextInt(400));
				}
				//then we do a certain times of insertion or deletion (depends on a random value)
				for(int i = 0; i < M; i++) {
					//if dice 0, put a new entry
					if(random.nextInt(2) == 0) {
						bst.put(random.nextInt(N), random.nextInt(400));
					}
					//else, randomly delete a value
					else {
						int deleteKey = random.nextInt(N);
						while(bst.size() > 0 && !bst.contains(deleteKey)) {
							deleteKey = random.nextInt(N);
						}
						bst.delete(deleteKey);
						
					}
				}
				averageDepth+=bst.size();
			}
			//in each combination of N and M, record the result:
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data2.csv"), true))){
				bw.write(M + "," + N + "," + (averageDepth/20) +"\n");
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
	}

}
