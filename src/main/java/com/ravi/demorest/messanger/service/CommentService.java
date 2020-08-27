package com.ravi.demorest.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ravi.demorest.messanger.dao.DbClass;
import com.ravi.demorest.messanger.model.Comment;
import com.ravi.demorest.messanger.model.ErrorMessage;
import com.ravi.demorest.messanger.model.Message;

public class CommentService {

	private Map<Integer, Message> messages = DbClass.getMessages();

	public List<Comment> getAllComments(int messageId) {
		Map<Integer, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}

	public Comment getComment(int messageId, int commentId) {
		ErrorMessage errorMessage = new ErrorMessage("Not found", 404, "http://javabrains.koushik.org");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();

		Message message = messages.get(messageId);
		if (message == null) {
			throw new WebApplicationException(response);
		}
		Map<Integer, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if (comment == null) {
			throw new NotFoundException(response);
		}
		return comment;
	}

	public Comment addComment(int messageId, Comment comment) {
		Map<Integer, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment updateComment(int messageId, Comment comment) {
		Map<Integer, Comment> comments = messages.get(messageId).getComments();
		if (comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment removeComment(int messageId, int commentId) {
		Map<Integer, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}

}
