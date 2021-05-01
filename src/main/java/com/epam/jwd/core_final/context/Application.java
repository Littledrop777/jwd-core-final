package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.context.impl.NassaMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    static void start() throws InvalidStateException {
        final Supplier<ApplicationContext> applicationContextSupplier = NassaContext::getInstance; // todo
        applicationContextSupplier.get().init();
        ApplicationMenu menu = NassaMenu.INSTANCE;

        menu.printAvailableOptions();
    }
}
