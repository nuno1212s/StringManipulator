package pt.nunogneto.string_processor.services;

import pt.nunogneto.string_processor.events.integration_events.PublishedStringEvent;

/**
 * Service that performs all the required operations on the string.
 */
public interface IStringConsumerService {

    String processString(PublishedStringEvent event);

}
