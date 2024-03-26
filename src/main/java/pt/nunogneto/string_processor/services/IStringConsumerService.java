package pt.nunogneto.string_processor.services;

import pt.nunogneto.string_processor.events.integration_events.PublishedStringEvent;

public interface IStringConsumerService {

    String processString(PublishedStringEvent event);

}
