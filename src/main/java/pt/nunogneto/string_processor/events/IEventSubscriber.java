package pt.nunogneto.string_processor.events;

import java.io.Serializable;
import java.util.function.Consumer;

public interface IEventSubscriber {

    <T extends Serializable> void subscribeEventStream(Consumer<T> eventConsumer, Class<T> tClass);

}
