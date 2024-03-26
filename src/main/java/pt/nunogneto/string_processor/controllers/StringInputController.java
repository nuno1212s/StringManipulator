package pt.nunogneto.string_processor.controllers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pt.nunogneto.string_processor.controllers.forms.StringInputForm;
import pt.nunogneto.string_processor.events.IEventPublisher;
import pt.nunogneto.string_processor.events.integration_events.PublishedStringEvent;

@Path("/api/v1")
@Consumes("application/json")
public class StringInputController {

    private final IEventPublisher publisher;

    public StringInputController(IEventPublisher publisher) {
        this.publisher = publisher;
    }

    public IEventPublisher getPublisher() {
        return publisher;
    }

    @POST
    @Path("/strings")
    public Response onStringReception(StringInputForm inputForm) {
        getPublisher().publishEvent(new PublishedStringEvent(inputForm.inputStr()));

        return Response.ok().build();
    }

}
