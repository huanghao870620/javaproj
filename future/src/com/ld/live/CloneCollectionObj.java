package com.ld.live;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

/**
 * ��¡���϶���
 * 
 * @author huang.hao
 * @version 1.0
 * @created 27-����-2016 15:13:09
 */
public class CloneCollectionObj {

	private Collection<CustomerSessionBinding> collection = null;

	public CloneCollectionObj(Collection<CustomerSessionBinding> collection) {
		this.collection = collection;
	}
	
	

	public Collection<CustomerSessionBinding> getCollection() {
		return collection;
	}

	/**
	 * ���
	 * @return
	 * @throws Exception
	 */
	public Object deepClone() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}

}