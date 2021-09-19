package com.osrmt.www.artifact;
import java.util.ArrayList;

import com.osframework.framework.logging.Debug;
import com.osrmt.www.services.LocalReferenceServices;

public class ProductList {
	
	private ArrayList products = new ArrayList();

	public ProductList() {
		super();
		loadProducts();
	}
	
	private void loadProducts() {
		try {
			products = LocalReferenceServices.getProducts();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	
	

	public ArrayList getProducts() {
		return products;
	}

	public void setProducts(ArrayList products) {
		this.products = products;
	}
	
	

}

