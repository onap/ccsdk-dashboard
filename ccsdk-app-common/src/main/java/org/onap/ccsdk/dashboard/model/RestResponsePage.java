/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *  ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.ccsdk.dashboard.model;

/**
 * Model for a response with count of items available, count of pages required
 * to display all items, and one page worth of data items.
 * 
 * @param <T>
 *            Model class for item
 */
public class RestResponsePage<T> extends ECTransportModel {

	private int totalItems;
	private int totalPages;
	private T items;

	public RestResponsePage() {
	}

	public RestResponsePage(final int totalItems, final int totalPages, final T items) {
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.items = items;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int items) {
		this.totalItems = items;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int pages) {
		this.totalPages = pages;
	}

	public T getItems() {
		return items;
	}

	public void setItems(final T data) {
		this.items = data;
	}

	@Override
	public String toString() {
		return "RestResponsePage[totalItems=" + totalItems + ", totalPages=" + totalPages + ", items=" + items + "]";
	}

}
