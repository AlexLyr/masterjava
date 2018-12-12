package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainXml {

    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    public static void main(String[] args) throws IOException, JAXBException {
        new MainXml()
                .getAllUserInProject("topjava")
                .forEach(System.out::println);
    }

    public List<User> getAllUserInProject(String projectName) throws IOException, JAXBException {
        Payload payload =JAXB_PARSER.unmarshal(Resources.getResource("payload.xml").openStream());
        return payload.getUsers().getUser()
                .stream()
                .filter(user -> user.getGroups()
                        .getGroup()
                        .stream()
                        .anyMatch(group->group.getProject().getId().equals(projectName))
                )
                .sorted(Comparator.comparing(User::getFullName))
                .collect(Collectors.toList());

    }
}
