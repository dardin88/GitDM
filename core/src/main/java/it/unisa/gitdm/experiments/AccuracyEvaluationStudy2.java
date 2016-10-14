/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.gitdm.experiments;

import it.unisa.gitdm.metrics.semanticMetric.Utility;
import java.io.File;

/**
 *
 * @author fabiopalomba
 */
public class AccuracyEvaluationStudy2 {

    public void evaluate() {

        File folder = new File("/Users/fabiopalomba/Desktop/toEvaluate/");
        String output = "project;TP;TN;FP;FN;accuracy;precision;recall;f-measure\n";

        //String[] lines = Utility.readFile("/Users/fabiopalomba/Desktop/ant_finale.csv").split("\n");
        //String[] lines = Utility.readFile("/Users/fabiopalomba/Desktop/amq_finale.csv").split("\n");
        //String[] lines = Utility.readFile("/Users/fabiopalomba/Desktop/aries_finale.csv").split("\n");
        //String[] lines = Utility.readFile("/Users/fabiopalomba/Desktop/camel_finale.csv").split("\n");
        for (File toEvaluate : folder.listFiles()) {

            if (!toEvaluate.isHidden()) {
                //if(toEvaluate.getAbsolutePath().equals("/Users/fabiopalomba/Desktop/toEvaluate/whirr_finale.csv")) {

                double truePositive = 0;
                double falsePositive = 0;
                double trueNegative = 0;
                double falseNegative = 0;

                System.out.println(toEvaluate.getAbsolutePath());

                output += toEvaluate.getName() + ";";

                String[] lines = Utility.readFile(toEvaluate.getAbsolutePath()).split("\n");

                for (int k = 1; k < lines.length; k++) {
                    String[] fields = lines[k].split(",");

//                        int i = 0;
//                        for (String field : fields) {
//                            System.out.println(i + ": " + field);
//                            i++;
//                        }
                    boolean isBuggy = Boolean.parseBoolean(fields[20]);
                    //String best = fields[26];
                    String predicted = fields[27];

                    if (isBuggy == false) {
                        int predictedModelPrediction = Integer.parseInt(fields[getValueByPredictedMethod(predicted, fields)]);

                        if (predictedModelPrediction == 0) {
                            trueNegative++;
                        } else {
                            falsePositive++;
                        }

                    } else {

                        int predictedModelPrediction = Integer.parseInt(fields[getValueByPredictedMethod(predicted, fields)]);

                        if (predictedModelPrediction == 0) {
                            truePositive++;
                        } else {
                            falseNegative++;
                        }
                    }
                }

                double accuracy = ((truePositive + trueNegative) / (trueNegative + truePositive + falseNegative + falsePositive));
                double precision = truePositive / (truePositive + falsePositive);
                double recall = truePositive / (truePositive + falseNegative);
                double fMeasure = 2 * ((precision * recall) / (precision + recall));

                //String output="project;TP;TN;FP;FN;accuracy;precision;recall;f-measure\n";
                output += truePositive + ";" + trueNegative + ";" + falsePositive + ";" + falseNegative + ";"
                        + accuracy + ";" + precision + ";" + recall + ";" + fMeasure + "\n";

//                    System.out.println("TP: " + truePositive + "\n"
//                            + "TN: " + trueNegative + "\n"
//                            + "FP: " + falsePositive + "\n"
//                            + "FN: " + falseNegative);
//    
//                    System.out.println("Precision: " + precision + "\n"
//                            + "Recall: " + recall + "\n"
//                            + "F-Measure: " + fMeasure + "\n"
//                            + "Accuracy: " + accuracy);
                Utility.writeFile(output, "/Users/fabiopalomba/Desktop/toEvaluate/report.csv");

                //       }
            }
        }
    }

    private int getValueByPredictedMethod(String pPredictionMethod, String[] fields) {

        if (pPredictionMethod.equals("HASSAN")) {
            return 21;
        } else if (pPredictionMethod.equals("SCATTERING")) {
            return 22;
        } else if (pPredictionMethod.equals("POSNETT")) {
            return 23;
        } else {
            return 24;
        }
    }
}
