package pt.nunogneto.string_processor.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.nunogneto.string_processor.events.IEventSubscriber;
import pt.nunogneto.string_processor.events.integration_events.PublishedStringEvent;

import java.io.Serializable;
import java.util.function.Consumer;

@QuarkusTest
public class StringConsumerServiceTest {

    @Inject
    IStringConsumerService stringConsumerService;

    @InjectMock
    private IEventSubscriber subscriber;

    @InjectMock
    private IStringManipulationExporter exporter;

    @Test
    public void testStringProcessing() {
        PublishedStringEvent event = new PublishedStringEvent("com.SQILLS.assignment an.oth8er  Sample.1nput-Str");

        Assertions.assertEquals("com.SQILLS.assignment an.oth8er Sample.1nput-Str com_SQILLS_assignment an_oth8er Sample_1nput_Str COM_SQILLS_ASSIGNMENT AN_OTH8ER SAMPLE_1NPUT_STR", stringConsumerService.processString(event));
    }

    @ApplicationScoped
    @Mock
    public static class MockEventSubscriber implements IEventSubscriber {

        @Override
        public <T extends Serializable> void subscribeEventStream(Consumer<T> eventConsumer, Class<T> tClass) {
            System.out.println("Subscribed to event stream");
        }
    }

    @ApplicationScoped
    @Mock
    public static class StringExporter implements IStringManipulationExporter {
        @Override
        public void exportManipulatedString(String toExport) {
            System.out.println("Exported string: " + toExport);
        }
    }

}
