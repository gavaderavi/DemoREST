package com.ravi.demorest;

import java.net.URI;
import java.util.List;

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

import org.glassfish.jersey.server.Uri;

@Path("employee")
//@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class EmpResource {

	EmpRepository empRepo = new EmpRepository();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Employee> getEmployee() {
		System.out.println("getEmployee Called..");

		return empRepo.getEmp();
	}

	@GET
	@Path("emp/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Employee getEmployee(@PathParam("id") int id) {
		System.out.println("getEmp Called..");

		return empRepo.getEmp(id);
	}

	@POST
	@Path("emp")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createEmp(Employee e1, @Context UriInfo uriInfo) {
		System.out.println(e1);
		empRepo.create(e1);
		String Id=String.valueOf(e1.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(Id).build();
		return Response.created(uri).build();
	}

	@PUT
	@Path("empupdate/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Employee updateEmp(@PathParam("id") int id, Employee e1) {
		System.out.println(e1);
		if (id == 0) {
			empRepo.create(e1);
		} else {
			empRepo.update(id, e1);
		}
		return e1;
	}

	@DELETE
	@Path("empdelete/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void deleteEmp(@PathParam("id") int id) {
		empRepo.delete(id);
	}
}
