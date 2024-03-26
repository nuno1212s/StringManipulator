package pt.nunogneto.string_processor.events;

import java.io.Serializable;

public interface IEventPublisher {

    <T extends Serializable> void publishEvent(T event);

}
