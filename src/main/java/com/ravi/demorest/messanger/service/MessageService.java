package com.ravi.demorest.messanger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.ravi.demorest.messanger.dao.DbClass;
import com.ravi.demorest.messanger.exception.DataNotFoundException;
import com.ravi.demorest.messanger.model.Message;

public class MessageService {

	private static Map<Integer, Message> messages = DbClass.getMessages();

	public MessageService() {
		messages.put(1, new Message(1, "Hello world", "Koushik"));
		messages.put(2, new Message(2, "Restful API", "Ravi"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<>(messages.values());
	}

	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messageForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year)
				messageForYear.add(message);
		}
		return messageForYear;
	}

	public List<Message> getAllMessagesPagination(int start, int size) {
		ArrayList<Message> list = new ArrayList<>(messages.values());
		if (start + size > list.size())
			return new ArrayList<>();
		return list.subList(start, start + size);
	}

	public Message getMessage(int id) {
		Message message=messages.get(id);
		if(message==null)
			throw new DataNotFoundException("Message with id "+id+" not found");
		return message;
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0)
			return null;
		messages.put(message.getId(), message);
		return message;
	}

	public Message deleteMessage(int id) {
		return messages.remove(id);
	}
}
