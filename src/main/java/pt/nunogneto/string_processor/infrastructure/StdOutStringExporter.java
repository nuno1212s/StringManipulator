package pt.nunogneto.string_processor.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import pt.nunogneto.string_processor.string_exporter.IStringManipulationExporter;

/**
 * Implementation of the {@link IStringManipulationExporter} that exports the manipulated string to the standard output.
 */
@ApplicationScoped
public class StdOutStringExporter implements IStringManipulationExporter {

    @Override
    public void exportManipulatedString(String toExport) {
        System.out.println(toExport);
    }
}
