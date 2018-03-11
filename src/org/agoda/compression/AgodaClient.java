package org.agoda.compression;

public class AgodaClient {

	public static void main(String[] args) throws IllegalAccessException {
		CompressionContext ctx = new CompressionContext();
		ctx.setCompressionStrategy(new ZipCompressionStrategy());
		
		ctx.compress("C:\\Users\\Siva\\Desktop\\test-forder\\input", 
		 "C:\\Users\\Siva\\Desktop\\test-forder\\output", 
		 1048576);
		
		ctx.decompress("C:\\Users\\Siva\\Desktop\\test-forder\\output", 
				"C:\\Users\\Siva\\Desktop\\test-forder\\de-output");

	}

}
