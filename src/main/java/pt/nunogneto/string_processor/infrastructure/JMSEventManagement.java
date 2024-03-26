package pt.nunogneto.string_processor.infrastructure;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.nunogneto.string_processor.events.IEventPublisher;
import pt.nunogneto.string_processor.events.IEventSubscriber;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Underlying implementation of the event distribution mechanism.
 * <p>
 * Uses JMS to publish and subscribe to events.
 */
@ApplicationScoped
@Named("NonCachedJMS")
public class JMSEventManagement implements IEventPublisher, IEventSubscriber {

    private final Logger logger = LoggerFactory.getLogger(JMSEventManagement.class);

    /**
     * Use a single JMS Connection for all of our JMS activities.
     * Initializing a new connection on every event would be
     * expensive. Instead, we use {@link JMSContext#createContext(int)}
     * to generate a new context ("session") for each event
     * while re-utilizing the underlying connection.
     * <p>
     * This context will live for the duration of the application since
     * we have to always be ready to receive events. Sending of events
     * create a short-lived context which gets immediately closed {@link #publishEvent(Serializable)}
     */
    private final JMSContext context;

    /*
    If we wanted to correctly inject the queue and maintain
    independence from the JMS provider, we would have to use
    the @Resource annotation, but this would require setting up
    JNDI, which is not a trivial task. For the purposes of this
    exercise, we will use the hardcoded queue name.
     */

    /*
    @Resource("jms/queue/string-processor")
    private Queue queue;
    */

    public JMSEventManagement(ConnectionFactory jmsConnectionFactory) {

        this.context = jmsConnectionFactory.createContext();

    }

    @PreDestroy
    public void cleanUp() {
        this.context.close();
    }

    @Override
    public <T extends Serializable> void publishEvent(T event) {
        // Initialize a small lived context to send the event,
        // As we don't want to partake in synchronization with the consumers
        try (JMSContext context = this.context.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            ObjectMessage eventObject = context.createObjectMessage(event);

            context.createProducer().send(context.createQueue("string-processor"), eventObject);
        }
    }

    @Override
    public <T extends Serializable> void subscribeEventStream(Consumer<T> eventConsumer, Class<T> tClass) {

        // Supporting extra features such as message selectors is easily done with
        // Proxy patterns or even just a simple if else block here, comparing the class
        // Types

        this.context.createConsumer(this.context.createQueue("string-processor")).setMessageListener(message -> {
            try {
                eventConsumer.accept(message.getBody(tClass));
            } catch (JMSException e) {
                logger.error("Error processing message", e);
            }
        });

    }
}
