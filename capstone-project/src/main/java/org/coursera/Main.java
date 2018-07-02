package org.coursera;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.random.EmpiricalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;


public class Main {

    public static void main(String[] args) throws IOException {

        // Create a new graph and load the Bitcoin OTC data...
        BitcoinTrustGraph bitcoinTrustGraph = new BitcoinTrustGraph();

        try (
                InputStream stream = Main.class.getResourceAsStream("/soc-sign-bitcoinotc.csv");
                Reader reader = new InputStreamReader(stream);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                int sourceId = Integer.parseInt(csvRecord.get(0));
                int targetId = Integer.parseInt(csvRecord.get(1));
                int rating = Integer.parseInt(csvRecord.get(2));
                double timestamp = Double.parseDouble(csvRecord.get(3));

                bitcoinTrustGraph.addEdge(new Edge(sourceId, targetId, rating, timestamp));

            }

        }

        // Compute the descriptive statistics...
        Set<Integer> nodes = bitcoinTrustGraph.getNodes();
        double[] scores = new double[nodes.size()];
        int i = 0;
        for (int node : nodes) {
            scores[i] = bitcoinTrustGraph.getTrustworthiness(node);
            i++;
        }
        DescriptiveStatistics statistics = new DescriptiveStatistics(scores);
        System.out.println(statistics.toString());

        // Create a plot of the empirical density function for trustworthiness
        EmpiricalDistribution empiricalDistribution = new EmpiricalDistribution();
        empiricalDistribution.load(scores);

    }

}
