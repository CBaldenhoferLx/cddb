package com.luxoft.cddb.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class FileLinkBean extends DefaultBean {
	
	private String ROOT_DIR = new File(".").getAbsolutePath();
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private AttachmentType type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date ts;
	
	protected abstract String getFolderName();
	
	public InputStream getContent() throws FileNotFoundException {
		final File file = new File(ROOT_DIR + File.pathSeparator + getFolderName() + File.pathSeparator + getName());
		return new FileInputStream(file);
	}
	
	public void setContent(InputStream is, boolean overwrite) throws IOException {
		final File file = new File(ROOT_DIR + File.pathSeparator + getFolderName() + File.pathSeparator + getName());
		if (overwrite || !file.exists()) {
			FileOutputStream fos = new FileOutputStream(file);
			is.transferTo(fos);
		}
	}

}
