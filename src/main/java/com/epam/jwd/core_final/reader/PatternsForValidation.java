package com.epam.jwd.core_final.reader;

public interface PatternsForValidation {
    String CREW_MEMBER = "[0-4]\\,([a-zA-Z]+(\\- ?[a-zA-Z]+)?(\\s[a-zA-Z]+)?)\\,[0-4]";
    String SPACESHIP = "[a-zA-Z]+(\\s[a-zA-Z]+)*?\\;[0-9]+\\;\\{([0-9]\\:[0-9]+\\,?)+\\}";
}
