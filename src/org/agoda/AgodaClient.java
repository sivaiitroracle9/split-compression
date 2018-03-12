package org.agoda;

import org.agoda.archive.zip.ZipCompressionStrategy;

public class AgodaClient {

	public static void main(String[] args) throws IllegalAccessException {
		ArchiveContext ctx = new ArchiveContext();
		ctx.setCompressionStrategy(new ZipCompressionStrategy());
		
//		ctx.compress("C:\\Users\\Siva\\Desktop\\test-forder\\input", 
//		 "C:\\Users\\Siva\\Desktop\\test-forder\\output", 
//		 524288000);
		
		ctx.decompress("C:\\Users\\Siva\\Desktop\\test-forder\\output", 
				"C:\\Users\\Siva\\Desktop\\test-forder\\de-output");

		System.out.println(Runtime.getRuntime().maxMemory());
	}

}
