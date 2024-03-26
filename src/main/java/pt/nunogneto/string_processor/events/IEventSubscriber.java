package pt.nunogneto.string_processor.events;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Similarly to {@link IEventPublisher}, this interface abstracts the event subscription mechanism.
 * <p>
 * Allows us to not depend on the underlying implementation of the event distribution
 * making this a lot easier to test and maintain.
 */
public interface IEventSubscriber {

    <T extends Serializable> void subscribeEventStream(Consumer<T> eventConsumer, Class<T> tClass);

}
