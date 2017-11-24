package com.attlas.scraper.demon;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;

import org.apache.log4j.Logger;

import com.attlas.scraper.demon.ApiResponse;


/**
 */
@Path("/docs/vacancies")
public class VacanciesResource {

  private static final Logger logger = Logger.getLogger(VacanciesResource.class);

  /**
   */
  @POST
  @Path("/{companies}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getVacanciesByCompaniesAndDates(@Context UriInfo uriInfo, @PathParam("companies") String companies) {
    logger.info(uriInfo.getRequestUri());
    //
    int exitVal = -1;
    try {
      Process p = Runtime.getRuntime().exec("php ./scripts/vacancies/"+companies+"/post.php " + uriInfo.getPath());
      exitVal = p.waitFor();
      BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = "";
      while ((line = reader.readLine())!= null) {
        logger.info(line);
      }
    } catch (Exception e) {
      logger.error("", e);
    }
    return Response.ok().entity(ApiResponse.build()).build();
  }

  /**
   */
  @GET
  @Path("/{companies}/{dates}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getVacanciesByCompaniesAndDates(@Context UriInfo uriInfo, @PathParam("companies") String companies, @PathParam("dates") String dates) {
    logger.info(uriInfo.getRequestUri());
    //
    List<Object> objList = new ArrayList<>();
    objList.add("Random String");
    objList.add(121); //int
    objList.add(1.22); //double
    objList.add(false); //boolean
    return Response.status(Response.Status.CREATED).entity(ApiResponse.build(objList)).build();
    //
    /*/
    return Response.status(Response.Status.OK).entity(new GenericEntity<List<Object>>(objList){}).type(MediaType.APPLICATION_JSON).build();
    /*/
    /*/
    List<Object> myList = new ArrayList<>();
    return Response.ok().entity(new GenericEntity<List<Object>>(myList){}).build();
    /*/
    //
    //return new ApiResponse();
    //return Response.status(201).entity("text").build();
    /*/
    try {
      return Response.status(Response.Status.CREATED).entity(new ObjectMapper().writeValueAsString(new ApiResponse())).build();
    } catch (JsonProcessingException e) {
      logger.error("Exception", e);  
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    /*/
    //return Response.status(Response.Status.CREATED).entity(new ApiResponse()).build();
  }
}
