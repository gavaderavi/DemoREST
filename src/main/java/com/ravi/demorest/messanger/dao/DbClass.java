package com.ravi.demorest.messanger.dao;

import java.util.HashMap;
import java.util.Map;

import com.ravi.demorest.messanger.model.Message;
import com.ravi.demorest.messanger.model.Profile;

public class DbClass {

	private static Map<Integer, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();

	public static Map<Integer, Message> getMessages() {
		return messages;
	}

	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
}
