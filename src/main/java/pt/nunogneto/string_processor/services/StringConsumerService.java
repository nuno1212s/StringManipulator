package pt.nunogneto.string_processor.services;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import pt.nunogneto.string_processor.events.IEventSubscriber;
import pt.nunogneto.string_processor.events.integration_events.PublishedStringEvent;
import pt.nunogneto.string_processor.string_exporter.IStringManipulationExporter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A startup singleton since we don't want to depend
 * On other scopes injecting this dependency. We want this
 * to start as soon as the application starts and subscribe to the events.
 */
@Startup
@Singleton
public class StringConsumerService implements IStringConsumerService {

    private final IEventSubscriber subscriber;

    private final IStringManipulationExporter stringExporter;

    public StringConsumerService(IEventSubscriber subscriber, IStringManipulationExporter stringExporter) {
        this.subscriber = subscriber;
        this.stringExporter = stringExporter;
    }

    @PostConstruct
    public void initializeSubscription() {
        this.subscriber.subscribeEventStream(this::handleReceivedEvent, PublishedStringEvent.class);
    }

    private String[] splitReceivedString(String toProcess) {
        return toProcess.split(" +");
    }

    private List<String> sanitizeAlphaNumeric(String[] splitString) {
        return Arrays.stream(splitString)
                .map(str_part -> str_part.chars()
                        .map(character -> Character.isLetterOrDigit(character) ? character : '_')
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString())
                .toList();
    }

    private void handleReceivedEvent(PublishedStringEvent event) {
        stringExporter.exportManipulatedString(processString(event.inputStr()));
    }

    @Override
    public String processString(String inputStr) {
        String[] splitString = splitReceivedString(inputStr);

        List<String> cleanAlphaNumeric = sanitizeAlphaNumeric(splitString);

        String finalVersion = cleanAlphaNumeric.stream().map(String::toUpperCase).collect(Collectors.joining(" "));

        return String.join(" ", Arrays.asList(String.join(" ", splitString), String.join(" ", cleanAlphaNumeric), finalVersion));
    }
}
