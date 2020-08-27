package com.ravi.demorest.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ravi.demorest.messanger.dao.DbClass;
import com.ravi.demorest.messanger.model.Profile;

public class ProfileService {

	Map<String, Profile> profiles = DbClass.getProfiles();

	public ProfileService() {
		profiles.put("ABC", new Profile(1,"ABC", "Rahul", "Jadhav"));
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<>(profiles.values());
	}

	public Profile getProfile(String proName) {
		return profiles.get(proName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty())
			return null;
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile deleteProfile(String proName) {
		return profiles.remove(proName);
	}
}
