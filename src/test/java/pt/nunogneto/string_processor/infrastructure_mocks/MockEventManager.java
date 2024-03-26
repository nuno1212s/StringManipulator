package pt.nunogneto.string_processor.infrastructure_mocks;

import io.quarkus.test.Mock;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import pt.nunogneto.string_processor.events.IEventPublisher;
import pt.nunogneto.string_processor.events.IEventSubscriber;

import java.io.Serializable;
import java.util.function.Consumer;

@ApplicationScoped
@Alternative()
@Priority(1)
public class MockEventManager implements IEventSubscriber, IEventPublisher {

    @Override
    public <T extends Serializable> void publishEvent(T event) {

    }

    @Override
    public <T extends Serializable> void subscribeEventStream(Consumer<T> eventConsumer, Class<T> tClass) {

    }
}
