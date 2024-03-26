package pt.nunogneto.string_processor.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import pt.nunogneto.string_processor.services.IStringManipulationExporter;

@ApplicationScoped
public class StdOutStringExporter implements IStringManipulationExporter {



    @Override
    public void exportManipulatedString(String toExport) {
        System.out.println(toExport);
    }
}
