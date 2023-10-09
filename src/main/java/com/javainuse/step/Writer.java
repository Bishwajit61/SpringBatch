package com.javainuse.step;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class Writer implements ItemWriter<String> {

	@Override
	public void write(Chunk<? extends String> chunk) throws Exception {
		System.out.println("WRITING..............");
		// TODO Auto-generated method stub
		for (String msg : chunk) {
			System.out.println("Writing the data " + msg);
		}
		
	}


}