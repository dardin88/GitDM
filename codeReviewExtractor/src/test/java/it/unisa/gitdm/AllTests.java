package it.unisa.gitdm;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
    AccountTest.class,
    BranchTest.class,
    ChangeTest.class,
    CommentTest.class,
    CommitTest.class,
    HttpRequestTest.class,
    MessageTest.class,
    ProjectTest.class,
    RangeTest.class})

public class AllTests {

}