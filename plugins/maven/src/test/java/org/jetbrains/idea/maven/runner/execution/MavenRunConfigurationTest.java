package org.jetbrains.idea.maven.runner.execution;

import com.intellij.testFramework.IdeaTestCase;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Element;

import java.util.Arrays;

public class MavenRunConfigurationTest extends IdeaTestCase {
  public void testSaveLoadRunnerParameters() {
    MavenRunConfiguration.MavenSettings s = new MavenRunConfiguration.MavenSettings();
    s.myRunnerParameters.setPomPath("some pom file");
    s.myRunnerParameters.setGoals(Arrays.asList("clean validate"));
    s.myRunnerParameters.setProfiles(Arrays.asList("prof1 prof2"));

    Element xml = XmlSerializer.serialize(s);
    MavenRunConfiguration.MavenSettings loaded
      = XmlSerializer.deserialize(xml, MavenRunConfiguration.MavenSettings.class);
    
    assertEquals(s.myRunnerParameters.getPomPath(), loaded.myRunnerParameters.getPomPath());
    assertEquals(s.myRunnerParameters.getGoals(), loaded.myRunnerParameters.getGoals());
    assertEquals(s.myRunnerParameters.getProfiles(), loaded.myRunnerParameters.getProfiles());
  }
}
