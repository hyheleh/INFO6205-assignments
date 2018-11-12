package edu.neu.huang.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.neu.huang.symbolTable.BSTSimple;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BSTSimple<Integer, Integer> bst = new BSTSimple<>();
		Integer[] Ms = new Integer[1000]; //this is the total times that insertions and deletions are done
		int D = 100;//initial depth
//		Integer[] Ms = {200, 1000, 5000, 25000, 125000, 625000};//max key value(this value has limited the max tree depth, I think it should be carefully chosen)
		Integer[] Ns = {400}; //finally I think N ~ sqrt(M) is better
		//each experiment repeated 20 times
		int tries = 20;
		//random seed;
		Random random = new Random();
		//initialize
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data1.csv")))){
			bw.write("M, N, D\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < Ns.length; i++) {
			Ns[i] = 100*(i+1);
		}
		//In this experiment, I try to test against various value of N(times) and M(maxValue)
		for(int N : Ns) {
			//I first initialize a BST in which depth is precisely N and max key value is M
			for(int M : Ms) {
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
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data1.csv"), true))){
					bw.write(M + "," + N + "," + (averageDepth/20) +"\n");
				} catch (Exception e) {
					e.printStackTrace();				
				}
			}
		}
		
		
		
		
		System.out.println(bst.size());//see the depth
	}

}
