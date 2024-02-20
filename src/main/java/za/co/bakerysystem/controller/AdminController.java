/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.bakerysystem.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.bakerysystem.dao.AdminDAO;
import za.co.bakerysystem.dao.impl.AdminDAOImpl;
import za.co.bakerysystem.model.Admin;
import za.co.bakerysystem.service.AdminService;
import za.co.bakerysystem.service.impl.AdminServiceImpl;

/**
 *
 * @author Train
 */
@Path("admin")
public class AdminController {

    AdminDAO adminDAO = new AdminDAOImpl();
    AdminService adminService = new AdminServiceImpl();

    @GET
    @Path("/{adminID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdminById(@PathParam("adminID") int adminID) {
        Admin admin = adminService.getAdminById(adminID);
        if (admin != null) {
            return Response.ok(admin).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAdmin(Admin admin) {
        boolean created = adminService.createAdmin(admin);
        if (created) {
            return Response.status(Response.Status.CREATED).entity("Signup successful").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Admin admin) {
        Admin authenticatedAdmin = adminService.login(admin.getEmailAddress(), admin.getPassword());
        if (authenticatedAdmin != null) {
            return Response.ok(authenticatedAdmin).entity("Login successful").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
