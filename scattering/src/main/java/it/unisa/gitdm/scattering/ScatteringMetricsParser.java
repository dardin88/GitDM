package it.unisa.gitdm.scattering;

import it.unisa.gitdm.bean.Scattering;
import it.unisa.gitdm.bean.ScatteringType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ScatteringMetricsParser {

    private final List<Scattering> metrics;

    public ScatteringMetricsParser() {
        this.metrics = new ArrayList<>();
    }

    public void parseFile(File f)
            throws NumberFormatException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                String developerName = st.nextToken();
                int periodId = Integer.parseInt(st.nextToken());
                int changes = Integer.parseInt(st.nextToken());
                int changedFile = Integer.parseInt(st.nextToken());
                double averageValue = Double.parseDouble(st.nextToken());
//                double medianValue = Double.parseDouble(st.nextToken());
//                double stDevValue = Double.parseDouble(st.nextToken());
//                double minMaxValue = Double.parseDouble(st.nextToken());

                this.metrics.add(new Scattering(developerName, periodId, ScatteringType.AVERAGE,
                        changes, changedFile, averageValue));

//                this.metrics.add(new Scattering(developerName, periodId, ScatteringType.MEDIAN,
//                        changes, changedFile, medianValue));
//                
//                this.metrics.add(new Scattering(developerName, periodId, ScatteringType.STDEV,
//                        changes, changedFile, stDevValue));
//                
//                this.metrics.add(new Scattering(developerName, periodId, ScatteringType.MAXMIN,
//                        changes, changedFile, minMaxValue));
            }
        }
    }

    public Scattering getMetrics(String developerName, int periodId,
                                 ScatteringType type) {
        for (Scattering wm : this.metrics) {
            if (wm.getDeveloperName().equals(developerName)) {
                if (wm.getPeriodId() == periodId) {
                    if (wm.getType().equals(type)) {
                        return wm;
                    }
                }
            }
        }
        return null;
    }
}
