package org.deidentifier.arx.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.io.importdata.CSVConfiguration;
import org.deidentifier.arx.io.importdata.Column;
import org.deidentifier.arx.io.importdata.DataSourceImportAdapter;

/**
 * This class implements an example on how to use the API to import data from
 * a CSV file. It uses more advanced features than {@link #Example2}. Columns
 * are renamed and could be ignored altogether.
 */
public class Example2a extends Example {

    /**
     * Main entry point
     */
    public static void main(final String[] args) throws IOException {

        try {

            // Define input adapter
            CSVConfiguration importConfig = new CSVConfiguration("data/test.csv", ';', true);

            // Define columns (index, name (optional) and datatype)
            importConfig.addColumn(new Column(0, "Alter", DataType.INTEGER));
            importConfig.addColumn(new Column(1, DataType.STRING));
            importConfig.addColumn(new Column(2, DataType.STRING));

            // Create data object
            final Data data = Data.create(importConfig);

            // Define attribute hierarchies
            data.getDefinition()
                .setAttributeType("Alter",
                                  Hierarchy.create("data/test_hierarchy_age.csv",
                                                   ';'));
            data.getDefinition()
                .setAttributeType("gender",
                                  Hierarchy.create("data/test_hierarchy_gender.csv",
                                                   ';'));
            data.getDefinition()
                .setAttributeType("zipcode",
                                  Hierarchy.create("data/test_hierarchy_zipcode.csv",
                                                   ';'));

            // Create an instance of the anonymizer
            final ARXAnonymizer anonymizer = new ARXAnonymizer();

            // Execute the algorithm
            final ARXConfiguration config = ARXConfiguration.create();
            config.addCriterion(new KAnonymity(2));
            config.setMaxOutliers(0d);
            final ARXResult result = anonymizer.anonymize(data, config);

            // Print info
            printResult(result, data);

            // Write results
            System.out.print(" - Writing data...");
            result.getOutput(false).save("data/test_anonymized.csv", ';');
            System.out.println("Done!");

        } catch (final IllegalArgumentException e) {

            throw new RuntimeException(e);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}