package com.ocyd.appfactory.manager;

import java.util.Comparator;

import com.ocyd.appfactory.pojo.Client;

public class ClientSort implements Comparator<Client> {

	
	public int compare(Client prev, Client now) {
		return (int) (now.getLogindatetime().getTime()-prev.getLogindatetime().getTime());
	}

}
