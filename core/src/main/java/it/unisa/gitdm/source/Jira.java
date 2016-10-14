package it.unisa.gitdm.source;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.browser.Page;
import com.ui4j.api.dom.Element;
import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Jira {

    public static List<Bug> extractBug(String address, String projectName, boolean isSVN) throws MalformedURLException {

        String issueType = "BUG";
        String status = "Resolved";
        String resolution = "Fixed";

        List<Bug> issues = new ArrayList<>();
        URL url = null;

        String regexSingleBugFix = Pattern.quote("<item>")
                + Pattern.compile("(.*?)") + Pattern.quote("</item>");
        Pattern patternSingleBugFix = Pattern.compile(regexSingleBugFix,
                Pattern.DOTALL);
        String regexIssueKey = Pattern.quote("<key id=\"")
                + Pattern.compile("(.*?)") + Pattern.quote("</key>");
        Pattern patternIssueKey = Pattern
                .compile(regexIssueKey, Pattern.DOTALL);
        String regexSummary = Pattern.quote("<summary>")
                + Pattern.compile("(.*?)") + Pattern.quote("</summary>");
        Pattern patternSummary = Pattern.compile(regexSummary, Pattern.DOTALL);
        String regexStatus = Pattern.quote("<status id=\"")
                + Pattern.compile("(.*?)") + Pattern.quote("</status>");
        Pattern patternStatus = Pattern.compile(regexStatus, Pattern.DOTALL);
        String regexResolution = Pattern.quote("<resolution id=\"")
                + Pattern.compile("(.*?)") + Pattern.quote("</resolution>");
        Pattern patternResolution = Pattern.compile(regexResolution,
                Pattern.DOTALL);
        String regexCreated = Pattern.quote("<created>")
                + Pattern.compile("(.*?)") + Pattern.quote("</created>");
        Pattern patternCreated = Pattern.compile(regexCreated, Pattern.DOTALL);
        String regexUpdated = Pattern.quote("<updated>")
                + Pattern.compile("(.*?)") + Pattern.quote("</updated>");
        Pattern patternUpdated = Pattern.compile(regexUpdated, Pattern.DOTALL);
        String regexResolved = Pattern.quote("<resolved>")
                + Pattern.compile("(.*?)") + Pattern.quote("</resolved>");
        Pattern patternResolved = Pattern
                .compile(regexResolved, Pattern.DOTALL);
        String regexLink = Pattern.quote("<link>") + Pattern.compile("(.*?)")
                + Pattern.quote("</link>");
        Pattern patternLink = Pattern.compile(regexLink, Pattern.DOTALL);
        String regexAttachments = Pattern.quote("<attachments>")
                + Pattern.compile("(.*?)") + Pattern.quote("</attachments>");
        Pattern patternAttachments = Pattern.compile(regexAttachments,
                Pattern.DOTALL);
        String regexType = Pattern.quote("<type id=\"")
                + Pattern.compile("(.*?)") + Pattern.quote("</type>");
        Pattern patternType = Pattern.compile(regexType, Pattern.DOTALL);
        String regexDescription = Pattern.quote("<description>")
                + Pattern.compile("(.*?)") + Pattern.quote("</description>");
        Pattern patternDescription = Pattern.compile(regexDescription,
                Pattern.DOTALL);
        String regexComments = Pattern.quote("<comments>")
                + Pattern.compile("(.*?)") + Pattern.quote("</comments>");
        Pattern patternComments = Pattern
                .compile(regexComments, Pattern.DOTALL);
        String regexProduct = Pattern.quote("<project id=\"")
                + Pattern.compile(".*?") + Pattern.quote("\" key=\"")
                + Pattern.compile(".*?") + Pattern.quote("\">")
                + Pattern.compile("(.*?)") + Pattern.quote("</project>");
        Pattern patternProduct = Pattern.compile(regexProduct, Pattern.DOTALL);
        String regexAssignee = Pattern.quote("<assignee username=\"")
                + Pattern.compile("(.*?)") + Pattern.quote("</assignee>");
        Pattern patternAssignee = Pattern
                .compile(regexAssignee, Pattern.DOTALL);

        Pattern split = Pattern.compile(">");

        // jiraAddress should be like
        // https://issues.jboss.org/
        long createdDate = -1;

        while (true) {

            String option = "sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+"
                    + projectName
                    + "+AND+issueType+%3D+"
                    + issueType
                    + "+AND+status+%3D+"
                    + status
                    + "+AND+Resolution+%3D+" + resolution;

            if (createdDate != -1) {
                createdDate += 60000;
                option += "+AND+created+%3E+" + createdDate;
            }

            option += "+ORDER+BY+created+ASC&tempMax=200";

            try {
                url = new URL(address + option);
            } catch (MalformedURLException ex) {
                Logger.getLogger(Jira.class.getName()).log(Level.SEVERE, null, ex);
            }

            String pageContent = Http.readFromURL(url);

            Matcher matcher = patternSingleBugFix.matcher(pageContent);

            int resultsAdded = 0;

            while (matcher.find()) {
                Bug issueToAdd = new Bug();
                String bugFixContent = matcher.group(1);
                Matcher tmpMatcher = patternIssueKey.matcher(bugFixContent);

                if (tmpMatcher.find()) {
                    String content = tmpMatcher.group(1);
                    String tmpId = split.split(content)[1];
                    issueToAdd.setID(tmpId.split("-")[1]);
                }

                tmpMatcher = patternSummary.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String summaryContent = tmpMatcher.group(1);
                    issueToAdd.setSubject(summaryContent.replaceAll("\n", "")
                            .replaceAll(";", " "));
                }

                tmpMatcher = patternStatus.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String statusReaded = tmpMatcher.group(1);
                    statusReaded = split.split(statusReaded)[1];
                    issueToAdd.setStatus(statusReaded);
                }

                tmpMatcher = patternResolution.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String resolutionReaded = tmpMatcher.group(1);
                    resolutionReaded = split.split(resolutionReaded)[1];
                    issueToAdd.setResolution(resolutionReaded);
                }

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy,H:m");

                tmpMatcher = patternCreated.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String toAnalyze = tmpMatcher.group(1);
                    Pattern comma = Pattern.compile(",");
                    Pattern space = Pattern.compile(" ");
                    toAnalyze = comma.split(toAnalyze)[1];
                    String[] tokens = space.split(toAnalyze);

                    toAnalyze = tokens[1] + "/" + tokens[2] + "/" + tokens[3]
                            + "," + tokens[4].substring(0, 5);
                    String date = cleanDate(toAnalyze);
                    try {
                        issueToAdd.setReportedTime(df.parse(date).getTime());
                        createdDate = df.parse(date).getTime();
                    } catch (ParseException ex) {
                        Logger.getLogger(Jira.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                tmpMatcher = patternUpdated.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String toAnalyze = tmpMatcher.group(1);
                    Pattern comma = Pattern.compile(",");
                    Pattern space = Pattern.compile(" ");
                    toAnalyze = comma.split(toAnalyze)[1];
                    String[] tokens = space.split(toAnalyze);
                    toAnalyze = tokens[1] + "/" + tokens[2] + "/" + tokens[3]
                            + "," + tokens[4];
                    String date = cleanDate(toAnalyze);
                    try {
                        issueToAdd.setLastChangedTime(df.parse(date).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(Jira.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                tmpMatcher = patternAttachments.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    System.out.println("Start RegEx");
                    String toAnalyze = tmpMatcher.group(1);
                    System.out.println("Stringa da analizzare: " + toAnalyze);
                    String regexAttachmentId = Pattern.quote("id=\"")
                            + Pattern.compile("(.*?)") + Pattern.quote("\"");
                    String regexAttachmentName = Pattern.quote("name=\"")
                            + Pattern.compile("(.*?)") + Pattern.quote("\"");
                    String regexAttachmentDate = Pattern.quote("created=\"")
                            + Pattern.compile("(.*?)") + Pattern.quote("\"");
                    Pattern patternAttachmentId = Pattern.compile(regexAttachmentId, Pattern.DOTALL);
                    Matcher idMatcher = patternAttachmentId.matcher(toAnalyze);
                    Pattern patternAttachmentName = Pattern.compile(regexAttachmentName, Pattern.DOTALL);
                    Matcher nameMatcher = patternAttachmentName.matcher(toAnalyze);
                    Pattern patternAttachmentDate = Pattern.compile(regexAttachmentDate, Pattern.DOTALL);
                    Matcher dateMatcher = patternAttachmentDate.matcher(toAnalyze);
                    List<String> patchList = new ArrayList<>();
                    //issueToAdd.setPatchURL(patchList);
                    String patchURL = null;
                    while (idMatcher.find()) {
                        String attachmentID = idMatcher.group(1);
                        nameMatcher.find();
                        String attachmentName = nameMatcher.group(1);
                        if (attachmentName.endsWith(".patch")) {
                            patchURL = address + "secure/attachment/";
                            patchURL += attachmentID + "/" + attachmentName;
                            System.out.println("ID: " + attachmentID + " Name: " + attachmentName);
                            break;
                            //dateMatcher.find();
                            //String attachmentDate = dateMatcher.group(1);
                            //String name = idMatcher.group(2);
                            //String date = idMatcher.group(3);
                        }
                        //patchURL += attachmentID;
                        //System.out.println("ID: "+attachmentID+" Name: "+attachmentName);
                        //patchList.add(patchURL+"/");

                    }
                    //issueToAdd.setPatchURL(patchList);
                    //setInvolvedClass(issueToAdd, projectName);
                    setInvolvedCommit(issueToAdd, address, projectName, patchURL, isSVN);
                }

                tmpMatcher = patternDescription.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String content1 = tmpMatcher.group(1);
                    issueToAdd.setBody(content1.replaceAll(";", "."));
                }

                tmpMatcher = patternProduct.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String product = tmpMatcher.group(1);
                    issueToAdd.setProduct(product.split(" - ")[0]);
                    if (product.split(" - ").length == 2) {
                        issueToAdd.setComponent(product.split(" - ")[1]);
                    } else {
                        issueToAdd.setComponent("");
                    }
                }

                tmpMatcher = patternAssignee.matcher(bugFixContent);
                if (tmpMatcher.find()) {
                    String assignee = tmpMatcher.group(1);
                    issueToAdd.setAssignedTo(assignee);
                }

                if (!issues.contains(issueToAdd)) {
                    issues.add(issueToAdd);
                    resultsAdded++;
                }
            }
            if (resultsAdded == 0) {
                return issues;
            }
        }
    }

    private static void setInvolvedCommit(Bug issueToAdd, String address, String projectName, String patchURL, boolean isSVN) throws MalformedURLException {

        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Development/scattering/" + projectName.toLowerCase() + "/gitRepository.data");
        GitRepository repository = process.getGitRepository();

        String url = address + "browse/" + projectName + "-" + issueToAdd.getID() + "?jql=project+%3D+"
                + projectName
                + "+AND+issuetype+%3D+Bug"
                + "+AND+status+%3D+Resolved+AND+resolution+%3D+Fixed"
                + "&devStatusDetailDialog=repository";

        BrowserEngine browser = BrowserFactory.getWebKit();
        Page page = browser.navigate(url);
        page.wait(10000);
        try {
            List<Element> list = page.getDocument().queryAll(".changesetid");
            System.out.println("Numero di commit in list: " + list.size());
            if (list.isEmpty()) {
                String revision = retrieveRevision(isSVN, patchURL);
                if (revision != null) {
                    System.out.println("Commit non c'è, e il numero di revision è: " + revision);
                    Commit fix = repository.getCommitByID(revision, isSVN);
                    if (fix != null) {
                        System.out.println("Fix: " + fix);
                        issueToAdd.setFix(fix);
                    }
                }
            } else {
                for (Element e : list) {
                    System.out.println(projectName + "-" + issueToAdd.getID() + ": " + e.getText());
                    Commit fix = repository.getCommitByID(e.getText(), isSVN);
                    if (fix != null) {
                        System.out.println("Fix: " + fix);
                        issueToAdd.setFix(fix);
                        break;
                    }
                }
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(Jira.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String retrieveRevision(boolean isSVN, String patchURL) throws MalformedURLException {
        if (patchURL == null)
            return null;
        URL url = new URL(patchURL);
        if (isSVN) {
            String regexInvolvedClass = Pattern.quote("--- ")
                    //+ Pattern.compile("(.*?)") + Pattern.compile("(\t|\b|\n)");
                    + Pattern.compile("(.*?)") + Pattern.compile("\\)");
            Pattern patternInvolvedClass = Pattern.compile(regexInvolvedClass, Pattern.DOTALL);
            String pageContent = Http.readFromURL(url);
            Matcher matcher = patternInvolvedClass.matcher(pageContent);
            if (matcher.find()) {
                try {
                    String toAnalyze[] = matcher.group(1).split("\\(");
                    return toAnalyze[1].substring(9);
                } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ex) {
                    Logger.getLogger(Jira.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        } else {
            String regexInvolvedRev = Pattern.quote("From ")
                    + Pattern.compile("(.*?)") + Pattern.quote(" ");
            Pattern patternInvolvedRev = Pattern.compile(regexInvolvedRev, Pattern.DOTALL);
            String pageContent = Http.readFromURL(url);
            Matcher matcher = patternInvolvedRev.matcher(pageContent);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        }
    }


    private static String cleanDate(String date) {
        Pattern space = Pattern.compile(" ");
        String[] tokens = space.split(date);
        date = tokens[0];
        return date.replace("Jan", "01").replace("Feb", "02")
                .replace("Mar", "03").replace("Apr", "04").replace("May", "05")
                .replace("Jun", "06").replace("Jul", "07").replace("Aug", "08")
                .replace("Sep", "09").replace("Oct", "10").replace("Nov", "11")
                .replace("Dec", "12");
    }
}
