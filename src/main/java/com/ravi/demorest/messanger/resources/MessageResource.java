
package com.ravi.demorest.messanger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.ravi.demorest.messanger.model.Message;
import com.ravi.demorest.messanger.service.MessageService;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({ MediaType.APPLICATION_XML })
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterbean) {
		if (filterbean.getYear() > 0)
			return messageService.getAllMessagesForYear(filterbean.getYear());
		if (filterbean.getStart() >= 0 && filterbean.getSize() >= 0)
			return messageService.getAllMessagesPagination(filterbean.getStart(), filterbean.getSize());
		return messageService.getAllMessages();
	}

	@GET
	@Path("/{id}")
	public Message getMessage(@PathParam("id") int id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI url = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("id", message.getId())
				.build();
		return url.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI url = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build();
		return url.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Integer.toString(message.getId()))
				.build().toString();
		return url;
	}

	@POST
	@Path("message")
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(String.valueOf(message.getId()))
				.build();
		return Response.created(uri).entity(newMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") int messageId, Message message) {
		System.out.println("In update");
		message.setId(messageId);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/message/{id}")
	public Message deleteMessage(@PathParam("id") int id) {
		return messageService.deleteMessage(id);
	}

	@GET
	@Path("/{id}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
