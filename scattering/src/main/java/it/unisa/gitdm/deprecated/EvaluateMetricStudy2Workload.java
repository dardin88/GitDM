package it.unisa.gitdm.deprecated;

import it.unisa.gitdm.metrics.semanticMetric.Utility;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class EvaluateMetricStudy2Workload {

    public static void main(String args[]) {
        File baseFolder = new File("/home/sesa/Scrivania/scattering");
        List<File> roots = new ArrayList<>();

        for (File file : baseFolder.listFiles()) {
            if ((!file.isHidden()) && (file.isDirectory())) {
                roots.add(file);
            }
        }

        for (File file : roots) {
            File firstExperimentFolder = new File(file.getAbsolutePath() + "/3m/periodsData_1/");

            String csvToPrint = "training;test;accuracy;precision;recall;fmeasure;ROC\n";

            EvaluateMetricStudy1All evaluate = new EvaluateMetricStudy1All();
            double accuracy;
            double precision;
            double recall;
            double ROC;

            for (int i = 0; i < firstExperimentFolder.listFiles().length; i++) {
                //System.out.println(counter + "/" + ( (trainingFile.listFiles().length*trainingFile.listFiles().length)-1)  );

                String trainingPath = firstExperimentFolder.listFiles()[i].getAbsolutePath();
                String trainingName = firstExperimentFolder.listFiles()[i].getName();

                if ((i + 1) != firstExperimentFolder.listFiles().length) {
                    String testPath = firstExperimentFolder.listFiles()[i + 1].getAbsolutePath();
                    String testName = firstExperimentFolder.listFiles()[i + 1].getName();

                    try {
                        DataSource source = new DataSource(trainingPath);
                        Instances trainingSet;
                        trainingSet = source.getDataSet();

                        trainingSet.setClassIndex(trainingSet.numAttributes() - 1);

                        source = new DataSource(testPath);
                        Instances testSet = source.getDataSet();
                        testSet.setClassIndex(testSet.numAttributes() - 1);

                        FilteredClassifier filteredClassifier = new FilteredClassifier();
                        // Bagging filteredClassifier = new Bagging();

                        filteredClassifier.setClassifier(new RandomForest());
                        filteredClassifier.buildClassifier(trainingSet);

                        Evaluation eval = new Evaluation(trainingSet);

                        eval.evaluateModel(filteredClassifier, testSet);
                        System.out.println(eval.toSummaryString("Result\n=====\n", false));

                        ArrayList<Prediction> predictions = eval.predictions();
                        for (Prediction prediction : predictions) {
                            System.out.println("Actual: " + prediction.actual());
                        }

                        if (!Double.isNaN(eval.weightedRecall())) {
                            if (eval.weightedFMeasure() > 0.01) {
                                if (eval.weightedAreaUnderROC() > 0.0) {
                                    csvToPrint += trainingName + ";" + testName + ";";

                                    accuracy = (eval.numTruePositives(0)
                                            + eval.numTruePositives(1)
                                            + eval.numTrueNegatives(1) + eval
                                            .numTrueNegatives(1))
                                            / (eval.numTruePositives(0)
                                            + eval.numTruePositives(1)
                                            + eval.numTrueNegatives(1)
                                            + eval.numTrueNegatives(1)
                                            + eval.numFalsePositives(0)
                                            + eval.numFalsePositives(1)
                                            + eval.numFalseNegatives(1) + eval
                                            .numFalseNegatives(1));

                                    precision = eval.weightedPrecision();

                                    recall = eval.weightedRecall();

                                    double fmeasure = ((2 * (precision * recall)) / (precision + recall));

                                    ROC = eval.weightedAreaUnderROC();

                                    csvToPrint += accuracy + ";" + precision + ";" + recall + ";" + fmeasure + ";" + ROC + "\n";

                                }
                            }
                        }

                    } catch (Exception e) {
                        // it is not possible to compute the prediction... skip
                    }
                }

                Utility.writeFile(csvToPrint, file.getAbsolutePath() + "/3m/results_periodsData_2_workload.csv");
            }
        }
    }

    public File getFileByName(File pRoot, String pName) {

        for (File file : pRoot.listFiles()) {
            if (file.getName().equals(pName)) {
                return file;
            }
        }

        return null;
    }

    public int getNumberOfTrue(File f) {
        int numOfTrue = 0;
        Pattern newLine = Pattern.compile("\n");
        String[] lines = newLine
                .split(Utility.readFile(f.getAbsolutePath()));
        for (String line : lines) {
            if (line.contains("true")) {
                numOfTrue++;
            }
        }
        return numOfTrue;
    }
}
