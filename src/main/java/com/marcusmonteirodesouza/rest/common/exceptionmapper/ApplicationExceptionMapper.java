package com.marcusmonteirodesouza.rest.common.exceptionmapper;

import com.marcusmonteirodesouza.rest.common.exception.AlreadyExistsException;
import com.marcusmonteirodesouza.rest.common.exceptionmapper.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Override
    public Response toResponse(Exception exception) {
        this.logger.log(Level.SEVERE, exception.getMessage(), exception);

        if (exception instanceof IllegalArgumentException) {
            var errorResponse = new ErrorResponse(new String[] {exception.getMessage()});

            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }

        if (exception instanceof AlreadyExistsException) {
            var errorResponse = new ErrorResponse(new String[] {exception.getMessage()});

            return Response.status(Response.Status.CONFLICT).entity(errorResponse).build();
        }

        var errorResponse = new ErrorResponse(new String[] {"Internal server error"});

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .header("content-type", "application/json")
                .entity(errorResponse)
                .build();
    }
}
