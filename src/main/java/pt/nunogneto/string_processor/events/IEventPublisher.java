package pt.nunogneto.string_processor.events;

import java.io.Serializable;

/**
 * Abstracts the event publishing mechanism.
 * <p>
 * Allows us to not depend on the underlying implementation of the event distribution
 * making this a lot easier to test and maintain.
 */
public interface IEventPublisher {

    <T extends Serializable> void publishEvent(T event);

}
