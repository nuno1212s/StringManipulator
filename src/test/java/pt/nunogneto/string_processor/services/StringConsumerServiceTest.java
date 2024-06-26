package pt.nunogneto.string_processor.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.nunogneto.string_processor.events.IEventSubscriber;
import pt.nunogneto.string_processor.events.integration_events.PublishedStringEvent;
import pt.nunogneto.string_processor.string_exporter.IStringManipulationExporter;

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
        String toManipulate = "com.SQILLS.assignment an.oth8er  Sample.1nput-Str";

        Assertions.assertEquals("com.SQILLS.assignment an.oth8er Sample.1nput-Str com_SQILLS_assignment an_oth8er Sample_1nput_Str COM_SQILLS_ASSIGNMENT AN_OTH8ER SAMPLE_1NPUT_STR", stringConsumerService.processString(toManipulate));
    }

    @ApplicationScoped
    @Alternative()
    @Priority(1)
    public static class StringExporter implements IStringManipulationExporter {
        @Override
        public void exportManipulatedString(String toExport) {
            System.out.println("Exported string: " + toExport);
        }
    }

}
