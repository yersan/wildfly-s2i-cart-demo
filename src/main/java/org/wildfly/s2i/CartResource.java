package org.wildfly.s2i;

import java.math.BigDecimal;
import java.util.Enumeration;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:yborgess@redhat.com">Yeray Borges</a>
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CartResource {

    @POST
    @Path("{key}/{value}")
    public Response addToCart(@Context HttpServletRequest request, @PathParam("key") String key, @PathParam("value") String value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("cart")
    public JsonObject getCart(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder cartData = Json.createArrayBuilder();

        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            cartData.add(Json.createObjectBuilder()
                    .add("key", key)
                    .add("value", (String) session.getAttribute(key))
            );
        }
        objectBuilder.add("cart", cartData);
        objectBuilder.add("pod", getPodName());
        objectBuilder.add("sessionId", session.getId());

        return objectBuilder.build();
    }

    private String getPodName() {
        String hostname = System.getenv("HOSTNAME");
        return hostname == null ? "localhost" : hostname;
    }

}
