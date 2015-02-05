/*
 * ARX: Powerful Data Anonymization
 * Copyright (C) 2012 - 2014 Florian Kohlmayer, Fabian Prasser
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package syntheticmedical;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.deidentifier.arx.examples.Example;

import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.AttributeType.Hierarchy.DefaultHierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.DataSubset;
import org.deidentifier.arx.criteria.DPresence;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.metric.Metric;

/**
 * This class implements an example on how to apply the d-presence criterion.
 *
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public class KAnonymity2 extends Example {

    /**
     * Entry point.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        try {
            // Define public dataset
            final Data data = Data.create("dataset/data.csv", '|');

            // Set data attribute types
            data.getDefinition().setAttributeType("id", AttributeType.INSENSITIVE_ATTRIBUTE);
            data.getDefinition().setAttributeType("patient_id", AttributeType.INSENSITIVE_ATTRIBUTE);
            data.getDefinition().setAttributeType("full_name", AttributeType.IDENTIFYING_ATTRIBUTE);
            
            data.getDefinition().setAttributeType("zip", Hierarchy.create("dataset/zip11.csv", '|'));
            data.getDefinition().setAttributeType("yob", Hierarchy.create("dataset/yob.csv", '|'));
            data.getDefinition().setAttributeType("gender", Hierarchy.create("dataset/gender.csv", '|'));
            data.getDefinition().setAttributeType("visit_date", Hierarchy.create("dataset/visit_date.csv", '|'));
            data.getDefinition().setAttributeType("hospital_name", Hierarchy.create("dataset/hospital_name.csv", '|'));
            
            data.getDefinition().setAttributeType("complaint", AttributeType.INSENSITIVE_ATTRIBUTE);
            data.getDefinition().setAttributeType("classification", AttributeType.INSENSITIVE_ATTRIBUTE);

            // Create an instance of the anonymizer
            final ARXAnonymizer anonymizer = new ARXAnonymizer();
            final ARXConfiguration config = ARXConfiguration.create();
            config.addCriterion(new KAnonymity(2));
            config.setMaxOutliers(0d);
            //config.setMaxOutliers(0.001d);
            //config.setMaxOutliers(0.005d);
            //config.setMaxOutliers(0.01d);
            //config.setMaxOutliers(0.02d);
            config.setMetric(Metric.createLossMetric(Metric.AggregateFunction.ARITHMETIC_MEAN));
            
            // Now anonymize
            final ARXResult result = anonymizer.anonymize(data, config);

            // Print input
            //System.out.println(" - Input data:");
            //print(data.getHandle().iterator());

            // Print info
            printResult(result, data);
            
            // Print results
            //System.out.println(" - Transformed data:");
            //print(result.getOutput(false).iterator());
            
            // Write results
            System.out.print(" - Writing data...");
            result.getOutput(false).save("dataset/anonymized_flash_k2.csv", '|');
            System.out.println("Done!");

        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
