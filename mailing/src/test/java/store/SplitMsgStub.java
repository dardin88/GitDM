/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author depiano.it
 */
public class SplitMsgStub extends Store{

    public SplitMsgStub(String name_project, int from_year, int from_month, int at_year, int at_month) {
        super(name_project, from_year, from_month, at_year, at_month);
    }

    @Override
    public void splitMsgs() throws IOException
    {
        PrintWriter out=new PrintWriter(new FileWriter(this.getDirSplitMsg().toString()+"\\msg0.txt",true));
       
        out.append("From general-return-2682-apmail-lucene-general-archive=lucene.apache.org@lucene.apache.org Tue Oct 05 18:01:42 2010\n" +
        "Return-Path: <general-return-2682-apmail-lucene-general-archive=lucene.apache.org@lucene.apache.org>\n" +
        "Delivered-To: apmail-lucene-general-archive@www.apache.org\n" +
        "Received: (qmail 58477 invoked from network); 5 Oct 2010 18:01:42 -0000\n" +
        "Received: from unknown (HELO mail.apache.org) (140.211.11.3)\n" +
        "  by 140.211.11.9 with SMTP; 5 Oct 2010 18:01:42 -0000\n" +
        "Received: (qmail 50673 invoked by uid 500); 5 Oct 2010 18:01:42 -0000\n" +
        "Delivered-To: apmail-lucene-general-archive@lucene.apache.org\n" +
        "Received: (qmail 50585 invoked by uid 500); 5 Oct 2010 18:01:41 -0000\n" +
        "Mailing-List: contact general-help@lucene.apache.org; run by ezmlm\n" +
        "Precedence: bulk\n" +
        "List-Help: <mailto:general-help@lucene.apache.org>\n" +
        "List-Unsubscribe: <mailto:general-unsubscribe@lucene.apache.org>\n" +
        "List-Post: <mailto:general@lucene.apache.org>\n" +
        "List-Id: <general.lucene.apache.org>\n" +
        "Reply-To: general@lucene.apache.org\n" +
        "Delivered-To: mailing list general@lucene.apache.org\n" +
        "Received: (qmail 50577 invoked by uid 99); 5 Oct 2010 18:01:40 -0000\n" +
        "Received: from athena.apache.org (HELO athena.apache.org) (140.211.11.136)\n" +
        "    by apache.org (qpsmtpd/0.29) with ESMTP; Tue, 05 Oct 2010 18:01:40 +0000\n" +
        "X-ASF-Spam-Status: No, hits=2.2 required=10.0\n" +
        "	tests=FREEMAIL_FROM,HTML_MESSAGE,RCVD_IN_DNSWL_NONE,SPF_PASS,T_TO_NO_BRKTS_FREEMAIL\n" +
        "X-Spam-Check-By: apache.org\n" +
        "Received-SPF: pass (athena.apache.org: domain of wkoscho@gmail.com designates 74.125.82.176 as permitted sender)\n" +
        "Received: from [74.125.82.176] (HELO mail-wy0-f176.google.com) (74.125.82.176)\n" +
        "    by apache.org (qpsmtpd/0.29) with ESMTP; Tue, 05 Oct 2010 18:01:34 +0000\n" +
        "Received: by wye20 with SMTP id 20so4354750wye.35\n" +
        "        for <general@lucene.apache.org>; Tue, 05 Oct 2010 11:01:13 -0700 (PDT)\n" +
        "DKIM-Signature: v=1; a=rsa-sha256; c=relaxed/relaxed;\n" +
        "        d=gmail.com; s=gamma;\n" +
        "        h=domainkey-signature:mime-version:received:received:date:message-id\n" +
        "         :subject:from:to:content-type;\n" +
        "        bh=/3mpZl7yXInnTKahBPnaQDkziUq5WwiA4Rz6K+zj3mg=;\n" +
        "        b=JHRYqxqxGLa6vTS5HQMWd5OuDCsNa3dUhJWh4M74o6UBAF/sXh9UllChDYCdbra3Bi\n" +
        "         y5cgWUWI+Xplc9G0uQo/2LueHYaSrqaWZxudK9wZWvPkhzZkvUS9UHSKrlTLkI0XVFWD\n" +
        "         gLbVF5q3ylLcX0d1rZEpxIAAFeYWwCcbgtSxc=\n" +
        "DomainKey-Signature: a=rsa-sha1; c=nofws;\n" +
        "        d=gmail.com; s=gamma;\n" +
        "        h=mime-version:date:message-id:subject:from:to:content-type;\n" +
        "        b=lQnRwdV4w5NBCRiAM0z+Ots/AV0cAqV9x7X2rTmQg8cZZOOtB8KHGSHz7Yg6wjveBx\n" +
        "         d4byGNG5N/rQj36R7MvnMwkef/OKXz2QD1ZOjKk8bCncA9zDjTPZVCkVKNPCu3tw61wL\n" +
        "         ZjGahYyyzsTlpeOCQDWnSeib3soBifl66Et/w=\n" +
        "MIME-Version: 1.0\n" +
        "Received: by 10.216.10.5 with SMTP id 5mr9518918weu.81.1286301673298; Tue, 05\n" +
        " Oct 2010 11:01:13 -0700 (PDT)\n" +
        "Received: by 10.216.16.211 with HTTP; Tue, 5 Oct 2010 11:01:13 -0700 (PDT)\n" +
        "Date: Tue, 5 Oct 2010 14:01:13 -0400\n" +
        "Message-ID: <AANLkTi=8xD0OCrHZgG=uhCa8RqDo6hiVA4ggy7yZBggQ@mail.gmail.com>\n" +
        "Subject: Weight for all Terms in all Documents\n" +
        "From: William Koscho <wkoscho@gmail.com>\n" +
        "To: general@lucene.apache.org\n" +
        "Content-Type: multipart/alternative; boundary=001636498f314f9b0e0491e272b8\n" +
        "\n" +
        "--001636498f314f9b0e0491e272b8\n" +
        "Content-Type: text/plain; charset=ISO-8859-1\n" +
        "\n" +
        "How do I get the weights for all terms in all documents?\n" +
        "\n" +
        "For a given set of documents, what are the series of API calls I need to\n" +
        "make to get the following type of information:\n" +
        "\n" +
        "doc1, termA_weight, termB_weight, etc..\n" +
        "doc2, termC_weight, termD_weight, etc..\n" +
        "doc3, termE_weight, termZ_weight, etc..\n" +
        "\n" +
        "It seems that I have to start with a Query object, that is typically\n" +
        "provided by an end-user.  However, in my case, I don't have an end user or a\n" +
        "specific query.  Instead I am trying to analyze the documents and interested\n" +
        "in getting the weights of all terms so that I can compute some statistics\n" +
        "about the similarity among documents.\n" +
        "\n" +
        "Thanks in advance,\n" +
        "Bill\n" +
        "\n" +
        "--001636498f314f9b0e0491e272b8--\n" +
        "+\n");
        out.close();
    } 
}
