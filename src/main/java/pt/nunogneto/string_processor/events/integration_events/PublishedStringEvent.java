package pt.nunogneto.string_processor.events.integration_events;

import java.io.Serializable;

public record PublishedStringEvent(String inputStr) implements Serializable { }
