package com.bbva.storage;

import com.google.appengine.tools.cloudstorage.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import static com.google.appengine.tools.cloudstorage.ListOptions.Builder;

public class CloudStorageClient {
	
	/**Used below to determine the size of chucks to read in. Should be > 1kb and < 10MB */
	private static final int BUFFER_SIZE = 2 * 1024 * 1024;

	private final GcsService gcsService;
	
	private static CloudStorageClient instance;
	
	private CloudStorageClient() {
		gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
		    .initialRetryDelayMillis(10)
		    .retryMaxAttempts(10)
		    .totalRetryPeriodMillis(15000)
		    .build());
	}
	
	public static CloudStorageClient getInstance() {
		if (instance == null) {
			instance = new CloudStorageClient();
		}
		return instance;
	}
	
	public void uploadFile(String bucket,final String file, String mediaType,
			final InputStream fileContent) throws GeneralSecurityException, IOException {
		
		GcsFileOptions instance = new GcsFileOptions.Builder()
												.mimeType(mediaType)
												.contentDisposition("attachment; filename=" + file)
												.build();

		GcsFilename gcsfileName = new GcsFilename(bucket, file);
		
		GcsOutputChannel outputChannel = gcsService.createOrReplace(gcsfileName, instance);
		OutputStream outStream = Channels.newOutputStream(outputChannel);

		copy(fileContent, outStream);
	    
	}
	
	public InputStream downloadFile(String bucket,final String file) throws IOException, GeneralSecurityException {
		
		GcsFilename fileName = new GcsFilename(bucket, file);

		GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
		return Channels.newInputStream(readChannel);
	}

	public void deleteFile(String bucket,final String file) throws IOException, GeneralSecurityException {

		GcsFilename fileName = new GcsFilename(bucket, file);
		Logger.getLogger("").info("FICHERO A BORRAR DESDE GCS  : " + fileName);
		gcsService.delete(fileName);
	}

	private void copy(InputStream input, OutputStream output) throws IOException {
	    try {
	    	IOUtils.copy(input, output);
	    } finally {
	      input.close();
	      output.close();
	    }
	  }

    public ListResult listObjects(String bucket) throws IOException{
	    return listObjects(bucket, ListOptions.DEFAULT.getPrefix(), ListOptions.DEFAULT.isRecursive());
    }

	public ListResult listObjects(String bucket, String prefix, boolean isRecursive) throws IOException{
	        ListOptions lo = new Builder().setPrefix(prefix).setRecursive(isRecursive).build();
            ListResult listFiles = gcsService.list(bucket,lo);
            return listFiles;

	}
}
